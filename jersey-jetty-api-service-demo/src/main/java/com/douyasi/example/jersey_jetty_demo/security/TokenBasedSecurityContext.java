package com.douyasi.example.jersey_jetty_demo.security;


import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * {@link SecurityContext} implementation for token-based authentication.
 *
 * @author raoyc
 */
public class TokenBasedSecurityContext implements SecurityContext {

    private final boolean secure;
    private AuthUser authUser;
    private static final String AUTHENTICATION_SCHEME = "Bearer";
    
    public TokenBasedSecurityContext(AuthUser authUser, boolean secure) {
        this.authUser = authUser;
        this.secure = secure;
    }

    @Override
    public Principal getUserPrincipal() {
        return authUser;
    }

    @Override
    public boolean isUserInRole(String role) {
        return true;
    }

    @Override
    public boolean isSecure() {
        return secure;
    }

    @Override
    public String getAuthenticationScheme() {
        return AUTHENTICATION_SCHEME;
    }

}
