package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
@XmlRootElement(name="etatMateriel")
@XmlAccessorType(XmlAccessType.FIELD)
public class EtatMateriel implements Serializable{
    @OneToOne(mappedBy = "etatMateriel")
    private Reponse reponse;
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    private int nombre;
    
    @Enumerated
    private TypeEtat typeEtat;
    
    @Enumerated
    private TypeEtat typeEtatConc;
    
    @Basic
    private int nombreDefecteux;
    
    @Basic
    private int nombreDefectueuxConc;
    
    @Basic
    private int jourCasse;
    
    @Basic
    private int nombreBralima;
    
    @ManyToOne
    private Materiel materiel;

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

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public TypeEtat getTypeEtat() {
        return typeEtat;
    }

    public void setTypeEtat(TypeEtat typeEtat) {
        this.typeEtat = typeEtat;
    }

    public int getNombreDefecteux() {
        return nombreDefecteux;
    }

    public void setNombreDefecteux(int nombreDefecteux) {
        this.nombreDefecteux = nombreDefecteux;
    }

    public int getNombreBralima() {
        return nombreBralima;
    }

    public void setNombreBralima(int nombreBralima) {
        this.nombreBralima = nombreBralima;
    }

    public Materiel getMateriel() {
        return materiel;
    }

    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public TypeEtat getTypeEtatConc() {
        return typeEtatConc;
    }

    public void setTypeEtatConc(TypeEtat typeEtatConc) {
        this.typeEtatConc = typeEtatConc;
    }

    public int getNombreDefectueuxConc() {
        return nombreDefectueuxConc;
    }

    public void setNombreDefectueuxConc(int nombreDefectueuxConc) {
        this.nombreDefectueuxConc = nombreDefectueuxConc;
    }

    public int getJourCasse() {
        return jourCasse;
    }

    public void setJourCasse(int jourCasse) {
        this.jourCasse = jourCasse;
    }
    
    
}
