package ru.spshop.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.spshop.model.JwtAuthentication;
import ru.spshop.services.JwtProvider;
import ru.spshop.services.JwtUtils;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final String authorization = "Authorization";
    private final String bearerAuth = "Bearer";
    private final JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        final String token = getTokenFromRequest((HttpServletRequest) request);
        if (token != null && jwtProvider.validateAccessToken(token)) {
            log.info("JwtFilter/doFilter/token: " + token);
            final Claims claims = jwtProvider.getAccessClaims(token);
            final JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);
            jwtInfoToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        fc.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(authorization);
        if (StringUtils.hasText(bearer) && bearer.startsWith(bearerAuth)) {
            return bearer.substring(bearerAuth.length());
        }
        return null;
    }

}
