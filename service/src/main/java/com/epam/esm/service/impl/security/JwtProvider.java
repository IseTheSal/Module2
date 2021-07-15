package com.epam.esm.service.impl.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String login) {
        Date date = Date.from(LocalDateTime.now().plusHours(10).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (ExpiredJwtException expEx) {
            throw new RuntimeException("error.jwt.expired");
        } catch (UnsupportedJwtException unsEx) {
            throw new RuntimeException("error.jwt.unsupported");
        } catch (MalformedJwtException mjEx) {
            throw new RuntimeException("error.jwt.malformed");
        } catch (SignatureException sEx) {
            throw new RuntimeException("error.jwt.invalid.signature");
        } catch (Exception e) {
            throw new RuntimeException("error.jwt.invalid.token");
        }
        return true;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
