package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@XmlRootElement(name="pointDeVente")
@XmlAccessorType(XmlAccessType.FIELD)
public class PointDeVente implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * La liste des plannings dans lequel se trouve le PDV
     */
    @XmlTransient
    @OneToMany(mappedBy = "pointDeVente")
    private List<PlanningPdv> planningPdvs;
    
    @Version
    @XmlTransient
    private int version;
    
    /**
     * L'identifiant de l'entité dans la BD
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Le nom du PDV
     */
    @Basic
    private String nom;
    
    /**
     * Le numero de compte
     */
    @Column(unique = true, nullable = false)
    private String code;
    
    /**
     * L'adresse du PDV
     */
    @Basic
    private String adresse;
    
    /**
     * La longitude 
     */
    @Basic
    private Double longitude;
    
    /**
     * La latitude
     */
    @Basic
    private Double latitude;
    
    /**
     * Le type du PDV (Depot, Bar)
     */
    @Enumerated(EnumType.STRING)
    private TypePdv typePdv;
    
    /**
     * La categorie du PDV (Di,Or,Br,Ag)
     */
    @Enumerated(EnumType.STRING)
    private TypeCategorie typeCategorie;
    
    /**
     * Le regime du PDV (Mixte, PVE)
     */
    @Enumerated(EnumType.STRING)
    private TypeRegime typeRegime;
    
    /**
     * Le circuit du PDV
     */
    @XmlTransient
    @ManyToOne
    private Circuit circuit;    
    
    /**
     * La liste des reponses envoyées par le PDV
     */
    @XmlTransient
    @OneToMany(mappedBy = "pointDeVente")
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public List<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(List<Reponse> reponses) {
        this.reponses = reponses;
    }

    public TypePdv getTypePdv() {
        return typePdv;
    }

    public void setTypePdv(TypePdv typePdv) {
        this.typePdv = typePdv;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TypeCategorie getTypeCategorie() {
        return typeCategorie;
    }

    public void setTypeCategorie(TypeCategorie typeCategorie) {
        this.typeCategorie = typeCategorie;
    }

    public TypeRegime getTypeRegime() {
        return typeRegime;
    }

    public void setTypeRegime(TypeRegime typeRegime) {
        this.typeRegime = typeRegime;
    }

    public List<PlanningPdv> getPlanningPdvs() {
        return planningPdvs;
    }

    public void setPlanningPdvs(List<PlanningPdv> planningPdvs) {
        this.planningPdvs = planningPdvs;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
    
    

    @Override
    public String toString() {
        return "PointDeVente{" + "version=" + version + ", id=" + id + ", nom=" + nom + ", code=" + code + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    
    
}
