package com.royken.bracongo.survey.resource;

import com.royken.bracongo.survey.entities.projection.NomBoisson;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("boisson")
public interface IBoissonResource {
    
    @GET
    @Produces(value = "application/json")
    List<NomBoisson> getAllBoisson();
    
}
