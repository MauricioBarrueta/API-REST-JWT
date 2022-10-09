package com.mauricio.apirestsecurityjwt.controllers;

import java.util.List;

import javax.validation.Valid;

import com.mauricio.apirestsecurityjwt.IService.CommentsService;
import com.mauricio.apirestsecurityjwt.dto.CommentsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publicaciones")
public class CommentsController {
    @Autowired
    private CommentsService cService;

    /* Se añade el método que muestra todos los comentarios de una publicación */
    @GetMapping("/{publicacion_Id}/comentarios")
    public List<CommentsDTO> listCommentsByPublication(@PathVariable("publicacion_Id") Long publicacion_Id) {
        return cService.getCommentsByPublicationId(publicacion_Id);
    }

    /* Se añade el método que muestra un comentario de acuerdo al 'id' de la publicación y el 'id' del comentario */
    @GetMapping("/{publicacion_Id}/comentarios/{id}")
    public ResponseEntity<CommentsDTO> getCommentById(@PathVariable("publicacion_Id") Long publicacion_Id, @PathVariable("id") Long comentario_Id) {
        CommentsDTO cDTO = cService.getCommentsById(publicacion_Id, comentario_Id);
        return new ResponseEntity<>(cDTO, HttpStatus.OK);
    }

    /* Se añade el método que inserta un comentario en una publiación en especifico, de acuerdo a su 'id' */
    @PostMapping("/{publicacion_Id}/comentarios")
    public ResponseEntity<CommentsDTO> saveComment(@Valid @PathVariable("publicacion_Id") Long publicacion_Id, @Valid @RequestBody CommentsDTO cDTO) {
        return new ResponseEntity<>(cService.newComment(publicacion_Id, cDTO), HttpStatus.OK);
    }

    /* Se añade el método que actualiza un comentario de acuerdo al 'id' y al 'id' de la publicación */
    @PutMapping("/{publicacion_Id}/comentarios/{id}")
    public ResponseEntity<CommentsDTO> updateComment(@PathVariable("publicacion_Id") Long publicacion_Id, @PathVariable("id") Long comentario_Id, @Valid @RequestBody CommentsDTO cDTO) {
        CommentsDTO updateComment = cService.updateComments(publicacion_Id, comentario_Id, cDTO);
        return new ResponseEntity<>(updateComment, HttpStatus.OK);
    }

    @DeleteMapping("/{publicacion_Id}/comentarios/{id}")
    public void deleteCommentById(@PathVariable("publicacion_Id") Long publicacion_Id, @PathVariable("id") Long comentario_Id) {
        cService.deleteComment(publicacion_Id, comentario_Id);
    }

    /** ResponseEntity
     *! ResponseEntity:
     ** Maneja toda la respuesta HTTP incluyendo el cuerpo, cabecera y códigos de estado permitiéndonos total libertad de configurar
     ** la respuesta que queremos que se envié desde nuestros endpoints.
     */
}
