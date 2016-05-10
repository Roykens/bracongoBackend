package com.royken.bracongo.survey.service.impl;

import com.royken.bracongo.survey.dao.IActionDao;
import com.royken.bracongo.survey.dao.IBoissonDao;
import com.royken.bracongo.survey.dao.IBoissonInfosDao;
import com.royken.bracongo.survey.dao.IDisponibiliteBoissonDao;
import com.royken.bracongo.survey.dao.IEtatMaterielDao;
import com.royken.bracongo.survey.dao.IEtatPlvDao;
import com.royken.bracongo.survey.dao.IFormatBoissonDao;
import com.royken.bracongo.survey.dao.IMaterielDao;
import com.royken.bracongo.survey.dao.IPlanningDao;
import com.royken.bracongo.survey.dao.IPlvDao;
import com.royken.bracongo.survey.dao.IPointDeVenteDao;
import com.royken.bracongo.survey.dao.IPrixBoissonDao;
import com.royken.bracongo.survey.dao.IReponseDao;
import com.royken.bracongo.survey.dao.IStockChaudDao;
import com.royken.bracongo.survey.dao.IsecteurDao;
import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.entities.Boisson;
import com.royken.bracongo.survey.entities.BoissonInfos;
import com.royken.bracongo.survey.entities.EtatMateriel;
import com.royken.bracongo.survey.entities.EtatPlv;
import com.royken.bracongo.survey.entities.FormatBoisson;
import com.royken.bracongo.survey.entities.Materiel;
import com.royken.bracongo.survey.entities.PLV;
import com.royken.bracongo.survey.entities.Planning;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Reponse;
import com.royken.bracongo.survey.entities.ReponseValue;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeBoisson;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.projection.BoissonDispoStat;
import com.royken.bracongo.survey.entities.projection.BoissonProjection;
import com.royken.bracongo.survey.entities.projection.DisponibiliteNumeriqueStat;
import com.royken.bracongo.survey.entities.projection.MaterielProjection;
import com.royken.bracongo.survey.entities.projection.PlvProjection;
import com.royken.bracongo.survey.entities.projection.ReponseProjection;
import com.royken.bracongo.survey.service.IReponseService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.bracongo.survey.service.util.ConvertorUtil;
import com.royken.generic.dao.DataAccessException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Stateless
public class ReponseServiceImpl implements IReponseService {

    @Inject
    private IReponseDao reponseDao;

    @Inject
    private IPointDeVenteDao pointDeVenteDao;

    @Inject
    private IPlanningDao planningDao;

    @Inject
    private IPrixBoissonDao prixBoissonDao;

    @Inject
    private IDisponibiliteBoissonDao disponibiliteBoissonDao;

    @Inject
    private IStockChaudDao stockChaudDao;

    @Inject
    private IBoissonInfosDao boissonInfosDao;

    @Inject
    private IEtatMaterielDao etatMaterielDao;

    @Inject
    private IEtatPlvDao etatPlvDao;

    @Inject
    private IFormatBoissonDao formatBoissonDao;

    @Inject
    private IMaterielDao materielDao;

    @Inject
    private IPlvDao plvDao;

    @Inject
    private IsecteurDao isecteurDao;
    
    @Inject
    private IActionDao actionDao;
    
    @Inject
    private IBoissonDao boissonDao;

    public IReponseDao getReponseDao() {
        return reponseDao;
    }

    public void setReponseDao(IReponseDao reponseDao) {
        this.reponseDao = reponseDao;
    }

    public IPointDeVenteDao getPointDeVenteDao() {
        return pointDeVenteDao;
    }

    public IBoissonInfosDao getBoissonInfosDao() {
        return boissonInfosDao;
    }

    public void setBoissonInfosDao(IBoissonInfosDao boissonInfosDao) {
        this.boissonInfosDao = boissonInfosDao;
    }

    public IMaterielDao getMaterielDao() {
        return materielDao;
    }

    public void setMaterielDao(IMaterielDao materielDao) {
        this.materielDao = materielDao;
    }

    public IPlvDao getPlvDao() {
        return plvDao;
    }

    public void setPlvDao(IPlvDao plvDao) {
        this.plvDao = plvDao;
    }

    public IsecteurDao getIsecteurDao() {
        return isecteurDao;
    }

    public void setIsecteurDao(IsecteurDao isecteurDao) {
        this.isecteurDao = isecteurDao;
    }

    public void setPointDeVenteDao(IPointDeVenteDao pointDeVenteDao) {
        this.pointDeVenteDao = pointDeVenteDao;
    }

    public IPlanningDao getPlanningDao() {
        return planningDao;
    }

    public void setPlanningDao(IPlanningDao planningDao) {
        this.planningDao = planningDao;
    }

    public IPrixBoissonDao getPrixBoissonDao() {
        return prixBoissonDao;
    }

    public void setPrixBoissonDao(IPrixBoissonDao prixBoissonDao) {
        this.prixBoissonDao = prixBoissonDao;
    }

    public IDisponibiliteBoissonDao getDisponibiliteBoissonDao() {
        return disponibiliteBoissonDao;
    }

    public void setDisponibiliteBoissonDao(IDisponibiliteBoissonDao disponibiliteBoissonDao) {
        this.disponibiliteBoissonDao = disponibiliteBoissonDao;
    }

    public IStockChaudDao getStockChaudDao() {
        return stockChaudDao;
    }

    public void setStockChaudDao(IStockChaudDao stockChaudDao) {
        this.stockChaudDao = stockChaudDao;
    }

    public IEtatMaterielDao getEtatMaterielDao() {
        return etatMaterielDao;
    }

    public void setEtatMaterielDao(IEtatMaterielDao etatMaterielDao) {
        this.etatMaterielDao = etatMaterielDao;
    }

    public IEtatPlvDao getEtatPlvDao() {
        return etatPlvDao;
    }

    public void setEtatPlvDao(IEtatPlvDao etatPlvDao) {
        this.etatPlvDao = etatPlvDao;
    }

    public IFormatBoissonDao getFormatBoissonDao() {
        return formatBoissonDao;
    }

    public void setFormatBoissonDao(IFormatBoissonDao formatBoissonDao) {
        this.formatBoissonDao = formatBoissonDao;
    }

    @Override
    public Reponse saveOrUpdateReponse(Reponse reponse) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reponse findReponseById(Reponse reponse) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reponse> findAllReponse() throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reponse> findAllReponseBetweenDates(Date debut, Date fin) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Reponse> findAllReponseByDate(Date date) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteReponse(Long id) throws ServiceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveReponseProjection(ReponseProjection reponseProjection) throws ServiceException {
        try {
            Reponse reponse = new Reponse();
            Planning planning = planningDao.findById(reponseProjection.getIdPlanning());
            PointDeVente pointDeVente = pointDeVenteDao.findById(Long.valueOf(reponseProjection.getIdPdv()));
            reponse.setPlanning(planning);
            reponse.setPointDeVente(pointDeVente);
            reponse.setHeurePassageSrdBracongo(reponseProjection.getHeurePassageSrdBrac());
            reponse.setHeurePassageSrdBralimba(reponseProjection.getHeurePassageSrdBral());
            reponse.setHeureDeVisite(reponseProjection.getHeureVisite());
            reponse.setJourDepuisDernierPassageFVD(reponseProjection.getJoursEcouleVisiteFDVBrac());
            reponse.setJourDernierPassageFVDBralimba(reponseProjection.getJoursEcouleVisiteFDVBral());
            reponse.setNombrePHN(reponseProjection.getNombrePhn());
            reponse.setVerificationFifo(reponseProjection.isFifo() == true ? ReponseValue.OUI : ReponseValue.NON);
            reponse.setSrdBracongo(reponseProjection.isSrdBrac() == true ? ReponseValue.OUI : ReponseValue.NON);
            reponse.setSrdBralimba(reponseProjection.isSrdBral() == true ? ReponseValue.OUI : ReponseValue.NON);
            reponseDao.create(reponse);
            Reponse reponse2 = reponseDao.findByPlanningAndPdv(planning, pointDeVente);
            saveBoissonInfos(reponse2, reponseProjection.getBoissonProjections());
            saveEtatMateriel(reponse2, reponseProjection.getMaterielProjections());
            saveEtatPlv(reponse2, reponseProjection.getPlvProjections());
            saveAction(reponse2, reponseProjection.getAction());
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void saveBoissonInfos(Reponse reponse, List<BoissonProjection> boissonProjections) {
        for (BoissonProjection boissonProjection : boissonProjections) {
            try {
                FormatBoisson boisson = formatBoissonDao.findById(Long.valueOf(boissonProjection.getIdSrveur()));
                BoissonInfos infos = new BoissonInfos();
                infos.setReponse(reponse);
                infos.setFormatBoisson(boisson);
                boolean disponible = boissonProjection.isDisponible();
                if (disponible) {
                    infos.setDisponibilite(disponible);
                    if (boissonProjection.getPrix().length() > 1) {
                        infos.setPrixPdv(Integer.parseInt(boissonProjection.getPrix()));
                    }
                    if (boissonProjection.getStock().length() > 1) {
                        infos.setStockChaud(Integer.parseInt(boissonProjection.getStock()));
                    }
                } else {
                    infos.setDisponibilite(disponible);
                    infos.setPrixPdv(0);
                    infos.setStockChaud(0);
                }
                boissonInfosDao.create(infos);
            } catch (DataAccessException ex) {
                Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void saveEtatMateriel(Reponse reponse, List<MaterielProjection> materielProjections) {
        for (MaterielProjection materielProjection : materielProjections) {
            try {
                EtatMateriel etatMateriel = new EtatMateriel();
                Materiel materiel = materielDao.findById(Long.valueOf(materielProjection.getIdServeur()));
                etatMateriel.setMateriel(materiel);
                etatMateriel.setReponse(reponse);
                etatMateriel.setNombre(Integer.parseInt(materielProjection.getNombreBrac()));
                etatMateriel.setNombreDefecteux(Integer.parseInt(materielProjection.getNombreCasseBrac()));
                etatMateriel.setNombreDefectueuxConc(Integer.parseInt(materielProjection.getNombreCasseConc()));
                etatMateriel.setTypeEtat(ConvertorUtil.getFromString(materielProjection.getEtatBrac()));
                etatMateriel.setTypeEtatConc(ConvertorUtil.getFromString(materielProjection.getEtatConc()));
                etatMateriel.setNombreBralima(Integer.parseInt(materielProjection.getNombreCon()));
                etatMaterielDao.create(etatMateriel);
            } catch (DataAccessException ex) {
                Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void saveEtatPlv(Reponse reponse, List<PlvProjection> plvProjections) {
        for (PlvProjection plvProjection : plvProjections) {
            try {
                PLV plv = plvDao.findById(Long.valueOf(plvProjection.getIdServeur()));
                EtatPlv etatPlv = new EtatPlv();
                etatPlv.setReponse(reponse);
                etatPlv.setPlv(plv);
                etatPlv.setNombre(Integer.parseInt(plvProjection.getNombreBrac()));
                etatPlv.setNombreBralima(Integer.parseInt(plvProjection.getNombreConc()));
                etatPlv.setTypeEtat(ConvertorUtil.getFromString(plvProjection.getEtatBrac()));
                etatPlv.setTypeEtatBralima(ConvertorUtil.getFromString(plvProjection.getEtatConc()));
                etatPlvDao.create(etatPlv);
                //  etatPlv.set
            } catch (DataAccessException ex) {
                Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int countReponseByCriteria(Secteur secteur, TypeBoisson typeBoisson, TypeRegime typeRegime, Date debut, Date fin) {
        try {
            //Secteur secteur1 = isecteurDao.findById(101L);
            //Secteur secteur1 = isecteurDao.findById(151L);
            Secteur secteur1 = isecteurDao.findById(102L);
            System.out.println("Le secteur");
            System.out.println(secteur1);
            if (secteur1 != null) {
                int result = reponseDao.countReponseByCriteria(secteur1, typeBoisson, typeRegime, debut, fin);
                System.out.println("Le resultat");
                System.out.println(result);
                return result;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int nombrePveDiEtOr(List<Reponse> reponses, boolean exclusif) {
        int result = 0;
        for (Reponse reponse : reponses) {
            if (reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Di || reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Or) {
                result++;
            }
        }
        return result;
    }

    public int nombrePdvAgEtBz(List<Reponse> reponses, boolean exclisif) {
        int result = 0;
        for (Reponse reponse : reponses) {
            if ((reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Ag || reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Br) && (reponse.getPointDeVente().getTypeRegime() == TypeRegime.PVE) == exclisif) {
                result++;
            }
        }
        return result;
    }
    
    public int nombrePdvDiEtOr(List<Reponse> reponses, boolean exclusif) {
        int result = 0;
        for (Reponse reponse : reponses) {
            if (reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Di || reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Or && (reponse.getPointDeVente().getTypeRegime() == TypeRegime.PVE) == exclusif) {
                result++;
            }
        }
        return result;
    }

    @Override
    public int countReponseGlobalStat(Planning planning, Secteur secteur, TypeRegime typeRegime, TypeCategorie categorie, Date debut, Date fin) throws ServiceException {
        try {
            return reponseDao.countReponseGlobalStat(planning, secteur, typeRegime, categorie, debut, fin);
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public BoissonDispoStat getAllBoissonDispoStat(Date debut, Date fin, Boolean biere, Boolean bracongo) throws ServiceException {
        try {
            List<Reponse> reponses;
            if(debut != null && fin != null ){
                reponses = reponseDao.findReponseBetweenDates(debut, fin);
            }
            else{
                reponses = reponseDao.findAll();
            }
            nombrePdvAgEtBz(reponses, true);
           int nombrePveDiOr =  nombrePdvDiEtOr(reponses, true);
           int nombrePveAgBr = nombrePdvAgEtBz(reponses, true);
           int nombrePvmDiOr = nombrePdvDiEtOr(reponses, false);
           int nombrePvmAgBr = nombrePdvAgEtBz(reponses, false);
           int a = nombrePveDiOr + nombrePveAgBr;
           int b = nombrePvmDiOr + nombrePvmAgBr;
           int c = a + b;
           BoissonDispoStat result = new BoissonDispoStat();
            Map<String, Integer> pveDiEtOr = new HashMap<String, Integer>();
            Map<String, Integer> pveAgEtBr = new HashMap<String, Integer>();
            Map<String, Integer> pvmDiEtOr = new HashMap<String, Integer>();
            Map<String, Integer> pvmAgEtBr = new HashMap<String, Integer>();
            Map<String, Integer> pve = new HashMap<String, Integer>();
            Map<String, Integer> pvm = new HashMap<String, Integer>();
            Map<String, Integer> pdv = new HashMap<String, Integer>();
            List<FormatBoisson> formatBoissons = formatBoissonDao.findAllByTypeForEnterprise(bracongo,  (biere == true) ? TypeBoisson.BI:TypeBoisson.BG);
            for (FormatBoisson formatBoisson : formatBoissons) {
                int pvedO = reponseDao.countDisponibiliteFormat(formatBoisson, true, true, debut, fin,null, null);
                int pveab = reponseDao.countDisponibiliteFormat(formatBoisson, false, true, debut, fin,null, null);
                int pvmdO = reponseDao.countDisponibiliteFormat(formatBoisson, true, false, debut, fin,null, null);
                int pvmab = reponseDao.countDisponibiliteFormat(formatBoisson, false, false, debut, fin,null, null);
                int pvE = reponseDao.countDisponibiliteFormat(formatBoisson, null, true, debut, fin,null, null);
                int pvM = reponseDao.countDisponibiliteFormat(formatBoisson, null, false, debut, fin,null, null);
                int pdV = reponseDao.countDisponibiliteFormat(formatBoisson, null, null, debut, fin,null, null);
                System.out.println(formatBoisson.getBoisson().getNom() + " " + pvedO + " " + pveab + " " + pvmdO + " "+ pvmab);
                pveDiEtOr.put(getNameFromFormatBoisson(formatBoisson), (nombrePveDiOr > 0) ? ((int)Math.round((pvedO*1.0) / nombrePveDiOr)) * 100 : 0);
                pveAgEtBr.put(getNameFromFormatBoisson(formatBoisson), (nombrePveAgBr > 0) ? ((int)Math.round((pveab * 1.0) / nombrePveAgBr)) * 100 : 0);
                pvmDiEtOr.put(getNameFromFormatBoisson(formatBoisson), (nombrePvmDiOr > 0) ? ((int)Math.round((pvmdO * 1.0) / nombrePvmDiOr)) * 100 : 0);
                pvmAgEtBr.put(getNameFromFormatBoisson(formatBoisson), (nombrePvmAgBr > 0) ? ((int)Math.round((pvmab * 1.0) / nombrePvmAgBr)) * 100 : 0);
                pve.put(getNameFromFormatBoisson(formatBoisson), (a  > 0) ? ((int)Math.round((pvE * 1.0) / a)) * 100 : 0);
                pvm.put(getNameFromFormatBoisson(formatBoisson), (b  > 0) ? ((int)Math.round((pvM * 1.0) / b)) * 100 : 0);
                pdv.put(getNameFromFormatBoisson(formatBoisson), (c  > 0) ? ((int)Math.round((pdV * 1.0) / c)) * 100 : 0);
            }
            result.setPveAgEtBr(pveAgEtBr);
            result.setPveDiEtOr(pveDiEtOr);
            result.setPvmAgEtBr(pvmAgEtBr);
            result.setPvmDiEtOr(pvmDiEtOr);
            result.setPdv(pdv);
            result.setPve(pve);
            result.setPvm(pvm);
           return result;
            
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    private String getNameFromFormatBoisson(FormatBoisson formatBoisson) {
        String result = formatBoisson.getBoisson().getNom();
        return result + " " + formatBoisson.getFormat().getVolume();
    }

    @Override
    public int countDisponibiliteFormat(FormatBoisson formatBoisson, Boolean DiEtOr, Boolean pve, Date debut, Date fin) throws ServiceException {
        try {
            // FormatBoisson boisson = formatBoissonDao.findById(1503L);
            FormatBoisson boisson = formatBoissonDao.findById(1502L);
            //FormatBoisson boisson = formatBoissonDao.findById(753L);
            // FormatBoisson boisson = formatBoissonDao.findById(759L);
            if (boisson != null) {
                return reponseDao.countDisponibiliteFormat(boisson, null, null, null, null, null, null);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    private void saveAction(Reponse reponse, Action action) {
        action.setReponse(reponse);
        try {
            actionDao.create(action);
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int nombrePveByTypeEtRegime(List<Reponse> reponses, boolean pve, boolean diOr){
        int result = 0;
        for (Reponse reponse : reponses) {
            if(((reponse.getPointDeVente().getTypeRegime() == TypeRegime.PVE) == pve)  && (((reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Di || reponse.getPointDeVente().getTypeCategorie() == TypeCategorie.Or)) == diOr))
                result ++;
        }
        return result;
    }
    
    
    private int distributionListBoison(List<Boisson> boissons, Reponse reponse){
        int result  = 0;
        for (Boisson boisson : boissons) {
            result += distributionBoisson(boisson, reponse);
        }
        return result > 0 ? 1:0;
    }
    
    private int distributionNumerique(List<Boisson> boissons, List<Reponse> reponses){
        int result = 0;
        
        for (Reponse reponse : reponses) {
            if (distributionListBoison(boissons, reponse) == 1) {
                result ++;
            }
        }
        return result;
    }
    
    private int distributionBoisson(Boisson boisson, Reponse reponse){
        try {
            List<FormatBoisson> formatBoissons = formatBoissonDao.findByBoisson(boisson);
            int temp = 0;
            for (FormatBoisson formatBoisson : formatBoissons) {
                temp += reponseDao.dispoibiliteFormatReponse(formatBoisson, reponse);
            }
            
            return (temp > 0)? 1:0;
            
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public DisponibiliteNumeriqueStat getAllDispoStat(Date debut, Date fin, Boolean biere, Boolean bracongo) throws ServiceException {
        DisponibiliteNumeriqueStat numeriqueStat = new DisponibiliteNumeriqueStat();
        try {
        //    DisponibiliteNumeriqueStat numeriqueStat = new DisponibiliteNumeriqueStat();
           // List<Boisson> biereBracongo = boissonDao.findAllByEnterpriseAndType(true, TypeBoisson.BI);
            List<Boisson> boissons = boissonDao.findAllByEnterpriseAndType(bracongo, biere == true?  TypeBoisson.BI : TypeBoisson.BG);
         //   List<Boisson> biereBralima = boissonDao.findAllByEnterpriseAndType(false, TypeBoisson.BI);
          //  List<Boisson> bgBralima = boissonDao.findAllByEnterpriseAndType(false, TypeBoisson.BG);
           
            List<Reponse> reponsesDiOrPve = reponseDao.findAllByDateTypeRegime(debut, fin, true, true, biere, bracongo);
            List<Reponse> reponsesAgBzPve = reponseDao.findAllByDateTypeRegime(debut, fin, false, true, biere, bracongo);
            List<Reponse> reponsesPve = reponseDao.findAllByDateTypeRegime(debut, fin, null, true, biere, bracongo);
            List<Reponse> reponsesDiOrPvm = reponseDao.findAllByDateTypeRegime(debut, fin, true, false, biere, bracongo);
            List<Reponse> reponsesAgBzPvm = reponseDao.findAllByDateTypeRegime(debut, fin, false, false, biere, bracongo);
            List<Reponse> reponsesPvm = reponseDao.findAllByDateTypeRegime(debut, fin, null, false, biere, bracongo);
            List<Reponse> reponsesPdv = reponseDao.findAllByDateTypeRegime(debut, fin, null, null, biere, bracongo);
            int nombreDiOrPve = distributionNumerique(boissons, reponsesDiOrPve);
            int nombreAgBzPve = distributionNumerique(boissons, reponsesAgBzPve);
            int nombrePve = distributionNumerique(boissons, reponsesPve);
            int nombreDiOrPvm = distributionNumerique(boissons, reponsesDiOrPvm);
            int nombreAgBzPvm = distributionNumerique(boissons, reponsesAgBzPvm);
            int nombrePvm = distributionNumerique(boissons, reponsesPvm);
            int nombrePdv = distributionNumerique(boissons, reponsesPdv);
            int a = reponsesDiOrPve.size();
            int b = reponsesAgBzPve.size();
            int c = reponsesDiOrPvm.size();
            int d = reponsesAgBzPvm.size();
            int e = reponsesPve.size();
            int f = reponsesPvm.size();
            int g = reponsesPdv.size();
            numeriqueStat.setPveDiO((a > 0) ? ((int)Math.round((nombreDiOrPve * 1.0)/ a)) * 100 : 0);
            numeriqueStat.setPveArBz((b > 0) ? ((int)Math.round((nombreAgBzPve * 1.0)/ b)) * 100 : 0);
            numeriqueStat.setPve((e > 0) ? ((int)Math.round((nombrePve * 1.0)/ e)) * 100 : 0);
            numeriqueStat.setPvmDiOr((c > 0) ? ((int)Math.round((nombreDiOrPvm * 1.0)/ c)) * 100 : 0);
            numeriqueStat.setPvmArBz((d > 0) ? ((int)Math.round((nombreAgBzPvm * 1.0)/ d)) * 100 : 0);
            numeriqueStat.setPvm((f > 0) ? ((int)Math.round((nombrePvm * 1.0)/ f)) * 100 : 0);
            numeriqueStat.setPdv((g > 0) ? ((int)Math.round((nombrePdv * 1.0)/ g)) * 100 : 0);
            
        } catch (DataAccessException ex) {
            Logger.getLogger(ReponseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numeriqueStat;
    }

}
