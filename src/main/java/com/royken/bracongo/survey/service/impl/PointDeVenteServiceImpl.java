package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.ICircuitDao;
import com.royken.bracongo.survey.dao.IEnqueteurDao;
import com.royken.bracongo.survey.dao.IPlanningDao;
import com.royken.bracongo.survey.dao.IPlanningPdvDao;
import com.royken.bracongo.survey.dao.IPointDeVenteDao;
import com.royken.bracongo.survey.dao.IZoneDao;
import com.royken.bracongo.survey.dao.IsecteurDao;
import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.Enqueteur;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PlanningPdv;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypePdv;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.entities.projection.PlanningEnquetteur;
import com.royken.bracongo.survey.service.IPointDeVenteService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.bracongo.survey.service.util.ImportationError;
import com.royken.bracongo.survey.service.util.ImportationResult;
import com.royken.generic.dao.DataAccessException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class PointDeVenteServiceImpl implements IPointDeVenteService {

    @Inject
    private IPointDeVenteDao pointDeVenteDao;

    @Inject
    private IEnqueteurDao enqueteurDao;

    @Inject
    private ICircuitDao circuitDao;

    @Inject
    private IZoneDao zoneDao;

    @Inject
    private IsecteurDao secteurDao;

    @Inject
    private IPlanningDao planningDao;

    @Inject
    private IPlanningPdvDao planningPdvDao;

    public ICircuitDao getCircuitDao() {
        return circuitDao;
    }

    public void setCircuitDao(ICircuitDao circuitDao) {
        this.circuitDao = circuitDao;
    }

    public IZoneDao getZoneDao() {
        return zoneDao;
    }

    public void setZoneDao(IZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    public IsecteurDao getSecteurDao() {
        return secteurDao;
    }

    public void setSecteurDao(IsecteurDao secteurDao) {
        this.secteurDao = secteurDao;
    }

    public IPointDeVenteDao getPointDeVenteDao() {
        return pointDeVenteDao;
    }

    public void setPointDeVenteDao(IPointDeVenteDao pointDeVenteDao) {
        this.pointDeVenteDao = pointDeVenteDao;
    }

    public IEnqueteurDao getEnqueteurDao() {
        return enqueteurDao;
    }

    public void setEnqueteurDao(IEnqueteurDao enqueteurDao) {
        this.enqueteurDao = enqueteurDao;
    }

    @Override
    public PointDeVente saveOrUpdatePDV(PointDeVente pointDeVente) throws ServiceException {

        try {
            if (pointDeVente.getId() == null) {
                pointDeVente.setActive(1);
                return pointDeVenteDao.create(pointDeVente);
            } else {
                return pointDeVenteDao.update(pointDeVente);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public PointDeVente findPDVById(Long id) throws ServiceException {
        try {
            return pointDeVenteDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<PointDeVente> findAllPointDeVente() throws ServiceException {
        try {
            return pointDeVenteDao.findAllActive();
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public void deletePDV(Long id) throws ServiceException {
        try {
            PointDeVente pointDeVente = pointDeVenteDao.findById(id);
            if (pointDeVente != null) {
                pointDeVente.setActive(0);
                pointDeVenteDao.update(pointDeVente);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<PointDeVente> findAllByEnqueteur(Long idEnqueteur) throws ServiceException {
        try {
            Enqueteur enqueteur = enqueteurDao.findById(idEnqueteur);
            if (enqueteur != null) {
                System.out.println("L'enqueteur");
                System.out.println(enqueteur);
                System.out.println(enqueteur.getSecteur());
                return pointDeVenteDao.findByEnqueteur(enqueteur);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<PointDeVente> findByCriteria(Long idSecteur, Long idZone, Long idCircuit) throws ServiceException {
        try {
            Secteur secteur = null;
            Zone zone = null;
            Circuit circuit = null;

            if (idCircuit > 0) {
                circuit = circuitDao.findById(idCircuit);
            }

            if (idZone > 0) {
                zone = zoneDao.findById(idZone);
            }

            if (idSecteur > 0) {
                secteur = secteurDao.findById(idSecteur);
            }

            return pointDeVenteDao.findBySecteurZoneCircuit(secteur, zone, circuit);
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public ImportationResult importPdv(InputStream stream, Long idCircuit) throws ServiceException {
        ImportationResult result = new ImportationResult();
        List<ImportationError> erreurs = new ArrayList<ImportationError>();
        int count = 0;
        try {
            Circuit circuit = circuitDao.findById(idCircuit);
            System.out.println("Le circuit service  " + circuit);
            Workbook workbook = WorkbookFactory.create(stream);
            final Sheet sheet = workbook.getSheetAt(0);
            int index = 1;
            Row row = sheet.getRow(index++);
            String compte;
            String nom;
            double longitude;
            double latitude;
            String adresse;
            while (row != null) {
                PointDeVente pointDeVente = new PointDeVente();
                if (circuit != null) {
                    pointDeVente.setCircuit(circuit);
                }
                System.out.println("Index +++++++ " + index);
                if (row.getCell(1) != null) {
                    nom = row.getCell(1).getStringCellValue();
                    //etudiant = etudiantDao.findByMatricule(matricule);
                    pointDeVente.setNom(nom);
                    if (row.getCell(2) != null) {
                        compte = row.getCell(2).getStringCellValue();
                        pointDeVente.setCode(compte);
                        if (row.getCell(3) != null) {
                            adresse = row.getCell(3).getStringCellValue();
                            pointDeVente.setAdresse(adresse);
                            if (row.getCell(4) != null) {
                                if (row.getCell(4).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                    latitude = row.getCell(4).getNumericCellValue();
                                    pointDeVente.setLatitude(latitude);
                                    if (row.getCell(5) != null) {
                                        if (row.getCell(5).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                            longitude = row.getCell(5).getNumericCellValue();
                                            pointDeVente.setLongitude(longitude);
                                            if (row.getCell(6) != null) {
                                                pointDeVente.setTypePdv(TypePdv.valueOf(row.getCell(6).getStringCellValue() + ""));
                                                if (row.getCell(7) != null) {
                                                    pointDeVente.setTypeCategorie(TypeCategorie.valueOf(row.getCell(7).getStringCellValue() + ""));
                                                    if (row.getCell(8) != null) {
                                                        pointDeVente.setTypeRegime(TypeRegime.valueOf(row.getCell(8).getStringCellValue() + ""));
                                                        try {
                                                            pointDeVenteDao.create(pointDeVente);
                                                            count++;
                                                        } catch (Exception e) {
                                                            ImportationError err = new ImportationError(index, e.getMessage());
                                                            erreurs.add(err);
                                                            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, e);
                                                        }

                                                    } else {
                                                        ImportationError err = new ImportationError(index, "Regime indisponible");
                                                        erreurs.add(err);
                                                    }
                                                } else {
                                                    ImportationError err = new ImportationError(index, "Categorie indisponible");
                                                    erreurs.add(err);
                                                }

                                            } else {
                                                ImportationError err = new ImportationError(index, "Type indisponible");
                                                erreurs.add(err);
                                            }
                                        } else {
                                            ImportationError err = new ImportationError(index, "Longitude incorrecte");
                                            erreurs.add(err);
                                        }
                                    } else {
                                        ImportationError err = new ImportationError(index, "Longitude indisponible");
                                        erreurs.add(err);
                                    }
                                } else {
                                    ImportationError err = new ImportationError(index, "Latitude incorrecte");
                                    erreurs.add(err);
                                }
                            } else {
                                ImportationError err = new ImportationError(index, "Latitude indisponible");
                                erreurs.add(err);
                            }
                        } else {
                            ImportationError err = new ImportationError(index, "Adresse indisponible");
                            erreurs.add(err);
                        }

                    } else {
                        ImportationError err = new ImportationError(index, "Note indisponible");
                        erreurs.add(err);
                    }
                } else {
                    ImportationError err = new ImportationError(index, "Nom indisponible");
                    erreurs.add(err);
                }
                row = sheet.getRow(index++);
            }

        } catch (IOException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidFormatException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        result.setNombreImporte(count);
        result.setErreurs(erreurs);
        return result;

    }

    @Override
    public List<PointDeVente> findByPlanningAndEnqueteur(Long idEnqueteur) throws ServiceException {
        List<PointDeVente> result = new ArrayList<PointDeVente>();
        try {
            Enqueteur enqueteur = enqueteurDao.findById(idEnqueteur);
            if (enqueteur != null) {
                Planning planning = planningDao.getByEnqueteur(enqueteur);
                List<PlanningPdv> planningPdvs = planningPdvDao.findByPlanning(planning);
                for (PlanningPdv planningPdv : planningPdvs) {
                    // PointDeVente pointDeVente = new PointDeVente();
                    result.add(planningPdv.getPointDeVente());
                }
                return result;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public PlanningEnquetteur getAllPointDeVenteByEnqueteur(String login, String password) throws ServiceException {
        PlanningEnquetteur planningEnquetteur = new PlanningEnquetteur();
        try {
            Enqueteur enqueteur = enqueteurDao.findEnqueteurByUsernameAndPassword(login, password);
            if (enqueteur != null) {
                Planning planning = planningDao.getByEnqueteur(enqueteur);
                if (planning != null) {
                    List<PointDeVente> result = new ArrayList<PointDeVente>();
                    List<PlanningPdv> planningPdvs = planningPdvDao.findByPlanning(planning);
                    for (PlanningPdv planningPdv : planningPdvs) {
                        result.add(planningPdv.getPointDeVente());
                    }
                    planningEnquetteur.setPointDeVentes(result);
                    planningEnquetteur.setIdPlanning(planning.getId());

                }
                return planningEnquetteur;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(PointDeVenteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
