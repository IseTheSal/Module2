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
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component
@PropertySource("classpath:security.properties")
public class JwtProvider {
    private static final String ADMIN_KEYCLOAK = "ADMIN";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String USER_ROLE = "ROLE_USER";
    private static final String ENCODING_ALGORITHM = "RSA";
    private static final int EXPIRE_HOURS = 10;

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${keycloak.public.key}")
    private String keycloakPublicKey;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String login) {
        Date date = Date.from(LocalDateTime.now().plusHours(EXPIRE_HOURS).atZone(ZoneId.systemDefault()).toInstant());
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
        } catch (ExpiredJwtException ex) {
            throw new RuntimeException("error.jwt.expired");
        } catch (UnsupportedJwtException ex) {
            throw new RuntimeException("error.jwt.unsupported");
        } catch (MalformedJwtException ex) {
            throw new RuntimeException("error.jwt.malformed");
        } catch (SignatureException ex) {
            throw new RuntimeException("error.jwt.invalid.signature");
        } catch (Exception ex) {
            return false;
        }
    }

    UserDetails findOrRegisterUser(String token) {
        try {
            AccessToken accessToken = TokenVerifier.create(token, AccessToken.class).getToken();
            UserDTO userDTO = new UserDTO();
            userDTO.setLogin(accessToken.getEmail());
            userDTO.setFirstName(accessToken.getGivenName());
            userDTO.setLastName(accessToken.getFamilyName());
            userDTO.setRole(findRoleByToken(accessToken));
            return userDetailsService.loadOrRegisterUser(userDTO);
        } catch (VerificationException e) {
            throw new RuntimeException(e);
        }
    }

    private UserRole findRoleByToken(AccessToken token) {
        Set<String> roles = token.getRealmAccess().getRoles();
        return roles.contains(ADMIN_KEYCLOAK) ? userDetailsService.findKeycloakRoleByName(ADMIN_ROLE)
                : userDetailsService.findKeycloakRoleByName(USER_ROLE);
    }

    public boolean validateKeycloak(String token) {
        try {
            PublicKey key = getKey(keycloakPublicKey);
            AccessToken keycloakToken = TokenVerifier.create(token, AccessToken.class).publicKey(key).verify().getToken();
            checkExpDate(keycloakToken.getExp());
            return true;
        } catch (VerificationException e) {
            return false;
        }
    }

    private PublicKey getKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key.getBytes());
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance(ENCODING_ALGORITHM);
            return kf.generatePublic(X509publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
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
        } catch (Exception ex) {
            throw new RuntimeException("error.jwt.invalid.token");
        }
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
