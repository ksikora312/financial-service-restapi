package eu.kamilsikora.financial.api.controller.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.service.outcome.RegularOutcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OutcomeControllerImpl implements OutcomeController {

    private final RegularOutcomeService regularOutcomeService;

    @Override
    public void addRegularOutcome(final UserPrincipal userPrincipal, final NewOutcomeDto newOutcomeDto) {
        regularOutcomeService.addNewOutcome(userPrincipal, newOutcomeDto);
    }
}
