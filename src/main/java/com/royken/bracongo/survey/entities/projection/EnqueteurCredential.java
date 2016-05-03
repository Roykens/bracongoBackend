package com.royken.bracongo.survey.entities.projection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="credential")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnqueteurCredential {
    
    private boolean isvalide;
    
    private String username;

    public EnqueteurCredential() {
    }

    public EnqueteurCredential(boolean isvalide, String username) {
        this.isvalide = isvalide;
        this.username = username;
    }
    
    

    public boolean isIsvalide() {
        return isvalide;
    }

    public void setIsvalide(boolean isvalide) {
        this.isvalide = isvalide;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
