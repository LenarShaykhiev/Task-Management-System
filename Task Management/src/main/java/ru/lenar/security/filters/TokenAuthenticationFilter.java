package ru.lenar.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.lenar.dto.SignInForm;
import ru.lenar.models.Customer;
import ru.lenar.repositories.CustomersRepository;
import ru.lenar.security.details.CustomerUserDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static ru.lenar.security.constant.SecurityConstant.*;

@Slf4j
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String TOKEN = "token";

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, CustomersRepository customersRepository){
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            SignInForm form = objectMapper.readValue(request.getReader(), SignInForm.class);
            log.info("Attempt authentication - email{}, password{}", form.getEmail(), form.getPassword());

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            form.getEmail(),
                            form.getPassword(),
                            new ArrayList<>())
                    );
        } catch (IOException e) {
            throw new IllegalArgumentException("Не работает");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        CustomerUserDetails userDetails = (CustomerUserDetails) authResult.getPrincipal();
        Customer customer = userDetails.getCustomer();
        System.out.println(customer.toString());

        String token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("authorities", ((CustomerUserDetails) authResult.getPrincipal()).getAuthorities().toString())
                .withClaim("email", ((CustomerUserDetails) authResult.getPrincipal()).getUsername())
                .sign(Algorithm.HMAC512(TOKEN_SECRET));
        response.addHeader(JWT_TOKEN_HEADER, TOKEN_PREFIX + token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap(TOKEN, token));
    }
}
