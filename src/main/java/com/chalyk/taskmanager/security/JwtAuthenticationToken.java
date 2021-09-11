package com.chalyk.taskmanager.security;

import com.chalyk.taskmanager.model.Account;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private Account account;
    private String token;
    Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticationToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Account account, String token) {
        super(authorities);
        this.account = account;
        this.token = token;
        this.authorities = account.getAuthorities();
        setAuthenticated(true);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return account;
    }
}
