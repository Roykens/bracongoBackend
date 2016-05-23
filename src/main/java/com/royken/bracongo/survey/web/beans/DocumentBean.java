package com.royken.bracongo.survey.web.beans;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.projection.BoissonDispoStat;
import com.royken.bracongo.survey.entities.projection.BoissonPrixStat;
import com.royken.bracongo.survey.entities.projection.DisponibiliteNumeriqueStat;
import com.royken.bracongo.survey.service.IReponseService;
import com.royken.bracongo.survey.service.ISecteurService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.generic.dao.DataAccessException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTimeFieldType;
import org.joda.time.LocalTime;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "documentBean")
@RequestScoped
public class DocumentBean {

    private Boolean choix;

    @EJB
    private IReponseService reponseService;

    @EJB
    private ISecteurService secteurService;

    private StreamedContent fichier;

    private Date debut;

    private Date fin;

    float llx;
    float lly;
    float urx;
    float ury;

    int nombrePVE;
    int nombreMixte;

    BoissonDispoStat resultBiBrac;

    BoissonDispoStat resultBgBrac;

    BoissonDispoStat resultBgBral;

    BoissonDispoStat resultBiBral;

    Map<Integer, BoissonPrixStat> prixStatBrac;

    Map<Integer, BoissonPrixStat> prixStatBral;

    public Boolean getChoix() {
        return choix;
    }

    public void setChoix(Boolean choix) {
        this.choix = choix;
    }

    public IReponseService getReponseService() {
        return reponseService;
    }

    public void setReponseService(IReponseService reponseService) {
        this.reponseService = reponseService;
    }

    public ISecteurService getSecteurService() {
        return secteurService;
    }

    public void setSecteurService(ISecteurService secteurService) {
        this.secteurService = secteurService;
    }

    public Date getDebut() {
        return debut;
    }

    public StreamedContent getFichier() {
        return fichier;
    }

    public void setFichier(StreamedContent fichier) {
        this.fichier = fichier;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    /**
     * Creates a new instance of DocumentBean
     */
    public DocumentBean() {
    }

    public void produceDocument() {

        System.out.println("TEST DES DONNEES choix ===========");
        System.out.println(choix);
        System.out.println("=====================");

        System.out.println("TEST DES DONNEES debut ===========");
        System.out.println(debut);
        System.out.println("=====================");

        System.out.println("TEST DES DONNEES fin ===========");
        System.out.println(fin);
        System.out.println("=====================");

        FacesContext context = FacesContext.getCurrentInstance();

        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            try {
                HttpServletResponse hsr = (HttpServletResponse) response;
                hsr.setContentType("application/pdf");
                hsr.setHeader("Content-Disposition", "attachment; filename=pv.pdf");
                produceRapport(((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                context.responseComplete();
            } catch (IOException ex) {
                Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void produceRapport(OutputStream stream) {
        Document doc = null;
        try {
            resultBiBrac = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.TRUE, Boolean.TRUE);
            resultBgBrac = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.FALSE, Boolean.TRUE);
            resultBgBral = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.FALSE, Boolean.FALSE);
            resultBiBral = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.TRUE, Boolean.FALSE);
            prixStatBrac = reponseService.getPrixMoyen(debut, fin, Boolean.TRUE, Boolean.TRUE);
            prixStatBral = reponseService.getPrixMoyen(debut, fin, Boolean.TRUE, Boolean.FALSE);
            doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, stream);
            doc.setPageSize(PageSize.A4.rotate());

            doc.open();
            llx = 5;
            lly = doc.bottom() + 515;
            urx = doc.right() + 30;
            ury = doc.top() + 20;
            produceReportMarket(writer, doc);
            doc.newPage();
            produceReportDisponiliteBiere(writer, doc);
            doc.newPage();
            produceReportClassement(writer, doc);
            doc.newPage();
            produceReportRespectPrix(writer, doc);
            doc.newPage();
            produceReportParcEmballage(writer, doc);
            doc.newPage();
            produceReportMarche(writer, doc);
            doc.newPage();
            produceReportAction(writer, doc);
            doc.close();
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produceReportMarket(PdfWriter writer, Document document) {
        try {
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect1 = new Rectangle(llx, lly, urx, ury);
            rect1.setBackgroundColor(BaseColor.GRAY);
            rect1.setBorder(Rectangle.BOX);
            rect1.setBorderWidth(1);

            canvas.rectangle(rect1);
            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(rect1);
            //ct.SetSimpleColumn(rect);
            Font f1 = new Font(Font.FontFamily.HELVETICA, 17.0f, Font.BOLD);
            Font f2 = new Font(Font.FontFamily.HELVETICA, 20.0f, Font.BOLD, BaseColor.WHITE);
            Font ff = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.BOLD, BaseColor.WHITE);
            Chunk c1 = new Chunk("BRAC-DMC-EN-073\t\t\t", f1);
            Chunk c2 = new Chunk("              Report Market Audit ", f2);
            StringBuilder dateString1 = new StringBuilder();
            if(choix){
                dateString1.append("Week : ").append(getWeekFromDate(debut));
            }
            else{
                dateString1.append("Month : ").append(getMonthFromDate(debut));
            }
            Chunk wee1 = new Chunk(dateString1.toString(), f2);
            Chunk date1 = new Chunk(" From "+dateString(debut)+" to "+ dateString(fin), ff);
            Phrase p = new Phrase();
            p.add(c1);
            p.add(c2);
            p.add(wee1);
            p.add(date1);
            Paragraph pa = new Paragraph(p);
            pa.setPaddingTop(30);
            ct.setYLine(ury - 5);
            ct.setAlignment(Element.ALIGN_MIDDLE);
            ct.addElement(pa);
            ct.go();
            document.add(new Paragraph("\n"));

            int pveDiS1, pveOrS1, pveAgS1, pveBrS1;
            int pveDiS2, pveOrS2, pveAgS2, pveBrS2;
            int pveDiS3, pveOrS3, pveAgS3, pveBrS3;
            int pveDiS4, pveOrS4, pveAgS4, pveBrS4;

            int pvmDiS1, pvmOrS1, pvmAgS1, pvmBrS1;
            int pvmDiS2, pvmOrS2, pvmAgS2, pvmBrS2;
            int pvmDiS3, pvmOrS3, pvmAgS3, pvmBrS3;
            int pvmDiS4, pvmOrS4, pvmAgS4, pvmBrS4;
            List<Secteur> secteurs = secteurService.findAllSecteur();

            pveDiS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Di, debut, fin);
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

            Font f3 = new Font(Font.FontFamily.HELVETICA, 16.0f, Font.BOLD);
            Font f4 = new Font(Font.FontFamily.HELVETICA, 16.0f, Font.NORMAL);
            Font f5 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.NORMAL);
            Chunk per = new Chunk("Période : ", f3);
            
            StringBuilder dateString = new StringBuilder();
            if(choix){
                dateString.append("Week ").append(getWeekFromDate(debut)+" ");
            }
            else{
                dateString.append("Month ").append(getMonthFromDate(debut)+" ");
            }
            Chunk wee = new Chunk(dateString.toString(), f4);
            Chunk date = new Chunk(dateString(debut)+" au "+ dateString(fin), f4);

            Paragraph p1 = new Paragraph();
            p1.add(per);
            p1.add(wee);
            p1.add(date);
            
            //p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            document.add(new Chunk("\n"));
            Chunk pdv = new Chunk("PDV opérationnels visités : ... ", f3);
            StringBuilder resume = new StringBuilder("PDV visités au courant");
            Calendar cal = Calendar.getInstance();
            cal.setTime(debut);
            int year = cal.get(Calendar.YEAR);
            if(choix){
                resume.append(" de la semaine ");
                resume.append(getWeekFromDate(debut));
            }
            else{
                resume.append(" du mois ");
                resume.append(getMonthFromDate(debut));
            }
            resume.append("-");
            resume.append(year);
            resume.append(" ( du "+ dateString(debut)+" au "+ dateString(fin)+")");
            resume.append(" par les Markets Audit Officers selon la configuration dans le tableau ci-dessous.");
            Chunk pdv1 = new Chunk(resume.toString(), f4);
            p1 = new Paragraph();
            p1.add(pdv);
            p1.add(pdv1);
            p1.setLeading(25);
            document.add(p1);

            document.add(new Chunk("\n"));
            float[] relativewidths = {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3};
            PdfPTable table = new PdfPTable(relativewidths);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);

            table.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f3, 1, 4, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("MIXTES", f3, 1, 4, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("TOTAL PDV", f3, 1, 4, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("TOTAL\n PAR\n SECTEUR", f3, 2, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("SECTEUR", f3, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("DI", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("OR", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("AG", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("BR", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("DI", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("OR", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("AG", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("BR", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("DI", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("OR", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("AG", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("BR", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("S1", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS1 + pvmDiS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS1 + pvmOrS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS1 + pvmAgS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS1 + pvmBrS1 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS1 + pvmDiS1 + pveOrS1 + pvmOrS1 + pveAgS1 + pvmAgS1 + pveBrS1 + pvmBrS1 + "", f5, 1, 1, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell("S2", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS2 + pvmDiS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS2 + pvmOrS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS2 + pvmAgS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS2 + pvmBrS2 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS2 + pvmDiS2 + pveOrS2 + pvmOrS2 + pveAgS2 + pvmAgS2 + pveBrS2 + pvmBrS2 + "", f5, 1, 1, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell("S3", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS3 + pvmDiS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS3 + pvmOrS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS3 + pvmAgS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS3 + pvmBrS3 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS3 + pvmDiS3 + pveOrS3 + pvmOrS3 + pveAgS3 + pvmAgS3 + pveBrS3 + pvmBrS3 + "", f5, 1, 1, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell("S4", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS4 + pvmDiS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS4 + pvmOrS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS4 + pvmAgS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS4 + pvmBrS4 + "", f5, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS4 + pvmDiS4 + pveOrS4 + pvmOrS4 + pveAgS4 + pvmAgS4 + pveBrS4 + pvmBrS4 + "", f5, 1, 1, false, BaseColor.WHITE));

            int a, b, c, d, e, f, g, h, i, j, k, l;
            table.addCell(DocumentUtil.createDefaultHeaderCell("Total", f5, 1, 1, false, BaseColor.WHITE));
            a = pveDiS1 + pveDiS2 + pveDiS3 + pveDiS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(a + "", f3, 1, 1, false, BaseColor.WHITE));
            b = pveOrS1 + pveOrS2 + pveOrS3 + pveOrS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(b + "", f3, 1, 1, false, BaseColor.WHITE));
            c = pveAgS1 + pveAgS2 + pveAgS3 + pveAgS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(c + "", f3, 1, 1, false, BaseColor.WHITE));
            d = pveBrS1 + pveBrS2 + pveBrS3 + pveBrS4;

            table.addCell(DocumentUtil.createDefaultHeaderCell(d + "", f3, 1, 1, false, BaseColor.WHITE));
            e = pvmDiS1 + pvmDiS2 + pvmDiS3 + pvmDiS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(e + "", f3, 1, 1, false, BaseColor.WHITE));
            f = pvmOrS1 + pvmOrS2 + pvmOrS3 + pvmOrS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(f + "", f3, 1, 1, false, BaseColor.WHITE));
            g = pvmAgS1 + pvmAgS2 + pvmAgS3 + pvmAgS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(g + "", f3, 1, 1, false, BaseColor.WHITE));
            h = pvmBrS1 + pvmBrS2 + pvmBrS3 + pvmBrS4;

            table.addCell(DocumentUtil.createDefaultHeaderCell(h + "", f3, 1, 1, false, BaseColor.WHITE));
            i = a + e;
            table.addCell(DocumentUtil.createDefaultHeaderCell(i + "", f3, 1, 1, false, BaseColor.WHITE));
            j = b + f;
            table.addCell(DocumentUtil.createDefaultHeaderCell(j + "", f3, 1, 1, false, BaseColor.WHITE));
            k = c + g;
            table.addCell(DocumentUtil.createDefaultHeaderCell(k + "", f3, 1, 1, false, BaseColor.WHITE));
            l = d + h;
            table.addCell(DocumentUtil.createDefaultHeaderCell(l + "", f3, 1, 1, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell(i + j + k + l + "", f3, 1, 1, false, BaseColor.WHITE));

            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);
            nombrePVE = a + b + c + d;
            nombreMixte = e + f + g + h;
            table.addCell(DocumentUtil.createDefaultHeaderCell(a + b + c + d + "", f3, 1, 4, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell(e + f + g + h + "", f3, 1, 4, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(i + j + k + l + "", f3, 1, 4, false, BaseColor.WHITE));
            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);

            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void produceReportDisponiliteBiere(PdfWriter writer, Document document) {
        try {
            int nombreBibra = resultBiBrac.getPveAgEtBr().size();
            int nombreBgBrac = resultBgBrac.getPveDiEtOr().size();
            int nombreBgBral = resultBgBral.getPveAgEtBr().size();
            int nombreBiBral = resultBiBral.getPveDiEtOr().size();
            DisponibiliteNumeriqueStat numeriqueStatBiBrac = reponseService.getAllDispoStat(debut, fin, Boolean.TRUE, Boolean.TRUE);
            DisponibiliteNumeriqueStat numeriqueStatBgBrac = reponseService.getAllDispoStat(debut, fin, Boolean.FALSE, Boolean.TRUE);
            DisponibiliteNumeriqueStat numeriqueStatBiBral = reponseService.getAllDispoStat(debut, fin, Boolean.TRUE, Boolean.FALSE);
            DisponibiliteNumeriqueStat numeriqueStatBgBral = reponseService.getAllDispoStat(debut, fin, Boolean.FALSE, Boolean.FALSE);
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect1 = new Rectangle(llx, lly, urx, ury);
            rect1.setBackgroundColor(BaseColor.GRAY);
            rect1.setBorder(Rectangle.BOX);
            rect1.setBorderWidth(1);
            canvas.rectangle(rect1);
            ColumnText ct = new ColumnText(writer.getDirectContent());

            ct.setSimpleColumn(rect1);
            //ct.SetSimpleColumn(rect);
            Font f1 = new Font(Font.FontFamily.HELVETICA, 17.0f, Font.BOLD);
            Font f2 = new Font(Font.FontFamily.HELVETICA, 20.0f, Font.BOLD, BaseColor.WHITE);
            Chunk c1 = new Chunk("BRAC-DMC-EN-073\t\t\t", f1);
            Chunk c2 = new Chunk("              Disponibilité Bières & BG (Di&Or - Ag&Br)", f2);
            Phrase p = new Phrase();
            p.add(c1);
            p.add(c2);
            Paragraph pa = new Paragraph(p);
            pa.setPaddingTop(30);
            ct.setYLine(ury - 5);
            ct.setAlignment(Element.ALIGN_MIDDLE);
            ct.addElement(pa);
            ct.go();
            document.add(new Paragraph("\n"));
            float[] relativewidths1 = new float[nombreBibra + nombreBiBral + 3];
            int i = 0;
            relativewidths1[i] = 2;
            for (i = 1; i < nombreBibra + nombreBiBral + 3; i++) {
                relativewidths1[i] = 1;
            }

            float[] relativewidths2 = new float[nombreBgBrac + nombreBgBral + 3];
            int j = 0;
            relativewidths2[j] = 2;
            for (j = 1; j < nombreBgBrac + nombreBgBral + 3; j++) {
                relativewidths2[j] = 1;
            }

            Font f5 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.BOLD);
            Font f6 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.NORMAL);
            Font f7 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL);
            Font f8 = new Font(Font.FontFamily.HELVETICA, 8f, Font.NORMAL);

            PdfPTable biereTable = new PdfPTable(relativewidths1);
            biereTable.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBorderColor(BaseColor.WHITE);
            biereTable.addCell(cell);
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BIÈRES BRACONGO", f7, 1, nombreBibra + 1, false, BaseColor.WHITE));
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BIÈRES BRALIMA", f7, 1, nombreBiBral + 1, false, BaseColor.WHITE));
            Map<String, Integer> data1 = resultBiBrac.getPveDiEtOr();
            Map<String, Integer> data2 = resultBiBrac.getPveAgEtBr();
            Map<String, Integer> data3 = resultBiBrac.getPve();
            Map<String, Integer> data4 = resultBiBrac.getPvmDiEtOr();
            Map<String, Integer> data5 = resultBiBrac.getPvmAgEtBr();
            Map<String, Integer> data6 = resultBiBrac.getPvm();
            Map<String, Integer> data7 = resultBiBrac.getPdv();

            Map<String, Integer> datai1 = resultBgBrac.getPveDiEtOr();
            Map<String, Integer> datai2 = resultBgBrac.getPveAgEtBr();
            Map<String, Integer> datai3 = resultBgBrac.getPve();
            Map<String, Integer> datai4 = resultBgBrac.getPvmDiEtOr();
            Map<String, Integer> datai5 = resultBgBrac.getPvmAgEtBr();
            Map<String, Integer> datai6 = resultBgBrac.getPvm();
            Map<String, Integer> datai7 = resultBgBrac.getPdv();

            Map<String, Integer> data1a = resultBiBral.getPveDiEtOr();
            Map<String, Integer> data2a = resultBiBral.getPveAgEtBr();
            Map<String, Integer> data3a = resultBiBral.getPve();
            Map<String, Integer> data4a = resultBiBral.getPvmDiEtOr();
            Map<String, Integer> data5a = resultBiBral.getPvmAgEtBr();
            Map<String, Integer> data6a = resultBiBral.getPvm();
            Map<String, Integer> data7a = resultBiBral.getPdv();

            Map<String, Integer> datai1a = resultBgBral.getPveDiEtOr();
            Map<String, Integer> datai2a = resultBgBral.getPveAgEtBr();
            Map<String, Integer> datai3a = resultBgBral.getPve();
            Map<String, Integer> datai4a = resultBgBral.getPvmDiEtOr();
            Map<String, Integer> datai5a = resultBgBral.getPvmAgEtBr();
            Map<String, Integer> datai6a = resultBgBral.getPvm();
            Map<String, Integer> datai7a = resultBgBral.getPdv();

            /* J'entre les noms des bières bracongo*/
            for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
                String key = entrySet.getKey();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true, BaseColor.WHITE));
            }

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV D.N. = 1", f8, 1, 1, true, BaseColor.WHITE));

            /* J'entre les noms des bières bralima*/
            for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
                String key = entrySet.getKey();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV D.N. = 1", f8, 1, 1, true, BaseColor.WHITE));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Di&Or)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPveDiO() + "%", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPveDiO() + "%", f8, 1, 1, false, BaseColor.WHITE));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Ag&Br)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data2.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPveArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : data2a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPveArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve */
            for (Map.Entry<String, Integer> entrySet : data3.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPve() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : data3a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPve() + "%", f8, 1, 1, false, BaseColor.WHITE));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Di&Or)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data4.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPvmDiOr() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : data4a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPvmDiOr() + "%", f8, 1, 1, false, BaseColor.WHITE));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Ag & Br)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pvm Ag et Bz*/
            for (Map.Entry<String, Integer> entrySet : data5.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPvmArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : data5a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPvmArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data6.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPvm() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : data6a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPvm() + "%", f8, 1, 1, false, BaseColor.WHITE));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("GLOBAL", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data7.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPdv() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : data7a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPdv() + "%", f8, 1, 1, false, BaseColor.WHITE));
            document.add(biereTable);
            document.add(new Chunk("\n"));

            PdfPTable bgTable = new PdfPTable(relativewidths2);
            bgTable.setWidthPercentage(100);
            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBorderColor(BaseColor.WHITE);
            bgTable.addCell(cell);
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BG BRACONGO", f7, 1, nombreBgBrac + 1, false, BaseColor.WHITE));
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BG BRALIMA", f7, 1, nombreBgBral + 1, false, BaseColor.WHITE));

            /* J'entre les noms des bières bracongo*/
            for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
                String key = entrySet.getKey();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true, BaseColor.WHITE));
            }

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV\nD.N. = 1", f8, 1, 1, true, BaseColor.WHITE));

            /* J'entre les noms des bières bralima*/
            for (Map.Entry<String, Integer> entrySet : datai1a.entrySet()) {
                String key = entrySet.getKey();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV\n D.N. = 1", f8, 1, 1, true, BaseColor.WHITE));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Di&Or)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPveDiO() + "%", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<String, Integer> entrySet : datai1a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPveDiO() + "%", f8, 1, 1, false, BaseColor.WHITE));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Ag&Br)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai2.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPveArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : datai2a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPveArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve */
            for (Map.Entry<String, Integer> entrySet : datai3.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPve() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : datai3a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPve() + "%", f8, 1, 1, false, BaseColor.WHITE));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Di&Or)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai4.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPvmDiOr() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : datai4a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPvmDiOr() + "%", f8, 1, 1, false, BaseColor.WHITE));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Ag & Br)", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pvm Ag et Bz*/
            for (Map.Entry<String, Integer> entrySet : datai5.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPvmArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : datai5a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPvmArBz() + "%", f8, 1, 1, false, BaseColor.WHITE));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai6.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPvm() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : datai6a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPvm() + "%", f8, 1, 1, false, BaseColor.WHITE));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("GLOBAL", f8, 1, 1, false, BaseColor.WHITE));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai7.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPdv() + "%", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : datai7a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPdv() + "%", f8, 1, 1, false, BaseColor.WHITE));

            document.add(bgTable);
        } catch (DocumentException ex) {
            Logger.getLogger(DocumentBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void produceReportClassement(PdfWriter writer, Document document) {
        try {
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect1 = new Rectangle(llx, lly, urx, ury);
            Rectangle rect2 = new Rectangle(llx, lly, urx, ury);
            rect1.setBackgroundColor(BaseColor.GRAY);
            rect1.setBorder(Rectangle.BOX);
            rect1.setBorderWidth(1);

            canvas.rectangle(rect1);
            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(rect1);
            //ct.SetSimpleColumn(rect);
            Font f1 = new Font(Font.FontFamily.HELVETICA, 17.0f, Font.BOLD);
            Font f2 = new Font(Font.FontFamily.HELVETICA, 20.0f, Font.BOLD, BaseColor.WHITE);
            Chunk c1 = new Chunk("BRAC-DMC-EN-073\t\t\t", f1);
            Chunk c2 = new Chunk("    Classement Disponibilité Bières & BG (Di&Or - Ag&Br) et Disponibilité moyenne générale", f2);
            Phrase p = new Phrase();
            p.add(c1);
            p.add(c2);
            Paragraph pa = new Paragraph(p);
            pa.setPaddingTop(30);
            ct.setYLine(ury - 5);
            ct.setAlignment(Element.ALIGN_MIDDLE);
            ct.addElement(pa);
            ct.go();
            document.add(new Paragraph("\n"));

            Map<String, Integer> pveDiOrSup = getclassementRegimeCategorieSup(true, true, true);
            Map<String, Integer> pveDiOrInf = getclassementRegimeCategorieSup(true, true, false);
            Map<String, Integer> pveAgBzSup = getclassementRegimeCategorieSup(true, false, true);
            Map<String, Integer> pveAgBzInf = getclassementRegimeCategorieSup(true, false, false);
            Map<String, Integer> pvmDiOrSup = getclassementRegimeCategorieSup(false, true, true);
            Map<String, Integer> pvmDiOrInf = getclassementRegimeCategorieSup(false, true, false);
            Map<String, Integer> pvmAgBzSup = getclassementRegimeCategorieSup(false, false, true);
            Map<String, Integer> pvmAgBzInf = getclassementRegimeCategorieSup(false, false, false);
            float[] relativewidth = {3, 0.1f, 3, 0.1f, 4};
            float[] relativewidth1 = {2, 0.1f, 2};
            float[] relativewidth2 = {3, 1};
            Rectangle rect3 = new Rectangle(llx, document.bottom(), urx, lly - 5);
            rect3.setBackgroundColor(BaseColor.WHITE);
            rect3.setBorder(Rectangle.BOX);
            rect3.setBorderWidth(0);
            PdfPTable content = new PdfPTable(relativewidth);
            //content.Setw
            content.setWidthPercentage(100);
            Font f5 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.BOLD);
            Font f6 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.NORMAL);
            Font f7 = new Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD);
            Font f8 = new Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL);

            PdfPTable pve = new PdfPTable(relativewidth1);
            pve.setWidthPercentage(100);
            PdfPTable mixte = new PdfPTable(relativewidth1);
            mixte.setWidthPercentage(100);
            //PdfPTable dispo = new PdfPTable(relativewidth1);

            PdfPTable classement = new PdfPTable(relativewidth2);
            classement.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell(new Phrase("PVE", f8));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setColspan(3);
            pve.addCell(cell);
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Di&Or >= 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pveDiOrSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Di&Or < 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pveDiOrInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            cell = new PdfPCell(classement);
            //  cell.setBorder(0);
            // cell.setBorderWidth(0);
            cell.setBorderColor(BaseColor.WHITE);
            pve.addCell(cell);

            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColorTop(BaseColor.WHITE);
            cell.setBorderColorBottom(BaseColor.WHITE);
            pve.addCell(cell);

            classement = new PdfPTable(relativewidth2);
            classement.setWidthPercentage(100);
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Ag&Br >= 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pveAgBzSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Ag&Br < 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pveAgBzInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            cell = new PdfPCell(classement);
            //  cell.setBorder(0);
            // cell.setBorderWidth(0);
            cell.setBorderColor(BaseColor.WHITE);
            pve.addCell(cell);

            cell = new PdfPCell();
            cell.addElement(pve);
            cell.setPadding(0);
            // cell.setBorder(0);
            //  cell.setBorderWidth(0);
            cell.setBorderWidthRight(2f);

            cell.setBorderColor(BaseColor.WHITE);
            content.addCell(cell);

            classement = new PdfPTable(relativewidth2);
            classement.setWidthPercentage(100);
            cell = new PdfPCell(new Phrase("MIXTE", f8));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setColspan(3);
            mixte.addCell(cell);
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Di&Or >= 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pvmDiOrSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Di&Or < 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pvmDiOrInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }

            cell = new PdfPCell(classement);
            // cell.setBorder(0);
            // cell.setBorderWidth(0);
            //  cell.setBorderWidthLeft(2f);
            cell.setBorderColor(BaseColor.WHITE);
            mixte.addCell(cell);

            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColorTop(BaseColor.WHITE);
            cell.setBorderColorBottom(BaseColor.WHITE);
            mixte.addCell(cell);

            classement = new PdfPTable(relativewidth2);
            classement.setWidthPercentage(100);
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Ag&Br >= 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pvmAgBzSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Ag&Br < 50 %", f8, 1, 2, false, BaseColor.WHITE));
            for (Map.Entry<String, Integer> entrySet : pvmAgBzInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false, BaseColor.WHITE));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false, BaseColor.WHITE));
            }

            cell = new PdfPCell(classement);
            // cell.setBorder(0);
            // cell.setBorderWidth(0);
            // cell.setBorderWidthRight(2f);
            cell.setBorderColor(BaseColor.WHITE);

            mixte.addCell(cell);

            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColorTop(BaseColor.WHITE);
            cell.setBorderColorBottom(BaseColor.WHITE);
            cell.setBorderColorLeft(BaseColor.WHITE);
            cell.setBorderColorRight(BaseColor.WHITE);
            cell.disableBorderSide(1);
            cell.disableBorderSide(0);
            cell.disableBorderSide(2);
            cell.disableBorderSide(3);
            content.addCell(cell);

            cell = new PdfPCell();
            cell.addElement(mixte);
            //cell.setBorder(0);
            cell.setPadding(0);
            cell.setBorderWidthLeft(2f);

            cell.setBorderColor(BaseColor.WHITE);

            content.addCell(cell);

            Paragraph p1 = new Paragraph("Disponibilité moyenne générale", f7);
            p1.setAlignment(Element.ALIGN_CENTER);
            float[] taille = {3, 2, 2, 2, 2};
            PdfPTable moyenne = new PdfPTable(taille);
            moyenne.setWidthPercentage(100);
            cell = new PdfPCell();
            cell.setBorderColor(BaseColor.WHITE);
            cell.setRowspan(2);
            moyenne.addCell(cell);

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Disponibilité moyenne générale Bières", f8, 1, 2, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Disponibilité moyenne générale BG", f8, 1, 2, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bracongo", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bralima", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bracongo", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bralima", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("PVE (Di&Or)", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPveDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPveDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPveDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPveDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("PVE (Ag&Br)", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPveAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPveAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPveAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPveAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPve()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPve()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPve()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPve()) + "%", f8, 1, 1, false, BaseColor.WHITE));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE (Di&Or)", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPvmDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPvmDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPvmDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPvmDiEtOr()) + "%", f8, 1, 1, false, BaseColor.WHITE));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE (Ag&Br)", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPvmAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPvmAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPvmAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPvmAgEtBr()) + "%", f8, 1, 1, false, BaseColor.WHITE));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPvm()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPvm()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPvm()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPvm()) + "%", f8, 1, 1, false, BaseColor.WHITE));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("GLOBAL", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPdv()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPdv()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPdv()) + "%", f8, 1, 1, false, BaseColor.WHITE));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPdv()) + "%", f8, 1, 1, false, BaseColor.WHITE));

            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColorTop(BaseColor.WHITE);
            cell.setBorderColorBottom(BaseColor.WHITE);
            cell.setBorderColorLeft(BaseColor.WHITE);
            cell.setBorderColorRight(BaseColor.WHITE);
            cell.disableBorderSide(2);
            //cell.disableBorderSide(1);
            cell.disableBorderSide(3);
            content.addCell(cell);

            cell = new PdfPCell();
            cell.addElement(p1);
            cell.addElement(Chunk.NEWLINE);
            cell.addElement(moyenne);
            cell.setBorderColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            content.addCell(cell);
            // canvas.rectangle(rect3);
            ColumnText ct2 = new ColumnText(writer.getDirectContent());
            ct2.setSimpleColumn(rect3);
            // ct.setYLine(ury - 5);
            // ct.setAlignment(Element.ALIGN_MIDDLE);
            ct2.addElement(content);
            ct2.go();
            // document.add(content);

        } catch (DocumentException ex) {
            Logger.getLogger(DocumentBean.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int getMoyenne(Map<String, Integer> map) {
        int temp = 0;
        int taille = map.size();
        for (Map.Entry<String, Integer> entrySet : map.entrySet()) {
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            temp += value;

        }

        return taille != 0 ? temp / taille : 0;
    }

    /* superieur designe si le % est superieur a 50*/
    private Map<String, Integer> getclassementRegimeCategorieSup(boolean pve, boolean diOr, boolean superieur) {
        Map<String, Integer> bibrac;
        Map<String, Integer> bibral;
        Map<String, Integer> result = new HashMap<String, Integer>();
        if (pve) {
            if (diOr) {
                bibrac = resultBiBrac.getPveDiEtOr();
                bibral = resultBiBral.getPveDiEtOr();
            } else {
                bibrac = resultBiBrac.getPveAgEtBr();
                bibral = resultBiBral.getPveAgEtBr();
            }
        } else {
            if (diOr) {
                bibrac = resultBiBrac.getPvmDiEtOr();
                bibral = resultBiBral.getPvmDiEtOr();
            } else {
                bibrac = resultBiBrac.getPvmAgEtBr();
                bibral = resultBiBral.getPvmAgEtBr();
            }
        }

        for (Map.Entry<String, Integer> entrySet : bibrac.entrySet()) {
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            if ((value >= 50) == superieur) {
                result.put(key, value);
            }
        }

        for (Map.Entry<String, Integer> entrySet : bibral.entrySet()) {
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            if ((value >= 50) == superieur) {
                result.put(key, value);
            }
        }
        if (superieur) {
            return sortByValue(result, false);
        }
        return sortByValue(result, true);
    }

    private static Map sortByValue(Map map, final boolean croissant) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (croissant) {
                    return ((Comparable) ((Map.Entry) (o1)).getValue())
                            .compareTo(((Map.Entry) (o2)).getValue());
                } else {
                    return ((Comparable) ((Map.Entry) (o2)).getValue())
                            .compareTo(((Map.Entry) (o1)).getValue());
                }
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private void produceReportRespectPrix(PdfWriter writer, Document document) {

        try {
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect1 = new Rectangle(llx, lly, urx, ury);
            Rectangle rect2 = new Rectangle(llx, lly, urx, ury);
            rect1.setBackgroundColor(BaseColor.GRAY);
            rect1.setBorder(Rectangle.BOX);
            rect1.setBorderWidth(1);

            canvas.rectangle(rect1);
            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(rect1);
            //ct.SetSimpleColumn(rect);
            Font f1 = new Font(Font.FontFamily.HELVETICA, 17.0f, Font.BOLD);
            Font f2 = new Font(Font.FontFamily.HELVETICA, 20.0f, Font.BOLD, BaseColor.WHITE);
            Chunk c1 = new Chunk("BRAC-DMC-EN-073\t\t\t", f1);
            Chunk c2 = new Chunk("    Respect des Prix Bières : Prix moyens pratiqués", f2);
            Phrase p = new Phrase();
            p.add(c1);
            p.add(c2);
            Paragraph pa = new Paragraph(p);
            pa.setPaddingTop(30);
            ct.setYLine(ury - 5);
            ct.setAlignment(Element.ALIGN_MIDDLE);
            ct.addElement(pa);
            ct.go();
            document.add(new Paragraph("\n"));
            Font f5 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.BOLD);
            Font f6 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.NORMAL);
            Font f7 = new Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD);
            Font f8 = new Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL);
            Paragraph p1 = new Paragraph("PRIX MOYENS DES PRODUITS BRACONGO", f6);
            p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            int taille1 = getMapSize(prixStatBrac);
            float[] relativewidth1 = new float[taille1 + 2];
            relativewidth1[0] = 2;
            relativewidth1[1] = 2;
            for (int i = 2; i < taille1 + 2; i++) {
                relativewidth1[i] = 1;
            }
            PdfPTable table1 = new PdfPTable(relativewidth1);
            table1.setWidthPercentage(100);
            table1.addCell(DocumentUtil.createDefaultHeaderCell("Secteurs", f8, 2, 1, true, BaseColor.WHITE));
            table1.addCell(DocumentUtil.createDefaultHeaderCell("Regime", f8, 2, 1, false, BaseColor.WHITE));
            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
                Integer key = entrySet.getKey();
                BoissonPrixStat value = entrySet.getValue();
                table1.addCell(DocumentUtil.createDefaultHeaderCell(key + "Cl", f8, 1, value.getPrix().size(), false, BaseColor.WHITE));
            }
            // table1.addCell(DocumentUtil.createDefaultHeaderCell("Regime", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixPve();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    table1.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true, BaseColor.WHITE));

                }
            }

            table1.addCell(DocumentUtil.createDefaultHeaderCell("Tous", f8, 3, 1, false, BaseColor.WHITE));
            table1.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixPve();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table1.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }
            table1.addCell(DocumentUtil.createDefaultHeaderCell("Mixte", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixMixte();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table1.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }

            table1.addCell(DocumentUtil.createDefaultHeaderCell("Global", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixGlobal();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table1.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }
            table1.addCell(DocumentUtil.createDefaultHeaderCell(" ", f8, 1, taille1 + 2, false, BaseColor.BLACK));
            table1.addCell(DocumentUtil.createDefaultHeaderCell("Prix conseillés", f8, 1, 2, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrix();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table1.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }

            table1.addCell(DocumentUtil.createDefaultHeaderCell("Écart (%)", f8, 1, 2, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Double> noms = value.getEcart();
                for (Map.Entry<String, Double> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    double ecart = entrySet1.getValue();
                    table1.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(ecart) + "%", f8, 1, 1, false, BaseColor.WHITE));

                }
            }
            document.add(table1);

            document.add(Chunk.NEWLINE);

            float[] relativewidth2 = {5, 0.1f, 1, 0.1f, 1};
            PdfPTable table2 = new PdfPTable(relativewidth2);
            table2.setWidthPercentage(100);

            int taille2 = getMapSize(prixStatBral);
            float[] relativewidth3 = new float[taille2 + 2];
            relativewidth3[0] = 2;
            relativewidth3[1] = 2;
            for (int i = 2; i < taille2 + 2; i++) {
                relativewidth3[i] = 1;
            }
            PdfPTable table3 = new PdfPTable(relativewidth3);
            table3.setWidthPercentage(100);
            table3.addCell(DocumentUtil.createDefaultHeaderCell("Secteurs", f8, 2, 1, true, BaseColor.WHITE));
            table3.addCell(DocumentUtil.createDefaultHeaderCell("Regime", f8, 2, 1, false, BaseColor.WHITE));
            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
                Integer key = entrySet.getKey();
                BoissonPrixStat value = entrySet.getValue();
                table3.addCell(DocumentUtil.createDefaultHeaderCell(key + "Cl", f8, 1, value.getPrix().size(), false, BaseColor.WHITE));
            }
            // table1.addCell(DocumentUtil.createDefaultHeaderCell("Regime", f8, 1, 1, false, BaseColor.WHITE));
            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixPve();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    table3.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true, BaseColor.WHITE));

                }
            }

            table3.addCell(DocumentUtil.createDefaultHeaderCell("Tous", f8, 3, 1, false, BaseColor.WHITE));
            table3.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixPve();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table3.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }
            table3.addCell(DocumentUtil.createDefaultHeaderCell("Mixte", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixMixte();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table3.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }

            table3.addCell(DocumentUtil.createDefaultHeaderCell("Global", f8, 1, 1, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrixGlobal();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table3.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }
            table3.addCell(DocumentUtil.createDefaultHeaderCell(" ", f8, 1, taille1 + 2, false, BaseColor.BLACK));
            table3.addCell(DocumentUtil.createDefaultHeaderCell("Prix conseillés", f8, 1, 2, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Integer> noms = value.getPrix();
                for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    int prix = entrySet1.getValue();
                    table3.addCell(DocumentUtil.createDefaultHeaderCell(prix + "", f8, 1, 1, false, BaseColor.WHITE));

                }
            }

            table3.addCell(DocumentUtil.createDefaultHeaderCell("Écart (%)", f8, 1, 2, false, BaseColor.WHITE));

            for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
                BoissonPrixStat value = entrySet.getValue();
                Map<String, Double> noms = value.getEcart();
                for (Map.Entry<String, Double> entrySet1 : noms.entrySet()) {
                    String key = entrySet1.getKey();
                    double ecart = entrySet1.getValue();
                    table3.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(ecart) + "%", f8, 1, 1, false, BaseColor.WHITE));

                }
            }

            PdfPCell cell = new PdfPCell();
            p1 = new Paragraph("PRIX MOYEN DES PRODUIT BRALIMA", f8);
            p1.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p1);
            cell.addElement(table3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColorTop(BaseColor.WHITE);
            cell.setBorderColorBottom(BaseColor.WHITE);
            cell.setBorderColorLeft(BaseColor.WHITE);
            cell.setBorderColorRight(BaseColor.WHITE);
            cell.disableBorderSide(2);
            cell.disableBorderSide(0);
            cell.disableBorderSide(3);
            table2.addCell(cell);
            for (int i = 0; i < 4; i++) {
                cell = new PdfPCell();
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorderColorTop(BaseColor.WHITE);
                cell.setBorderColorBottom(BaseColor.WHITE);
                cell.setBorderColorLeft(BaseColor.WHITE);
                cell.setBorderColorRight(BaseColor.WHITE);
                cell.disableBorderSide(2);
                //cell.disableBorderSide(1);
                cell.disableBorderSide(3);
                table2.addCell(cell);
            }

            document.add(table2);

        } catch (DocumentException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void produceReportParcEmballage(PdfWriter writer, Document document) {
        try {
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect1 = new Rectangle(llx, lly, urx, ury);
            Rectangle rect2 = new Rectangle(llx, lly, urx, ury);
            rect1.setBackgroundColor(BaseColor.GRAY);
            rect1.setBorder(Rectangle.BOX);
            rect1.setBorderWidth(1);

            canvas.rectangle(rect1);
            ColumnText ct = new ColumnText(writer.getDirectContent());
            ct.setSimpleColumn(rect1);
            //ct.SetSimpleColumn(rect);
            Font f1 = new Font(Font.FontFamily.HELVETICA, 17.0f, Font.BOLD);
            Font f2 = new Font(Font.FontFamily.HELVETICA, 20.0f, Font.BOLD, BaseColor.WHITE);
            Chunk c1 = new Chunk("BRAC-DMC-EN-073\t\t\t", f1);
            Chunk c2 = new Chunk("    Respect des Prix Bières : Prix moyens pratiqués", f2);
            Phrase p = new Phrase();
            p.add(c1);
            p.add(c2);
            Paragraph pa = new Paragraph(p);
            pa.setPaddingTop(30);
            ct.setYLine(ury - 5);
            ct.setAlignment(Element.ALIGN_MIDDLE);
            ct.addElement(pa);
            ct.go();
            document.add(new Paragraph("\n"));
            Font f5 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.BOLD);
            Font f6 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.NORMAL);
            Font f7 = new Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD);
            Font f8 = new Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL);

            PdfPTable table = new PdfPTable(14);
            table.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            cell.setRowspan(2);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);
            table.addCell(DocumentUtil.createDefaultHeaderCell("PDV", f8, 2, 1, true, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("PARC D'EMBALLAGES BRACONGO", f8, 1, 2, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("PARC D'EMBALLAGES BRALIMA", f8, 1, 2, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Nombre de jours moyns écoulés depuis la dernière visite du Délégué", f8, 1, 2, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("SERVICE SRD BRACONGO", f8, 1, 3, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("SERVICE SRD BRALIMA", f8, 1, 3, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Total", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Parc moyen", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Total", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Parc moyen", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Délégué Bracongo", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Délégué Bralima", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Heure moyenne de passage", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("PDV SERVIS", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("%", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("Heure moyenne de passage", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("PDV SERVIS", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell("%", f8, 1, 1, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(nombrePVE + "", f8, 1, 1, false, BaseColor.WHITE));
            int nombreparcPveBrac = reponseService.parcEmballage(true, true, debut, fin);
            int nombreparcMixteBrac = reponseService.parcEmballage(true, false, debut, fin);
            int nombreparcPveBral = reponseService.parcEmballage(false, true, debut, fin);
            int nombreparcMixteBral = reponseService.parcEmballage(false, false, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(nombreparcPveBrac + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcPveBrac / nombrePVE) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(nombreparcPveBral + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcPveBral / nombrePVE) + "", f8, 1, 1, false, BaseColor.WHITE));
            double jourBrac = reponseService.jourMoyenEcoule(true, true, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(jourBrac) + "", f8, 1, 1, false, BaseColor.WHITE));
            double jourBral = reponseService.jourMoyenEcoule(false, true, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(jourBral) + "", f8, 1, 1, false, BaseColor.WHITE));
            /////////////////////////////////////////////////
            LocalTime heurePveBrac = reponseService.getHeureMoyenneSrd(true, true, debut, fin);
            LocalTime heureMixteBrac = reponseService.getHeureMoyenneSrd(true, false, debut, fin);
            LocalTime heureGlobalBrac = reponseService.getHeureMoyenneSrd(true, null, debut, fin);
            LocalTime heurePveBral = reponseService.getHeureMoyenneSrd(false, true, debut, fin);
            LocalTime heureMixteBral = reponseService.getHeureMoyenneSrd(false, false, debut, fin);
            LocalTime heureGlobalBral = reponseService.getHeureMoyenneSrd(false, null, debut, fin);
            int visitePveBrac = reponseService.nombrePdvServiParSrd(true, true, debut, fin);
            int visiteMixteBrac = reponseService.nombrePdvServiParSrd(true, false, debut, fin);
            int visiteGlobalBrac = reponseService.nombrePdvServiParSrd(true, null, debut, fin);
            int visitePveBral = reponseService.nombrePdvServiParSrd(false, true, debut, fin);
            int visiteMixteBral = reponseService.nombrePdvServiParSrd(false, false, debut, fin);
            int visiteGlobalBral = reponseService.nombrePdvServiParSrd(false, null, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(heurePveBrac.get(DateTimeFieldType.hourOfDay()) + ":" + heurePveBrac.get(DateTimeFieldType.minuteOfHour()) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(visitePveBrac + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(((int) ((visitePveBrac * 1.0) / nombrePVE * 100)) + "%", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(heurePveBral.get(DateTimeFieldType.hourOfDay()) + ":" + heurePveBral.get(DateTimeFieldType.minuteOfHour()) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(visitePveBral + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(((int) ((visitePveBral * 1.0) / nombrePVE * 100)) + "%", f8, 1, 1, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell("MIXTES", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(nombreMixte + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(nombreparcMixteBrac + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcMixteBrac / nombreMixte) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(nombreparcMixteBral + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcMixteBral / nombreMixte) + "", f8, 1, 1, false, BaseColor.WHITE));
            double jourMixteBrac = reponseService.jourMoyenEcoule(true, false, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(jourMixteBrac) + "", f8, 1, 1, false, BaseColor.WHITE));
            double jourMixteBral = reponseService.jourMoyenEcoule(false, false, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(jourMixteBral) + "", f8, 1, 1, false, BaseColor.WHITE));
            /////////////////////////////////////////////////

            table.addCell(DocumentUtil.createDefaultHeaderCell(heureMixteBrac.get(DateTimeFieldType.hourOfDay()) + ":" + heureMixteBrac.get(DateTimeFieldType.minuteOfHour()) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(visiteMixteBrac + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(((int) ((visiteMixteBrac * 1.0) / nombreMixte * 100)) + "%", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(heureMixteBral.get(DateTimeFieldType.hourOfDay()) + ":" + heureMixteBral.get(DateTimeFieldType.minuteOfHour()) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(visiteMixteBral + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(((int) ((visiteMixteBral * 1.0) / nombreMixte * 100)) + "%", f8, 1, 1, false, BaseColor.WHITE));

            table.addCell(DocumentUtil.createDefaultHeaderCell("GLOBAL", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreMixte + nombrePVE) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcPveBrac + nombreparcMixteBrac) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcPveBrac + nombreparcMixteBrac) / (nombreMixte + nombrePVE) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcPveBral + nombreparcMixteBral) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell((nombreparcPveBral + nombreparcMixteBral) / (nombreMixte + nombrePVE) + "", f8, 1, 1, false, BaseColor.WHITE));
            double jourGlobalBrac = reponseService.jourMoyenEcoule(true, null, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(jourGlobalBrac), f8, 1, 1, false, BaseColor.WHITE));
            double jourGlobalBral = reponseService.jourMoyenEcoule(false, null, debut, fin);
            table.addCell(DocumentUtil.createDefaultHeaderCell(calculateEcart(jourGlobalBral), f8, 1, 1, false, BaseColor.WHITE));
            /////////////////////////////////////////////////

            table.addCell(DocumentUtil.createDefaultHeaderCell(heureGlobalBrac.get(DateTimeFieldType.hourOfDay()) + ":" + heureGlobalBrac.get(DateTimeFieldType.minuteOfHour()) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(visiteGlobalBrac + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(((int) ((visiteGlobalBrac * 1.0) / (nombrePVE + nombreMixte) * 100)) + "%", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(heureGlobalBral.get(DateTimeFieldType.hourOfDay()) + ":" + heureGlobalBral.get(DateTimeFieldType.minuteOfHour()) + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(visiteGlobalBral + "", f8, 1, 1, false, BaseColor.WHITE));
            table.addCell(DocumentUtil.createDefaultHeaderCell(((int) ((visiteGlobalBral * 1.0) / (nombrePVE + nombreMixte) * 100)) + "%", f8, 1, 1, false, BaseColor.WHITE));

            document.add(table);

        } catch (DocumentException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produceReportMarche(PdfWriter writer, Document document) {

    }

    private void produceReportAction(PdfWriter writer, Document document) {

    }

    private static int getMapSize(Map<Integer, BoissonPrixStat> map) {
        int result = 0;
        for (Map.Entry<Integer, BoissonPrixStat> entrySet : map.entrySet()) {
            //Integer key = entrySet.getKey();
            BoissonPrixStat value = entrySet.getValue();
            result += value.getPrix().size();

        }
        return result;
    }

    private String calculateEcart(double value) {
        DecimalFormat df1 = new DecimalFormat("#.#");
        df1.setRoundingMode(RoundingMode.CEILING);
        return df1.format(value);

    }

    private String dateString(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return day+"/"+(month+1)+"/"+year;

    }

    private int getWeekFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        return week;
    }
    
    private int getMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week = cal.get(Calendar.MONTH);
        return week+1;
    }
}
