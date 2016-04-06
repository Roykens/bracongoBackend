package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Circuit;
import com.royken.bracongo.survey.entities.PointDeVente;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypePdv;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.Zone;
import com.royken.bracongo.survey.service.ICircuitService;
import com.royken.bracongo.survey.service.IPointDeVenteService;
import com.royken.bracongo.survey.service.ISecteurService;
import com.royken.bracongo.survey.service.IZoneService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.bracongo.survey.service.util.ImportationResult;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "pointDeVenteBean")
@SessionScoped
public class PointDeVenteBean implements Serializable {
    
    private Secteur secteur;
    
    private Zone zone;
    
    private Circuit circuit;
    
    private PointDeVente[] pointDeVenteChoisis;

    private List<PointDeVente> pointDeVntes = new ArrayList<PointDeVente>();

    private UploadedFile file;

    private PointDeVente pointDeVente = new PointDeVente();

    private List<Circuit> circuits;

    private List<Zone> zones;

    private List<Secteur> secteurs;

    private String id;

    private List<TypeCategorie> typeCategories = new ArrayList<TypeCategorie>();

    private List<TypePdv> typePdvs = new ArrayList<TypePdv>();

    private List<TypeRegime> typeRegimes = new ArrayList<TypeRegime>();

    @EJB
    private IPointDeVenteService pointDeVenteService;

    @EJB
    private ICircuitService circuitService;

    @EJB
    private IZoneService zoneService;

    @EJB
    private ISecteurService secteurService;

    Long idS = -1L, idZ = -1L, idC = -1L, idA = -1L;
    
    private ImportationResult importationResult = new ImportationResult();

    private MapModel model = new DefaultMapModel();
    
    private Long idCircuit;
    
    private MapModel simpleModel;
    
    private Marker marker;

    /**
     * Creates a new instance of PointDeVenteBean
     */
    public PointDeVenteBean() {
        //model.addOverlay(new Marker(new LatLng(36.879466, 30.667648), "M1"));
        typeRegimes.add(TypeRegime.PVE);
        typeRegimes.add(TypeRegime.Mixte);
        typePdvs.add(TypePdv.BAR);
        typePdvs.add(TypePdv.DEPOT);
        typePdvs.add(TypePdv.MIXTE);
        typeCategories.add(TypeCategorie.Di);
        typeCategories.add(TypeCategorie.Ag);
        typeCategories.add(TypeCategorie.Br);
        typeCategories.add(TypeCategorie.Or);
    }
    
    @PostConstruct
    public void init() {
        simpleModel = new DefaultMapModel();
          
        //Shared coordinates
        LatLng coord1 = new LatLng(36.879466, 30.667648);
        LatLng coord2 = new LatLng(36.883707, 30.689216);
        LatLng coord3 = new LatLng(36.879703, 30.706707);
        LatLng coord4 = new LatLng(36.885233, 30.702323);
          
        //Basic marker
        simpleModel.addOverlay(new Marker(coord1, "Konyaalti"));
        simpleModel.addOverlay(new Marker(coord2, "Ataturk Parki"));
        simpleModel.addOverlay(new Marker(coord3, "Karaalioglu Parki"));
        simpleModel.addOverlay(new Marker(coord4, "Kaleici"));
    }
  
    public MapModel getSimpleModel() {
        return simpleModel;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public PointDeVente[] getPointDeVenteChoisis() {
        return pointDeVenteChoisis;
    }

    public void setPointDeVenteChoisis(PointDeVente[] pointDeVenteChoisis) {
        this.pointDeVenteChoisis = pointDeVenteChoisis;
    }

   

    public List<PointDeVente> getPointDeVntes() {
        try {
          //  pointDeVntes = pointDeVenteService.findAllPointDeVente();
            filtrer();
            return pointDeVntes;
            //  
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public List<Circuit> getCircuits() {
        try {
            circuits = circuitService.findAllCircuit();
            return circuits;
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Collections.EMPTY_LIST;
    }

    public List<Zone> getZones() throws ServiceException {
        zones = zoneService.findAllZone();
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Secteur> getSecteurs() throws ServiceException {
        secteurs = secteurService.findAllSecteur();
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public IZoneService getZoneService() {
        return zoneService;
    }

    public void setZoneService(IZoneService zoneService) {
        this.zoneService = zoneService;
    }

    public ISecteurService getSecteurService() {
        return secteurService;
    }

    public void setSecteurService(ISecteurService secteurService) {
        this.secteurService = secteurService;
    }

    public Long getIdS() {
        return idS;
    }

    public void setIdS(Long idS) {
        this.idS = idS;
    }

    public Long getIdZ() {
        return idZ;
    }

    public void setIdZ(Long idZ) {
        this.idZ = idZ;
    }

    public Long getIdC() {
        return idC;
    }

    public void setIdC(Long idC) {
        this.idC = idC;
    }

    public Long getIdA() {
        return idA;
    }

    public void setIdA(Long idA) {
        this.idA = idA;
    }

    public MapModel getModel() {
        
         model.addOverlay(new Marker(new LatLng(36.879466, 30.667648), "M1"));
//        if (pointDeVntes.size() > 0) {
//            for (PointDeVente pointDeVnte : pointDeVntes) {
//                model.addOverlay(new Marker(new LatLng(pointDeVnte.getLatitude(), pointDeVnte.getLongitude()), pointDeVnte.getNom()));
//            }
//            System.out.println("J'ai mis  "+ pointDeVntes.size() + "points");
//        }
        return model;
    }
    
      public MapModel getModel2() {
          System.out.println("J'entre");
          System.out.println(pointDeVntes.size());
        // model.addOverlay(new Marker(new LatLng(36.879466, 30.667648), "M1"));
          //model.addOverlay(new Marker(new LatLng(-4.328993, 15.340236), "M1"));
        if (pointDeVntes.size() > 0) {
            for (PointDeVente pointDeVnte : pointDeVntes) {
                model.addOverlay(new Marker(new LatLng(pointDeVnte.getLatitude(), pointDeVnte.getLongitude()), pointDeVnte.getNom()));
             //   model.addOverlay(new Marker);
            }
            System.out.println("J'ai mis  "+ pointDeVntes.size() + "points");
        }
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }

    public void setCircuits(List<Circuit> circuits) {
        this.circuits = circuits;
    }

    public void setPointDeVntes(List<PointDeVente> pointDeVntes) {
        this.pointDeVntes = pointDeVntes;
    }

    public IPointDeVenteService getPointDeVenteService() {
        return pointDeVenteService;
    }

    public void setPointDeVenteService(IPointDeVenteService pointDeVenteService) {
        this.pointDeVenteService = pointDeVenteService;
    }

    public PointDeVente getPointDeVente() {

        return pointDeVente;
    }

    public void setPointDeVente(PointDeVente pointDeVente) {
        this.pointDeVente = pointDeVente;
    }

    public ICircuitService getCircuitService() {
        return circuitService;
    }

    public void setCircuitService(ICircuitService circuitService) {
        this.circuitService = circuitService;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void saveOrUpdatePDV() {
        System.out.println(pointDeVente);
        try {
            pointDeVente.setCircuit(circuitService.findCircuitById(Long.valueOf(id)));
            pointDeVenteService.saveOrUpdatePDV(pointDeVente);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Opération reussie", pointDeVente.getNom() + " a été enregistré "));
        } catch (ServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec opération", pointDeVente.getNom() + " n'a pas été enregistré "));
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointDeVente = new PointDeVente();
    }

    public void deletePvd() {
        try {
            pointDeVenteService.deletePDV(pointDeVente.getId());
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        pointDeVente = new PointDeVente();
    }

    public List<TypeCategorie> getTypeCategories() {
        return typeCategories;
    }

    public void setTypeCategories(List<TypeCategorie> typeCategories) {
        this.typeCategories = typeCategories;
    }

    public List<TypePdv> getTypePdvs() {
        return typePdvs;
    }

    public void setTypePdvs(List<TypePdv> typePdvs) {
        this.typePdvs = typePdvs;
    }

    public List<TypeRegime> getTypeRegimes() {
        return typeRegimes;
    }

    public void setTypeRegimes(List<TypeRegime> typeRegimes) {
        this.typeRegimes = typeRegimes;
    }

    public void filtrer() throws ServiceException {
        System.out.println("Je filtre");
        System.out.println("");
        pointDeVntes = new ArrayList<PointDeVente>();
        pointDeVntes = pointDeVenteService.findByCriteria((idS == null) ? -1 : idS,
                (idZ == null) ? -1 : idZ,
                (idC == null) ? -1 : idC);
        getModel2();
    }

    public ImportationResult getImportationResult() {
        return importationResult;
    }

    public void setImportationResult(ImportationResult importationResult) {
        this.importationResult = importationResult;
    }

    public Long getIdCircuit() {
        return idCircuit;
    }

    public void setIdCircuit(Long idCircuit) {
        this.idCircuit = idCircuit;
    }

    
    public ImportationResult importer() throws IOException {
        try {
            //  noteService.importNotes(file.getInputstream(),idC,idE, idAca,session.ordinal());
            System.out.println("Le circuit");
            System.out.println(idCircuit);
            importationResult = new ImportationResult();
            importationResult =  pointDeVenteService.importPdv(file.getInputstream(), idCircuit);
            System.out.println(importationResult);
            return importationResult;
        } catch (ServiceException ex) {
            Logger.getLogger(PointDeVenteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void toto(){
        System.out.println("J'ai pris ");
        for (PointDeVente pointDeVenteChoisi : pointDeVenteChoisis) {
            System.out.println(pointDeVenteChoisi);
        }
    }
    
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }
      
    public Marker getMarker() {
        return marker;
    }
    
    

}
