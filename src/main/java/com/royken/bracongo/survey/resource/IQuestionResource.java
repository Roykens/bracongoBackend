package com.royken.bracongo.survey.resource;

import com.royken.bracongo.survey.entities.Question;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/question")
public interface IQuestionResource {
    
    @GET
    @Produces(value = "application/json")
    List<Question> getAllQuestion();
}
