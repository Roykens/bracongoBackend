package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.projection.BoissonDispoStat;
import com.royken.bracongo.survey.entities.projection.BoissonPrixStat;
import com.royken.bracongo.survey.entities.projection.DisponibiliteNumeriqueStat;
import com.royken.bracongo.survey.entities.projection.ReponseProjection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import org.joda.time.LocalTime;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IReponseService {

    public Reponse saveOrUpdateReponse(Reponse reponse) throws ServiceException;

    public Reponse findReponseById(Reponse reponse) throws ServiceException;

    public List<Reponse> findAllReponse() throws ServiceException;

    public List<Reponse> findAllReponseBetweenDates(Date debut, Date fin) throws ServiceException;

    public List<Reponse> findAllReponseByDate(Date date) throws ServiceException;

    public void deleteReponse(Long id) throws ServiceException;

    public void saveReponseProjection(ReponseProjection reponseProjection) throws ServiceException;

    @Deprecated
    public int countReponseByCriteria(Secteur secteur, TypeBoisson typeBoisson, TypeRegime typeRegime, Date debut, Date fin);

    public int countReponseGlobalStat(Planning planning, Secteur secteur, TypeRegime typeRegime, TypeCategorie categorie, Date debut, Date fin) throws ServiceException;

    public BoissonDispoStat getAllBoissonDispoStat(Date debut, Date fin, Boolean biere, Boolean bracongo) throws ServiceException;

    public DisponibiliteNumeriqueStat getAllDispoStat(Date debut, Date fin, Boolean biere, Boolean bracongo) throws ServiceException;

    public int countDisponibiliteFormat(FormatBoisson formatBoisson, Boolean DiEtOr, Boolean pve, Date debut, Date fin) throws ServiceException;
    
    public Map<Integer, BoissonPrixStat> getPrixMoyen(Date debut, Date fin, Boolean biere, Boolean bracongo) throws ServiceException;

    public int parcEmballage(Boolean bracongo, Boolean pve, Date debut, Date fin) throws ServiceException;
    
    public double jourMoyenEcoule(Boolean bracongo, Boolean pve, Date debut, Date fin) throws ServiceException;
    
    public LocalTime getHeureMoyenneSrd(Boolean bracongo, Boolean pve, Date debut, Date fin)throws ServiceException;
            
    public int nombrePdvServiParSrd(Boolean bracongo, Boolean pve, Date debut, Date fin) throws ServiceException;
    
}
