package com.royken.bracongo.survey.entities.projection;

import com.royken.bracongo.survey.entities.Action;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="reponseProjection")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReponseProjection {
    
    private int idPdv;
    
    private Long idPlanning;

    private int parcEmballageBracongo;

    private int parcEmballageBralima;

    private Date heureVisite;

    private int joursEcouleVisiteFDVBrac;

    private boolean srdBrac;

    private Date heurePassageSrdBrac;

    private int joursEcouleVisiteFDVBral;

    private boolean srdBral;

    private Date heurePassageSrdBral;

    private boolean fifo;

    private int nombrePhn;

    private List<BoissonProjection> boissonProjections;

    private List<PlvProjection> plvProjections;

    private List<MaterielProjection> materielProjections;
    
    private Action action;

    public int getIdPdv() {
        return idPdv;
    }

    public void setIdPdv(int idPdv) {
        this.idPdv = idPdv;
    }

    public int getParcEmballageBracongo() {
        return parcEmballageBracongo;
    }

    public void setParcEmballageBracongo(int parcEmballageBracongo) {
        this.parcEmballageBracongo = parcEmballageBracongo;
    }

    public int getParcEmballageBralima() {
        return parcEmballageBralima;
    }

    public void setParcEmballageBralima(int parcEmballageBralima) {
        this.parcEmballageBralima = parcEmballageBralima;
    }

    public Date getHeureVisite() {
        return heureVisite;
    }

    public void setHeureVisite(Date heureVisite) {
        this.heureVisite = heureVisite;
    }

    public int getJoursEcouleVisiteFDVBrac() {
        return joursEcouleVisiteFDVBrac;
    }

    public void setJoursEcouleVisiteFDVBrac(int joursEcouleVisiteFDVBrac) {
        this.joursEcouleVisiteFDVBrac = joursEcouleVisiteFDVBrac;
    }

    public boolean isSrdBrac() {
        return srdBrac;
    }

    public void setSrdBrac(boolean srdBrac) {
        this.srdBrac = srdBrac;
    }

    public Date getHeurePassageSrdBrac() {
        return heurePassageSrdBrac;
    }

    public void setHeurePassageSrdBrac(Date heurePassageSrdBrac) {
        this.heurePassageSrdBrac = heurePassageSrdBrac;
    }

    public int getJoursEcouleVisiteFDVBral() {
        return joursEcouleVisiteFDVBral;
    }

    public void setJoursEcouleVisiteFDVBral(int joursEcouleVisiteFDVBral) {
        this.joursEcouleVisiteFDVBral = joursEcouleVisiteFDVBral;
    }

    public boolean isSrdBral() {
        return srdBral;
    }

    public void setSrdBral(boolean srdBral) {
        this.srdBral = srdBral;
    }

    public Date getHeurePassageSrdBral() {
        return heurePassageSrdBral;
    }

    public void setHeurePassageSrdBral(Date heurePassageSrdBral) {
        this.heurePassageSrdBral = heurePassageSrdBral;
    }

    public boolean isFifo() {
        return fifo;
    }

    public void setFifo(boolean fifo) {
        this.fifo = fifo;
    }

    public int getNombrePhn() {
        return nombrePhn;
    }

    public void setNombrePhn(int nombrePhn) {
        this.nombrePhn = nombrePhn;
    }

    public List<BoissonProjection> getBoissonProjections() {
        return boissonProjections;
    }

    public void setBoissonProjections(List<BoissonProjection> boissonProjections) {
        this.boissonProjections = boissonProjections;
    }

    public List<PlvProjection> getPlvProjections() {
        return plvProjections;
    }

    public void setPlvProjections(List<PlvProjection> plvProjections) {
        this.plvProjections = plvProjections;
    }

    public List<MaterielProjection> getMaterielProjections() {
        return materielProjections;
    }

    public void setMaterielProjections(List<MaterielProjection> materielProjections) {
        this.materielProjections = materielProjections;
    }

    public Long getIdPlanning() {
        return idPlanning;
    }

    public void setIdPlanning(Long idPlanning) {
        this.idPlanning = idPlanning;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "ReponseProjection{" + "idPdv=" + idPdv + ", idPlanning=" + idPlanning + ", parcEmballageBracongo=" + parcEmballageBracongo + ", parcEmballageBralima=" + parcEmballageBralima + ", heureVisite=" + heureVisite + ", joursEcouleVisiteFDVBrac=" + joursEcouleVisiteFDVBrac + ", srdBrac=" + srdBrac + ", heurePassageSrdBrac=" + heurePassageSrdBrac + ", joursEcouleVisiteFDVBral=" + joursEcouleVisiteFDVBral + ", srdBral=" + srdBral + ", heurePassageSrdBral=" + heurePassageSrdBral + ", fifo=" + fifo + ", nombrePhn=" + nombrePhn + ", boissonProjections=" + boissonProjections + ", plvProjections=" + plvProjections + ", materielProjections=" + materielProjections + ", action=" + action + '}';
    }
    
    
}
