package com.royken.bracongo.survey.resource;

import com.royken.bracongo.survey.entities.projection.FormatReponse;
import com.royken.bracongo.survey.entities.projection.ReponseProjection;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */


public interface IReponseResource {
    
    @POST
    @Produces(value = "application/json") 
    void createAnnee(ReponseProjection projection);
    
    @GET
    @Path(value = "zozo")
    int zozo();
    
    @GET
    @Path(value = "tata")
    int tata();
    
   // @POST
    //Toto insert(Toto toto);
    
}
