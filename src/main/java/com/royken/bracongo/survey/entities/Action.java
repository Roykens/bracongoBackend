package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@XmlRootElement(name="action")
@XmlAccessorType(XmlAccessType.FIELD)
public class Action implements Serializable{
    
    @Version
    @XmlTransient
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long id;
    
    
    @OneToOne
    @XmlTransient
    private Reponse reponse;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinDeContrat;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinRenouvellementContrat; 
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean contratPartiel;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean reclamationRemise;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean fermeNonOperationel;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean mixteSollicitantCoversion;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinOperation3Bac1;
    
    @Basic
    private int nombreBacs;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean demenageSansPrevenir;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean renforcerEnCapacite;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinPlv;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinConsignationEmballage;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean adresseErronee;
    
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoin5ChaisesContre1;

    @Column(columnDefinition = "tinyint(1) default true")
    private boolean phnCapsule;
    
    @XmlTransient
    @Column(columnDefinition = "int default 1")
    private int active;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    public boolean isBesoinDeContrat() {
        return besoinDeContrat;
    }

    public void setBesoinDeContrat(boolean besoinDeContrat) {
        this.besoinDeContrat = besoinDeContrat;
    }

    public boolean isBesoinRenouvellementContrat() {
        return besoinRenouvellementContrat;
    }

    public void setBesoinRenouvellementContrat(boolean besoinRenouvellementContrat) {
        this.besoinRenouvellementContrat = besoinRenouvellementContrat;
    }

    public boolean isContratPartiel() {
        return contratPartiel;
    }

    public void setContratPartiel(boolean contratPartiel) {
        this.contratPartiel = contratPartiel;
    }

    public boolean isReclamationRemise() {
        return reclamationRemise;
    }

    public void setReclamationRemise(boolean reclamationRemise) {
        this.reclamationRemise = reclamationRemise;
    }

    public boolean isFermeNonOperationel() {
        return fermeNonOperationel;
    }

    public void setFermeNonOperationel(boolean fermeNonOperationel) {
        this.fermeNonOperationel = fermeNonOperationel;
    }

    public boolean isMixteSollicitantCoversion() {
        return mixteSollicitantCoversion;
    }

    public void setMixteSollicitantCoversion(boolean mixteSollicitantCoversion) {
        this.mixteSollicitantCoversion = mixteSollicitantCoversion;
    }

    public boolean isBesoinOperation3Bac1() {
        return besoinOperation3Bac1;
    }

    public void setBesoinOperation3Bac1(boolean besoinOperation3Bac1) {
        this.besoinOperation3Bac1 = besoinOperation3Bac1;
    }

    public int getNombreBacs() {
        return nombreBacs;
    }

    public void setNombreBacs(int nombreBacs) {
        this.nombreBacs = nombreBacs;
    }

    public boolean isDemenageSansPrevenir() {
        return demenageSansPrevenir;
    }

    public void setDemenageSansPrevenir(boolean demenageSansPrevenir) {
        this.demenageSansPrevenir = demenageSansPrevenir;
    }

    public boolean isRenforcerEnCapacite() {
        return renforcerEnCapacite;
    }

    public void setRenforcerEnCapacite(boolean renforcerEnCapacite) {
        this.renforcerEnCapacite = renforcerEnCapacite;
    }

    public boolean isBesoinPlv() {
        return besoinPlv;
    }

    public void setBesoinPlv(boolean besoinPlv) {
        this.besoinPlv = besoinPlv;
    }

    public boolean isBesoinConsignationEmballage() {
        return besoinConsignationEmballage;
    }

    public void setBesoinConsignationEmballage(boolean besoinConsignationEmballage) {
        this.besoinConsignationEmballage = besoinConsignationEmballage;
    }

    public boolean isAdresseErronee() {
        return adresseErronee;
    }

    public void setAdresseErronee(boolean adresseErronee) {
        this.adresseErronee = adresseErronee;
    }

    public boolean isBesoin5ChaisesContre1() {
        return besoin5ChaisesContre1;
    }

    public void setBesoin5ChaisesContre1(boolean besoin5ChaisesContre1) {
        this.besoin5ChaisesContre1 = besoin5ChaisesContre1;
    }

    public boolean isPhnCapsule() {
        return phnCapsule;
    }

    public void setPhnCapsule(boolean phnCapsule) {
        this.phnCapsule = phnCapsule;
    }

    @Override
    public String toString() {
        return "Action{" + "reponse=" + reponse + ", besoinDeContrat=" + besoinDeContrat + ", besoinRenouvellementContrat=" + besoinRenouvellementContrat + ", contratPartiel=" + contratPartiel + ", reclamationRemise=" + reclamationRemise + ", fermeNonOperationel=" + fermeNonOperationel + ", mixteSollicitantCoversion=" + mixteSollicitantCoversion + ", besoinOperation3Bac1=" + besoinOperation3Bac1 + ", nombreBacs=" + nombreBacs + ", demenageSansPrevenir=" + demenageSansPrevenir + ", renforcerEnCapacite=" + renforcerEnCapacite + ", besoinPlv=" + besoinPlv + ", besoinConsignationEmballage=" + besoinConsignationEmballage + ", adresseErronee=" + adresseErronee + ", besoin5ChaisesContre1=" + besoin5ChaisesContre1 + ", phnCapsule=" + phnCapsule + '}';
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    
    
    
}
