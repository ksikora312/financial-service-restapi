package eu.kamilsikora.financial.api.service.outcome;

import eu.kamilsikora.financial.api.entity.User;
import eu.kamilsikora.financial.api.entity.expenses.Category;
import eu.kamilsikora.financial.api.errorhandling.ObjectDoesNotExistException;
import eu.kamilsikora.financial.api.repository.outcome.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private static final String UNCATEGORIZED_CATEGORY = "Uncategorized";

    public Category resolveAndIncrementUsage(final User user, final Long categoryId) {
        Category categoryToIncrement;
        if(categoryId == null) {
            categoryToIncrement = user.getCategories().stream()
                    .filter(cat -> cat.getName().equalsIgnoreCase(UNCATEGORIZED_CATEGORY))
                    .findFirst().orElseGet(() -> createAndPersistUncategorized(user));
        }
        else {
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
