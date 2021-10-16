package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.dto.outcome.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeDetailsDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.ContinuityOutcome;
import eu.kamilsikora.financial.api.entity.expenses.ContinuitySingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.Expenses;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.entity.expenses.RegularSingleOutcome;
import eu.kamilsikora.financial.api.entity.expenses.SingleOutcome;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring", imports = {OutcomeType.class, LocalDate.class, Date.class})
public abstract class OutcomeMapper {

    @AfterMapping
    protected void handleDate(@MappingTarget RegularSingleOutcome regularSingleOutcome) {
        if (regularSingleOutcome.getDate() == null) {
            regularSingleOutcome.setDate(Date.valueOf(LocalDate.now()));
        }
    }

    @AfterMapping
    protected void handleDate(@MappingTarget ContinuityOutcome continuityOutcome) {
        final LocalDateTime now = LocalDateTime.now();
        continuityOutcome.setAddedDate(now);
        continuityOutcome.setLastUsage(now);
        final LocalDateTime nextUsage = now.plus(continuityOutcome.getTimeIntervalInDays(), ChronoUnit.DAYS);
        continuityOutcome.setNextUsage(nextUsage);
    }

    @Mapping(target = "outcomeType", expression = "java(OutcomeType.REGULAR_OUTCOME)")
    @Mapping(target = "expenses", source = "user.expenses")
    @Mapping(source = "category", target = "category")
    public abstract RegularSingleOutcome mapToEntity(NewOutcomeDto newOutcomeDto, User user, Category category);

    @Mapping(target = "outcomeType", expression = "java(singleOutcome.getOutcomeType().toString())")
    @Mapping(target = "date", expression = "java(singleOutcome.getDate().toString())")
    @Mapping(target = "category", expression = "java(singleOutcome.getCategory().getName())")
    public abstract OutcomeDetailsDto mapToDto(SingleOutcome singleOutcome);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "id", ignore = true)
    public abstract ContinuityOutcome mapToEntity(NewContinuityOutcomeDto newContinuityOutcomeDto, User user, Category category);

    @Mapping(target = "item", source = "continuityOutcome.description")
    @Mapping(target = "expenses", source = "expenses")
    @Mapping(target = "source", source = "continuityOutcome")
    @Mapping(target = "date", expression = "java(Date.valueOf(LocalDate.now()))")
    @Mapping(target = "outcomeType", expression = "java(OutcomeType.CONTINUOUS_OUTCOME)")
    public abstract ContinuitySingleOutcome continuitySingleOutcome(ContinuityOutcome continuityOutcome, Expenses expenses);
}
