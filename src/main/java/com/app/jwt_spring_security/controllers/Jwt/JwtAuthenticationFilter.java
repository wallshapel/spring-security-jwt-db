package com.app.jwt_spring_security.controllers.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Heredamos de la clase OncePerRequestFilter para crear filtros personalizados que se ejecuten por cada solicitud http

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    // Método obligatorio que hay que implementar por herencia. Gestionará todos los filtros relacionados al JWT
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Obtenemos el token del request
            final String token = getTokenFromRequest(request);
            final String username;

            if (token != null) { // Si el token no es nulo
                username = jwtService.getUsernameFromToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtService.isTokenValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response); // Devolvemos a la cadena de filtros el control
        } catch (Exception ex) {
            // Maneja cualquier excepción y configura el estado de la respuesta en UNAUTHORIZED
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized: " + ex.getMessage());
        }
    }

    // Devuelve el String del token
    protected String getTokenFromRequest(HttpServletRequest request) { // Le pasamos el request ya que del header del request es de donde obtendremos el token
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // Obtenemos el token del header
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) // Verifica si en el authHeader está el texto "Bearer "
            return authHeader.substring(7); // Devolvemos el token sin el texto inicial "Bearer "
        return null;
    }
}
