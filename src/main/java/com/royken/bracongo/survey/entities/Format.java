package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */

@Entity
@XmlRootElement(name="format")
@XmlAccessorType(XmlAccessType.FIELD)
public class Format implements Serializable{
    @OneToMany(mappedBy = "format")
    private List<FormatBoisson> formatBoissons;
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    private int volume;

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

    @Override
    public String toString() {
        return "Format{" + "version=" + version + ", id=" + id + ", volume=" + volume + '}';
    }
    
    
}
