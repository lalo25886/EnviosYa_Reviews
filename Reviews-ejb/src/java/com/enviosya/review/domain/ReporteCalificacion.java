/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enviosya.review.domain;

import com.enviosya.review.persistence.ReviewEntity;
import java.util.List;

/**
 *
 * @author Gonzalo
 */
public class ReporteCalificacion {
    private Long idEnvio;
    private Long idCadete;
    private List<ReviewEntity> listaCalificaciones;

    public Long getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(Long idEnvio) {
        this.idEnvio = idEnvio;
    }

    public Long getIdCadete() {
        return idCadete;
    }

    public void setIdCadete(Long idCadete) {
        this.idCadete = idCadete;
    }

    public List<ReviewEntity> getListaCalificaciones() {
        return listaCalificaciones;
    }

    public void setListaCalificaciones(List<ReviewEntity> listaCalificaciones) {
        this.listaCalificaciones = listaCalificaciones;
    }
}
