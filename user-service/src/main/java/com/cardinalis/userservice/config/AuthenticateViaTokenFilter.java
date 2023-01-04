package com.cardinalis.userservice.config;

import com.cardinalis.userservice.model.UserEntity;
import com.cardinalis.userservice.repository.UserRepository;
import com.cardinalis.userservice.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@AllArgsConstructor
public class AuthenticateViaTokenFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        boolean valid = tokenService.isTokenValid(token);

        // Check token for every endpoints
        if (valid) {
            authenticateClient(token);
        }

        filterChain.doFilter(request, response);
    }
    private void authenticateClient(String token) {
        UUID idUsuario = tokenService.getIdUser(token);
        UserEntity usuario = userRepository.findById(idUsuario).get();

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getRoles());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
