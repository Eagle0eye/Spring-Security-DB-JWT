package com.spring_security.demo.service;

import com.spring_security.demo.utilities.JWTUtil;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {

    private final JWTUtil jwtUtil;

    public JWTServiceImpl(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1 * 60 * 1000)))
                .signWith(jwtUtil.getKey())
                .compact();
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = jwtUtil.extractUserName(token);
            return (username.equals(userDetails.getUsername()) && !jwtUtil.isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }
}
