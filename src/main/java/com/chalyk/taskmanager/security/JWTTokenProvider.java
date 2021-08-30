package com.chalyk.taskmanager.security;

import com.chalyk.taskmanager.model.Account;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {

    public String generateToken(Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());

        String accountId = Long.toString(account.getId());

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", accountId);
        claimsMap.put("login", account.getLogin());
        claimsMap.put("firstName", account.getFirstName());
        claimsMap.put("lastName", account.getLastName());

        return Jwts.builder()
                .setSubject(accountId)
                .addClaims(claimsMap)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException |
                MalformedJwtException |
                UnsupportedJwtException |
                IllegalArgumentException exception
        ) {
            return false;
        }

    }

    public Long getAccountIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String) claims.get("id");

        return Long.parseLong(id);
    }

}
