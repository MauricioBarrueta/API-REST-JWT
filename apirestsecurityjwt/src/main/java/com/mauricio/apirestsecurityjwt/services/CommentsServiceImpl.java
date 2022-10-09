package com.mauricio.apirestsecurityjwt.services;

import java.util.List;
import java.util.stream.Collectors;

import com.mauricio.apirestsecurityjwt.IService.CommentsService;
import com.mauricio.apirestsecurityjwt.dto.CommentsDTO;
import com.mauricio.apirestsecurityjwt.exceptions.AppException;
import com.mauricio.apirestsecurityjwt.exceptions.ResourceNotFoundException;
import com.mauricio.apirestsecurityjwt.models.Comments;
import com.mauricio.apirestsecurityjwt.models.Publications;
import com.mauricio.apirestsecurityjwt.repositories.CommentsRepository;
import com.mauricio.apirestsecurityjwt.repositories.PublicationsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository cRepository;

    @Autowired
    private PublicationsRepository pRepository;

    //* Método que agrega un comentario a la publicación con el 'id' obtenido
    @Override
    public CommentsDTO newComment(Long publicacion_Id, CommentsDTO commentDTO) {
        Comments comentario = mapToEntity(commentDTO); /* Mapea de DTO a Entity para poder guardar */
        /* Se obtiene los datos de la publicacion de acuerdo a su 'id' */
        Publications publicacion = pRepository.findById(publicacion_Id)
                    .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacion_Id));

        comentario.setPublicacion(publicacion); /* Se asigna los valores al campo de publicacion de la tabla 'comentarios' */
        /* Se crea una nueva variable de tipo Comments que ya contiene los datos de la publicacion y genera un nuevo comentario */
        Comments newComment = cRepository.save(comentario); 

        return mapToDTO(newComment); /* Retorna la respuesta como DTO */
    }    

    //* Método que obtiene todos los comentarios de una publicación de acuerdo a su 'id'
    @Override
    public List<CommentsDTO> getCommentsByPublicationId(Long publicacion_Id) {
        List<Comments> comentarios = cRepository.findByPublicacionId(publicacion_Id);

        /* Se mapea el comentario a DTO para poder obtenerlo y se devuelve como una lista */
        return comentarios.stream().map(comentario -> mapToDTO(comentario)).collect(Collectors.toList());       
    }

    //* Método que obtiene un comentario de una publicacion de acuerdo al 'id' de ambos
    @Override
    public CommentsDTO getCommentsById(Long publicacion_Id, Long comentario_Id) {
        Publications publicacion = pRepository.findById(publicacion_Id)
                    .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacion_Id));
        
        Comments comentario = cRepository.findById(comentario_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentario_Id));

        //* Verifica que el 'id' de la publicacion registrada en el comentario es igual al 'id' de la publicación consultada
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            //* La sentencia 'throw' se utiliza para lanzar explícitamente una excepción
            throw new AppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        return mapToDTO(comentario); /* Se mapea como DTO y se retorna */         
    }

    //* Método que actualiza un comentario de acuerdo a su 'id'
    @Override
    public CommentsDTO updateComments(Long publicacion_Id, Long comentario_Id, CommentsDTO soliCom) {
        //! VER SI SE PUEDE MODIFICAR PARA QUE NO SEA REDUNDANTE
        Publications publicacion = pRepository.findById(publicacion_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacion_Id));

        Comments comentario = cRepository.findById(comentario_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentario_Id));

        // * Verifica que el 'id' de la publicacion registrada en el comentario es igual al 'id' de la publicación consultada
        if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            // * La sentencia 'throw' se utiliza para lanzar explícitamente una excepción
            throw new AppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        comentario.setNombre(soliCom.getNombre());
        comentario.setEmail(soliCom.getEmail());
        comentario.setCuerpo(soliCom.getCuerpo());

        Comments updatedComment = cRepository.save(comentario);

        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long publicacion_Id, Long comentario_Id) {
        Publications publicacion = pRepository.findById(publicacion_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", publicacion_Id));

        Comments comentario = cRepository.findById(comentario_Id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentario_Id));
         // * Verifica que el 'id' de la publicacion registrada en el comentario es igual al 'id' de la publicación consultada
         if (!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            // * La sentencia 'throw' se utiliza para lanzar explícitamente una excepción
            throw new AppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicación");
        }

        cRepository.delete(comentario);
    }

    /* Conversión de una Entidad a DTO */
    private CommentsDTO mapToDTO(Comments comentario) {
        CommentsDTO cDTO = new CommentsDTO();
        cDTO.setId(comentario.getId());
        cDTO.setNombre(comentario.getNombre());
        cDTO.setEmail(comentario.getEmail());
        cDTO.setCuerpo(comentario.getCuerpo());

        return cDTO;
    }
    
    /* Conversión de DTO a Entidad */
    private Comments mapToEntity(CommentsDTO comentarioDTO) {
        Comments comentario = new Comments();

        comentario.setId(comentarioDTO.getId());
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());

        return comentario;
    }
   



    /** Funciones Lambda (->)
     *! Funciones Lambda (->)
     ** Las expresiones lambda son funciones anónimas, es decir, funciones que no necesitan una clase. El operador lambda (->) separa la 
     ** declaración de parámetros de la declaración del cuerpo de la función. Parámetros: Cuando se tiene un solo parámetro no es 
     ** necesario utilizar los paréntesis
     */
}