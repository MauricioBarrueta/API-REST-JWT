package com.mauricio.apirestsecurityjwt.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comentarios")
public class Comments {   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false, name = "nombre")
    private String nombre;
    @Column(nullable = false, name = "email")
    private String email;
    @Column(nullable = false, name = "cuerpo")
    private String cuerpo;

    //! ManyToOne: Anotación que relaciona las entidades, en este caso indica que muchos comentarios pueden estar en una publicacion
    /** Conceptos:
     *! Fetch: Se utiliza para determinar cómo debe ser cargada la entidad
     *! LAZY(perezoso): Indica que la relación solo se cargará cuando la propiedad sea leída por primera vez.
     *! EAGER(ansioso): Indica que la relación debe de ser cargada al momento de cargar la entidad.
     */
    @ManyToOne(fetch = FetchType.LAZY) //* n -> 1
    //! JoinColumn: Se utiliza para marcar una propiedad la cual requiere de un JOIN (unión) para poder accederlas,
    @JoinColumn(name = "publicacion_Id", nullable = false)
    private Publications publicacion;

    public Comments() { }
    
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getCuerpo() { return cuerpo; }

    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }

    public Publications getPublicacion() { return publicacion; }

    public void setPublicacion(Publications publicacion) { this.publicacion = publicacion; }
}
