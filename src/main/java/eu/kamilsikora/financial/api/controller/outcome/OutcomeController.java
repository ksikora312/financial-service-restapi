package eu.kamilsikora.financial.api.controller.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeSummaryDto;
import eu.kamilsikora.financial.api.dto.outcome.category.CategoriesDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/outcome/")
@Api(tags = {"Outcome controller"})
public interface OutcomeController {

    @PostMapping("/regular")
    @ResponseStatus(HttpStatus.CREATED)
    void addRegularOutcome(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewOutcomeDto newOutcomeDto);

    @PostMapping("/continuity")
    @ResponseStatus(HttpStatus.CREATED)
    void addContinuityOutcome(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewContinuityOutcomeDto newContinuityOutcomeDto);

    @PostMapping("/category/{category}")
    @ResponseStatus(HttpStatus.CREATED)
    void addCategory(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("category") String category);

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    CategoriesDto getAllCategories(@AuthenticationPrincipal UserPrincipal userPrincipal);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    OutcomeSummaryDto getOutcomesSummary(@AuthenticationPrincipal UserPrincipal userPrincipal);
}
