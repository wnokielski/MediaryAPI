package com.mediary.Services.Implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtils {

    @Value("${jwt.validity}")
    private int jwtValidity;

    @Value("${jwt.secret}")
    private String secret;

    //generate token for user
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create().withPayload(claims).withSubject(subject).withIssuedAt(new Date(System.currentTimeMillis())).
                withExpiresAt(new Date(System.currentTimeMillis() + jwtValidity * 1000)).sign(algorithm);

    }
}
