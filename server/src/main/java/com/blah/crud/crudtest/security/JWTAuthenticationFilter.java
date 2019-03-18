package com.blah.crud.crudtest.security;


import com.auth0.jwt.JWT;
import com.blah.crud.crudtest.authuser.MyUserPrinciple;
import com.blah.crud.crudtest.persistence.entity.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.lang.System;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.blah.crud.crudtest.security.SecurityConstants.EXPIRATION_TIME;
import static com.blah.crud.crudtest.security.SecurityConstants.HEADER_STRING;
import static com.blah.crud.crudtest.security.SecurityConstants.SECRET;
import static com.blah.crud.crudtest.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUser creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUser.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                            //(creds.getAuthorityr() != null) ? Collections.singletonList(new Authority(creds.getAuthorityr())):
                            //Collections.emptyList()
                            //creds.getAuthorities()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((MyUserPrinciple) auth.getPrincipal()).getUsername())
                //.withClaim("authorityr", ((ApplicationUser) auth.getPrincipal()).getAuthorityr())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        res.addHeader("Access-Control-Expose-Headers", HEADER_STRING);
    }
}