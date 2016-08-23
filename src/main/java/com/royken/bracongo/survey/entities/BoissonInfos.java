package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@XmlRootElement(name="boissonInfos")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoissonInfos implements Serializable{
    
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
     * Indique si le format de boisson est disponible au PDV
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean disponibilite;
    
    /**
     * Le prix du format de boisson dans le PDV
     */
    @Basic
    private int prixPdv;
    
    /**
     * Le stock chaud du format de boisson dans le PDV
     */
    @Basic
    private int stockChaud;
    
    /**
     * Le format de boisson
     */
    @ManyToOne
    private FormatBoisson  formatBoisson;
    
    /**
     * La reponse à laquele l'infos est associée
     */
    @ManyToOne
    private Reponse reponse;
    
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

    public boolean isDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(boolean disponibilite) {
        this.disponibilite = disponibilite;
    }

    public int getPrixPdv() {
        return prixPdv;
    }

    public void setPrixPdv(int prixPdv) {
        this.prixPdv = prixPdv;
    }

    public int getStockChaud() {
        return stockChaud;
    }

    public void setStockChaud(int stockChaud) {
        this.stockChaud = stockChaud;
    }

    public FormatBoisson getFormatBoisson() {
        return formatBoisson;
    }

    public void setFormatBoisson(FormatBoisson formatBoisson) {
        this.formatBoisson = formatBoisson;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    @Override
    public String toString() {
        return "BoissonInfos{" + "id=" + id + ", disponibilite=" + disponibilite + ", prixPdv=" + prixPdv + ", stockChaud=" + stockChaud + ", formatBoisson=" + formatBoisson + '}';
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
    
}
