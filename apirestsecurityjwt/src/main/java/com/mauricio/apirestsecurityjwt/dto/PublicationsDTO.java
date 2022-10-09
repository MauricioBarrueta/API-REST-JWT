package com.mauricio.apirestsecurityjwt.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.mauricio.apirestsecurityjwt.models.Comments;

//! DTO (Objeto de Transferencia de Datos): Transporta datos entre procesos..
/**
 ** Tiene como finalidad de crear un objeto plano (POJO) con una serie de atributos que puedan ser enviados o recuperados del servidor 
 ** en una sola invocación, de tal forma que un DTO puede contener información de múltiples fuentes o tablas y concentrarlas 
 ** en una única clase simple. */
public class PublicationsDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "El titulo debe tener más de 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min = 2, message = "La descripción debe tener más de 2 caracteres")
    private String descripcion;

    @NotEmpty
    @Size(min = 2, message = "El contenido debe contar con más de 2 caracteres")
    private String contenido;

    //! Set: Coleccion que no puede contener elementos duplicados
    //* También se añaden los getters y setters en el modelo
    private Set<Comments> comentarios; //* Para mostrar tambien sus comentarios

    public PublicationsDTO() { }

    public PublicationsDTO(Long id, String titulo, String descripcion, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }

    public Long getId() { return id; }   

    public void setId(Long id) { this.id = id; }
  
    public String getTitulo() { return titulo; }
   
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
 
    public void setDescripcion(String descripcion) { this.descripcion = descripcion;  }
  
    public String getContenido() { return contenido; }

    public void setContenido(String contenido) { this.contenido = contenido; }

    public Set<Comments> getComentarios() {  return comentarios; }

    public void setComentarios(Set<Comments> comentarios) { this.comentarios = comentarios; }   
}
