package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.projection.NomBoisson;
import com.royken.bracongo.survey.entities.projection.Question;
import com.royken.bracongo.survey.resource.IQuestionResource;
import com.royken.bracongo.survey.service.IFormatBoissonService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/question")
public class QuestionResourceImpl implements IQuestionResource{
    
    @EJB
    private IFormatBoissonService boissonService;

    public IFormatBoissonService getBoissonService() {
        return boissonService;
    }

    public void setBoissonService(IFormatBoissonService boissonService) {
        this.boissonService = boissonService;
    }   

    @Override
    public Question getQuestion() {
        try {
            Question question = new Question();
            List<NomBoisson> bgBracongo = boissonService.getAllbyEnterprise(true, TypeBoisson.BG);
            List<NomBoisson> biBracongo = boissonService.getAllbyEnterprise(true, TypeBoisson.BI);
            List<NomBoisson> bgBralima = boissonService.getAllbyEnterprise(false, TypeBoisson.BG);
            List<NomBoisson> biBralima = boissonService.getAllbyEnterprise(false, TypeBoisson.BI);
            question.setBgBracongo(bgBracongo);
            question.setBgBralima(bgBralima);
            question.setBiereBracongo(biBracongo);
            question.setBierreBralima(biBralima);
            return question;
            } catch (ServiceException ex) {
            Logger.getLogger(QuestionResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
