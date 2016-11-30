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

    private Long idCliente;//Cliente que envia la review

    private int califEmisorCadete; //Calificacion del emisor sobre el cadete

    private int califReceptorCadete; //Calificacion del receptor sobre el cadete

    private int califEmisorServicio;//Calificacion del emisor sobre el servicio

    private int califReceptorServicio;//Calificacion del receptor sobre el cadete

    @Column(length = 300)
    private String comentarioEmisor;

    @Column(length = 300)
    private String comentarioReceptor;

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
