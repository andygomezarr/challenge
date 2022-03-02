package com.challenge.security;

import static com.challenge.security.Constants.HEADER_AUTHORIZACION_KEY;
import static com.challenge.security.Constants.SECRET_KEY;
import static com.challenge.security.Constants.TOKEN_BEARER_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    // arma el filtro para el token
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain
    ) throws IOException, ServletException {
        try {
            String header = req.getHeader(HEADER_AUTHORIZACION_KEY);
            if (header == null || !header.startsWith(TOKEN_BEARER_PREFIX)) {
                chain.doFilter(req, res);
            }
            UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(req, res);
        } catch (Exception exception) {
            throw exception;
        }
    }

    // retorna el token o las cabeceras
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws Exception {
        try {
            String token = request.getHeader(HEADER_AUTHORIZACION_KEY);
            if (token != null) {
                String user = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, ""))
                        .getBody()
                        .getSubject();
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
                return null;
            }
            return null;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
