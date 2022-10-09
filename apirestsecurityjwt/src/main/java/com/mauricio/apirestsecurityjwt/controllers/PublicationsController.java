package com.mauricio.apirestsecurityjwt.controllers;

import com.mauricio.apirestsecurityjwt.utils.AppConstantes;

import javax.validation.Valid;

import com.mauricio.apirestsecurityjwt.IService.PublicationsService;
import com.mauricio.apirestsecurityjwt.dto.PublicationsDTO;
import com.mauricio.apirestsecurityjwt.dto.PublicationsRes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicationsController {
    @Autowired
    private PublicationsService pService;

    //* Se añade el método que lista todas las publicaciones
    @GetMapping
    public PublicationsRes ListAllPublications(
        @RequestParam(value = "numPag", defaultValue = AppConstantes.DEFAULT_PAGE_NUMBER, required = false) int numPag, 
        @RequestParam(value = "pageSize", defaultValue = AppConstantes.DEFAULT_PAGE_SIZE, required = false) int pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstantes.DEFAULT_SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstantes.DEFAULT_DIR, required = false) String sortDir) {
        
        return pService.getAllPublications(numPag, pageSize, sortBy, sortDir);
        /** Ejemplo:
         ** http://localhost:8080/publicaciones/?numPag=2&pageSize=5
         */
    }

    //* Se añade el método que guarda una publicación
    @PreAuthorize("hasRole('ADMIN')") 
    @PostMapping    
    public ResponseEntity<PublicationsDTO> savePublication(@Valid @RequestBody PublicationsDTO publiationsDTO) {
        return new ResponseEntity<>(pService.crearPublicacion(publiationsDTO), HttpStatus.CREATED);
    }    

    //* Se añade el método que mustra un registro por su 'id'
    @GetMapping(path = "/{id}")
    public ResponseEntity<PublicationsDTO> getPublicationById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pService.getPublicationById(id));
    }
   
    //* Se añade el método que actualiza una publicación tomando su 'id'
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<PublicationsDTO> updatePublication(@Valid @RequestBody PublicationsDTO publicationDTO, @PathVariable("id") Long id) {
        PublicationsDTO publicationRes = pService.updatePublication(publicationDTO, id);

        return new ResponseEntity<>(publicationRes, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePublicationById(@PathVariable("id") Long id) {    
        pService.deletePublication(id);
    }

    //* ETIQUETAS:
    //! @PreAuthorize: Indica que el usuario debe ser de tipo ADMIN
    //! @Valid: Aplica las validaciones antes de realizar el post
    
}