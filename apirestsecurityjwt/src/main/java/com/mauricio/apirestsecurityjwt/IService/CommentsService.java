package com.mauricio.apirestsecurityjwt.IService;

import java.util.List;

import com.mauricio.apirestsecurityjwt.dto.CommentsDTO;

public interface CommentsService {

    public CommentsDTO newComment(Long publicacion_Id, CommentsDTO commentDTO); //* Se pasa el 'id' de la publicación y el comentario que irá en ella

    public List<CommentsDTO> getCommentsByPublicationId(Long publicacion_Id);

    public CommentsDTO getCommentsById(Long publicacion_Id, Long comentario_Id);

    public CommentsDTO updateComments(Long publicacion_Id, Long comentario_Id, CommentsDTO soliCom);
    
    public void deleteComment(Long publicacion_Id, Long comentario_Id);
}
