package com.enviosya.review.persistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Gonzalo
 */
@Entity
public class ReviewEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    private Long idEnvio;
    
    @NotNull
    private int califEmisorCadete;
    
    @NotNull
    private int califReceptorCadete;
    
    @NotNull
    private int califEmisorServicio;
    
    @NotNull
    private int califReceptorServicio;
    
    @Column(length = 300)
    private String comentarioEmisor;
    
    @Column(length = 300)
    private String comentarioReceptor;
    
    @NotNull
    @Column(length = 300)
    private String estado;
    
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReviewEntity)) {
            return false;
        }
        ReviewEntity other = (ReviewEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReviewEntity id=" + id;
    }
    
}
