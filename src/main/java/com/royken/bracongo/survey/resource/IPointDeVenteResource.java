package com.royken.bracongo.survey.resource;

import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.projection.PlanningEnquetteur;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/pdv")
public interface IPointDeVenteResource {
    
    @GET
    @Produces(value = "application/json")
    public List<PointDeVente> getAllPointDeVente();
    
    @GET
    @Produces(value = "application/json")
    @Path(value = "{id : \\d+}")
    public PlanningEnquetteur getAllPointDeVenteByEnqueteur(@PathParam(value = "id")long id);
    
    @GET
    @Produces(value = "application/json")
    @Path(value = "planning/{login}/{password}")
    public PlanningEnquetteur getAllPointDeVenteByEnqueteur(@PathParam(value = "login")String login, @PathParam(value = "password") String password);
}
