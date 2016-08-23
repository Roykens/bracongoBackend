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
@XmlRootElement(name="etatPlv")
@XmlAccessorType(XmlAccessType.FIELD)
public class EtatPlv implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Enumerated
    private TypeEtat typeEtat;
    
    @Column
    private int nombre;
    
    @Basic
    private int nombreBralima;
    
    @Enumerated
    private TypeEtat typeEtatBralima;
    
    @ManyToOne
    private PLV plv;
    
    @OneToOne
    private Reponse reponse;
    
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

    public TypeEtat getTypeEtat() {
        return typeEtat;
    }

    public void setTypeEtat(TypeEtat typeEtat) {
        this.typeEtat = typeEtat;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public int getNombreBralima() {
        return nombreBralima;
    }

    public void setNombreBralima(int nombreBralima) {
        this.nombreBralima = nombreBralima;
    }

    public TypeEtat getTypeEtatBralima() {
        return typeEtatBralima;
    }

    public void setTypeEtatBralima(TypeEtat typeEtatBralima) {
        this.typeEtatBralima = typeEtatBralima;
    }

    public PLV getPlv() {
        return plv;
    }

    public void setPlv(PLV plv) {
        this.plv = plv;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    

}
