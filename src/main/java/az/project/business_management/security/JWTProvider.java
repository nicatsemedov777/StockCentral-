package az.project.business_management.security;

import az.project.business_management.constant.SecurityConstants;
import az.project.business_management.enums.RoleType;
import az.project.business_management.model.jwt.JwtToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JWTProvider {
    private final JWTVerifier jwtVerifier;
    private final SecurityConstants securityConstants;

    public JWTProvider(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
        this.jwtVerifier = JWT.require(Algorithm.HMAC256(securityConstants.getSecretKey()))
                .withSubject("BUSINESS MANAGEMENT BACKEND")
                .withIssuer("smdovn42@gmail.com")
                .build();
    }

    public JwtToken getJWTToken(String userId, RoleType roleType, String organizationId) {
        Date createDate = new Date();
        Date expirationDate = getExpirationDate();
        String jwtToken = JWT
                .create()
                .withSubject("BUSINESS MANAGEMENT BACKEND")
                .withExpiresAt(expirationDate)
                .withIssuedAt(createDate)
                .withClaim("userId", userId)
                .withClaim("userRoles", roleType.name())
                .withClaim("organizationId", organizationId)
                .withIssuer("smdovn42@gmail.com")
                .sign(Algorithm.HMAC256(securityConstants.getSecretKey()));

        return JwtToken.builder()
                .token(jwtToken)
                .createDate(createDate.getTime())
                .expirationDate(expirationDate.getTime())
                .build();
    }

    public String extractUserId(String token) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaim("userId").asString();
    }

    public List<String> getRolesListString(List<RoleType> roleTypes) {
        return roleTypes.stream().map(Enum::name).collect(Collectors.toList());
    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 3);
        return calendar.getTime();
    }

    public Boolean isExpired(JwtToken jwtToken) {
        Date expirationDate = new Date(jwtToken.getExpirationDate());
        return new Date().after(expirationDate);
    }
}
