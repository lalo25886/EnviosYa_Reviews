/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enviosya.review.domain;

import com.enviosya.review.persistence.BlackListEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo
 */
@Stateless
@LocalBean
public class BlackListBean {
 static Logger log = Logger.getLogger("FILE");
    @PersistenceContext
    private EntityManager em;
    @PostConstruct
    private void init() {
    }

    public BlackListEntity agregar(BlackListEntity unBlackListEntity) {
        try {
            em.persist(unBlackListEntity);
            return unBlackListEntity;
        } catch (Exception e) {
            log.error("Error en agregar Cliente Entity: " + e.getMessage());
        }
         return null;
    }

    public BlackListEntity modificar(BlackListEntity unClienteEntity) {
        try {
            em.merge(unClienteEntity);
            return unClienteEntity;
        } catch (Exception e) {
             log.error("Error en eliminar Cliente Entity: " + e.getMessage());
        }
        return null;
    }

    public boolean eliminar(BlackListEntity unBlackListEntity) {
        try {
            BlackListEntity aBorrar =
            em.find(BlackListEntity.class, unBlackListEntity.getId());
            em.remove(aBorrar);
            return true;
        } catch (Exception e) {
             log.error("Error en eliminar Cliente Entity: " + e.getMessage());

        }
          return false;
    }

    public List<BlackListEntity> listar() {
        List<BlackListEntity> list =
                em.createQuery("select u from BlackListEntity u").getResultList();
        return list;
    }

//    public Client buscar(Long id) {
//        BlackListEntity ent = em.find(BlackListEntity.class, id);
//        Client u = new Client();
//        u.setId(ent.getId());
//        u.setNombre(ent.getNombre());
//        return u;
//    }

    public List<BlackListEntity> buscar(String palabra) {
        System.out.println("palabra: " + palabra);
         List<BlackListEntity> listaCliente = em.createQuery("SELECT u.* FROM BlackListEntity u "
                + "WHERE u.palabra = :palabra",BlackListEntity.class)
                .setParameter("palabra", palabra).getResultList();
        System.err.println(listaCliente.isEmpty()+"LALO");
        return listaCliente;
    }
     public boolean existe(String palabra) {
        List<BlackListEntity> listaCliente =
                em.createQuery("select  from BlackListEntity u "
                + "where u.palabra = :palabra")
                .setParameter("palabra", palabra).getResultList();
       
        return listaCliente.isEmpty();
        
    }

    public List<BlackListEntity> listarClientesEnvios() {
        List<BlackListEntity> listaClientes = em.createQuery("SELECT u "
                + "FROM ClientEntity u",
               BlackListEntity.class).getResultList();
       return listaClientes;
   }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
