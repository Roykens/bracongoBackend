package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@XmlRootElement(name="secteur")
@XmlAccessorType(XmlAccessType.FIELD)
public class Secteur implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Version
    private int version;
    
    /**
     * L'identifiant de l'entité dans la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Le code du secteur
     */
    @Column(unique = true)
    private String code;
    
    /**
     * La liste des zones du secteur
     */
    @OneToMany(mappedBy = "secteur")
    private List<Zone> zones;
    
    /**
     * Le mao responsable du secteur
     */
    @OneToOne(mappedBy = "secteur")
    private Enqueteur enqueteur;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    @Override
    public String toString() {
        return "Secteur{" + "id=" + id + ", code=" + code + '}';
    }

    public Enqueteur getEnqueteur() {
        return enqueteur;
    }

    public void setEnqueteur(Enqueteur enqueteur) {
        this.enqueteur = enqueteur;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
    
}
