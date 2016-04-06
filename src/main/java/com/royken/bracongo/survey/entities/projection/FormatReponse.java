package com.royken.bracongo.survey.entities.projection;

import com.royken.bracongo.survey.entities.EtatMateriel;
import com.royken.bracongo.survey.entities.ReponseValue;
import com.royken.bracongo.survey.entities.TypePdv;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name = "formatReponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class FormatReponse {
    
    private Long idPdv;

    private TypePdv typePdv;

    private Date heureDeVisite;

    private int jourDepuisDernierPassageFVD;

    private ReponseValue srdBracongo;

    private Date heurePassageSrdBracongo;

    private int jourDernierPassageFVDBralimba;

    private ReponseValue srdBralimba;

    private Date heurePassageSrdBralimba;

    private int nombrePHN;

    private ReponseValue verificationFifo;

    private EtatMateriel etatMateriel;

    private Long idPlanning;
    
    
    

    public Long getIdPdv() {
        return idPdv;
    }

    public void setIdPdv(Long idPdv) {
        this.idPdv = idPdv;
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

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }
    
    

}
