package com.mauricio.apirestsecurityjwt.controllers;

import java.util.Collections;

import com.mauricio.apirestsecurityjwt.dto.JwtAuthResponseDTO;
import com.mauricio.apirestsecurityjwt.dto.LoginDTO;
import com.mauricio.apirestsecurityjwt.dto.RegistreUserDTO;
import com.mauricio.apirestsecurityjwt.models.Roles;
import com.mauricio.apirestsecurityjwt.models.User;
import com.mauricio.apirestsecurityjwt.repositories.RolRepository;
import com.mauricio.apirestsecurityjwt.repositories.UserRepository;
import com.mauricio.apirestsecurityjwt.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    //* Se obtiene el usuario y se guarda como objeto del método Authentication    
    @PostMapping("/Login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO LDTO) {        
        Authentication auth = authenticationManager.authenticate(
                       new UsernamePasswordAuthenticationToken(LDTO.getUsernameOrEmail(), LDTO.getPassword())); /* Se pasan las credenciales */
        SecurityContextHolder.getContext().setAuthentication(auth); /* Se establece la autenticación */

        //* Se obtiene el token de 'JwtTokenProvider'
        String token = jwtTokenProvider.generateAccessToken(auth);

        return ResponseEntity.ok(new JwtAuthResponseDTO(token));
    }
    
    //* Método para guardar un nuevo usuario
    @PostMapping("/addUser")
    public ResponseEntity<?> addNewUser(@RequestBody RegistreUserDTO registreDTO) { //* <?>: De tipo anónimo
        //! Verifican si el usuario existe mediante su 'username' o 'email'
        if(userRepository.existsByUsername(registreDTO.getUsername())) {
            return new ResponseEntity<>("El nombre de usuario: " 
                       + registreDTO.getUsername() + " ya existe", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(registreDTO.getEmail())) {
            return new ResponseEntity<>("El email: " 
                        + registreDTO.getEmail() + " ya existe", HttpStatus.BAD_REQUEST);
        } 

        //* Si retorna 'false' entonces se procede a crear el nuevo usuario
        User usuario = new User();
        usuario.setNombre(registreDTO.getNombre());
        usuario.setUsername(registreDTO.getUsername());
        usuario.setEmail(registreDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registreDTO.getPassword())); /* Encripta la password */

        Roles rol = rolRepository.findByNombre("ROLE_ADMIN").get(); /* Se obtiene el rol del repositorio */
        //! Método singleton: Crea un conjunto inmutable sobre un solo elemento especificado.
        //* Inmutable: Objeto cuyo estado no puede ser modificado una vez creado.​
        usuario.setRoles(Collections.singleton(rol)); /* Y se agrega el rol al objeto 'usuario' */

        userRepository.save(usuario);
        return new ResponseEntity<>("Usuario registrado", HttpStatus.OK);
    }  
}
