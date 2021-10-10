package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.entity.expenses.OutcomeType;
import eu.kamilsikora.financial.api.entity.expenses.RegularSingleOutcome;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.sql.Date;
import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = OutcomeType.class)
public abstract class OutcomeMapper {

    @AfterMapping
    protected void handleDate(@MappingTarget RegularSingleOutcome regularSingleOutcome) {
        if (regularSingleOutcome.getDate() == null) {
            regularSingleOutcome.setDate(Date.valueOf(LocalDate.now()));
        }
    }

    @Mapping(target = "outcomeType", expression = "java(OutcomeType.REGULAR_OUTCOME)")
    @Mapping(target = "expenses", source = "user.expenses")
    @Mapping(source = "category", target = "category")
    public abstract RegularSingleOutcome mapToEntity(NewOutcomeDto newOutcomeDto, User user, Category category);

}
