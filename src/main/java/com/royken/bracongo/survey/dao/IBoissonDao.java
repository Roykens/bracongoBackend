package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IBoissonDao extends IGenericDao<Boisson, Long>{
    /**
     * La liste des boisson d'un type et d'une enreprise
     * @param isBracongo l'entreprise (true pour BRACONGO et false pour BRALIMA)
     * @param typeBoisson le type de boisson
     * @return la liste de boissons filtrée
     * @throws DataAccessException 
     */
    public List<Boisson> findAllByEnterpriseAndType(boolean isBracongo, TypeBoisson typeBoisson) throws DataAccessException;
    
    /**
     * le boisson avec un nom donné
     * @param name le nom de la boisson
     * @return la boisson
     * @throws DataAccessException 
     */
    public Boisson findByName(String name) throws DataAccessException;
    
    /**
     * la liste des boissons
     * @return la liste
     * @throws DataAccessException 
     */
    public List<Boisson> findAllActive() throws DataAccessException;
    
}
