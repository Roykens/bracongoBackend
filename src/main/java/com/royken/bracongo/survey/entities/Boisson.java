package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@XmlRootElement(name="boisson")
@XmlAccessorType(XmlAccessType.FIELD)
public class Boisson implements Serializable{
    @XmlTransient
    @OneToMany(mappedBy = "boisson")
    private List<FormatBoisson> formatBoissons;
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true)
    private String nom;
    
    @Enumerated
    private TypeBoisson typeBoisson;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean isBracongo;
    
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public TypeBoisson getTypeBoisson() {
        return typeBoisson;
    }

    public void setTypeBoisson(TypeBoisson typeBoisson) {
        this.typeBoisson = typeBoisson;
    }

    public boolean isIsBracongo() {
        return isBracongo;
    }

    public void setIsBracongo(boolean isBracongo) {
        this.isBracongo = isBracongo;
    }

    public List<FormatBoisson> getFormatBoissons() {
        return formatBoissons;
    }

    public void setFormatBoissons(List<FormatBoisson> formatBoissons) {
        this.formatBoissons = formatBoissons;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    

    @Override
    public String toString() {
        return "Boisson{" + "version=" + version + ", id=" + id + ", nom=" + nom + ", typeBoisson=" + typeBoisson + ", isBracongo=" + isBracongo + '}';
    }
    
    
    
}
