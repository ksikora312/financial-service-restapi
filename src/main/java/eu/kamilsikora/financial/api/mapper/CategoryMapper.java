package eu.kamilsikora.financial.api.mapper;

import eu.kamilsikora.financial.api.dto.outcome.category.CategoryDto;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto mapToDto(Category category);

    List<CategoryDto> mapToDto(List<Category> categories);
}
