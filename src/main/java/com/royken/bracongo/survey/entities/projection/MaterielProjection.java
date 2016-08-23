package com.royken.bracongo.survey.entities.projection;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="materielProjection")
@XmlAccessorType(XmlAccessType.FIELD)
public class MaterielProjection implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private int idServeur;

    private String nom;

    private String nombreBrac;

    private String EtatBrac;

    private String nombreCon;

    private String EtatConc;

    private String nombreCasseBrac;

    private String nombreCasseConc;
    
    private String jourCasse;

    public int getIdServeur() {
        return idServeur;
    }

    public void setIdServeur(int idServeur) {
        this.idServeur = idServeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNombreBrac() {
        return nombreBrac;
    }

    public void setNombreBrac(String nombreBrac) {
        this.nombreBrac = nombreBrac;
    }

    public String getEtatBrac() {
        return EtatBrac;
    }

    public void setEtatBrac(String EtatBrac) {
        this.EtatBrac = EtatBrac;
    }

    public String getNombreCon() {
        return nombreCon;
    }

    public void setNombreCon(String nombreCon) {
        this.nombreCon = nombreCon;
    }

    public String getEtatConc() {
        return EtatConc;
    }

    public void setEtatConc(String EtatConc) {
        this.EtatConc = EtatConc;
    }

    public String getNombreCasseBrac() {
        return nombreCasseBrac;
    }

    public void setNombreCasseBrac(String nombreCasseBrac) {
        this.nombreCasseBrac = nombreCasseBrac;
    }

    public String getNombreCasseConc() {
        return nombreCasseConc;
    }

    public void setNombreCasseConc(String nombreCasseConc) {
        this.nombreCasseConc = nombreCasseConc;
    }

    public String getJourCasse() {
        return jourCasse;
    }

    public void setJourCasse(String jourCasse) {
        this.jourCasse = jourCasse;
    }
    
    

    @Override
    public String toString() {
        return "MaterielProjection{" + "idServeur=" + idServeur + ", nom=" + nom + ", nombreBrac=" + nombreBrac + ", EtatBrac=" + EtatBrac + ", nombreCon=" + nombreCon + ", EtatConc=" + EtatConc + ", nombreCasseBrac=" + nombreCasseBrac + ", nombreCasseConc=" + nombreCasseConc + '}';
    }
    
    
    
}
