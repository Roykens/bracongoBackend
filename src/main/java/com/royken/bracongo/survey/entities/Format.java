package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@XmlRootElement(name="format")
@XmlAccessorType(XmlAccessType.FIELD)
public class Format implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * La liste des formats de boisson du format
     */
    @OneToMany(mappedBy = "format")
    private List<FormatBoisson> formatBoissons;
    
    @Version
    private int version;
    
    /**
     * L'identifiant de l'entité dans la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Le volume du format
     */
    @Basic
    private int volume;
    
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
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
        return "Format{" + "version=" + version + ", id=" + id + ", volume=" + volume + '}';
    }
    
    
}
