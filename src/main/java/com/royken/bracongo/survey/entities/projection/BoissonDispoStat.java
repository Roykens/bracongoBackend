package com.royken.bracongo.survey.entities.projection;

import java.io.Serializable;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name="boissonDispoStat")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoissonDispoStat implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Map<String,Integer> pveDiEtOr;
    
    private Map<String,Integer> pveAgEtBr;
    
    private Map<String, Integer> pvmDiEtOr;
    
    private Map<String, Integer> pvmAgEtBr;
    
    private Map<String, Integer> pve;
    
    private Map<String, Integer> pvm;
    
    private Map<String, Integer> pdv;

    public Map<String, Integer> getPveDiEtOr() {
        return pveDiEtOr;
    }

    public void setPveDiEtOr(Map<String, Integer> pveDiEtOr) {
        this.pveDiEtOr = pveDiEtOr;
    }

    public Map<String, Integer> getPveAgEtBr() {
        return pveAgEtBr;
    }

    public void setPveAgEtBr(Map<String, Integer> pveAgEtBr) {
        this.pveAgEtBr = pveAgEtBr;
    }

    public Map<String, Integer> getPvmDiEtOr() {
        return pvmDiEtOr;
    }

    public void setPvmDiEtOr(Map<String, Integer> pvmDiEtOr) {
        this.pvmDiEtOr = pvmDiEtOr;
    }

    public Map<String, Integer> getPvmAgEtBr() {
        return pvmAgEtBr;
    }

    public void setPvmAgEtBr(Map<String, Integer> pvmAgEtBr) {
        this.pvmAgEtBr = pvmAgEtBr;
    }

    public Map<String, Integer> getPve() {
        return pve;
    }

    public void setPve(Map<String, Integer> pve) {
        this.pve = pve;
    }

    public Map<String, Integer> getPvm() {
        return pvm;
    }

    public void setPvm(Map<String, Integer> pvm) {
        this.pvm = pvm;
    }

    public Map<String, Integer> getPdv() {
        return pdv;
    }

    public void setPdv(Map<String, Integer> pdv) {
        this.pdv = pdv;
    }

    @Override
    public String toString() {
        return "BoissonDispoStat{" + "pveDiEtOr=" + pveDiEtOr + ", pveAgEtBr=" + pveAgEtBr + ", pvmDiEtOr=" + pvmDiEtOr + ", pvmAgEtBr=" + pvmAgEtBr + ", pve=" + pve + ", pvm=" + pvm + ", pdv=" + pdv + '}';
    }
    
}
