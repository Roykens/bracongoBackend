package com.royken.bracongo.survey.entities.projection;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="boissonProjection")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoissonProjection implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private String nom;
    private int idSrveur;
    private String prix;
    private String stock;
    private boolean disponible;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdSrveur() {
        return idSrveur;
    }

    public void setIdSrveur(int idSrveur) {
        this.idSrveur = idSrveur;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "BoissonProjection{" + "nom=" + nom + ", idSrveur=" + idSrveur + ", prix=" + prix + ", stock=" + stock + ", disponible=" + disponible + '}';
    }
    
    
    
}
