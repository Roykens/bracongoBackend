package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IReponseDao extends IGenericDao<Reponse, Long>{
    
    public Reponse findByPlanningAndPdv(Planning planning, PointDeVente pointDeVente) throws DataAccessException;
    
    public List<Reponse> findReponseBetweenDates(Date debut, Date fin) throws DataAccessException;
    
    public List<Reponse> findReponseForDate(Date date) throws DataAccessException;
    
  //  public int countReponseByCriteria(Secteur secteur, TypeBoisson typeBoisson, TypeRegime typeRegime, Date debut, Date fin) throws DataAccessException;
    
    public Reponse findByPdvAndDate(PointDeVente pointDeVente, Date date) throws DataAccessException;
    
    public int countReponseGlobalStat(Planning planning, Secteur secteur, TypeRegime typeRegime, TypeCategorie categorie, Date debut, Date fin) throws DataAccessException;
    
    public int countDisponibiliteFormat(FormatBoisson formatBoisson, Boolean DiEtOr, Boolean pve, Date debut, Date fin, Boolean biere, Boolean bracongo) throws DataAccessException;
    
    public int dispoibiliteFormatReponse(FormatBoisson formatBoisson, Reponse reponse) throws DataAccessException;
    
    public int stockChaudFormatReponse(FormatBoisson formatBoisson, Boolean DiEtOr, Boolean pve, Date debut, Date fin, Boolean biere, Boolean bracongo)throws DataAccessException;
    
    public List<Reponse> findAllByDateTypeRegime(Date debut, Date fin, Boolean DiEtOr, Boolean pve, Boolean biere, Boolean bracongo) throws DataAccessException;
    
    public int prixMoyenFormat(FormatBoisson formatBoisson, Date debut, Date fin, Boolean pve) throws DataAccessException;
    
    public int countParEmballage(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException;
    
    public double nombreJourMoyenEcoule(Boolean bracongo, Boolean pve, Date debut, Date fin)throws DataAccessException;
    
    public List<Date> getHeuresPassageSrd(Boolean bracongo, Boolean pve,Date debut, Date fin) throws DataAccessException;
    
    public int nombrepdvVisiteParSrd(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException;
}
