package com.blah.crud.crudtest.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.blah.crud.crudtest.authuser.MyUserPrinciple;
import com.blah.crud.crudtest.authuser.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.blah.crud.crudtest.security.SecurityConstants.HEADER_STRING;
import static com.blah.crud.crudtest.security.SecurityConstants.SECRET;
import static com.blah.crud.crudtest.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    //@Autowired
    //private AuthenticationManager authenticationManager;

    @Autowired
    protected JWTAuthorizationFilter(AuthenticationManager authManager, UserDetailsServiceImpl userDetailsService) {
        //this.userDetailsService = userDetailsService;
        super(authManager);
        //this.authenticationManager = authManager;
        this.userDetailsService = userDetailsService;
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

        if (authentication == null) {
            System.out.println("BAD NULL AUTHENTICATE RIGHT NOW");
        }

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    //MOST IMPORTANT!
    //From with in the filter stack, alters the security context and allows it to move on assuming it is correct.

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String general = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    //.withClaim("authorities", "ROLE_GUEST")
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            System.out.println("Reached some kinda position");
            if (general != null) {
                System.out.println(general);
                return authenticateFromName(general);
            }
            return null;
        }
        return null;
    }

    private UsernamePasswordAuthenticationToken authenticateFromName(String name) {
        MyUserPrinciple appUser = (MyUserPrinciple) userDetailsService.loadUserByUsername(name);
        if (appUser != null) {
            System.out.println("GOT THIS FAR, 'im at the UserDetails load part.");
        }
        //return new UsernamePasswordAuthenticationToken(name, null, new ArrayList<>());
        return new UsernamePasswordAuthenticationToken(name, appUser.getPassword(), appUser.getAuthorities());
    }
}
