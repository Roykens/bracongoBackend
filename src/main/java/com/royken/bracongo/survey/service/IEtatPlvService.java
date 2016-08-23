package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.EtatPlv;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEtatPlvService {
    /**
     * Enregistre ou modifie un EtatPlv dans la BD
     * @param etatPlv l'tatplv
     * @return l'EtatPlv
     * @throws ServiceException 
     */
    public EtatPlv saveOrUpdateEtatPlv(EtatPlv etatPlv) throws ServiceException;
    
    /**
     * Rechercher un EtatPlv avec son id
     * @param id l'id
     * @return l'Etat Plv
     * @throws ServiceException 
     */
    public EtatPlv findEtatPlvById(Long id) throws ServiceException;
    
    /**
     * Retourne la liste de toutes les EtatPlv de la BD
     * @return LA LISTE
     * @throws ServiceException 
     */
    public List<EtatPlv> findAllEtatPlv() throws ServiceException;
    
    /**
     * Supprime un EtatPlv connaissant son id
     * @param id l'id
     * @throws ServiceException 
     */
    public void deletetatPlv(Long id) throws ServiceException;
}
