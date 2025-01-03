package az.project.business_management.controller;

import az.project.business_management.model.jwt.JwtToken;
import az.project.business_management.model.request.LoginRequest;
import az.project.business_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/public")
public class PublicController {
    private final UserService userService;

    @PostMapping("login")
    public JwtToken login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }



}
