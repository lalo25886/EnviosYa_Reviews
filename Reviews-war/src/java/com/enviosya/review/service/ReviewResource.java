
package com.enviosya.review.service;

import com.enviosya.review.domain.ReviewBean;
import com.enviosya.review.persistence.ReviewEntity;
import com.google.gson.Gson;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jms.JMSException;
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
//    @POST
//    @Path("add")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response agregar(String body) throws JMSException {
//        Gson gson = new Gson();
//        ReviewEntity u = gson.fromJson(body, ReviewEntity.class);
//        u.setFecha(new Date());
//        Response r;
//        ReviewEntity creado = reviewBean.agregar(u);
//        if (creado == null) {
//            r = Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity("Review")
//                    .build();
//        } else {
//            r = Response
//                    .status(Response.Status.CREATED)
//                    .entity(gson.toJson(creado))
//                    .build();
//        }
//        return r;
//    }
//
//    @POST
//    @Path("update")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response modificar(String body) {
//        Gson gson = new Gson();
//        ReviewEntity u = gson.fromJson(body, ReviewEntity.class);
//        Response r;
//        ReviewEntity modificado = reviewBean.modificar(u);
//        if (modificado == null) {
//            r = Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity("Review")
//                    .build();
//        } else {
//            r = Response
//                    .status(Response.Status.CREATED)
//                    .entity(gson.toJson(modificado))
//                    .build();
//        }
//        return r;
//    }
//    @POST
//    @Path("delete")
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response eliminar(String body) {
//        Gson gson = new Gson();
//        ReviewEntity u = gson.fromJson(body, ReviewEntity.class);
//        Response r;
//        Boolean modificado = reviewBean.eliminar(u);
//        if (!modificado) {
//            r = Response
//                    .status(Response.Status.BAD_REQUEST)
//                    .entity("Reviewe")
//                    .build();
//        } else {
//            r = Response
//                    .status(Response.Status.CREATED)
//                    .entity(gson.toJson(modificado))
//                    .build();
//        }
//        return r;
//    }

    @GET
    @Path("getReview/{idCadete}")
    @Consumes(MediaType.TEXT_HTML)
    public String getReviewCadete(@PathParam("idCadete") String idCadete) {
        ReviewEntity unReview = new ReviewEntity();
        unReview.setIdCadete(Long.parseLong(idCadete));
        String retorno ="";// reviewBean.buscarReview(unReview.getId());
        return retorno;
    }
}
