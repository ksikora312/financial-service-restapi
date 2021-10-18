package eu.kamilsikora.financial.api.controller.category;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoriesDto;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoryDto;
import eu.kamilsikora.financial.api.service.outcome.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Override
    public void addCategory(final UserPrincipal userPrincipal, final String category) {
        categoryService.addNewCategory(userPrincipal, category);
    }

    @Override
    public CategoryDto updateCategory(final UserPrincipal userPrincipal, final Long id, String name) {
        return categoryService.updateCategory(userPrincipal, id, name);
    }

    @Override
    public CategoriesDto getAllCategories(final UserPrincipal userPrincipal) {
        return categoryService.getAllCategories(userPrincipal);
    }
}
