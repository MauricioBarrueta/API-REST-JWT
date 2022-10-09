package com.mauricio.apirestsecurityjwt.IService;

import com.mauricio.apirestsecurityjwt.dto.PublicationsDTO;
import com.mauricio.apirestsecurityjwt.dto.PublicationsRes;

//!
public interface PublicationsService {    
    
    public PublicationsDTO crearPublicacion(PublicationsDTO publicacionDTO);

    public PublicationsRes getAllPublications(int numPag, int pageSize, String sortBy, String sortDir);

    public PublicationsDTO getPublicationById(Long id);

    public PublicationsDTO updatePublication(PublicationsDTO publicationDTO, Long id);

    public void deletePublication(Long id);
}
