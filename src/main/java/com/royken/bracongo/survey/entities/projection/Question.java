package com.royken.bracongo.survey.entities.projection;

import com.royken.bracongo.survey.entities.Materiel;
import com.royken.bracongo.survey.entities.PLV;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="question")
@XmlAccessorType(XmlAccessType.FIELD)
public class Question implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private List<NomBoisson> biereBracongo;
    
    private List<NomBoisson> bgBracongo;
    
    private List<NomBoisson> bierreBralima;
    
    private List<NomBoisson> bgBralima;
    
    private List<PLV> plvs;
    
    private List<Materiel> materiels;

    public List<NomBoisson> getBiereBracongo() {
        return biereBracongo;
    }

    public void setBiereBracongo(List<NomBoisson> biereBracongo) {
        this.biereBracongo = biereBracongo;
    }

    public List<NomBoisson> getBgBracongo() {
        return bgBracongo;
    }

    public void setBgBracongo(List<NomBoisson> bgBracongo) {
        this.bgBracongo = bgBracongo;
    }

    public List<NomBoisson> getBierreBralima() {
        return bierreBralima;
    }

    public void setBierreBralima(List<NomBoisson> bierreBralima) {
        this.bierreBralima = bierreBralima;
    }

    public List<NomBoisson> getBgBralima() {
        return bgBralima;
    }

    public void setBgBralima(List<NomBoisson> bgBralima) {
        this.bgBralima = bgBralima;
    }

    public List<PLV> getPlvs() {
        return plvs;
    }

    public void setPlvs(List<PLV> plvs) {
        this.plvs = plvs;
    }

    public List<Materiel> getMateriels() {
        return materiels;
    }

    public void setMateriels(List<Materiel> materiels) {
        this.materiels = materiels;
    }
    
}
