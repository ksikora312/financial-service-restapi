package eu.kamilsikora.financial.api.controller.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

}
