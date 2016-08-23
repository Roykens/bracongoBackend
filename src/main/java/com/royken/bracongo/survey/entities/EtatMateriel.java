package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
@XmlRootElement(name="etatMateriel")
@XmlAccessorType(XmlAccessType.FIELD)
public class EtatMateriel implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @OneToOne(mappedBy = "etatMateriel")
    private Reponse reponse;
    
    @Version
    private int version;
    
    /**
     * L'identifiant de l'entité dans la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Le nombre de matériel dans le PDV
     */
    @Basic
    private int nombre;
    
    /**
     * L'etat général du matériel (M,B,TB)
     */
    @Enumerated
    private TypeEtat typeEtat;
    
    /**
     * L'etat général du matériel de la concurrence
     */
    @Enumerated
    private TypeEtat typeEtatConc;
    
    /**
     * Le nombre de matériel défectueux
     */
    @Basic
    private int nombreDefecteux;
    
    @Basic
    private int nombreDefectueuxConc;
    
    @Basic
    private int jourCasse;
    
    @Basic
    private int nombreBralima;
    
    /**
     * Le matériel 
     */
    @ManyToOne
    private Materiel materiel;
    
    /**
     * Indique si l'entité est active ou supprimée (0 = supprimé, 1 = actif)
     */
    @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;

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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
}
