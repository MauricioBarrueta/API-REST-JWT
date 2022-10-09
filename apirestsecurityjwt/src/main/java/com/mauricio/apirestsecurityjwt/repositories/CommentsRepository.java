package com.mauricio.apirestsecurityjwt.repositories;

import java.util.List;

import com.mauricio.apirestsecurityjwt.models.Comments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    public List<Comments> findByPublicacionId(Long publicacion_Id);
    
}
