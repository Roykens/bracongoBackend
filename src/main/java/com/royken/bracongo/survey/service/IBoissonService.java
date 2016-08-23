package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.TypeBoisson;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IBoissonService {
    
    /**
     * Enregistrer ou modifie la boisson dans la BD
     * @param boisson la boisson
     * @return la boisson
     * @throws ServiceException 
     */
    public Boisson saveOrUpdateBoisson(Boisson boisson) throws ServiceException;
    
    /**
     * Recherche une boisson connaissant son ID
     * @param id L'id de la boisson
     * @return la boisson
     * @throws ServiceException 
     */
    public Boisson findBoissonById(Long id) throws ServiceException;
    
    /**
     * Supprime une boisson dans la BD connaissant son id
     * @param id l'id de la boisson
     * @throws ServiceException 
     */
    public void deleteBoisson(Long id) throws ServiceException;
    
    /**
     * Retourne la liste des boissons de la BD
     * @return la liste
     * @throws ServiceException 
     */
    public List<Boisson> findAllBoisson() throws ServiceException;
    
    /**
     * Retourne la liste de boissons connaissant le type et l'entreprise
     * @param isBracongo l'entreprise (true pour Bracongo)
     * @param typeBoisson le type de la boisson (BI pour bière, BG pour boisson gazeuse)
     * @return La liste
     * @throws ServiceException 
     */
    public List<Boisson> findBoissonByEnterpriseAndType(boolean isBracongo, TypeBoisson typeBoisson) throws ServiceException;
}
