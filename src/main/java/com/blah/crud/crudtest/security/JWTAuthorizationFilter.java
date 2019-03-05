package com.blah.crud.crudtest.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.blah.crud.crudtest.persistence.entity.Authority;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static com.blah.crud.crudtest.security.SecurityConstants.HEADER_STRING;
import static com.blah.crud.crudtest.security.SecurityConstants.SECRET;
import static com.blah.crud.crudtest.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
    //MOST IMPORTANT!
    //From with in the filter stack, alters the security context and allows it to move on assuming it is correct.

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.

            //verifying the roles at this level maybe a bad idea? Perhaps find a better way.
            String host = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .withClaim("authorities", "ROLE_HOST")
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            String guest = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .withClaim("authorities", "ROLE_GUEST")
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            String general = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    //.withClaim("authorities", "ROLE_GUEST")
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (host != null && guest == null) {
                return new UsernamePasswordAuthenticationToken(host, null,
                        Collections.singletonList(new Authority("ROLE_HOST")));
            }
            else if (guest != null && host ==null) {
                return new UsernamePasswordAuthenticationToken(guest, null,
                        Collections.singletonList(new Authority("ROLE_GUEST")));
            }
            else if (general != null) {
                return new UsernamePasswordAuthenticationToken(general, null,
                        Collections.EMPTY_LIST);
            }
            return null;
        }
        return null;
    }
}
