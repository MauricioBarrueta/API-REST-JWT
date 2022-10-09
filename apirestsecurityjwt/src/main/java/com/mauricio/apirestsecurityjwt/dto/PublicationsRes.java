package com.mauricio.apirestsecurityjwt.dto;

import java.util.List;

public class PublicationsRes {

    private List<PublicationsDTO> contenido;
    private int numeroPag;
    private int paginaSize;
    private long totalElementos;
    private int totalPag;
    private boolean last;

    public PublicationsRes() { }
  
    public List<PublicationsDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<PublicationsDTO> contenido) {
        this.contenido = contenido;
    }
   
    public int getNumeroPag() {
        return numeroPag;
    }

    public void setNumeroPag(int numeroPag) {
        this.numeroPag = numeroPag;
    }

    public int getPaginaSize() {
        return paginaSize;
    }

    public void setPaginaSize(int paginaSize) {
        this.paginaSize = paginaSize;
    }

    public long getTotalElementos() {
        return totalElementos;
    }

    public void setTotalElementos(long totalElementos) {
        this.totalElementos = totalElementos;
    }

    public int getTotalPag() {
        return totalPag;
    }

    public void setTotalPag(int totalPag) {
        this.totalPag = totalPag;
    }
 
    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}

