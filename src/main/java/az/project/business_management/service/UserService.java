package az.project.business_management.service;

import az.project.business_management.entity.User;
import az.project.business_management.error.exception.AuthenticationException;
import az.project.business_management.error.exception.ResourceNotFoundException;
import az.project.business_management.model.request.LoginRequest;
import az.project.business_management.model.response.LoginResponse;
import az.project.business_management.repository.UserRepository;
import az.project.business_management.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.username()).
                orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (passwordEncoder.matches(loginRequest.password(), user.getPassword()))
            return buildLoginResponse(user);


        throw new AuthenticationException("Password is not correct");

    }

    private LoginResponse buildLoginResponse(User u) {
        return LoginResponse.builder()
                .jwtToken(jwtProvider.getJWTToken(u.getId(), u.getRoleType(), u.getOrganisation().getId()))
                .userRole(u.getRoleType())
                .userFullName(u.getFullName())
                .organisationName(u.getOrganisation().getName())
                .build();
    }
}
