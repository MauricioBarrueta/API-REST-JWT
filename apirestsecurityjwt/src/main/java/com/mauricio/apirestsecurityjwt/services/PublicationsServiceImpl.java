package com.mauricio.apirestsecurityjwt.services;

import java.util.List;
import java.util.stream.Collectors;

import com.mauricio.apirestsecurityjwt.IService.PublicationsService;
import com.mauricio.apirestsecurityjwt.dto.PublicationsDTO;
import com.mauricio.apirestsecurityjwt.dto.PublicationsRes;
import com.mauricio.apirestsecurityjwt.exceptions.ResourceNotFoundException;
import com.mauricio.apirestsecurityjwt.models.Publications;
import com.mauricio.apirestsecurityjwt.repositories.PublicationsRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PublicationsServiceImpl implements PublicationsService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private PublicationsRepository pRepository;
    
    /* Método que crea una nueva publicación */    
    @Override
    public PublicationsDTO crearPublicacion(PublicationsDTO publicacionDTO) {        
        Publications publications = mapToEntity(publicacionDTO); //* Se convierten los datos DTO a Entity para poder guardarlos en la bd
        Publications saveNewPublication = pRepository.save(publications); //* Se llama al repositorio y se almacenan la entidad
        PublicationsDTO publicationResDTO = mapToDTO(saveNewPublication); //* Se convierte los datos y se retornan como DTO
        return publicationResDTO;
    }

    /* Método que lista todas las publicaciones en un array a manera de lista */
    @Override
    public PublicationsRes getAllPublications(int numPag, int pageSize, String sortBy, String sortDir) {        
        //* El sort ordena el orden (ascendente o descendente)
        /** Se implementa la condición IF anidada
        ** Si el sort equivale a ascendente y tiene un nombre se ordena de manera ascendente
        ** Si no pasa un nombre se ordena a manera descendente 
        *! Ejemplo: http://localhost:8080/publicaciones?numPage=0&pageSize=10&sortBy=id&sortDir=asc / desc
        */
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        //! Pageable: Implementa paginación y consulta
        Pageable pageable = PageRequest.of(numPag, pageSize, sort); /* Sort.by(): Indica el orden en que se ordenará */
        //! Page: Es una sublista de la lista (Pageable), obtiene la info sobre la posición del dato en la lista
        Page<Publications> publicaciones = pRepository.findAll(pageable);        
        //* Lista los datos ya con la info de su posición
        List<Publications> publicacionesList = publicaciones.getContent();

        /** CONCEPTOS
     * ! Conceptos:
    //* stream(): Permite realizar operaciones sobre la colección, como buscar, filtrar, reordenar, reducir, etc.
    //* map(): Permite representar una estructura de datos para almacenar pares "clave/valor"; para una clave solamente tenemos un valor.
    //* Collectors.toList(): Convierte la colección (stream) en un array en lista
    */
        List<PublicationsDTO> content = publicacionesList.stream().map(publicacion -> mapToDTO(publicacion)).collect(Collectors.toList()); /* Se obtienen los datos, se mapean y los regresa en una lista como DTO */
        
        //* Se le asigna el contenido DTO de la lista a la clase Respuesta
        PublicationsRes pRes = new PublicationsRes();
        pRes.setContenido(content);
        pRes.setNumeroPag(numPag);
        pRes.setPaginaSize(pageSize);
        pRes.setTotalElementos(publicaciones.getTotalElements());
        pRes.setTotalPag(publicaciones.getTotalPages());
        pRes.setLast(publicaciones.isLast());

        return pRes;
    }    

    /* Método que obtiene una publicación de acuerdo a su 'id' */
    @Override
    public PublicationsDTO getPublicationById(Long id) {        
        Publications publications = pRepository
                    .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));

        return mapToDTO(publications);        
    }

    /* Método que actualiza la publicación de acuerdo a su 'id' */
    @Override
    public PublicationsDTO updatePublication(PublicationsDTO publicationDTO, Long id) {
        //* Se obtiene su 'id'
        Publications publications = pRepository
                    .findById(id).orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
        //* Se asigna la respuesta DTO a la Entidad para posteriormente actualizarla
        publications.setTitulo(publicationDTO.getTitulo());
        publications.setDescripcion(publicationDTO.getDescripcion());
        publications.setContenido(publicationDTO.getContenido());

        Publications updatedPublication = pRepository.save(publications);

        //* Retrona la respuesta convertida a DTO
        return mapToDTO(updatedPublication);      
    }

    /* Método que elimina una publicación */
    @Override
    public void deletePublication(Long id) {
       pRepository.deleteById(id);        
    }   

    /* Método que convierte de Entidad a DTO */
    private PublicationsDTO mapToDTO(Publications publications) {       
        PublicationsDTO pDTO = modelMapper.map(publications, PublicationsDTO.class);

        return pDTO;
    }

    //! Se emplea la dependencia ModelMapper en ambos métodos, como parámetros se pasa primero el objeto y segundo el destino

    /* Método que convierte de DTO a Entidad */
    private Publications mapToEntity(PublicationsDTO publicationDTO) {        
        Publications publicacions = modelMapper.map(publicationDTO, Publications.class);
        
        return publicacions;
    }

  
}
