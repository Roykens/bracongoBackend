package com.royken.bracongo.survey.dao;

import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.generic.dao.DataAccessException;
import com.royken.generic.dao.IGenericDao;
import java.util.List;
/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public interface IPointDeVenteDao extends IGenericDao<PointDeVente, Long>{
 
    public List<PointDeVente> findByEnqueteur(Enqueteur enqueteur) throws DataAccessException;
}
