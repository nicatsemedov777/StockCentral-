package az.project.business_management.service;

import az.project.business_management.entity.User;
import az.project.business_management.error.exception.AuthenticationException;
import az.project.business_management.error.exception.ResourceNotFoundException;
import az.project.business_management.model.jwt.JwtToken;
import az.project.business_management.model.request.LoginRequest;
import az.project.business_management.repository.UserRepository;
import az.project.business_management.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTProvider jwtProvider;

    public JwtToken login(LoginRequest loginRequest) {
        List<User> users = userRepository.findAllByUsername(loginRequest.username());

        if (users.isEmpty())
            throw new ResourceNotFoundException("User not found");
        else {
            for (User u : users) {
                if(passwordEncoder.matches(loginRequest.password(), u.getPassword()))
                    return jwtProvider.getJWTToken(u.getId(),u.getRoleType(),u.getOrganisation().getId());

            }
        }

        throw new AuthenticationException("Password is not correct");

    }
}
