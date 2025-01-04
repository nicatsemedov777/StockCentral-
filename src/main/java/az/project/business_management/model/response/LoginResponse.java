package az.project.business_management.model.response;

import az.project.business_management.enums.RoleType;
import az.project.business_management.model.jwt.JwtToken;
import lombok.Builder;

@Builder
public record LoginResponse(JwtToken jwtToken, String userFullName, String organisationName, RoleType userRole) {
}
