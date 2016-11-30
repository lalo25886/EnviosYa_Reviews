
package com.enviosya.review.domain;

import com.enviosya.review.persistence.ReviewEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
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
    @Resource(lookup = "jms/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/QueueCadete")
    private Queue queueCadete;
    static Logger log = Logger.getLogger("FILE");
    @PersistenceContext
    private EntityManager em;
    private BlackListBean bl;
    @PostConstruct
    private void init() {
    }

    public ReviewEntity agregar(ReviewEntity unReviewEntity) throws JMSException {
        try {
            if (validoTextoContraBlackList(unReviewEntity.getComentarioEmisor())){
                if (validoClienteEnEnvio(unReviewEntity.getIdCliente(), unReviewEntity.getIdEnvio())){
                    if (validoCantMinimaDePalabras(unReviewEntity.getComentarioEmisor())){
                        if (noExisteReviewEnvio(unReviewEntity.getIdEnvio())){
                            unReviewEntity.setEstado("Pending");
                            em.persist(unReviewEntity);
                              try (
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession()) {
                              MessageProducer productorDeMensajeCadete =
                    session.createProducer(queueCadete);
                              Message mensaje =
            session.createTextMessage("Creo Review");
            productorDeMensajeCadete.send(mensaje);
                              }
                            return unReviewEntity;
                        }else{
                            log.error("Error en validacion de si existe review");
                        }
                    }else{
                        log.error("Error en validacion cantidad minima de palabras");
                    }
                }else{
                    log.error("Error en validacion Cliente en envio");
                }
            }else{
                unReviewEntity.setEstado("Rejected");
                em.persist(unReviewEntity);
                return unReviewEntity;
            }                
        } catch (Exception e) {
            log.error("Error en agregar Review Entity: " + e.getMessage());
        }
         return null;
    }

    public ReviewEntity modificar(ReviewEntity unReviewEntity) {
        try {
            em.merge(unReviewEntity);
            return unReviewEntity;
        } catch (Exception e) {
             log.error("Error en eliminar Review Entity: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminar(ReviewEntity unReviewEntity) {
        try {
            ReviewEntity aBorrar =
            em.find(ReviewEntity.class, unReviewEntity.getId());
            em.remove(aBorrar);
            return true;
        } catch (Exception e) {
             log.error("Error en eliminar Review Entity: " + e.getMessage());

        }
          return false;
    }

    public List<ReviewEntity> listar() {
        List<ReviewEntity> list =
                em.createQuery("select u from ReviewEntity u").getResultList();
        return list;
    }

    public List<ReviewEntity> buscar(Long id) {
        List<ReviewEntity> listaReview =
                em.createQuery("select u from ReviewEntity u "
                + "where u.id = :id")
                .setParameter("id", id).getResultList();
        return listaReview;
    }
    public boolean noExisteReviewEnvio(Long id) {
      /*  List<ReviewEntity> listaReview =
                em.createQuery("select u from ReviewEntity u "
                + "where u.idEnvio = :idEnvio")
                .setParameter("idEnvio", id).getResultList();
        return listaReview.isEmpty();
        */
      return true;
    }
    
    private boolean validoTextoContraBlackList(String texto)
    {
        /*bl = new BlackListBean();
        String[] textoSpliteado = texto.split(" ");
        for (int i = 0; i < textoSpliteado.length; i++) {
            if(bl.existe(textoSpliteado[i].trim()))
            {
                  return false;
            }
        }*/
        return true;
    }
    
    private boolean validoClienteEnEnvio(int idCliente, Long idEnvio)
    {
        return true;
    }
    
    private boolean validoCantMinimaDePalabras(String texto)
    {
        String[] textoSpliteado = texto.split(" ");
        /*for (int i = 0; i < textoSpliteado.length; i++) {
            if(!bl.existe(textoSpliteado[i]))
            {
                  return false;
            }
        }*/
        return true;
    }
    public List<ReviewEntity> listarClientesEnvios() {
        List<ReviewEntity> listaClientes = em.createQuery("SELECT u "
                + "FROM ReviewEntity u",
               ReviewEntity.class).getResultList();
       return listaClientes;
   }

    public List<ReviewEntity> listarReviewsCadete(Long idCadete) {
        List<ReviewEntity> listaClientes = em.createQuery("SELECT u "
                + "FROM ReviewEntity u",
               ReviewEntity.class).getResultList();
       return listaClientes;
   }
}

