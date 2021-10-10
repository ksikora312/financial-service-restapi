package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoriesDto;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoryDto;
import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.errorhandling.ObjectAlreadyExistsException;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.mapper.CategoryMapper;
import eu.kamilsikora.financial.api.repository.outcome.CategoryRepository;
import eu.kamilsikora.financial.api.service.UserHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserHelperService userHelperService;
    private final CategoryMapper categoryMapper;

    private static final String UNCATEGORIZED_CATEGORY = "Uncategorized";

    @Transactional
    public void addNewCategory(final UserPrincipal userPrincipal, final String categoryName) {
        final User user = userHelperService.getActiveUser(userPrincipal);
        String normalizedCategoryName = normalizeCategoryName(categoryName);
        categoryRepository.findByUserAndName(user, normalizedCategoryName).ifPresent(this::handleAlreadyExistingCategory);
        final Category category = buildCategory(user, normalizedCategoryName);
        user.getCategories().add(category);
        categoryRepository.save(category);
    }

    private String normalizeCategoryName(final String category) {
        final String lowerCaseCategoryName = category.trim().toLowerCase();
        return lowerCaseCategoryName.substring(0, 1).toUpperCase() + lowerCaseCategoryName.substring(1);
    }

    private void handleAlreadyExistingCategory(final Category category) {
        throw new ObjectAlreadyExistsException("Category " + category.getName() + " already exists!");
    }

    private Category buildCategory(final User user, final String categoryName) {
        final Category category = new Category();
        category.setUser(user);
        category.setName(categoryName);
        category.setUsages(0);
        return category;
    }

    @Transactional(readOnly = true)
    public CategoriesDto getAllCategories(final UserPrincipal userPrincipal) {
        final User user = userHelperService.getActiveUser(userPrincipal);

        List<Category> categoriesSortedByUsages = getSortedListOfCategories(user);
        List<CategoryDto> categoryDtos = categoryMapper.mapToDto(categoriesSortedByUsages);
        CategoriesDto categoriesDto = new CategoriesDto();
        categoriesDto.setCategories(categoryDtos);
        return categoriesDto;
    }

    private List<Category> getSortedListOfCategories(final User user) {
        return user.getCategories().stream()
                .sorted(Comparator.comparing(Category::getUsages).reversed())
                .collect(Collectors.toList());
    }

    Category resolveAndIncrementUsage(final User user, final Long categoryId) {
        Category categoryToIncrement;
        if (categoryId == null) {
            categoryToIncrement = user.getCategories().stream()
                    .filter(cat -> cat.getName().equalsIgnoreCase(UNCATEGORIZED_CATEGORY))
                    .findFirst().orElseGet(() -> createAndPersistUncategorized(user));
        } else {
            categoryToIncrement = user.getCategories().stream()
                    .filter(category -> category.getId().equals(categoryId))
                    .findFirst().orElseThrow(() -> new ObjectDoesNotExistException("Category does not exist!"));
        }
        categoryToIncrement.incrementUsages();
        return categoryToIncrement;
    }

    private Category createAndPersistUncategorized(final User user) {
        final Category uncategorized = new Category();
        uncategorized.setName(UNCATEGORIZED_CATEGORY);
        uncategorized.setUsages(0);
        uncategorized.setUser(user);
        return categoryRepository.save(uncategorized);
    }
}
