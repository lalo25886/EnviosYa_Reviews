package com.enviosya.review.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Gonzalo
 */
@Entity
@XmlRootElement
public class ReviewEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long idEnvio;

    private Long idCliente;

    private int califCadete;

    private int califServicio;

    @Column(length = 300)
    private String comentario;

    @NotNull
    @Column(length = 300)
    private String estado;

    @Temporal(TemporalType.TIMESTAMP)
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
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null
                ? id.hashCode()
                : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ReviewEntity)) {
            return false;
        }
        ReviewEntity other = (ReviewEntity) object;
        if ((this.id == null
                && other.id != null)
                || (this.id != null
                && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReviewEntity id=" + id;
    }
}
