package com.royken.bracongo.survey.resource;

import com.royken.bracongo.survey.entities.projection.EnqueteurCredential;
import com.royken.bracongo.survey.entities.projection.PlanningEnquetteur;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/authenticate")
public interface IAuthenticationResource {
    
    @GET
    @Produces(value = "application/json")
    @Path(value = "{user}/{passwd}")
    public EnqueteurCredential checkLogin(@PathParam(value = "user")String username,@PathParam(value = "passwd")String password);

    
}
