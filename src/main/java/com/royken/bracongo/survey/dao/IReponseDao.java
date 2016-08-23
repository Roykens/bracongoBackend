package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IReponseDao extends IGenericDao<Reponse, Long>{
    
    public Reponse findByPlanningAndPdv(Planning planning, PointDeVente pointDeVente) throws DataAccessException;
    
    /**
     * Retourne la list de reponse envoyéées dans un intervalle de dates
     * @param debut le début de l'intervalle
     * @param fin la fin de l'intervalle
     * @return la liste de reponse
     * @throws DataAccessException 
     */
    public List<Reponse> findReponseBetweenDates(Date debut, Date fin) throws DataAccessException;
    
    public List<Reponse> findReponseForDate(Date date) throws DataAccessException;
    
    
    public Reponse findByPdvAndDate(PointDeVente pointDeVente, Date date) throws DataAccessException;
    
    /**
     * Retourne le nombre de visitespar secteur, régime de PDV, catégorie de PDV, et plage de dates
     * @param planning
     * @param secteur Le secteur
     * @param typeRegime Le regime des PDV
     * @param categorie La categorie des PDV
     * @param debut le début de la plage de dates
     * @param fin la fin de la plage
     * @return la liste
     * @throws DataAccessException 
     */
    public int countReponseGlobalStat(Planning planning, Secteur secteur, TypeRegime typeRegime, TypeCategorie categorie, Date debut, Date fin) throws DataAccessException;
    
    /**
     * Retourne le nombre total de disponibilité d'un format de boisson dans un ensemble de réponse
     * @param formatBoisson le format de boisson
     * @param diEtOr PDV di et or ou Ag et Br
     * @param pve PDV exclusif ou mixte
     * @param debut début de la plage
     * @param fin fin de la plage
     * @param biere
     * @param bracongo
     * @return le nombre d disponibilité
     * @throws DataAccessException 
     */
    public int countDisponibiliteFormat(FormatBoisson formatBoisson, Boolean diEtOr, Boolean pve, Date debut, Date fin, Boolean biere, Boolean bracongo) throws DataAccessException;
    
    public int dispoibiliteFormatReponse(FormatBoisson formatBoisson, Reponse reponse) throws DataAccessException;
    
    public int stockChaudFormatReponse(FormatBoisson formatBoisson, Boolean diEtOr, Boolean pve, Date debut, Date fin, Boolean biere, Boolean bracongo)throws DataAccessException;
 
    /* Nombre de pdv ayant un stock chaud*/
    public int pdvStockChaudFormat(FormatBoisson formatBoisson, Boolean diEtOr, Boolean pve, Date debut, Date fin) throws DataAccessException;
    
    public List<Reponse> findAllByDateTypeRegime(Date debut, Date fin, Boolean diEtOr, Boolean pve, Boolean biere, Boolean bracongo) throws DataAccessException;
    
    public int prixMoyenFormat(FormatBoisson formatBoisson, Date debut, Date fin, Boolean pve) throws DataAccessException;
    
    public int countParEmballage(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException;
    
    public double nombreJourMoyenEcoule(Boolean bracongo, Boolean pve, Date debut, Date fin)throws DataAccessException;
    
    public List<Date> getHeuresPassageSrd(Boolean bracongo, Boolean pve,Date debut, Date fin) throws DataAccessException;
    
    /**
     * Retourne le nombre de PDV visité par les SRD
     * @param bracongo si le SRD est de la bracongo
     * @param pve s'il s'agit d'un PVE
     * @param debut début de la période
     * @param fin fin de la période
     * @return le nombre
     * @throws DataAccessException 
     */
    public int nombrepdvVisiteParSrd(Boolean bracongo, Boolean pve, Date debut, Date fin) throws DataAccessException;
}
