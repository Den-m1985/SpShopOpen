package ru.spshop.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.spshop.model.JwtAuthentication;
import ru.spshop.service.JwtUtils;
import ru.spshop.service.AuthService;

import java.io.IOException;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final String authorization = "Authorization";
    private final String bearerAuth = "Bearer";
    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && authService.validateToken(token)) {
            final Claims claims = authService.getClaims(token);
//            for (String key : claims.keySet()) {
//                log.info("JwtFilter doFilter() claims" + key + ": " + claims.get(key));
//            }
            final JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(authorization);
        if (StringUtils.hasText(bearer) && bearer.startsWith(bearerAuth)) {
            return bearer.substring(bearerAuth.length());
        }
        return null;
    }

}
