package com.royken.bracongo.survey.resource;

import com.royken.bracongo.survey.entities.PLV;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("plv")
public interface IPlvResource {
    
    @GET
    @Produces(value = "application/json")
    public List<PLV> getAllPlv();
}
