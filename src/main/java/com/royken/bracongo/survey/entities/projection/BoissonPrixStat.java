package com.royken.bracongo.survey.entities.projection;

import java.io.Serializable;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="boissonPrixStat")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoissonPrixStat implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Map<String, Integer> prixPve;
    
    private Map<String, Integer> prixMixte;
    
    private Map<String, Integer> prixGlobal;
    
    private Map<String, Double> ecart;
    
    /*
    * Le prix conseillé
    */
    private Map<String, Integer> prix;

    public Map<String, Integer> getPrixPve() {
        return prixPve;
    }

    public void setPrixPve(Map<String, Integer> prixPve) {
        this.prixPve = prixPve;
    }

    public Map<String, Integer> getPrixMixte() {
        return prixMixte;
    }

    public void setPrixMixte(Map<String, Integer> prixMixte) {
        this.prixMixte = prixMixte;
    }

    public Map<String, Integer> getPrixGlobal() {
        return prixGlobal;
    }

    public void setPrixGlobal(Map<String, Integer> prixGlobal) {
        this.prixGlobal = prixGlobal;
    }

    public Map<String, Integer> getPrix() {
        return prix;
    }

    public void setPrix(Map<String, Integer> prix) {
        this.prix = prix;
    }

    public Map<String, Double> getEcart() {
        return ecart;
    }

    public void setEcart(Map<String, Double> ecart) {
        this.ecart = ecart;
    }
    
    
}
