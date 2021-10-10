package eu.kamilsikora.financial.api.dto.outcome.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoriesDto {
    private List<CategoryDto> categories;
}
