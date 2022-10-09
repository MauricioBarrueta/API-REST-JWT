package com.mauricio.apirestsecurityjwt.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentsDTO {

    private Long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;

    @Email
    @NotEmpty(message = "El email no puede estar vacio")
    private String email;

    @NotEmpty
    @Size(message = "Debe tener más de 10 caracteres")
    private String cuerpo;

    public CommentsDTO() { }
   
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
 
    public void setEmail(String email) { this.email = email; }

    public String getCuerpo() { return cuerpo; }

    public void setCuerpo(String cuerpo) { this.cuerpo = cuerpo; }    
}
