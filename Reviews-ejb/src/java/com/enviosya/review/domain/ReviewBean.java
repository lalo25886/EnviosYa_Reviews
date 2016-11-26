
package com.enviosya.review.domain;

import com.enviosya.review.persistence.ReviewEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Gonzalo
 */
@Stateless
@LocalBean
public class ReviewBean {

    static Logger log = Logger.getLogger("FILE");
    @PersistenceContext
    private EntityManager em;
    @PostConstruct
    private void init() {
    }

    public ReviewEntity agregar(ReviewEntity unClientEntity) {
        try {
            em.persist(unClientEntity);
            return unClientEntity;
        } catch (Exception e) {
            log.error("Error en agregar Cliente Entity: " + e.getMessage());
        }
         return null;
    }

    public ReviewEntity modificar(ReviewEntity unClienteEntity) {
        try {
            em.merge(unClienteEntity);
            return unClienteEntity;
        } catch (Exception e) {
             log.error("Error en eliminar Cliente Entity: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminar(ReviewEntity unClientEntity) {
        try {
            ReviewEntity aBorrar =
            em.find(ReviewEntity.class, unClientEntity.getId());
            em.remove(aBorrar);
            return true;
        } catch (Exception e) {
             log.error("Error en eliminar Cliente Entity: " + e.getMessage());

        }
          return false;
    }

    public List<ReviewEntity> listar() {
        List<ReviewEntity> list =
                em.createQuery("select u from ClientEntity u").getResultList();
        return list;
    }

//    public Client buscar(Long id) {
//        ReviewEntity ent = em.find(ReviewEntity.class, id);
//        Client u = new Client();
//        u.setId(ent.getId());
//        u.setNombre(ent.getNombre());
//        return u;
//    }

    public List<ReviewEntity> buscar(String nombre) {
        List<ReviewEntity> listaCliente =
                em.createQuery("select u from ClientEntity u "
                + "where u.nombre = :nombre")
                .setParameter("nombre", nombre).getResultList();
        return listaCliente;
    }

    public List<ReviewEntity> listarClientesEnvios() {
        List<ReviewEntity> listaClientes = em.createQuery("SELECT u "
                + "FROM ClientEntity u",
               ReviewEntity.class).getResultList();
       return listaClientes;
   }


}

