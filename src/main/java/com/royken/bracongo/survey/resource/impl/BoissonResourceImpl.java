package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.projection.NomBoisson;
import com.royken.bracongo.survey.resource.IBoissonResource;
import com.royken.bracongo.survey.service.IBoissonService;
import com.royken.bracongo.survey.service.IFormatBoissonService;
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
@Path("/boisson")
public class BoissonResourceImpl implements IBoissonResource{
    
    @EJB
    private IBoissonService boissonService;
    
    @EJB
    private IFormatBoissonService formatBoissonService;

    public IBoissonService getBoissonService() {
        return boissonService;
    }

    public void setBoissonService(IBoissonService boissonService) {
        this.boissonService = boissonService;
    }

    public IFormatBoissonService getFormatBoissonService() {
        return formatBoissonService;
    }

    public void setFormatBoissonService(IFormatBoissonService formatBoissonService) {
        this.formatBoissonService = formatBoissonService;
    }
    
    

    @Override
    public List<NomBoisson> getAllBoisson() {
        try {
            return formatBoissonService.getAllbyEnterprise(null, null);
        } catch (ServiceException ex) {
            Logger.getLogger(BoissonResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }
    
}
