package com.mauricio.apirestsecurityjwt.security;


import java.util.Date;

import com.mauricio.apirestsecurityjwt.exceptions.AppException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}") /* Obtiene un valor de la propiedad de application.properties */
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}") /* Obtiene un valor de la propiedad de application.properties */
    private int jwtExpirationInMs;

    //* Método que genera el token con el algorítmo y su llave secreta, ademas de que establece el usuario y el tiempo de expiración
    public String generateAccessToken(Authentication authentication) {
        String username = authentication.getName(); /* Se obtiene el 'username' del objeto Authentication */
        Date actualDate = new Date();        
        Date expirationDate = new Date(actualDate.getTime() + jwtExpirationInMs); //* getTime(): Obtiene la hora actual y se suma el tiempo de expiración

        //* Se crea el token pasandole el 'username' y el 'tiempo de expiración'
        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact(); //! Se usa el algoritmo HS512

        return token;
    }

    //* Método que obtiene el 'username' del token
    public String getUsernameFromJWT(String token) {
        //! Claims: Son los datos del token, en este caso 'username' y 'fecha de caducidad'
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    //! parser(): analiza una cadena de texto como JSON, transformando opcionalmente el valor producido por el análisis

    //* Método que valida el token
    public boolean validateToken(String token) {
        //* Valida el token con la clave secreta
        try {            
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true; /* Si existe retorna 'true' */

        } catch (SignatureException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Firma JWT no válida");
        } catch (MalformedJwtException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Token JWT no válido");
        } catch (ExpiredJwtException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Token JWT caducado");
        } catch (UnsupportedJwtException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Token JWT no compatible");
        } catch (IllegalArgumentException ex) {
            throw new AppException(HttpStatus.BAD_REQUEST, "Claims vacío");
        }
    }    
}
