package com.royken.bracongo.survey.entities.projection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */

@XmlRootElement(name="nomBoisson")
@XmlAccessorType(XmlAccessType.FIELD)
public class NomBoisson {
    
    private Long idFormatBoisson;
    
    private String nomFormat;

    public Long getIdFormatBoisson() {
        return idFormatBoisson;
    }

    public void setIdFormatBoisson(Long idFormatBoisson) {
        this.idFormatBoisson = idFormatBoisson;
    }

    public String getNomFormat() {
        return nomFormat;
    }

    public void setNomFormat(String nomFormat) {
        this.nomFormat = nomFormat;
    }
    
    
}