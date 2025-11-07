package com.bank.bank_rest.bankcards.security;

import com.bank.bank_rest.bankcards.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

    private static final String AUTHENTICATION_SCHEME = "Bearer ";
    private static final String AUTHENTICATION_HEADER = "Authorization";
    private final JwtService jwtService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain
    ) throws ServletException, IOException {

        var authHeader = request.getHeader(AUTHENTICATION_HEADER);
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, AUTHENTICATION_SCHEME)){
            filterChain.doFilter(request, response);
            return;
        }
        var token = authHeader.substring(AUTHENTICATION_SCHEME.length());
        var username = jwtService.extractUsername(token);

        if (StringUtils.isNotEmpty(username)&& SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userService
                    .getUserDetailsService()
                    .loadUserByUsername(username);

            if (jwtService.isValidToken(token,userDetails)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
