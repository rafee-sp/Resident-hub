package com.rafee.residenthub.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Service
public class JwtService {

    @Value("${jwt.expiration.access-token}")
    private long accessExpirationTime;
    @Value("${jwt.expiration.refresh-token}")
    private long refreshExpirationTime;
    @Value("${jwt.expiration.message-token}")
    private long messageExpirationTime;

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret-key}") String secretKey){
         this.key  = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String userId, boolean isAccessToken){

        long expirationTime =  isAccessToken ? accessExpirationTime : refreshExpirationTime;

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String validateAndExtractUserEmail(String token){
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (Exception e) {
            return null; // handles invalid signatures and expired token
        }
    }

    public String generateTwilioToken(String messageId){

        return Jwts.builder()
                .subject(messageId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + messageExpirationTime))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public String validateAndExtractMessageId(String token){
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();

        } catch (ExpiredJwtException e){
            log.warn("The message token has been expired");
            return null;
        } catch (Exception e) {
            return null; // handles invalid signatures and expired token
        }
    }
}
