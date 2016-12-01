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
    private int califCadete;
    private int califServicio;
    private String comentario;
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

    public int getCalifCadete() {
        return califCadete;
    }

    public void setCalifCadete(int califCadete) {
        this.califCadete = califCadete;
    }

    public int getCalifServicio() {
        return califServicio;
    }

    public void setCalifServicio(int califServicio) {
        this.califServicio = califServicio;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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
}
