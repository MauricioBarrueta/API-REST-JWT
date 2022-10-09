package com.mauricio.apirestsecurityjwt.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
//! @UniqueConstraint: Establece resreicciones a la tabla
//* En este caso, establece que no puede haber una publicación con el 'titulo' repetido
@Table(name = "publicaciones", uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})
public class Publications {  
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* Se genera automáticamente y se autoincrementa */    
    @Column(unique = true, nullable = false) /* Es un valor único y no puede ser nulo */
    private Long id;

    @Column(nullable = false, name = "titulo", unique = true)
    private String titulo;
    @Column(nullable = false, name = "descripcion")
    private String descripcion;
    @Column(nullable = false, name = "contenido")
    private String contenido;

    //* @JsonBackReference: Resuelve el problema de los enlaces externos anidados o bidirecionales (recurciones infinitas).
    @JsonBackReference

    //! OneToMany: Uno a muchos, en este caso especifica que una publicación puede tener muchos comentarios
//* orphanRemoval: Util para eliminar objetos dependientes. En este caso, cada que se elimine una publicación también se eliminan los comentarios relacionados a ella
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true) //* 1 -> n
    /** HashSet
     *! Contiene un conjunto de objetos, pero de una manera que le permite determinar fácil y rápidamente si un objeto ya está en el conjunto 
     *! o no. Lo hace administrando internamente un array y almacenando el objeto utilizando un índice que se calcula a partir del código
     *! hash del objeto. 
    */
    private Set<Comments> comentarios = new HashSet<>();

    public Publications() { }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getContenido() { return contenido; }

    public void setContenido(String contenido) { this.contenido = contenido; }

    public Set<Comments> getComentarios() {
        return comentarios;
    }

    public void setComentarios(Set<Comments> comentarios) {
        this.comentarios = comentarios;
    }   
}
