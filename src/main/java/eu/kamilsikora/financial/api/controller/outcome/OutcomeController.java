package eu.kamilsikora.financial.api.controller.outcome;

import eu.kamilsikora.financial.api.configuration.auth.UserPrincipal;
import eu.kamilsikora.financial.api.dto.outcome.NewOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeOverviewDto;
import eu.kamilsikora.financial.api.dto.outcome.OutcomeSummaryDto;
import eu.kamilsikora.financial.api.dto.outcome.OverviewType;
import eu.kamilsikora.financial.api.dto.outcome.UpdateOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.ContinuityOutcomeDetailsDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.NewContinuityOutcomeDto;
import eu.kamilsikora.financial.api.dto.outcome.continuity.UpdateContinuityOutcomeDto;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;

@RequestMapping("/outcome")
@Api(tags = {"Outcome controller"})
public interface OutcomeController {

    @PostMapping("/regular")
    @ResponseStatus(HttpStatus.CREATED)
    void addRegularOutcome(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewOutcomeDto newOutcomeDto);

    @PostMapping("/continuity")
    @ResponseStatus(HttpStatus.CREATED)
    void addContinuityOutcome(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody NewContinuityOutcomeDto newContinuityOutcomeDto);

    @PutMapping("/continuity")
    @ResponseStatus(HttpStatus.OK)
    void updateContinuityOutcome(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateContinuityOutcomeDto updateContinuityOutcomeDto);

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    OutcomeDetailsDto updateOutcome(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UpdateOutcomeDto updateOutcomeDto);

    @GetMapping("/overview")
    @ResponseStatus(HttpStatus.OK)
    Page<OutcomeOverviewDto> getOverview(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam OverviewType type,
                                         @RequestParam(required = true) Integer pageNumber,
                                         @RequestParam(required = true) Integer pageSize,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
                                         @RequestParam(required = false) Long category);

    @GetMapping("/continuity/{id}")
    ContinuityOutcomeDetailsDto getDetails(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable("id") Long id);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    OutcomeSummaryDto getOutcomesSummary(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam OverviewType type,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                         @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
                                         @RequestParam(required = false) Long category);
}
