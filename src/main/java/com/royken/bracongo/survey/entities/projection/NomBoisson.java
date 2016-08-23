package com.royken.bracongo.survey.entities.projection;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */

@XmlRootElement(name="nomBoisson")
@XmlAccessorType(XmlAccessType.FIELD)
public class NomBoisson implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Long idFormatBoisson;
    
    private String nomFormat;
    
    private int prix;
    
    private boolean bracongo;
    
    private boolean biere;

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

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public boolean isBracongo() {
        return bracongo;
    }

    public void setBracongo(boolean bracongo) {
        this.bracongo = bracongo;
    }

    public boolean isBiere() {
        return biere;
    }

    public void setBiere(boolean biere) {
        this.biere = biere;
    }
    
    
    
    
}