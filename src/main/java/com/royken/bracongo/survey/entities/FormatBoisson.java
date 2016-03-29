package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
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

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
@XmlRootElement(name="formatBoisson")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormatBoisson implements Serializable{
    @OneToMany(mappedBy = "formatBoisson")
    private List<StockChaud> stockChauds;
    
    @OneToMany(mappedBy = "formatBoisson")
    private List<PrixBoisson> prixBoissons;
    @OneToMany(mappedBy = "formatBoisson")
    private List<DisponibiliteBoisson> disponibiliteBoissons;
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Boisson boisson;
    
    @ManyToOne
    private Format format;

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

   public List<PrixBoisson> getPrixBoissons() {
        return prixBoissons;
    }

    public void setPrixBoissons(List<PrixBoisson> prixBoissons) {
        this.prixBoissons = prixBoissons;
    }

    public List<DisponibiliteBoisson> getDisponibiliteBoissons() {
        return disponibiliteBoissons;
    }

    public void setDisponibiliteBoissons(List<DisponibiliteBoisson> disponibiliteBoissons) {
        this.disponibiliteBoissons = disponibiliteBoissons;
    }

    public List<StockChaud> getStockChauds() {
        return stockChauds;
    }

    public void setStockChauds(List<StockChaud> stockChauds) {
        this.stockChauds = stockChauds;
    }
    
    
}
