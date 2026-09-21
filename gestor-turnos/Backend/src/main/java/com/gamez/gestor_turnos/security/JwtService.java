package com.gamez.gestor_turnos.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.model.Empleado;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException; 

@Service
public class JwtService {

    

@Value("${jwt.secret}")
private String secretKey;


    public String generarToken(Empleado empleado) {
        
        Map<String, Object> extraClaims = new HashMap<>();
        
        List<String> permisos = empleado.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
                
        extraClaims.put("permisos", permisos);

        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(empleado.getDni()) 
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extraerUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    @SuppressWarnings("unchecked")
    public List<String> extraerPermisos(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("permisos", List.class);
    }

    public boolean isTokenValido(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException | IllegalArgumentException e) {
            return false;
        }
    }
}