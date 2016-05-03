package com.royken.bracongo.survey.resource.impl;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.projection.EnqueteurCredential;
import com.royken.bracongo.survey.resource.IAuthenticationResource;
import com.royken.bracongo.survey.service.IEnqueteurService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Path("/authenticate")
public class AuthenticationResourceImpl implements IAuthenticationResource{
    
    @EJB
    private IEnqueteurService enqueteurService;

    public IEnqueteurService getEnqueteurService() {
        return enqueteurService;
    }

    public void setEnqueteurService(IEnqueteurService enqueteurService) {
        this.enqueteurService = enqueteurService;
    }
    
    

    @Override
    public EnqueteurCredential checkLogin(String username, String password) {
        try {
            Enqueteur enqueteur = enqueteurService.findByLoginAndPassord(username, password);
            if(enqueteur != null){
                EnqueteurCredential enqueteurCredential = new EnqueteurCredential(true, enqueteur.getUsername());
                return enqueteurCredential;
            }
        } catch (ServiceException ex) {
            Logger.getLogger(AuthenticationResourceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new EnqueteurCredential(false, "");
    }
    
}
