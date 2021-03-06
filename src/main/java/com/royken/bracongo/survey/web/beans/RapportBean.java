package com.royken.bracongo.survey.web.beans;

import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.projection.BoissonDispoStat;
import com.royken.bracongo.survey.service.IReponseService;
import com.royken.bracongo.survey.service.ISecteurService;
import com.royken.bracongo.survey.service.ServiceException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;



/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "rapportBean")
@RequestScoped
public class RapportBean {

    @EJB
    private IReponseService reponseService;

    @EJB
    private ISecteurService secteurService;

    private BarChartModel model;

    private BarChartModel dispoBiBracModel;

    private BarChartModel dispoBgBracModel;

    private BarChartModel dispoBiBralModel;

    private BarChartModel dispoBgBralModel;

    private BarChartModel model2;

    private BoissonDispoStat resultBiBrac;

    private BoissonDispoStat resultBgBrac;

    private int secteur1;

    private int secteur2;

    private int secteur3;

    private int secteur4;

    private int secteur;

    private BoissonDispoStat resultBgBral;

    private BoissonDispoStat resultBiBral;

    private List<Secteur> secteurs;

    public IReponseService getReponseService() {
        return reponseService;
    }

    public void setReponseService(IReponseService reponseService) {
        this.reponseService = reponseService;
    }

    public int getSecteur1() {
        try {
            secteurs = secteurService.findAllSecteur();
            if(secteurs.size() > 3){
                 Date debut, fin;
                Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    debut = cal.getTime();
                    cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 23);
                    cal.set(Calendar.MINUTE, 0);
                    fin = cal.getTime();

                    System.out.println("Mes datessssssssss");
                    System.out.println(debut + "    ===     "+ fin);
                secteur1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), null, null,debut,fin);
                secteur2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), null, null, debut,fin);
                secteur3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), null, null, debut,fin);
                secteur4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), null, null, debut,fin);
            }
            
        } catch (ServiceException ex) {
            Logger.getLogger(RapportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return secteur1;
    }

    public int getSecteur2() {
        return secteur2;
    }

    public int getSecteur3() {
        return secteur3;
    }

    public int getSecteur4() {
        return secteur4;
    }

    public int getSecteur() {
        return secteur1 + secteur2 + secteur3 + secteur4;
    }

    /**
     * Creates a new instance of RapportBean
     */
    public RapportBean() {
    }

    public ISecteurService getSecteurService() {
        return secteurService;
    }

    public void setSecteurService(ISecteurService secteurService) {
        this.secteurService = secteurService;
    }

    
    public BarChartModel getModel() {
        model = new BarChartModel();
        try {
            int pveDiS1, pveOrS1, pveAgS1, pveBrS1;
            int pveDiS2, pveOrS2, pveAgS2, pveBrS2;
            int pveDiS3, pveOrS3, pveAgS3, pveBrS3;
            int pveDiS4, pveOrS4, pveAgS4, pveBrS4;

            int pvmDiS1, pvmOrS1, pvmAgS1, pvmBrS1;
            int pvmDiS2, pvmOrS2, pvmAgS2, pvmBrS2;
            int pvmDiS3, pvmOrS3, pvmAgS3, pvmBrS3;
            int pvmDiS4, pvmOrS4, pvmAgS4, pvmBrS4;
            if(secteurs == null){
                secteurs = secteurService.findAllSecteur();
            }
            
            if (secteurs.size() >= 4) {

                Date debut, fin;
                Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    debut = cal.getTime();
                    cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 23);
                    cal.set(Calendar.MINUTE, 0);
                    fin = cal.getTime();

                    System.out.println("Mes datessssssssss");
                    System.out.println(debut + "    ===     "+ fin);
                
                pveDiS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Di, debut,fin);
                pveDiS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Di, debut, fin);
                pveDiS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Di, debut, fin);
                pveDiS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Di, debut, fin);

                pveBrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Br, debut, fin);
                pveBrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Br, debut, fin);
                pveBrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Br, debut, fin);
                pveBrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Br, debut, fin);

                pveAgS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Ag, debut, fin);
                pveAgS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Ag, debut, fin);
                pveAgS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Ag, debut, fin);
                pveAgS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Ag, debut, fin);

                pveOrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Or, debut, fin);
                pveOrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Or, debut, fin);
                pveOrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Or, debut, fin);
                pveOrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Or, debut, fin);

                pvmDiS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Di, debut, fin);
                pvmDiS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Di, debut, fin);
                pvmDiS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Di, debut, fin);
                pvmDiS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Di, debut, fin);

                pvmBrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Br, debut, fin);
                pvmBrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Br, debut, fin);
                pvmBrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Br, debut, fin);
                pvmBrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Br, debut, fin);

                pvmAgS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Ag, debut, fin);
                pvmAgS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Ag, debut, fin);
                pvmAgS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Ag, debut, fin);
                pvmAgS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Ag, debut, fin);

                pvmOrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Or, debut, fin);
                pvmOrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Or, debut, fin);
                pvmOrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Or, debut, fin);
                pvmOrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Or, debut, fin);

                ChartSeries pveDi = new ChartSeries();
                pveDi.setLabel("Di-PVE");
                pveDi.set(secteurs.get(0).getCode(), pveDiS1);
                pveDi.set(secteurs.get(1).getCode(), pveDiS2);
                pveDi.set(secteurs.get(2).getCode(), pveDiS3);
                pveDi.set(secteurs.get(3).getCode(), pveDiS4);
                ChartSeries pve0r = new ChartSeries();
                pve0r.setLabel("Or-PVE");
                pve0r.set(secteurs.get(0).getCode(), pveOrS1);
                pve0r.set(secteurs.get(1).getCode(), pveOrS2);
                pve0r.set(secteurs.get(2).getCode(), pveOrS3);
                pve0r.set(secteurs.get(3).getCode(), pveOrS4);
                ChartSeries pveAg = new ChartSeries();
                pveAg.setLabel("AG-PVE");
                pveAg.set(secteurs.get(0).getCode(), pveAgS1);
                pveAg.set(secteurs.get(1).getCode(), pveAgS2);
                pveAg.set(secteurs.get(2).getCode(), pveAgS3);
                pveAg.set(secteurs.get(3).getCode(), pveAgS4);
                ChartSeries pveBr = new ChartSeries();
                pveBr.setLabel("BR-PVE");
                pveBr.set(secteurs.get(0).getCode(), pveBrS1);
                pveBr.set(secteurs.get(1).getCode(), pveBrS2);
                pveBr.set(secteurs.get(2).getCode(), pveBrS3);
                pveBr.set(secteurs.get(3).getCode(), pveBrS4);

                ChartSeries pvmDi = new ChartSeries();
                pvmDi.setLabel("Di-Mixte");
                pvmDi.set(secteurs.get(0).getCode(), pvmDiS1);
                pvmDi.set(secteurs.get(1).getCode(), pvmDiS2);
                pvmDi.set(secteurs.get(2).getCode(), pvmDiS3);
                pvmDi.set(secteurs.get(3).getCode(), pvmDiS4);
                ChartSeries pvm0r = new ChartSeries();
                pvm0r.setLabel("Or-Mixte");
                pvm0r.set(secteurs.get(0).getCode(), pvmOrS1);
                pvm0r.set(secteurs.get(1).getCode(), pvmOrS2);
                pvm0r.set(secteurs.get(2).getCode(), pvmOrS3);
                pvm0r.set(secteurs.get(3).getCode(), pvmOrS4);
                ChartSeries pvmAg = new ChartSeries();
                pvmAg.setLabel("AG-Mixte");
                pvmAg.set(secteurs.get(0).getCode(), pvmAgS1);
                pvmAg.set(secteurs.get(1).getCode(), pvmAgS2);
                pvmAg.set(secteurs.get(2).getCode(), pvmAgS3);
                pvmAg.set(secteurs.get(3).getCode(), pvmAgS4);
                ChartSeries pvmBr = new ChartSeries();
                pvmBr.setLabel("BR-Mixte");
                pvmBr.set(secteurs.get(0).getCode(), pvmBrS1);
                pvmBr.set(secteurs.get(1).getCode(), pvmBrS2);
                pvmBr.set(secteurs.get(2).getCode(), pvmBrS3);
                pvmBr.set(secteurs.get(3).getCode(), pvmBrS4);

                model.addSeries(pveDi);
                model.addSeries(pve0r);
                model.addSeries(pveAg);
                model.addSeries(pveBr);

                model.addSeries(pvmDi);
                model.addSeries(pvm0r);
                model.addSeries(pvmAg);
                model.addSeries(pvmBr);
            }
            model.setTitle("Répartition des visites");
            model.setLegendPosition("ne");
            Axis xAxis = model.getAxis(AxisType.X);
            xAxis.setLabel("Secteur / Regime / Catégorie");
            Axis yAxis = model.getAxis(AxisType.Y);
            yAxis.setLabel("Nombre");
            yAxis.setMin(0);
            yAxis.setMax(20);
        } catch (ServiceException ex) {
            Logger.getLogger(RapportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return model;
    }

    public void setModel(BarChartModel model) {
        this.model = model;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }

    public BarChartModel getDispoBiBracModel() {
        dispoBiBracModel = new BarChartModel();
        return dispoBiBracModel;
    }

    public void setDispoBiBracModel(BarChartModel dispoBiBracModel) {
        this.dispoBiBracModel = dispoBiBracModel;
    }

    public BarChartModel getDispoBgBracModel() {
        return dispoBgBracModel;
    }

    public void setDispoBgBracModel(BarChartModel dispoBgBracModel) {
        this.dispoBgBracModel = dispoBgBracModel;
    }

    public BarChartModel getDispoBiBralModel() {
        return dispoBiBralModel;
    }

    public void setDispoBiBralModel(BarChartModel dispoBiBralModel) {
        this.dispoBiBralModel = dispoBiBralModel;
    }

    public BarChartModel getDispoBgBralModel() {
        return dispoBgBralModel;
    }

    public void setDispoBgBralModel(BarChartModel dispoBgBralModel) {
        this.dispoBgBralModel = dispoBgBralModel;
    }

    public BarChartModel getModel2() {
        try {
             Date debut, fin;
                Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    debut = cal.getTime();
                    cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 23);
                    cal.set(Calendar.MINUTE, 0);
                    fin = cal.getTime();

                    System.out.println("Mes datessssssssss");
                    System.out.println(debut + "    ===     "+ fin);
            resultBiBrac = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.TRUE, Boolean.TRUE);
            System.out.println(resultBiBrac);
            model2 = new BarChartModel();
            Map<String, Integer> data1 = resultBiBrac.getPveDiEtOr();
            ChartSeries pveDiOr = new ChartSeries();
            pveDiOr.setLabel("PVE(Di & Or)");
            for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                pveDiOr.set(key, value == -1 ? 0 : value);
            }

            Map<String, Integer> data2 = resultBiBrac.getPveAgEtBr();
            ChartSeries pveAgBr = new ChartSeries();
            pveAgBr.setLabel("PVE(Ag & Br)");
            for (Map.Entry<String, Integer> entrySet : data2.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                pveAgBr.set(key, value == -1 ? 0 : value);
            }

            Map<String, Integer> data3 = resultBiBrac.getPvmDiEtOr();
            ChartSeries pvmDiOr = new ChartSeries();
            pvmDiOr.setLabel("PVM(Di & Or)");
            for (Map.Entry<String, Integer> entrySet : data3.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                pvmDiOr.set(key, value == -1 ? 0 : value);
            }

            Map<String, Integer> data4 = resultBiBrac.getPvmAgEtBr();
            ChartSeries pvmAgBr = new ChartSeries();
            pvmAgBr.setLabel("PVM(Ag & Br)");
            for (Map.Entry<String, Integer> entrySet : data4.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                pvmAgBr.set(key, value == -1 ? 0 : value);
            }
            model2.addSeries(pveDiOr);
            model2.addSeries(pveAgBr);
            model2.addSeries(pvmDiOr);
            model2.addSeries(pvmAgBr);

            model2.setTitle("Disponibilité Bière Bracongo");
            model2.setLegendPosition("e");
            model2.setShowPointLabels(true);
            model2.getAxes().put(AxisType.X, new CategoryAxis("Format de Boisson"));
            Axis yAxis = model2.getAxis(AxisType.Y);
            Axis xAxis = model2.getAxis(AxisType.X);
            yAxis.setLabel("Pourcentage (%)");
            yAxis.setMin(0);
            // yAxis.setMax(3);
            yAxis.setMax(100);
            xAxis.setTickAngle(60);
            return model2;
        } catch (ServiceException ex) {
            Logger.getLogger(RapportBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
