package com.royken.bracongo.survey.entities.projection;

import com.royken.bracongo.survey.entities.PointDeVente;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name = "planningEnqueteur")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlanningEnquetteur implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private Long idPlanning;
    
    private List<PointDeVente> pointDeVentes;

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public List<PointDeVente> getPointDeVentes() {
        return pointDeVentes;
    }

    public void setPointDeVentes(List<PointDeVente> pointDeVentes) {
        this.pointDeVentes = pointDeVentes;
    }

    @Override
    public String toString() {
        return "PlanningEnquetteur{" + "idPlanning=" + idPlanning + ", pointDeVentes=" + pointDeVentes + '}';
    }
    
    
}
