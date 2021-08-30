package com.chalyk.taskmanager.security;

import com.chalyk.taskmanager.model.Account;
import com.chalyk.taskmanager.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    public static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final JWTTokenProvider jwtTokenProvider;
    private final AccountService accountService;

    @Autowired
    public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, AccountService accountService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJWTFromRequest(httpServletRequest);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Long accountId = jwtTokenProvider.getAccountIdFromToken(jwt);
                Account account = accountService.findAccountById(accountId);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        account, null, Collections.emptyList()
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            LOGGER.error("Could not set user auth");
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String taskToken = request.getHeader(SecurityConstants.HEADER_STRING);

        if (StringUtils.hasText(taskToken) && taskToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return taskToken.split(" ")[1];
        }
        return null;
    }
}
