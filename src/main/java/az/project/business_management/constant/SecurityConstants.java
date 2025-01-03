package az.project.business_management.constant;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class SecurityConstants {
    @Value("${security.auth.whitelist}")
    private String[] whitelist;

    @Value("${security.jwt.secret-key}")
    private String secretKey;
}
