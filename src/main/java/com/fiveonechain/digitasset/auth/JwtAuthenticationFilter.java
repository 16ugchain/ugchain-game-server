package com.fiveonechain.digitasset.auth;

import com.fiveonechain.digitasset.auth.token.RawAccessJwtToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by yuanshichao on 2016/11/9.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTH_TOKEN_NAME = "Authorization";
    private static final String AUTH_BEARER = "Bearer ";

    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AuthenticationEntryPoint authenticationEntryPoint) {
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        Assert.notNull(authenticationEntryPoint,
                "authenticationEntryPoint cannot be null");
        this.authenticationManager = authenticationManager;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Optional<String> tokenOpt = extractTokenFromHeader(request);
        if (!tokenOpt.isPresent()) {
            tokenOpt = extractTokenFromCookie(request);
        }
        if (!tokenOpt.isPresent()) {
            this.authenticationEntryPoint.commence(request, response,
                    new AuthenticationCredentialsNotFoundException("[Authorization: Bearer jwt] NOT FOUND"));
            return;
        }

        try {
            String token = tokenOpt.get();
            JwtAuthenticationToken authRequest = new JwtAuthenticationToken(new RawAccessJwtToken(token));
            Authentication authResult = this.authenticationManager
                    .authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(request, response, failed);
            return;
        }

        chain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(AUTH_TOKEN_NAME);
        if (header == null || !header.startsWith(AUTH_BEARER)) {
            return Optional.empty();
        }
        return Optional.ofNullable(header.substring(7));
    }

    private Optional<String> extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }

        for (Cookie c : cookies) {
            if (AUTH_TOKEN_NAME.equals(c.getName())) {
                return Optional.ofNullable(c.getValue());
            }
        }
        return Optional.empty();
    }

    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return this.authenticationEntryPoint;
    }

    protected AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

}
