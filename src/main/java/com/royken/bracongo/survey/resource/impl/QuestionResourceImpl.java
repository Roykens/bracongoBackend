package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.Materiel;
import com.royken.bracongo.survey.entities.PLV;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.projection.NomBoisson;
import com.royken.bracongo.survey.entities.projection.Question;
import com.royken.bracongo.survey.resource.IQuestionResource;
import com.royken.bracongo.survey.service.IFormatBoissonService;
import com.royken.bracongo.survey.service.IMaterielService;
import com.royken.bracongo.survey.service.IPlvService;
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
    
    @EJB
    private IMaterielService materielService;
    
    @EJB
    private IPlvService plvService;

    public IFormatBoissonService getBoissonService() {
        return boissonService;
    }

    public void setBoissonService(IFormatBoissonService boissonService) {
        this.boissonService = boissonService;
    }   

    public IMaterielService getMaterielService() {
        return materielService;
    }

    public void setMaterielService(IMaterielService materielService) {
        this.materielService = materielService;
    }

    public IPlvService getPlvService() {
        return plvService;
    }

    public void setPlvService(IPlvService plvService) {
        this.plvService = plvService;
    }
  
    @Override
    public Question getQuestion() {
        try {
            Question question = new Question();
            List<NomBoisson> bgBracongo = boissonService.getAllbyEnterprise(true, TypeBoisson.BG);
            List<NomBoisson> biBracongo = boissonService.getAllbyEnterprise(true, TypeBoisson.BI);
            List<NomBoisson> bgBralima = boissonService.getAllbyEnterprise(false, TypeBoisson.BG);
            List<NomBoisson> biBralima = boissonService.getAllbyEnterprise(false, TypeBoisson.BI);
            List<Materiel> materiels = materielService.findAllMateriel();
            List<PLV> plvs = plvService.findAllPlv();
            question.setBgBracongo(bgBracongo);
            question.setBgBralima(bgBralima);
            question.setBiereBracongo(biBracongo);
            question.setBierreBralima(biBralima);
            question.setMateriels(materiels);
            question.setPlvs(plvs);
            return question;
            } catch (ServiceException ex) {
            Logger.getLogger(QuestionResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
