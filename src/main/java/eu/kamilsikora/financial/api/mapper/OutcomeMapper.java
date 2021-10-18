package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.dto.outcome.UpdateOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.ContinuityOutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.UpdateContinuityOutcomeDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.entity.expenses.RegularSingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper(componentModel = "spring", imports = {OutcomeType.class, LocalDate.class, Date.class, ChronoUnit.class})
public abstract class OutcomeMapper {

    @AfterMapping
    protected void handleDate(@MappingTarget RegularSingleOutcome regularSingleOutcome) {
        if (regularSingleOutcome.getDate() == null) {
            regularSingleOutcome.setDate(Date.valueOf(LocalDate.now()));
        }
    }

    @AfterMapping
    protected void handleDate(@MappingTarget ContinuityOutcome continuityOutcome) {
        if (continuityOutcome.getId() == null) {
            final LocalDateTime now = LocalDateTime.now();
            continuityOutcome.setAddedDate(now);
            continuityOutcome.setLastUsage(now);
        }
        final LocalDateTime nextUsage = continuityOutcome.getLastUsage().plus(continuityOutcome.getTimeIntervalInDays(), ChronoUnit.DAYS);
        continuityOutcome.setNextUsage(nextUsage);
        continuityOutcome.setActive(true);
    }


    @Mapping(target = "outcomeType", expression = "java(OutcomeType.REGULAR_OUTCOME)")
    @Mapping(target = "expenses", source = "user.expenses")
    @Mapping(source = "category", target = "category")
    @Mapping(target = "name", source = "newOutcomeDto.name")
    public abstract RegularSingleOutcome mapToEntity(NewOutcomeDto newOutcomeDto, User user, Category category);

    @Mapping(target = "outcomeType", expression = "java(singleOutcome.getOutcomeType().toString())")
    @Mapping(target = "date", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "category", expression = "java(singleOutcome.getCategory().getName())")
    public abstract OutcomeDetailsDto mapToDto(SingleOutcome singleOutcome);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "id", ignore = true)
    public abstract ContinuityOutcome mapToEntity(NewContinuityOutcomeDto newContinuityOutcomeDto, User user, Category category);

    @Mapping(target = "expenses", source = "expenses")
    @Mapping(target = "source", source = "continuityOutcome")
    @Mapping(target = "date", expression = "java(Date.valueOf(LocalDate.now()))")
    @Mapping(target = "outcomeType", expression = "java(OutcomeType.CONTINUOUS_OUTCOME)")
    public abstract ContinuitySingleOutcome continuitySingleOutcome(ContinuityOutcome continuityOutcome, Expenses expenses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "name", source = "updateOutcomeDto.name")
    public abstract void mapIntoSingleOutcome(@MappingTarget SingleOutcome singleOutcome, UpdateOutcomeDto updateOutcomeDto, Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "continuityOutcome.id", ignore = true)
    @Mapping(target = "continuityOutcome.category", source = "category")
    @Mapping(target = "continuityOutcome.user", ignore = true)
    @Mapping(target = "continuityOutcome.nextUsage", expression = "java(continuityOutcome.getLastUsage().plus(continuityOutcome.getTimeIntervalInDays(), ChronoUnit.DAYS))")
    public abstract void mapIntoContinuityOutcome(@MappingTarget ContinuityOutcome continuityOutcome, UpdateContinuityOutcomeDto update, Category category);

    public abstract OutcomeOverviewDto mapToOverviewDto(ContinuityOutcome continuityOutcome);

    @Mapping(target = "outcomeType", expression = "java(continuitySingleOutcome.getOutcomeType().toString())")
    @Mapping(target = "date", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "category", expression = "java(continuitySingleOutcome.getCategory().getName())")
    public abstract OutcomeDetailsDto mapToDto(ContinuitySingleOutcome continuitySingleOutcome);
    public abstract List<OutcomeDetailsDto> mapToDto(List<ContinuitySingleOutcome> continuitySingleOutcomes);

    @Mapping(target = "addedDate", dateFormat = "dd.MM.yyyy HH:mm")
    @Mapping(target = "lastUsage", dateFormat = "dd.MM.yyyy HH:mm")
    @Mapping(target = "nextUsage", dateFormat = "dd.MM.yyyy HH:mm")
    public abstract ContinuityOutcomeDetailsDto mapToDetailsDto(ContinuityOutcome continuityOutcome);
}
