package org.perficient.registrationsystem.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.perficient.registrationsystem.dto.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class JwUtilService Created on 20/10/2022
 *
 * @Author Iván Camilo Rincon Saavedra
 */
@Service
public class JwtUtilService {
    @Value("${app.JWT_SECRET_KEY}")
    private String JWT_SECRET_KEY;

    private final int TOKEN_DURATION_MINUTES = 5;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        var username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private String createToken(Map<String, Object> claims, String subject, Date expirationDate) {

        return Jwts
                .builder()
                //.setClaims(claims) // the claims has information of JWT id, issued at, expiration time, we set the roles
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public TokenDto generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();//roles, o privilegios
        // Adding additional information like "claim"

//        var rol = userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0);
//        claims.put("rol", rol);

        // Expiration time
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.add(Calendar.MINUTE, TOKEN_DURATION_MINUTES);

        // Creating Token Dto
        var jwt = createToken(claims, userDetails.getUsername(), expirationDate.getTime());
        return TokenDto
                .builder()
                .token(jwt)
                .expirationDate(expirationDate.getTime())
                .build();
    }
}
