package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.Materiel;
import com.royken.bracongo.survey.resource.IMaterielResource;
import com.royken.bracongo.survey.service.IMaterielService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/materiel")
public class MaterielResourceImpl implements IMaterielResource{
    
    @EJB
    private IMaterielService materielService;

    public IMaterielService getMaterielService() {
        return materielService;
    }

    public void setMaterielService(IMaterielService materielService) {
        this.materielService = materielService;
    }
    
    

    @Override
    public List<Materiel> getAllMateriels() {
        try {
            return materielService.findAllMateriel();
        } catch (ServiceException ex) {
            Logger.getLogger(MaterielResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
}
