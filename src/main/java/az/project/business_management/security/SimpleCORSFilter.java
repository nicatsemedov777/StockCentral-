package az.project.business_management.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)

public class SimpleCORSFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        response.setHeader("Access-Control-Allow-Origin", getOrigin(request));
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, content-type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    private static String getOrigin(HttpServletRequest request) {
        String origin = request.getHeader("Origin");

        if (Objects.isNull(origin))
            return "*";

        if (origin.endsWith("/"))
            return removeLastCharOptional(origin);

        return origin;
    }

    private static String removeLastCharOptional(String s) {
        return Optional.ofNullable(s)
                .filter(str -> !str.isEmpty())
                .map(str -> str.substring(0, str.length() - 1))
                .orElse(s);
    }

}
