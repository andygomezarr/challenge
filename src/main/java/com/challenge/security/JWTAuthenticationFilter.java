package com.challenge.security;

import static com.challenge.security.Constants.HEADER_AUTHORIZACION_KEY;
import static com.challenge.security.Constants.ISSUER_INFO;
import static com.challenge.security.Constants.SECRET_KEY;
import static com.challenge.security.Constants.TOKEN_BEARER_PREFIX;
import static com.challenge.security.Constants.TOKEN_EXPIRATION_TIME;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.challenge.entities.Userent;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    // realiza la autenticación
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Userent credenciales = new ObjectMapper().readValue(request.getInputStream(), Userent.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credenciales.getUsername(),
                            credenciales.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // arma las cabezeras para el token
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth
    ) throws IOException, ServletException {
        String token = Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
                .setSubject(((User)auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
        response.addHeader(HEADER_AUTHORIZACION_KEY, TOKEN_BEARER_PREFIX + " " + token);
    }
}
