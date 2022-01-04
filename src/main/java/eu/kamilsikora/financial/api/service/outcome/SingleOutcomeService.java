package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.FilteringParametersDto;
import eu.kamilsikora.financial.api.dto.PageDto;
import eu.kamilsikora.financial.api.dto.outcome.CategorySummaryDto;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeSummaryDto;
import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.RegularSingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.OutcomeMapper;
import eu.kamilsikora.financial.api.repository.outcome.ContinuityOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.ContinuitySingleOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.RegularSingleOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.SingleOutcomeRepository;
import eu.kamilsikora.financial.api.repository.outcome.SingleOutcomeSpecification;
import eu.kamilsikora.financial.api.service.UserHelperService;
import eu.kamilsikora.financial.api.validation.ExceptionThrowingValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SingleOutcomeService implements OverviewProvider {
    private final RegularSingleOutcomeRepository regularSingleOutcomeRepository;
    private final SingleOutcomeRepository singleOutcomeRepository;
    private final ContinuitySingleOutcomeRepository continuitySingleOutcomeRepository;
    private final ContinuityOutcomeRepository continuityOutcomeRepository;
    private final UserHelperService userHelperService;
    private final CategoryService categoryService;
    private final OutcomeMapper outcomeMapper;
    private final ExceptionThrowingValidator validator;

    private static final int MAX_DATES = 15;

    @Transactional
    public void addNewOutcome(final UserPrincipal userPrincipal, final NewOutcomeDto newOutcomeDto) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final Category category = categoryService.resolveAndIncrementUsage(user, newOutcomeDto.getCategoryId());
        final RegularSingleOutcome outcome = outcomeMapper.mapToEntity(newOutcomeDto, user, category);
        validator.validate(outcome, newOutcomeDto);
        regularSingleOutcomeRepository.save(outcome);
    }

    @Override
    public boolean canHandle(final OverviewType overviewType) {
        return (overviewType == OverviewType.REGULAR_SINGLE_OUTCOME ||
                overviewType == OverviewType.CONTINUITY_SINGLE_OUTCOME ||
                overviewType == OverviewType.SHOPPING_LIST_SINGLE_OUTCOME ||
                overviewType == OverviewType.ALL);
    }

    @Override
    public Page<OutcomeOverviewDto> getOverview(final User user, final PageDto page, final FilteringParametersDto filteringParameters) {
        final Specification<SingleOutcome> specification =
                new SingleOutcomeSpecification<>(filteringParameters).buildOnParameters();
        final Pageable pageable = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by("date").descending().and(Sort.by("value").descending()));
        Page<SingleOutcome> outcomesPage = singleOutcomeRepository.findAll(specification, pageable);
        return outcomesPage.map(outcomeMapper::mapToOverviewDto);
    }

    @Transactional(readOnly = true)
    public OutcomeSummaryDto provideSummary(final User user, final FilteringParametersDto parameters) {
        final Specification<SingleOutcome> specification =
                new SingleOutcomeSpecification<>(parameters).buildOnParameters();
        final List<SingleOutcome> outcomes = singleOutcomeRepository.findAll(specification, Sort.by("date").ascending());
        final Map<Category, List<SingleOutcome>> categoryToOutcomes = getAsCategoryToOutcomes(outcomes);
        final List<LocalDate> dates = getCorrectListOfDates(parameters.getStartDate(), parameters.getEndDate());

        final List<CategorySummaryDto> categorySummaries = new ArrayList<>();
        categoryToOutcomes.keySet()
                .forEach(category ->
                    categorySummaries.add(aggregateValuesByDates(category, categoryToOutcomes.get(category), dates)));

        final String datePattern = "dd.MM.yyyy";
        return new OutcomeSummaryDto(
                parameters.getStartDate().format(DateTimeFormatter.ofPattern(datePattern)),
                parameters.getEndDate().format(DateTimeFormatter.ofPattern(datePattern)),
                dates, categorySummaries);
    }

    @Transactional(readOnly = true)
    public Page<OutcomeOverviewDto> outcomesByContinuityOutcome(final UserPrincipal userPrincipal,
                                                                final Long continuityOutcomeId,
                                                                final Integer pageNumber,
                                                                final Integer pageSize) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        final ContinuityOutcome continuityOutcome = continuityOutcomeRepository
                .findByUserAndId(user, continuityOutcomeId).orElseThrow(() ->
                        new ObjectDoesNotExistException("Continuity outcome does not exist!"));
        final Specification<ContinuitySingleOutcome> spec = SingleOutcomeSpecification.outcomesByContinuityOutcome(continuityOutcome);
        final Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("date").descending());
        Page<ContinuitySingleOutcome> page = continuitySingleOutcomeRepository.findAll(spec, pageable);
        return page.map(outcomeMapper::mapToOverviewDto);
    }

    private List<LocalDate> getCorrectListOfDates(final LocalDate start, final LocalDate end) {
        int daysToCheck = 1;
        List<LocalDate> dates;
        do {
            dates = end.datesUntil(start, Period.ofDays(-daysToCheck)).collect(Collectors.toList());
            Collections.reverse(dates);
            daysToCheck++;
        } while(dates.size() > MAX_DATES);
        return dates;
    }

    private Map<Category, List<SingleOutcome>> getAsCategoryToOutcomes(final List<SingleOutcome> outcomes) {
        final Set<Category> categories = new HashSet<>();
        outcomes.forEach(out -> categories.add(out.getCategory()));
        Map<Category, List<SingleOutcome>> map = new HashMap<>();
        categories.forEach(category -> {
            map.put(category, getOutcomesWithCategory(outcomes, category));
        });
        return map;
    }

    private List<SingleOutcome> getOutcomesWithCategory(final List<SingleOutcome> outcomes, final Category category) {
        return outcomes.stream()
                .filter(out -> out.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    private CategorySummaryDto aggregateValuesByDates(final Category category, final List<SingleOutcome> outcomes, final List<LocalDate> dates) {
        final List<Double> values = new ArrayList<>();
        final List<Integer> numberOfOrders = new ArrayList<>();
        for(final LocalDate date: dates) {
            final List<SingleOutcome> outcomesToBeAggregatedByThisDate = outcomes.stream()
                    .filter(out -> out.getDate().isBefore(date))
                    .collect(Collectors.toList());
            values.add(aggregateValue(outcomesToBeAggregatedByThisDate));
            numberOfOrders.add(outcomesToBeAggregatedByThisDate.size());
            outcomes.removeAll(outcomesToBeAggregatedByThisDate);
        }
        return new CategorySummaryDto(category.getName(), values, numberOfOrders);
    }

    private Double aggregateValue(final List<SingleOutcome> outcomes) {
        return outcomes.stream()
                .mapToDouble(SingleOutcome::getValue)
                .sum();
    }
}
