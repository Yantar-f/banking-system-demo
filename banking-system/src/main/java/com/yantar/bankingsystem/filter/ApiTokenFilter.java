package com.yantar.bankingsystem.filter;

import com.yantar.bankingsystem.config.AccessTokenConfig;
import com.yantar.bankingsystem.config.Role;
import com.yantar.bankingsystem.exception.InvalidTokenException;
import com.yantar.bankingsystem.model.TokenClaims;
import com.yantar.bankingsystem.util.TokenUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class ApiTokenFilter extends OncePerRequestFilter {
    private final AccessTokenConfig accessTokenConfig;
    private final TokenUtil tokenUtil;

    public ApiTokenFilter(AccessTokenConfig accessTokenConfig, TokenUtil tokenUtil) {
        this.accessTokenConfig = accessTokenConfig;
        this.tokenUtil = tokenUtil;
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> accessToken = extractAccessTokenFromRequest(request);

        if (accessToken.isEmpty()) {
            filterChain.doFilter(request,response);
            return;
        }

        try {
            authenticate(accessToken.get(), request);
        } catch (InvalidTokenException exception) {
            //
            //TODO:
            //  LOG EVENT
            //
        }

        filterChain.doFilter(request,response);
    }

    private void authenticate(String accessToken, HttpServletRequest request) {
        TokenClaims claims = parseAccessToken(accessToken);

        UsernamePasswordAuthenticationToken contextAuthToken = new UsernamePasswordAuthenticationToken(
                claims.getSubjectId(),
                claims.getSessionId(),
                List.of(new SimpleGrantedAuthority(Role.ROLE_USER.name()))
        );

        contextAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(contextAuthToken);
    }

    private TokenClaims parseAccessToken(String accessToken) {
        return tokenUtil.parseAccessToken(accessToken);
    }

    private Optional<String> extractAccessTokenFromRequest(HttpServletRequest request) {
        Optional<Cookie> cookie = Optional.ofNullable(WebUtils.getCookie(request, accessTokenConfig.getCookieName()));
        return cookie.map(Cookie::getValue);
    }

    private List<SimpleGrantedAuthority> convertRolesToAuthorities(String[] roles) {
        return Arrays.stream(roles).map(SimpleGrantedAuthority::new).toList();
    }
}
