package com.royken.bracongo.survey.service;

import com.royken.bracongo.survey.entities.Enqueteur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Local
public interface IEnqueteurService {
    
    /**
     * Enregistre ou modifie un enqueteur dans la BD
     * @param enqueteur l'enqueteur
     * @return l'enqueteur
     * @throws ServiceException 
     */
    public Enqueteur saveOrUpdateEnqueteur(Enqueteur enqueteur) throws ServiceException;
    
    /**
     * Recherche un enqueteur connaissant son id
     * @param id l'id
     * @return l'enqueteur
     * @throws ServiceException 
     */
    public Enqueteur findEnqueteurById(Long id) throws ServiceException;
    
    /**
     * Retourne la liste des enquêteurs
     * @return la liste
     * @throws ServiceException 
     */
    public List<Enqueteur> findAllEnqueteur() throws ServiceException;
    
    /**
     * Supprime l'enqueteur connaissant son id
     * @param id l'id
     * @throws ServiceException 
     */
    public void deleteEnqueteur(Long id) throws ServiceException;
    
    /**
     * Recherche un enqueteur avec le login et le mot de passe
     * @param login le login
     * @param password le mot de passe
     * @return l'enqueteur
     * @throws ServiceException 
     */
    public Enqueteur findByLoginAndPassord(String login, String password) throws ServiceException;
    
}
