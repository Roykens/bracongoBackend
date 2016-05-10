package com.royken.bracongo.survey.entities.projection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="disponibiliteNumeriqueStat")
@XmlAccessorType(XmlAccessType.FIELD)
public class DisponibiliteNumeriqueStat {
    
    private int pveDiO;
    
    private int pveArBz;
    
    private int pve;
    
    private int pvmDiOr;
    
    private int pvmArBz;
    
    private int pvm;
    
    private int pdv;

    public int getPveDiO() {
        return pveDiO;
    }

    public void setPveDiO(int pveDiO) {
        this.pveDiO = pveDiO;
    }

    public int getPveArBz() {
        return pveArBz;
    }

    public void setPveArBz(int pveArBz) {
        this.pveArBz = pveArBz;
    }

    public int getPve() {
        return pve;
    }

    public void setPve(int pve) {
        this.pve = pve;
    }

    public int getPvmDiOr() {
        return pvmDiOr;
    }

    public void setPvmDiOr(int pvmDiOr) {
        this.pvmDiOr = pvmDiOr;
    }

    public int getPvmArBz() {
        return pvmArBz;
    }

    public void setPvmArBz(int pvmArBz) {
        this.pvmArBz = pvmArBz;
    }

    public int getPvm() {
        return pvm;
    }

    public void setPvm(int pvm) {
        this.pvm = pvm;
    }

    public int getPdv() {
        return pdv;
    }

    public void setPdv(int pdv) {
        this.pdv = pdv;
    }
    
    
}
