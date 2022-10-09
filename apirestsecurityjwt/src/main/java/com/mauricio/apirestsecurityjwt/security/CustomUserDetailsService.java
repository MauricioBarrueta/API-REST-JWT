package com.mauricio.apirestsecurityjwt.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.mauricio.apirestsecurityjwt.models.Roles;
import com.mauricio.apirestsecurityjwt.models.User;
import com.mauricio.apirestsecurityjwt.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository uRepository;

    //* Método que carga o busca un usuario por su 'username'
    @Override
    //! throw: Se utiliza para lanzar explícitamente una excepción
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException { 
        User usuario = uRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("No se encontró el nombre de usuario / email: " + usernameOrEmail));

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), mapRoles(usuario.getRoles()));
    }

    //* GrantedAuthority: Concede una autoridad a un objeto de autenticación, en este caso los roles
    //! Collection: Recorre (itera) la entidad roles
    private Collection<? extends GrantedAuthority> mapRoles(Set<Roles> roles) { //* Set: Colección que no puede tener elementos duplicados
        //* SimpleGrantedAuthority: Almacena la autoridad concedida al objeto (rol)
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                    .collect(Collectors.toList()); /* Se obtiene y se convierte a lista */
    }

    //! 3:56:00
    
}
