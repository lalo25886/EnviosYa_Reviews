
package com.enviosya.review.domain;

import com.enviosya.review.persistence.ReviewEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJBException;
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
import javax.persistence.PersistenceException;
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
    @Resource(lookup = "jms/QueueReview")
    private Queue queueReview;
    static Logger log = Logger.getLogger("FILE");
    @PersistenceContext
    private EntityManager em;
    private BlackListBean bl;
    @PostConstruct
    private void init() {
    }

    public ReviewEntity agregar(ReviewEntity unReviewEntity) throws JMSException {
        try {
            em.persist(unReviewEntity);
            this.notificar(unReviewEntity);
            return unReviewEntity;
        } catch (PersistenceException | JMSException e) {
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
    
    public boolean tieneTextoBlackList(String texto)
        throws PersistenceException {
        List<String> listaPalabras;
        try {
            listaPalabras =
                    em.createQuery("select u.palabra from BlackListEntity u")
                    .getResultList();
            String[] textoSpliteado = texto.split(" ");
            if (listaPalabras.isEmpty()) {
                return false;
            }
            for (int i = 0; i <= textoSpliteado.length; i++) {
                if (listaPalabras.contains(textoSpliteado[i].trim())) {
                    return true;
                }
            }
            return false;
        } catch (PersistenceException e) {
            log.error("Error en tieneTextoBlackList:"
                       + " " + e.getMessage());
               throw new PersistenceException("Error en tieneTextoBlackList.");
        }
    }
    public boolean validoClienteEnEnvio(Long idEnvio, Long idCliente)
            throws MalformedURLException, IOException, EJBException {
        String link = "http://localhost:8080/Shipments-war/shipment/isclient/"
                + idEnvio + "/" + idCliente;
        String error = "0";
        String r = "";
        try {

            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                                    + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output = "";
            while ((output = br.readLine()) != null) {
                    r = output;
            }
            System.out.println("VALIDO CLIENTE RES: " + r);
            conn.disconnect();
            if (r.equalsIgnoreCase(error)) {
                return false;
            }
        } catch (MalformedURLException ex) {
            log.error("Error en validoClienteEnEnvio[1]:"
                       + " " + ex.getMessage());
               throw new MalformedURLException("Error en la URL: " + link);
        } catch (EJBException ex) {
            log.error("Error en validoClienteEnEnvio[1]:"
                       + " " + ex.getMessage());
               throw new MalformedURLException("Error en la URL: " + link);
        } catch (IOException ex) {
           log.error("Error en validoClienteEnEnvio[2]:"
                       + " " + ex.getMessage());
               throw new IOException("Error en validoClienteEnEnvio.");
        }
        return true;
    }

    public boolean validoCantMinimaDePalabras(String texto) {
        String[] textoSpliteado = texto.split(" ");
        if (textoSpliteado.length > 3 ) {
            return true;
        }
        return false;
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
      public boolean isNumeric(String cadena) {
	try {
		Integer.parseInt(cadena);
		return true;
	} catch (NumberFormatException nfe) {
		return false;
	}
      }
      public boolean enRango(int valor) {
	try {
                if (valor > 0 && valor < 6) {
                    return true;
                } else {
                    return false;
                }
	} catch (NumberFormatException nfe) {
		return false;
	}
      }
      public boolean tieneReviewPendiente(Long envio, String estado) 
              throws PersistenceException {
        List<ReviewEntity> listaReview;
        try {
        listaReview = em.createQuery("select u from ReviewEntity u "
                + "where u.idEnvio = :id and u.estado = :estado")
                .setParameter("id", envio)
                .setParameter("estado", estado)
                .getResultList();
        return !listaReview.isEmpty();

        } catch (PersistenceException e) {
            log.error("Error en tieneReviewPendiente(Long envio, "
                    + "String estado): " + e.getMessage());
            throw new PersistenceException("Error en tieneReviewPendiente"
                    + "(Long envio, String estado). ");
        }
    }
    public int calificarSemantica (String texto) {
        // Acá se debería invocar un servicio que retorne la calificación
        // del texto. En nuestro caso envíamos siempre el mismo ya que el
        //profesor nos comenta que lo hagamos así
        return 1;
    }
    public void notificar(ReviewEntity review) throws JMSException {

        try (
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession() ) {

            MessageProducer productorDeMensajeReview =
                        session.createProducer(queueReview);

            Message mensaje =
                session.createTextMessage("El proceso de review del cliente "
                        + "número " + review.getIdCliente() + " ha "
                        + "finalizado correctamente.");
                productorDeMensajeReview.send(mensaje);
        session.close();
        } catch (JMSException ex) {
            log.error("Error al notificar la review número " + review.getId());
            throw new JMSException("Error al notificar la review "
                    + "número " + review.getId());
        }
    }
}

