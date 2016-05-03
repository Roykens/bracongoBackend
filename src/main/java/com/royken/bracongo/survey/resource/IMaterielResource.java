package com.royken.bracongo.survey.resource;

import com.royken.bracongo.survey.entities.Materiel;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("materiel")
public interface IMaterielResource {
 
    @GET
    @Produces(value = "application/json")
    public List<Materiel> getAllMateriels();
}
