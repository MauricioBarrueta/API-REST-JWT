package com.mauricio.apirestsecurityjwt.repositories;

import java.util.Optional;

import com.mauricio.apirestsecurityjwt.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    
    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsernameOrEmail(String username, String emal);

    public Optional<User> findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);
}
