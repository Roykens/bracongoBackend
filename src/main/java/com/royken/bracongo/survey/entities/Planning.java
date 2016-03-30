package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
@XmlRootElement(name="planning")
@XmlAccessorType(XmlAccessType.FIELD)
public class Planning implements Serializable{
    @OneToMany(mappedBy = "planning")
    private List<PlanningPdv> planningPdvs;
    @OneToOne(mappedBy = "planning")
    private Reponse reponse;
    
    @XmlTransient
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Enqueteur enqueteur;
    
    @OneToMany
    private List<PointDeVente> pointDeVentes;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datePlaning;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enqueteur getEnqueteur() {
        return enqueteur;
    }

    public void setEnqueteur(Enqueteur enqueteur) {
        this.enqueteur = enqueteur;
    }

    public List<PointDeVente> getPointDeVentes() {
        return pointDeVentes;
    }

    public void setPointDeVentes(List<PointDeVente> pointDeVentes) {
        this.pointDeVentes = pointDeVentes;
    }

    public Date getDatePlaning() {
        return datePlaning;
    }

    public void setDatePlaning(Date datePlaning) {
        this.datePlaning = datePlaning;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public List<PlanningPdv> getPlanningPdvs() {
        return planningPdvs;
    }

    public void setPlanningPdvs(List<PlanningPdv> planningPdvs) {
        this.planningPdvs = planningPdvs;
    }

    @Override
    public String toString() {
        return "Planning{" + "version=" + version + ", id=" + id + ", enqueteur=" + enqueteur + ", datePlaning=" + datePlaning + '}';
    }
    
    
}
