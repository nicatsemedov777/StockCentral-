package az.project.business_management.controller;

import az.project.business_management.service.TurnoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/turnover")
public class TurnoverController {
    private final TurnoverService turnoverService;

    @GetMapping("daily")
    @PreAuthorize(value = "hasAuthority('ADMIN')")
    public Double getDailyTurnover() {
        return turnoverService.getDailyTurnover();
    }
}
