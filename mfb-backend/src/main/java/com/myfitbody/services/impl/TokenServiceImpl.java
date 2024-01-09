package com.myfitbody.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.myfitbody.domain.exceptions.JWTException;
import com.myfitbody.domain.user.User;
import com.myfitbody.services.contracts.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${api.security.jwt.secret}")
    private String jwtSecret;

    @Override
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.create()
                    .withIssuer("my-fit-body")
                    .withSubject(user.getEmail())
                    .withClaim("email", user.getEmail())
                    .withClaim("roles", user.getRoles())
                    .withExpiresAt(getExpirationTime())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTException("Error while generating token");
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtSecret);
            return JWT.require(algorithm)
                    .withIssuer("my-fit-body")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTException("Error while validating token");
        }
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
