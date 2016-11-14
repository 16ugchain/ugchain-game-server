package com.fiveonechain.digitasset.auth;

import com.fiveonechain.digitasset.auth.token.RawAccessJwtToken;
import com.fiveonechain.digitasset.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

    private final String signingKey;

    public JwtAuthenticationProvider(String signingKey) {
        this.signingKey = signingKey;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(signingKey);
        UserContext context = buildUserContext(jwsClaims);
        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private UserContext buildUserContext(Jws<Claims> jwsClaims) {
        try {
            String subject = jwsClaims.getBody().getSubject();
            List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
            Long id = Long.valueOf(jwsClaims.getBody().get("id", Integer.class));
            List<GrantedAuthority> authorities = scopes.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority))
                    .collect(Collectors.toList());
            return UserContext.create(id, subject, authorities);
        } catch (Exception e) {
            LOGGER.error("Invalid JWT token: ", e);
            throw new BadCredentialsException("Invalid JWT token: ", e);
        }
    }
}
