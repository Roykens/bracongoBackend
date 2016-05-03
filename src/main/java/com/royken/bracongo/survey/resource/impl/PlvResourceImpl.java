package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.PLV;
import com.royken.bracongo.survey.resource.IPlvResource;
import com.royken.bracongo.survey.service.IPlvService;
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
@Path("/plv")
public class PlvResourceImpl implements IPlvResource{
    
    @EJB
    private IPlvService plvService;

    public IPlvService getPlvService() {
        return plvService;
    }

    public void setPlvService(IPlvService plvService) {
        this.plvService = plvService;
    }
       

    @Override
    public List<PLV> getAllPlv() {
        try {
           return plvService.findAllPlv();
        } catch (ServiceException ex) {
            Logger.getLogger(PlvResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
}
