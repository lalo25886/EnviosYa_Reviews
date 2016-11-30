/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enviosya.review.domain;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Gonzalo
 */
public class Review {
    private Long id;
    private Long idEnvio;
    private Long idCliente;
    private int califEmisorCadete;
    private int califReceptorCadete;
    private int califEmisorServicio;
    private int califReceptorServicio;
    private String comentarioEmisor;
    private String comentarioReceptor;
    private String estado;
    private Date fecha;
    private Long idCadete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Long idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public int getCalifEmisorCadete() {
        return califEmisorCadete;
    }

    public void setCalifEmisorCadete(int califEmisorCadete) {
        this.califEmisorCadete = califEmisorCadete;
    }

    public int getCalifReceptorCadete() {
        return califReceptorCadete;
    }

    public void setCalifReceptorCadete(int califReceptorCadete) {
        this.califReceptorCadete = califReceptorCadete;
    }

    public int getCalifEmisorServicio() {
        return califEmisorServicio;
    }

    public void setCalifEmisorServicio(int califEmisorServicio) {
        this.califEmisorServicio = califEmisorServicio;
    }

    public int getCalifReceptorServicio() {
        return califReceptorServicio;
    }

    public void setCalifReceptorServicio(int califReceptorServicio) {
        this.califReceptorServicio = califReceptorServicio;
    }

    public String getComentarioEmisor() {
        return comentarioEmisor;
    }

    public void setComentarioEmisor(String comentarioEmisor) {
        this.comentarioEmisor = comentarioEmisor;
    }


    public String getComentarioReceptor() {
        return comentarioReceptor;
    }

    public void setComentarioReceptor(String comentarioReceptor) {
        this.comentarioReceptor = comentarioReceptor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Long getIdCadete() {
        return idCadete;
    }

    public void setIdCadete(Long idCadete) {
        this.idCadete = idCadete;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.idEnvio);
        hash = 37 * hash + this.califEmisorCadete;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Review other = (Review) obj;
        if (this.califEmisorCadete != other.califEmisorCadete) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.idEnvio, other.idEnvio)) {
            return false;
        }
        return true;
    }

    
}
