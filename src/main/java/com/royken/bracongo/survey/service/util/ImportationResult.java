package com.royken.bracongo.survey.service.util;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@XmlRootElement(name = "resultat")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportationResult {
    
    private int nombreImporte;
    
    private List<ImportationError> erreurs;

    public ImportationResult() {
    }

    public int getNombreImporte() {
        return nombreImporte;
    }

    public void setNombreImporte(int nombreImporte) {
        this.nombreImporte = nombreImporte;
    }

    public List<ImportationError> getErreurs() {
        return erreurs;
    }

    public void setErreurs(List<ImportationError> erreurs) {
        this.erreurs = erreurs;
    }

    @Override
    public String toString() {
        return "ImportationResult{" + "nombreImporte=" + nombreImporte + ", erreurs=" + erreurs + '}';
    }
    
    
}
