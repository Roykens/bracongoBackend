package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.service.IPlvService;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "plvBean")
@RequestScoped
public class PlvBean {

    @EJB
    private IPlvService plvService;
    
    
    /**
     * Creates a new instance of PlvBean
     */
    public PlvBean() {
    }
    
}
