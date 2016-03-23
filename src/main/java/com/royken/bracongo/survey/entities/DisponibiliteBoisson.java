package com.royken.bracongo.survey.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Entity
@XmlRootElement(name="disponibiliteBoisson")
@XmlAccessorType(XmlAccessType.FIELD)
public class DisponibiliteBoisson implements Serializable{
    @OneToOne(mappedBy = "disponibiliteBoisson")
    private Reponse reponse;
    
    @Version
    private int version;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Enumerated
    private ReponseValue reponseValue;
    
    @ManyToOne
    private FormatBoisson formatBoisson;

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

    public ReponseValue getReponseValue() {
        return reponseValue;
    }

    public void setReponseValue(ReponseValue reponseValue) {
        this.reponseValue = reponseValue;
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
    
    
}
