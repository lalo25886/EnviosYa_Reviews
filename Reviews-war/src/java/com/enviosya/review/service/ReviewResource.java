
package com.enviosya.review.service;

import com.enviosya.review.domain.ReporteCalificacion;
import com.enviosya.review.domain.ReviewBean;
import com.enviosya.review.persistence.ReviewEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.jms.JMSException;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Gonzalo
 */
@Path("review")
public class ReviewResource {
    @EJB
    private ReviewBean reviewBean;

    @Context
    private UriInfo context;

    public ReviewResource() {
    }

    @GET
    @Path("getReviews")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        List<ReviewEntity> list = reviewBean.listar();
        Gson gson = new Gson();
        return gson.toJson(list);
    }
//    @GET
//    @Path("getReview/{id}")
//    @Consumes(MediaType.TEXT_HTML)
//    public String getRevieweNotificar(@PathParam("id") String id) {
//        ReviewEntity unReview = new ReviewEntity();
//        unReview.setId(Long.parseLong(id));
//        String retorno ="";// reviewBean.buscarReview(unReview.getId());
//        return retorno;
//    }
//
    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregar(String body) {
        Gson gson = new Gson();
        String vacio = "";
        ReviewEntity u = gson.fromJson(body, ReviewEntity.class);
  Response r;
        String estado = "pending";
        try {
            if (reviewBean.tieneReviewPendiente(u.getIdEnvio(), estado)) {
                String error = "Error al agregar una review. "
                + "Existen reviews pendientes para el envío número "
                        + u.getIdEnvio() + ".";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (u.getComentario().equalsIgnoreCase(vacio)) {
                String error = "Error al agregar una review. "
                + "Debe ingresar un comentario.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (!reviewBean.isNumeric(String.valueOf(u.getIdCliente()))) {
                String error = "Error al agregar una review. "
                + "Verfique los datos del envío y del cliente.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (!reviewBean.enRango(u.getCalifCadete())
                || !reviewBean.enRango(u.getCalifServicio())) {
                String error = "Error al agregar una review. "
                + "Las calificaciones deben ser un valor numérico entre"
                        + " 1 y 5";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (!reviewBean
                    .validoClienteEnEnvio(u.getIdEnvio(), u.getIdCliente())) {
                String error = "Error al agregar una review. "
                + "El cliente con id: " + u.getIdCliente() + "no fue quien "
                        + "realizó el envío";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (!reviewBean
                    .validoCantMinimaDePalabras(u.getComentario())) {
                 String error = "Error al agregar una review. "
                + "El comentario debe contener mas palabras.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (reviewBean.tieneTextoBlackList(u.getComentario())) {
                u.setEstado("rejected");
            }
            int calif = reviewBean.calificarSemantica(u.getComentario());
            //calificaciones
            // 1 - positivo
            // 2 - neutro
            // 3 - negativo
            if (calif == 1) {
                u.setEstado("approved");
            } else {
                u.setEstado("rejected");
            }
            u.setFecha(new Date());
            ReviewEntity creado = reviewBean.agregar(u);
             return Response
                            .status(Response.Status.CREATED)
                            .entity("La review se creo exitosamente "
                                    + "con el numero: " + creado.getId() + ".")
                            .build();
        } catch (IOException ex) {
            String error = "Error al agregar una review. "
                + "Verifique los datos. Excepción: IOException";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        } catch (NumberFormatException ex) {
            String error = "Error al agregar una review. "
                + "Verifique los datos. Excepción: NumberFormatException "
                    + ex.getMessage();
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();

        } catch (JMSException ex) {
            String error = "Error al agregar una review. "
                + "Verifique los datos. Excepción: JMSException";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        } catch (PersistenceException ex) {
            String error = "Error al agregar una review. "
                + "Verifique los datos. Excepción: PersistenceException";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        }
    }


    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificar(String body) {
        Gson gson = new Gson();
        ReviewEntity u = gson.fromJson(body, ReviewEntity.class);
        Response r;
        try {
        ReviewEntity modificado = reviewBean.modificar(u);
        return Response
                    .status(Response.Status.CREATED)
                    .entity(gson.toJson(modificado))
                    .build();
        } catch (PersistenceException  e) {
            String error = "Error al modidicar una review. "
                + "Verifique los datos. Excepción: PersistenceException";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        }
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminar(String body) {
        Gson gson = new Gson();
        ReviewEntity u = gson.fromJson(body, ReviewEntity.class);
        Response r;
        Boolean modificado = reviewBean.eliminar(u);
        if (!modificado) {
            String error = "Error al eliminar una review. "
                + "Verifique los datos.";
            r = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        } else {
            r = Response
                    .status(Response.Status.CREATED)
                    .entity(gson.toJson(modificado))
                    .build();
        }
        return r;
    }

    @GET
    @Path("getReview/{idCadete}")
    @Consumes(MediaType.TEXT_HTML)
    public String getReviewCadete(@PathParam("idCadete") String idCadete) {
        ReviewEntity unReview = new ReviewEntity();
        unReview.setIdCadete(Long.parseLong(idCadete));
        String retorno ="";
        return retorno;
    }

    @POST
    @Path("add2")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarDos(String body) {
        Gson gson = new Gson();
        String vacio = "";

        ReviewEntity u = gson.fromJson(body, ReviewEntity.class);
  Response r;
        String estado = "pending";
        try {
            if (reviewBean.tieneReviewPendiente(u.getIdEnvio(), estado)) {
                String error = "Error al agregar una review. "
                + "Existen reviews pendientes para el envío número "
                        + u.getIdEnvio() + ".";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (u.getComentario().equalsIgnoreCase(vacio)) {
                String error = "Error al agregar una review. "
                + "Debe ingresar un comentario.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (!reviewBean.isNumeric(String.valueOf(u.getIdCliente()))) {
                String error = "Error al agregar una review. "
                + "Verfique los datos del envío y del cliente.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (!reviewBean.enRango(u.getCalifCadete())
                || !reviewBean.enRango(u.getCalifServicio())) {
                String error = "Error al agregar una review. "
                + "Las calificaciones deben ser un valor numérico entre"
                        + " 1 y 5";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }

            if (!reviewBean
                    .validoCantMinimaDePalabras(u.getComentario())) {
                 String error = "Error al agregar una review. "
                + "El comentario debe contener mas palabras.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            if (reviewBean.tieneTextoBlackList(u.getComentario())) {
                u.setEstado("rejected");
            }
            int calif =
                    reviewBean.calificarSemantica(u.getComentario());
            //calificaciones
            // 1 - positivo
            // 2 - neutro
            // 3 - negativo
            if (calif == 1) {
                u.setEstado("approved");
            } else {
                u.setEstado("rejected");
            }
            u.setFecha(new Date());
            ReviewEntity creado = reviewBean.agregar(u);
             return Response
                            .status(Response.Status.CREATED)
                            .entity("La review se creo exitosamente "
                                    + "con el numero: " + creado.getId() + ".")
                            .build();
        } catch (NumberFormatException ex) {
            String error = "Error al agregar una review. "
                + "Verifique los datos. Excepción: NumberFormatException "
                    + ex.getMessage();
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();

        } catch (JMSException ex) {
            String error = "Error al agregar una review. "
                + "Verifique los datos. Excepción: JMSException";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        } catch (PersistenceException ex) {
            String error = "Error al agregar una review. "
                + "Verifique los datos. Excepción: PersistenceException";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        }
    }
    @POST
    @Path("reporteCalif/{idCliente}/{idEnvio}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response reporteCalif(@PathParam("idCliente") String idC, 
                                 @PathParam("idEnvio") String idE) {
        Gson responses = new GsonBuilder().create();
        ReviewEntity unR = null;
        try {
            System.out.println(idC + " y " + idE );
            System.out.println("IS NUMERIC");
            if (!reviewBean.isNumeric(idE) || !reviewBean.isNumeric(idC)) {
                String error = "Error al obtener reporte. "
                + "Verfique los datos del envío.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            System.out.println("validoClienteLogueado LLLLLLLLLLLLLLLL");
            if (!reviewBean.validoClienteLogueado(Long.valueOf(idC))) {
                 String error = "Error al obtener reporte. "
                + "El usuario no esta logueado.";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            System.out.println("obtenerReview LLLLLLLLLLLLLLLL");
            List<ReviewEntity> lista =
                    reviewBean.obtenerReview(Long.parseLong(idE));
            if (lista.isEmpty()) {
                String error = "Error al obtener reporte. "
                + "No existe un envío con el id " + idE + ".";
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
            }
            for (int i = 0; i <= lista.size(); i++) {
                 if (lista.get(i) != null) {
                     unR = lista.get(i);
                     i = lista.size() + 1;
                 }
            }
            List<ReviewEntity> listaRet =
                    reviewBean.obtenerCalif(unR.getIdCadete(), "approved");
            ReporteCalificacion reporte = new ReporteCalificacion();
            reporte.setIdEnvio(unR.getIdEnvio());
            reporte.setIdCadete(unR.getIdCadete());
            reporte.setListaCalificaciones(listaRet);
            String i = responses.toJson(reporte);
            return Response
                            .status(Response.Status.CREATED)
                            .entity(i)
                            .build();
        } catch (PersistenceException e) {
            String error = responses.toJson("[1] Error al obtener reporte. "
                + "Verifique los datos.");
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        } catch (IOException ex) {
             String error = responses.toJson("[2] Error al obtener reporte. "
                + "Verifique los datos.");
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        } catch (EJBException ex) {
             String error = responses.toJson("[3] Error al obtener reporte. "
                + "Verifique los datos.");
                return Response
                            .status(Response.Status.ACCEPTED)
                            .entity(error)
                            .build();
        }
    }
}
