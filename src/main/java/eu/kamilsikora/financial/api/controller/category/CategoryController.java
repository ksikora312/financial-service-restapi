package eu.kamilsikora.financial.api.controller.category;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoriesDto;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoryDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/outcome/category")
@Api(tags = {"Category controller"})
public interface CategoryController {
    @PostMapping("/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    void addCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("category") String category);

    @PutMapping("/{id}/{name}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDto updateCategory(@AuthenticationPrincipal UserPrincipal userPrincipal,
                               @PathVariable("id") Long id, @PathVariable("name") String name);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    CategoriesDto getAllCategories(@AuthenticationPrincipal UserPrincipal userPrincipal);
}
