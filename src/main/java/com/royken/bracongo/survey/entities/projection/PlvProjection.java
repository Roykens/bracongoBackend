package com.royken.bracongo.survey.entities.projection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="plvProjection")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlvProjection {
    
     private String nom;

    private int idServeur;

    private String nombreBrac;

    private String nombreConc;

    private String etatBrac;

    private String etatConc;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(int idServeur) {
        this.idServeur = idServeur;
    }

    public String getNombreBrac() {
        return nombreBrac;
    }

    public void setNombreBrac(String nombreBrac) {
        this.nombreBrac = nombreBrac;
    }

    public String getNombreConc() {
        return nombreConc;
    }

    public void setNombreConc(String nombreConc) {
        this.nombreConc = nombreConc;
    }

    public String getEtatBrac() {
        return etatBrac;
    }

    public void setEtatBrac(String etatBrac) {
        this.etatBrac = etatBrac;
    }

    public String getEtatConc() {
        return etatConc;
    }

    public void setEtatConc(String etatConc) {
        this.etatConc = etatConc;
    }

    @Override
    public String toString() {
        return "PlvProjection{" + "nom=" + nom + ", idServeur=" + idServeur + ", nombreBrac=" + nombreBrac + ", nombreConc=" + nombreConc + ", etatBrac=" + etatBrac + ", etatConc=" + etatConc + '}';
    }
    
    
    
}
