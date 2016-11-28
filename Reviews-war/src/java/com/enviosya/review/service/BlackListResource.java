/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enviosya.review.service;

import com.enviosya.review.domain.BlackListBean;
import com.enviosya.review.persistence.BlackListEntity;
import com.google.gson.Gson;
import java.util.List;
import javax.ejb.EJB;
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
 * @author Gustavo
 */
@Path("blacklist")
public class BlackListResource {

@EJB
    private BlackListBean blackListBean;

    @Context
    private UriInfo context;

    public BlackListResource() {
    }

    @GET
    @Path("getBlackList")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        List<BlackListEntity> list = blackListBean.listar();
        Gson gson = new Gson();
        return gson.toJson(list);
    }
    @GET
    @Path("getBlackList/{id}")
    @Consumes(MediaType.TEXT_HTML)
    public String getRevieweNotificar(@PathParam("id") String id) {
        BlackListEntity unReview = new BlackListEntity();
        unReview.setId(Long.parseLong(id));
        String retorno ="";// blackListBean.buscarReview(unReview.getId());
        return retorno;
    }
     @POST
    @Path("getBlackListPalabra")
    @Produces(MediaType.APPLICATION_JSON)
    public String getBlackListPalabra(String body) {
        Gson gson = new Gson();
        BlackListEntity u = gson.fromJson(body, BlackListEntity.class);
        System.out.println("llego a ws: " + body);
        BlackListBean unaBlackList = new BlackListBean();
       
         List<BlackListEntity> list = unaBlackList.buscar(u.getPalabra().trim());
        
        return gson.toJson(list);
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregar(String body) {
        Gson gson = new Gson();
        BlackListEntity u = gson.fromJson(body, BlackListEntity.class);
        Response r;
        BlackListEntity creado = blackListBean.agregar(u);
        if (creado == null) {
            r = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Review")
                    .build();
        } else {
            r = Response
                    .status(Response.Status.CREATED)
                    .entity(gson.toJson(creado))
                    .build();
        }
        return r;
    }

    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificar(String body) {
        Gson gson = new Gson();
        BlackListEntity u = gson.fromJson(body, BlackListEntity.class);
        Response r;
        BlackListEntity modificado = blackListBean.modificar(u);
        if (modificado == null) {
            r = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Review")
                    .build();
        } else {
            r = Response
                    .status(Response.Status.CREATED)
                    .entity(gson.toJson(modificado))
                    .build();
        }
        return r;
    }
    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminar(String body) {
        Gson gson = new Gson();
        BlackListEntity u = gson.fromJson(body, BlackListEntity.class);
        Response r;
        Boolean modificado = blackListBean.eliminar(u);
        if (!modificado) {
            r = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Reviewe")
                    .build();
        } else {
            r = Response
                    .status(Response.Status.CREATED)
                    .entity(gson.toJson(modificado))
                    .build();
        }
        return r;
    }    
}
