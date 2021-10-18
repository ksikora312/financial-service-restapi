package eu.kamilsikora.financial.api.controller.category;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoriesDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/outcome/")
@Api(tags = {"Category controller"})
public interface CategoryController {
    @PostMapping("/category/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    void addCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("category") String category);

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    CategoriesDto getAllCategories(@AuthenticationPrincipal UserPrincipal userPrincipal);
}
