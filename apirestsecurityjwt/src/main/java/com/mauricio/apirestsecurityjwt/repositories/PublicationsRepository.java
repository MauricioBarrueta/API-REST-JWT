package com.mauricio.apirestsecurityjwt.repositories;

import com.mauricio.apirestsecurityjwt.models.Publications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationsRepository extends JpaRepository<Publications, Long> {
    
}
