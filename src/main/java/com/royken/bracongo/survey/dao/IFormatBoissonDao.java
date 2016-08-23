package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.Format;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IFormatBoissonDao extends IGenericDao<FormatBoisson, Long>{
    
    /**
     * Retourne la liste des formats de boisson
     * @return la liste
     * @throws DataAccessException 
     */
    public List<FormatBoisson> findAllActive() throws DataAccessException;
    
    /**
     * 
     * @param bracongo
     * @param typeBoisson
     * @return
     * @throws DataAccessException 
     */
    public List<FormatBoisson> findAllByTypeForEnterprise(Boolean bracongo, TypeBoisson typeBoisson)throws DataAccessException;
    
    /**
     * Retourne le format de boisson connaissant la boisson et le format
     * @param boisson la boisson
     * @param format le format
     * @return le format de boisson recherché
     * @throws DataAccessException 
     */
    public FormatBoisson findByBoissonAndFormat(Boisson boisson, Format format) throws DataAccessException;
    
    /**
     * Retourne la liste des formats de boisson d'une boisson
     * @param boisson la boisson
     * @return la liste
     * @throws DataAccessException 
     */
    public List<FormatBoisson> findByBoisson(Boisson boisson) throws DataAccessException;
    
    /**
     * Retourne la liste des formats de boisson d'une entreprise, d'yn type de boisson et d'un format
     * @param bracongo l'entreprise (True si c'est Bracongo)
     * @param typeBoisson le type de boisson (BI pour bière, BG pour boisson gazeuse)
     * @param format le format
     * @return la liste
     * @throws DataAccessException 
     */
    public List<FormatBoisson> findAllByTypeForEnterpriseAndFormat(Boolean bracongo, TypeBoisson typeBoisson, Format format) throws DataAccessException;
    
}
