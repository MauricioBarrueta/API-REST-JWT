package com.mauricio.apirestsecurityjwt.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

//! Clase que valida si el token es correcto para poder dar acceso a los recursos de la aplicación
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    //* Método autocreado que valida el token
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //* Se obtiene el token de la solicitud HTTP
        String token = getJwtFromRequest(request);

        //* Se valida el token
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            //* Se obtiene el 'username' del token
            String username = tokenProvider.getUsernameFromJWT(token);

            //* Se carga el usuario asociado al token
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                                                (userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //* Se establece la seguridad
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        //* Valida el filtro
        filterChain.doFilter(request, response);
    }

    //* Bearer token de acceso
    //! Bearer: Formato que permite la autorización en conjunto de un usuario
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            //* substring: Recorta el String
            return bearerToken.substring(7, bearerToken.length()); /* 7 es porque se quita la palabra 'Bearer ' del token, que son 7 caracteres */
        }

        return null;
    }
    
}
