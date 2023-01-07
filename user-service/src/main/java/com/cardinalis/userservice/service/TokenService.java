package com.cardinalis.userservice.service;

import com.cardinalis.userservice.config.AppProperties;
import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.security.oauth.CustomOAuth2User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private String expiration;

    @Value("${jwt.secret}")
    private String secret;

    private AppProperties appProperties;
    public String generateToken(Authentication authentication) {
        UserEntity logado = (UserEntity) authentication.getPrincipal();
        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));

        Claims claims = Jwts.claims()
                .setId(logado.getId().toString());

        return Jwts.builder()
                .setIssuer("API Twitter")
                .setClaims(claims)
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public String generateTokenForGithub(Authentication authentication) {
//        UserEntity logado = (UserEntity) authentication.getPrincipal();
//        Date today = new Date();
//        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
//
//        Claims claims = Jwts.claims()
//                .setId(logado.getId().toString());
//
//        return Jwts.builder()
//                .setIssuer("API Twitter")
//                .setClaims(claims)
//                .setIssuedAt(today)
//                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
        CustomOAuth2User userPrincipal = (CustomOAuth2User) authentication.getPrincipal();

        Date today = new Date();
        Date expirationDate = new Date(today.getTime() + Long.parseLong(expiration));
        Claims claims = Jwts.claims()
                .setId(userPrincipal.getId().toString());
        return Jwts.builder()
                .setIssuer("API Twitter")
                .setSubject(userPrincipal.getId().toString())
                .setClaims(claims)
                .setIssuedAt(today)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public UUID getIdUser(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return UUID.fromString(claims.getSubject());
    }
}