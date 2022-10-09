package com.mauricio.apirestsecurityjwt.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//! Clase en donde se generan las 'passwords' y se guardan en la base de datos
public class PasswordEncoderGenerator {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        //* Se corre como Java Application y se copia el password generado por la consola
        System.out.println(passwordEncoder.encode("password"));        
    }    
}
