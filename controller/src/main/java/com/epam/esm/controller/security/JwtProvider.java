package com.epam.esm.controller.security;

import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.UserRole;
import com.epam.esm.service.impl.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String USER_ROLE = "ROLE_USER";

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    public JwtProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    UserDetails findOrRegisterUser(String token) {
        try {
            AccessToken accessToken = TokenVerifier.create(token, AccessToken.class).getToken();
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(accessToken.getEmail());
            userDTO.setFirstName(accessToken.getName());
            userDTO.setLastName(accessToken.getFamilyName());
            userDTO.setRole(findRoleByToken(accessToken));
            return userDetailsService.loadOrRegisterUser(userDTO);
        } catch (VerificationException e) {
            throw new RuntimeException(e);
        }
    }

    private UserRole findRoleByToken(AccessToken token) {
        Set<String> roles = token.getRealmAccess().getRoles();
        return roles.contains(ADMIN_ROLE) ? userDetailsService.findKeycloakRoleByName(ADMIN_ROLE)
                : userDetailsService.findKeycloakRoleByName(USER_ROLE);
    }

    public boolean validateKeycloak(String token) {
        try {
            AccessToken keycloakToken = TokenVerifier.create(token, AccessToken.class).getToken();
            checkExpDate(keycloakToken.getExp());
            return true;
        } catch (VerificationException e) {
            return false;
        }
    }

    private void checkExpDate(Long expireDate) throws VerificationException {
        boolean after = new Date().after(new Date(expireDate * 1000));
        if (after) {
            throw new VerificationException("error.jwt.expired");
        }
    }

    public void recogniseException(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("error.jwt.expired");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("error.jwt.unsupported");
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("error.jwt.malformed");
        } catch (SignatureException ex) {
            throw new RuntimeException("error.jwt.invalid.signature");
        } catch (Exception ex) {
            throw new RuntimeException("error.jwt.invalid.token");
        }
    }
    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
