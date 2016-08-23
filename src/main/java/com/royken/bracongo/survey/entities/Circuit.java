package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@XmlRootElement(name="circuit")
@XmlAccessorType(XmlAccessType.FIELD)
public class Circuit implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Version
    @XmlTransient
    private int version;
    
    /**
     * L'identifiant du circuit dans la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Le code du circuit
     */
    @Column
    private String code;
    
    /**
     * La liste des points de vente du circuit
     */
    @OneToMany(mappedBy = "circuit")
    private List<PointDeVente> pointDeVentes;
    
    /**
     * La zone dans laquelle se trouve le circuit
     */
    @ManyToOne
    private Zone zone;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<PointDeVente> getPointDeVentes() {
        return pointDeVentes;
    }

    public void setPointDeVentes(List<PointDeVente> pointDeVentes) {
        this.pointDeVentes = pointDeVentes;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    
    
    @Override
    public String toString() {
        return "Circuit{" + "version=" + version + ", id=" + id + ", code=" + code + ", zone=" + zone + '}';
    }
    
    
}
