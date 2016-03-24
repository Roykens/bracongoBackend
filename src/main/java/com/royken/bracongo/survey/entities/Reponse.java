package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
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
@XmlRootElement(name="reponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reponse implements Serializable{
    
    
    @Version
    @XmlTransient
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private TypePdv typePdv;
    
    
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date heureDeVisite;
   
    @Basic
    private int jourDepuisDernierPassageFVD;
    
    @Enumerated
    private ReponseValue srdBracongo;
    
    
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date heurePassageSrdBracongo;
    
    @Basic
    private int jourDernierPassageFVDBralimba;
    
    @Enumerated
    private ReponseValue srdBralimba;
    
    
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date heurePassageSrdBralimba;
    
    @Basic
    private int nombrePHN;
    
    @Enumerated
    private ReponseValue verificationFifo;
    
    @OneToOne
    private EtatMateriel etatMateriel;
    
    
    @OneToOne
    private DisponibiliteBoisson disponibiliteBoisson;
    
    @OneToOne
    private PrixBoisson prixBoisson;
   
    @ManyToOne
    private Enqueteur enqueteur;
    
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

    public TypePdv getTypePdv() {
        return typePdv;
    }

    public void setTypePdv(TypePdv typePdv) {
        this.typePdv = typePdv;
    }

    public Date getHeureDeVisite() {
        return heureDeVisite;
    }

    public void setHeureDeVisite(Date heureDeVisite) {
        this.heureDeVisite = heureDeVisite;
    }

    public int getJourDepuisDernierPassageFVD() {
        return jourDepuisDernierPassageFVD;
    }

    public void setJourDepuisDernierPassageFVD(int jourDepuisDernierPassageFVD) {
        this.jourDepuisDernierPassageFVD = jourDepuisDernierPassageFVD;
    }

    public ReponseValue getSrdBracongo() {
        return srdBracongo;
    }

    public void setSrdBracongo(ReponseValue srdBracongo) {
        this.srdBracongo = srdBracongo;
    }

    public Date getHeurePassageSrdBracongo() {
        return heurePassageSrdBracongo;
    }

    public void setHeurePassageSrdBracongo(Date heurePassageSrdBracongo) {
        this.heurePassageSrdBracongo = heurePassageSrdBracongo;
    }

    public int getJourDernierPassageFVDBralimba() {
        return jourDernierPassageFVDBralimba;
    }

    public void setJourDernierPassageFVDBralimba(int jourDernierPassageFVDBralimba) {
        this.jourDernierPassageFVDBralimba = jourDernierPassageFVDBralimba;
    }

    public ReponseValue getSrdBralimba() {
        return srdBralimba;
    }

    public void setSrdBralimba(ReponseValue srdBralimba) {
        this.srdBralimba = srdBralimba;
    }

    public Date getHeurePassageSrdBralimba() {
        return heurePassageSrdBralimba;
    }

    public void setHeurePassageSrdBralimba(Date heurePassageSrdBralimba) {
        this.heurePassageSrdBralimba = heurePassageSrdBralimba;
    }

    public int getNombrePHN() {
        return nombrePHN;
    }

    public void setNombrePHN(int nombrePHN) {
        this.nombrePHN = nombrePHN;
    }

    public ReponseValue getVerificationFifo() {
        return verificationFifo;
    }

    public void setVerificationFifo(ReponseValue verificationFifo) {
        this.verificationFifo = verificationFifo;
    }

    public EtatMateriel getEtatMateriel() {
        return etatMateriel;
    }

    public void setEtatMateriel(EtatMateriel etatMateriel) {
        this.etatMateriel = etatMateriel;
    }


    public DisponibiliteBoisson getDisponibiliteBoisson() {
        return disponibiliteBoisson;
    }

    public void setDisponibiliteBoisson(DisponibiliteBoisson disponibiliteBoisson) {
        this.disponibiliteBoisson = disponibiliteBoisson;
    }

    public PrixBoisson getPrixBoisson() {
        return prixBoisson;
    }

    public void setPrixBoisson(PrixBoisson prixBoisson) {
        this.prixBoisson = prixBoisson;
    }

    public Enqueteur getEnqueteur() {
        return enqueteur;
    }

    public void setEnqueteur(Enqueteur enqueteur) {
        this.enqueteur = enqueteur;
    }
    
    

}
