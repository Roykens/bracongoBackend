package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@XmlRootElement(name="formatBoisson")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormatBoisson implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * La liste des boissonsInfos du format de boisson
     */
    @OneToMany(mappedBy = "formatBoisson")
    private List<BoissonInfos> boissonInfoss;
    
    @Version
    private int version;
    
    /**
     * L'identifiant de l'entité dans la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Le prix conseillé du format de boisson
     */
    @Basic
    private int prix;
    
    /**
     * La boisson à laquelle ce format est associé
     */
    @ManyToOne
    private Boisson boisson;
    
    /**
     * Le format auquel ce format est associé
     */
    @ManyToOne
    private Format format;
    
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

    public Boisson getBoisson() {
        return boisson;
    }

    public void setBoisson(Boisson boisson) {
        this.boisson = boisson;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }


    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public List<BoissonInfos> getBoissonInfoss() {
        return boissonInfoss;
    }

    public void setBoissonInfoss(List<BoissonInfos> boissonInfoss) {
        this.boissonInfoss = boissonInfoss;
    }

    @Override
    public String toString() {
        return "FormatBoisson{" + "version=" + version + ", id=" + id + ", prix=" + prix + ", boisson=" + boisson + ", format=" + format + ", active=" + active + '}';
    }
    
   
    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    
    
    
    
    
}
