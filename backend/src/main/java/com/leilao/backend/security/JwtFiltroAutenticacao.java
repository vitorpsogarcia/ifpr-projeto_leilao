package com.leilao.backend.security;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.leilao.backend.service.PessoaService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
public class JwtFiltroAutenticacao extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PessoaService  pessoaService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (isPublicRoute(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        String token = null;
        String username = null;

        if (request.getCookies() != null) {
            Optional<Cookie> tokenCookie = Arrays.stream(request.getCookies())
              .filter(cookie -> cookie.getName().equals("token")).findFirst();

            if (tokenCookie.isPresent()) {
                token = tokenCookie.get().getValue();
            }
        }

        if (token != null) {
            username = jwtService.extractUsername(token);
        } else {
            response.sendRedirect(response.encodeRedirectURL("/autenticacao/login"));
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = pessoaService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails.getUsername())) {
                var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isPublicRoute(String uri) {
        return uri.startsWith("/autenticacao/") || uri.startsWith("/categoria/") || uri.equals("/leilao/public");
    }
}

