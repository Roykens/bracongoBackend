package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@XmlRootElement(name="enqueteur")
@XmlAccessorType(XmlAccessType.FIELD)
public class Enqueteur implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Le secteur de l'enqueteur
     */
    @OneToOne
    private Secteur secteur;
    
    /**
     * La liste des plannings de l'enqueteur
     */
    @OneToMany(mappedBy = "enqueteur")
    private List<Planning> plannings;
   
    @Version
    @XmlTransient
    private int version;
    
    /**
     * L'identifiant de l'enqueteur dans la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Le nom de l'enqueteur
     */
    @Column(unique = true)
    private String nom;
    
    /**
     * Le login de l'enqueteur
     */
    @Column(unique = true)
    private String username;
    
    
    private String matricule;
    
    /**
     * Le mot de passe de l'enqueteur
     */
    @Column(unique = true)
    private String codeSecret;
    
    /**
     * La liste des reponses envoyées par l'enqueteur
     */
    @OneToMany(mappedBy = "enqueteur")
    private List<Reponse> reponses;
    
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }

    public List<Planning> getPlannings() {
        return plannings;
    }

    public void setPlannings(List<Planning> plannings) {
        this.plannings = plannings;
    }

    public String getCodeSecret() {
        return codeSecret;
    }

    public void setCodeSecret(String codeSecret) {
        this.codeSecret = codeSecret;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    

    @Override
    public String toString() {
        return "Enqueteur{" + "secteur=" + secteur + ", version=" + version + ", id=" + id + ", nom=" + nom + ", matricule=" + matricule + ", codeSecret=" + codeSecret + '}';
    }
    
    
}
