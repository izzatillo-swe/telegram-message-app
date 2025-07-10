package com.company.telegrammessageapp.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTProvider {

    @Value("${app.jwt.access.key}")
    private String ACCESS_SECRET;

    @Value("${app.jwt.access.expiration}")
    private long ACCESS_EXPIRATION;

    @Value("${app.jwt.refresh.key}")
    private String REFRESH_SECRET;

    @Value("${app.jwt.refresh.expiration}")
    private long REFRESH_EXPIRATION;

    public String createAccessToken(String id) {
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String id) {
        return Jwts.builder()
                .subject(id)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(getSignKeyForRefresh(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ACCESS_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getSignKeyForRefresh() {
        byte[] keyBytes = Decoders.BASE64.decode(REFRESH_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserId(String token) {
        try {
            Jws<Claims> claimsJws = extractClaimsForAccessToken(token);
            return claimsJws.getPayload().getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String extractUserIdFromRefreshToken(String token) {
        try {
            return extractClaimsForRefreshToken(token).getPayload().getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private Jws<Claims> extractClaimsForAccessToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseSignedClaims(token);
    }

    private Jws<Claims> extractClaimsForRefreshToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKeyForRefresh())
                .build()
                .parseSignedClaims(token);
    }

    public boolean isTokenExpired(String token, boolean isRefresh) {
        try {
            Claims claims = isRefresh
                    ? extractClaimsForRefreshToken(token).getPayload()
                    : extractClaimsForAccessToken(token).getPayload();

            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        return !isTokenExpired(refreshToken, true);
    }

}
