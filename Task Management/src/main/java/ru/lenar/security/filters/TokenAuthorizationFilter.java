package ru.lenar.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.lenar.security.constant.SecurityConstant.*;

@Slf4j
public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    public TokenAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JWT_TOKEN_HEADER);

        if (tokenHeader == null || !tokenHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWT_TOKEN_HEADER);
        if (token != null) {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(TOKEN_SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));

            if (decodedJWT != null) {
                List<String> authorities = new ArrayList<>();
                authorities.add(decodedJWT.getClaim("authorities").toString());
                System.out.println(new UsernamePasswordAuthenticationToken(token, null, authorities.stream()
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList())));
                return new UsernamePasswordAuthenticationToken(decodedJWT, null, authorities.stream()
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
            } else {
                logger.warn("wrong token");
                System.out.println("wrong token");
            }
            return null;
        } else {
            logger.warn("token is missing");
            System.out.println("token is missing");
        }
        return null;
    }
}
