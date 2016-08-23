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
    private static final long serialVersionUID = 1L;
    
    @Version
    @XmlTransient
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    /**
     * L'identitiant d'une action dans la BD
     */
    private Long id;
    
    
    @OneToOne
    @XmlTransient
    /**
     * La reponse à laquelle l'Action est associée
     */
    private Reponse reponse;
    
    @Column(columnDefinition = "tinyint(1) default true")
    /**
     * Indique si le PDV a besoin d'un contrat
     */
    private boolean besoinDeContrat;
    
    @Column(columnDefinition = "tinyint(1) default true")
    /**
     * Indique si le PDV a besoin d'un renouvellement de contrat
     */
    private boolean besoinRenouvellementContrat; 
    
    /**
     * Indique si le PDV execute partiellement le contrat
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean contratPartiel;
    
    /**
     * Indique si le PDV a des reclamations de remise
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean reclamationRemise;
    
    /**
     * Indique si le PDV est ferme ou non operationnel
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean fermeNonOperationel;
    
    /**
     * Indique s'il s'agit d'un PDV mixte voulant devenir PVE
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean mixteSollicitantCoversion;
    
    /**
     * Indique si le PDV a besoin de l'operation 3 bacs contre 1
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinOperation3Bac1;
    
    /**
     * Le nombre de bacs à echanger dans l'operation 3bacs/1
     */
    @Basic
    private int nombreBacs;
    
    /**
     * Indique si le PDV a demenage sans prevenir
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean demenageSansPrevenir;
    
    /**
     * Indique si le PDV a besoin d'un renforcement en capacité
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean renforcerEnCapacite;
    
    /**
     * Indique si le PDV a besoin de PLV
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinPlv;
    
    /**
     * Indique si le PDV a besoin de consignation d'emballage
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoinConsignationEmballage;
    
    /**
     * Indique si le PDV a une adresse erronee
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean adresseErronee;
    
    /**
     * Indique si le PDV a besoin de 5 chaises contre 1
     */
    @Column(columnDefinition = "tinyint(1) default true")
    private boolean besoin5ChaisesContre1;
    
    /**
     * Indique les PHN du PDV etaient capsulés
     */

    @Column(columnDefinition = "tinyint(1) default true")
    private boolean phnCapsule;
    
    /**
     * Indique si l'entité est active ou supprimée (0 = supprimé, 1 = actif)
     */
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
