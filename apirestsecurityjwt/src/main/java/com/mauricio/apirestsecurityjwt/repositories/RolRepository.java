package com.mauricio.apirestsecurityjwt.repositories;

import java.util.Optional;

import com.mauricio.apirestsecurityjwt.models.Roles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Roles, Long> {

    public Optional<Roles> findByNombre(String nombre);   
    
}
