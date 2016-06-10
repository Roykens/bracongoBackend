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
import com.royken.bracongo.survey.entities.Action;
import com.royken.bracongo.survey.entities.Commentaire;
import com.royken.bracongo.survey.entities.Secteur;
import com.royken.bracongo.survey.entities.TypeCategorie;
import com.royken.bracongo.survey.entities.TypeRegime;
import com.royken.bracongo.survey.entities.projection.BoissonDispoStat;
import com.royken.bracongo.survey.entities.projection.BoissonPrixStat;
import com.royken.bracongo.survey.entities.projection.BoissonStockDispoStat;
import com.royken.bracongo.survey.entities.projection.BoissonStockStat;
import com.royken.bracongo.survey.entities.projection.DisponibiliteNumeriqueStat;
import com.royken.bracongo.survey.service.IActionService;
import com.royken.bracongo.survey.service.ICommentaireService;
import com.royken.bracongo.survey.service.IReponseService;
import com.royken.bracongo.survey.service.ISecteurService;
import com.royken.bracongo.survey.service.ServiceException;
import com.royken.bracongo.survey.web.beans.util.ActionParseUtil;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
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

    private Boolean excel;

    @EJB
    private IReponseService reponseService;

    @EJB
    private ISecteurService secteurService;

    @EJB
    private IActionService actionService;
    
    @EJB
    private ICommentaireService commentaireService;

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

    BoissonStockStat stockBibrac;

    BoissonStockStat stockBibral;

    BoissonStockDispoStat stockDispoBibrac;

    BoissonStockDispoStat stockDispoBibral;

    Map<Integer, BoissonPrixStat> prixStatBrac;

    Map<Integer, BoissonPrixStat> prixStatBral;

    int pveDiS1, pveOrS1, pveAgS1, pveBrS1;
    int pveDiS2, pveOrS2, pveAgS2, pveBrS2;
    int pveDiS3, pveOrS3, pveAgS3, pveBrS3;
    int pveDiS4, pveOrS4, pveAgS4, pveBrS4;

    int pvmDiS1, pvmOrS1, pvmAgS1, pvmBrS1;
    int pvmDiS2, pvmOrS2, pvmAgS2, pvmBrS2;
    int pvmDiS3, pvmOrS3, pvmAgS3, pvmBrS3;
    int pvmDiS4, pvmOrS4, pvmAgS4, pvmBrS4;

    private List<Action> actions;
    
    private List<Commentaire> commentaires;

    XSSFColor b = new XSSFColor(new java.awt.Color(255, 255, 255));

    XSSFCellStyle grey;

    XSSFCellStyle black;

    XSSFCellStyle gold;

    XSSFCellStyle blue;

    XSSFCellStyle yellow;

    XSSFCellStyle myStyle;

    XSSFCellStyle myStyle2;

    XSSFCellStyle myStyle3;

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

    public IActionService getActionService() {
        return actionService;
    }

    public void setActionService(IActionService actionService) {
        this.actionService = actionService;
    }

    public Date getDebut() {
        return debut;
    }

    public StreamedContent getFichier() {
        return fichier;
    }

    public Boolean getExcel() {
        return excel;
    }

    public void setExcel(Boolean excel) {
        this.excel = excel;
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

    public ICommentaireService getCommentaireService() {
        return commentaireService;
    }

    public void setCommentaireService(ICommentaireService commentaireService) {
        this.commentaireService = commentaireService;
    }

    
    
    /**
     * Creates a new instance of DocumentBean
     */
    public DocumentBean() {
    }

    public void produceDocument() throws ArithmeticException{

        FacesContext context = FacesContext.getCurrentInstance();

        Object response = context.getExternalContext().getResponse();
        if (response instanceof HttpServletResponse) {
            try {
                HttpServletResponse hsr = (HttpServletResponse) response;
                if (excel != null) {
                    actions = actionService.getByDates(debut, fin);
                    commentaires = commentaireService.getAllByDates(debut, fin);
                    stockDispoBibrac = reponseService.getAllBoissonStockDispoStat(debut, fin, Boolean.TRUE, Boolean.TRUE);
                    stockDispoBibral = reponseService.getAllBoissonStockDispoStat(debut, fin, Boolean.TRUE, Boolean.FALSE);
                    stockBibrac = reponseService.getAllBoissonStockStat(debut, fin, Boolean.TRUE, Boolean.TRUE);
                    stockBibral = reponseService.getAllBoissonStockStat(debut, fin, Boolean.TRUE, Boolean.FALSE);
                    resultBiBrac = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.TRUE, Boolean.TRUE);
                    resultBgBrac = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.FALSE, Boolean.TRUE);
                    resultBgBral = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.FALSE, Boolean.FALSE);
                    resultBiBral = reponseService.getAllBoissonDispoStat(debut, fin, Boolean.TRUE, Boolean.FALSE);
                    prixStatBrac = reponseService.getPrixMoyen(debut, fin, Boolean.TRUE, Boolean.TRUE);
                    prixStatBral = reponseService.getPrixMoyen(debut, fin, Boolean.TRUE, Boolean.FALSE);
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
                    if (!excel) {
                        hsr.setContentType("application/pdf");
                        hsr.setHeader("Content-Disposition", "attachment; filename=rapport.pdf");
                        produceRapport(((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                        context.responseComplete();
                    } else {
                        System.out.println("HHHHHHHHH");
                        hsr.setContentType("application/xlsx");
                        hsr.setHeader("Content-Disposition", "attachment; filename=rapport.xlsx");
                        produceExcel(((HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse()).getOutputStream());
                        context.responseComplete();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServiceException ex) {
                Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void produceRapport(OutputStream stream) {
        Document doc = null;
        try {

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
            if (choix) {
                dateString1.append("Week : ").append(getWeekFromDate(debut));
            } else {
                dateString1.append("Month : ").append(getMonthFromDate(debut));
            }
            Chunk wee1 = new Chunk(dateString1.toString(), f2);
            Chunk date1 = new Chunk(" From " + dateString(debut) + " to " + dateString(fin), ff);
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

            Font f3 = new Font(Font.FontFamily.HELVETICA, 16.0f, Font.BOLD);
            Font f4 = new Font(Font.FontFamily.HELVETICA, 16.0f, Font.NORMAL);
            Font f5 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.NORMAL);
            Chunk per = new Chunk("Période : ", f3);

            StringBuilder dateString = new StringBuilder();
            if (choix) {
                dateString.append("Week ").append(getWeekFromDate(debut) + " ");
            } else {
                dateString.append("Month ").append(getMonthFromDate(debut) + " ");
            }
            Chunk wee = new Chunk(dateString.toString(), f4);
            Chunk date = new Chunk(dateString(debut) + " au " + dateString(fin), f4);

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
            if (choix) {
                resume.append(" de la semaine ");
                resume.append(getWeekFromDate(debut));
            } else {
                resume.append(" du mois ");
                resume.append(getMonthFromDate(debut));
            }
            resume.append("-");
            resume.append(year);
            resume.append(" ( du " + dateString(debut) + " au " + dateString(fin) + ")");
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
        return day + "/" + (month + 1) + "/" + year;

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
        return week + 1;
    }

    private void produceExcel(OutputStream stream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            XSSFFont blackFont = workbook.createFont();
            //font.setFontHeightInPoints((short) 30);
            //font.setFontName("IMPACT");
            //font.setItalic(true);
            blackFont.setColor(HSSFColor.WHITE.index);

            myStyle = workbook.createCellStyle();
            myStyle.setRotation((short) 90);
            myStyle.setFillForegroundColor(HSSFColor.GOLD.index);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);

            grey = workbook.createCellStyle();
            grey.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
            //style6.setFillPattern(XSSFCellStyle.LESS_DOTS);
            grey.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            grey.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            grey.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            grey.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            grey.setAlignment(HorizontalAlignment.CENTER);
            grey.setFillPattern(CellStyle.SOLID_FOREGROUND);

            black = workbook.createCellStyle();
            black.setFillForegroundColor(HSSFColor.BLACK.index);
            black.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            black.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            black.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            black.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            //style6.setFillPattern(XSSFCellStyle.LESS_DOTS);
            // black.setAlignment(HorizontalAlignment.CENTER);
            black.setFont(blackFont);
            black.setFillPattern(CellStyle.SOLID_FOREGROUND);

            gold = workbook.createCellStyle();
            gold.setFillForegroundColor(HSSFColor.GOLD.index);
            gold.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            gold.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            gold.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            gold.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            //style6.setFillPattern(XSSFCellStyle.LESS_DOTS);
            gold.setAlignment(HorizontalAlignment.CENTER);
            gold.setFillPattern(CellStyle.SOLID_FOREGROUND);

            blue = workbook.createCellStyle();
            blue.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            blue.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            blue.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            blue.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            blue.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            //style6.setFillPattern(XSSFCellStyle.LESS_DOTS);
            blue.setAlignment(HorizontalAlignment.CENTER);
            blue.setFillPattern(CellStyle.SOLID_FOREGROUND);

            yellow = workbook.createCellStyle();
            yellow.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            yellow.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            yellow.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            yellow.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            yellow.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            //style6.setFillPattern(XSSFCellStyle.LESS_DOTS);
            yellow.setAlignment(HorizontalAlignment.CENTER);
            yellow.setFillPattern(CellStyle.SOLID_FOREGROUND);

            myStyle = workbook.createCellStyle();
            myStyle.setRotation((short) 90);
            myStyle.setFillForegroundColor(HSSFColor.GOLD.index);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            myStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            //myStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

            myStyle2 = workbook.createCellStyle();
            myStyle2.setRotation((short) 90);
            myStyle2.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
            myStyle2.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            myStyle2.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            myStyle2.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            myStyle2.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            // myStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);

            myStyle3 = workbook.createCellStyle();
            myStyle3.setRotation((short) 90);
            myStyle3.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            myStyle3.setBorderColor(XSSFCellBorder.BorderSide.LEFT, b);
            myStyle3.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, b);
            myStyle3.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, b);
            myStyle3.setBorderColor(XSSFCellBorder.BorderSide.TOP, b);
            //myStyle3.setFillPattern(CellStyle.SOLID_FOREGROUND);

            XSSFSheet spreadsheet = workbook.createSheet("reporting");
            produceExcelReporting(spreadsheet);
            XSSFSheet spreadsheet2 = workbook.createSheet("disponibiliteBi&Bg");
            produceExcelDispo(spreadsheet2, workbook);
            XSSFSheet spreadsheet3 = workbook.createSheet("classement");
            produceExcelClassement(spreadsheet3);
            XSSFSheet spreadsheet4 = workbook.createSheet("respectPrix");
            produceExcelRespect(spreadsheet4);
            XSSFSheet spreadsheet5 = workbook.createSheet("stockChaud");
            produceExcelStock(spreadsheet5);
            XSSFSheet spreadsheet6 = workbook.createSheet("parcEmballage");
            produceExcelParc(spreadsheet6);
            XSSFSheet spreadshhet7 = workbook.createSheet("action");
            produceExcelAction(spreadshhet7);
            XSSFSheet spreadshhet8 = workbook.createSheet("marche");
            produceExcelCommentaire(spreadshhet8);
            workbook.write(stream);
            stream.flush();
            stream.close();

        } catch (IOException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produceExcelReporting(XSSFSheet sheet) {
        int rowId = 1;
        int colId = 1;
        Row row = sheet.createRow(rowId);
        Cell cell = row.createCell(colId++);
        cell.setCellValue("");
        cell = row.createCell(colId);
        cell.setCellValue("PVE");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));
        colId += 4;
        cell = row.createCell(colId);
        cell.setCellValue("MIXTES");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));

        colId += 4;
        cell = row.createCell(colId);
        cell.setCellValue("TOTAL PDV");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));
        colId += 4;
        cell = row.createCell(colId);
        cell.setCellValue("TOTAL\n PAR\n SECTEUR");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));

        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("SECTEURS");
        String[] cat = {"DI", "OR", "AG", "BR"};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                cell = row.createCell(colId++);
                cell.setCellValue(cat[j]);
            }
        }
        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("S1");
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmDiS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmOrS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmAgS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmBrS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS1 + pvmDiS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS1 + pvmOrS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS1 + pvmAgS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS1 + pvmBrS1);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS1 + pvmDiS1 + pveOrS1 + pvmOrS1 + pveAgS1 + pvmAgS1 + pveBrS1 + pvmBrS1);

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("S2");
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmDiS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmOrS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmAgS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmBrS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS2 + pvmDiS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS2 + pvmOrS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS2 + pvmAgS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS2 + pvmBrS2);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS2 + pvmDiS2 + pveOrS2 + pvmOrS2 + pveAgS2 + pvmAgS2 + pveBrS2 + pvmBrS2);

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("S3");
        cell = row.createCell(colId++);
        System.out.println("TOTOTOTOTOTOTOTOTOTOTO = " + pveDiS1);
        cell.setCellValue(pveDiS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmDiS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmOrS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmAgS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmBrS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS3 + pvmDiS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS3 + pvmOrS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS3 + pvmAgS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS3 + pvmBrS3);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS3 + pvmDiS3 + pveOrS3 + pvmOrS3 + pveAgS3 + pvmAgS3 + pveBrS3 + pvmBrS3);

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("S4");
        cell = row.createCell(colId++);
        System.out.println("TOTOTOTOTOTOTOTOTOTOTO = " + pveDiS1);
        cell.setCellValue(pveDiS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmDiS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmOrS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmAgS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pvmBrS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS4 + pvmDiS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveOrS4 + pvmOrS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveAgS4 + pvmAgS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveBrS4 + pvmBrS4);
        cell = row.createCell(colId++);
        cell.setCellValue(pveDiS4 + pvmDiS4 + pveOrS4 + pvmOrS4 + pveAgS4 + pvmAgS4 + pveBrS4 + pvmBrS4);

        int a, b, c, d, e, f, g, h, i, j, k, l;
        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("Total");
        a = pveDiS1 + pveDiS2 + pveDiS3 + pveDiS4;
        cell = row.createCell(colId++);
        cell.setCellValue(a);
        b = pveOrS1 + pveOrS2 + pveOrS3 + pveOrS4;
        cell = row.createCell(colId++);
        cell.setCellValue(b);
        c = pveAgS1 + pveAgS2 + pveAgS3 + pveAgS4;
        cell = row.createCell(colId++);
        cell.setCellValue(c);
        d = pveBrS1 + pveBrS2 + pveBrS3 + pveBrS4;
        cell = row.createCell(colId++);
        cell.setCellValue(d);
        e = pvmDiS1 + pvmDiS2 + pvmDiS3 + pvmDiS4;
        cell = row.createCell(colId++);
        cell.setCellValue(e);
        f = pvmOrS1 + pvmOrS2 + pvmOrS3 + pvmOrS4;
        cell = row.createCell(colId++);
        cell.setCellValue(f);
        g = pvmAgS1 + pvmAgS2 + pvmAgS3 + pvmAgS4;
        cell = row.createCell(colId++);
        cell.setCellValue(g);
        h = pvmBrS1 + pvmBrS2 + pvmBrS3 + pvmBrS4;
        cell = row.createCell(colId++);
        cell.setCellValue(h);
        i = a + e;
        cell = row.createCell(colId++);
        cell.setCellValue(i);
        j = b + f;
        cell = row.createCell(colId++);
        cell.setCellValue(j);
        k = c + g;
        cell = row.createCell(colId++);
        cell.setCellValue(k);
        l = d + h;
        cell = row.createCell(colId++);
        cell.setCellValue(l);
        cell = row.createCell(colId++);
        cell.setCellValue(i + j + k + l);

        nombrePVE = a + b + c + d;
        nombreMixte = e + f + g + h;
        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("");

        cell = row.createCell(colId);
        cell.setCellValue(a + b + c + d);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));
        colId += 4;
        cell = row.createCell(colId);
        cell.setCellValue(e + f + g + h);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));

        colId += 4;
        cell = row.createCell(colId);
        cell.setCellValue(i + j + k + l);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));

    }

    private void produceExcelStock(XSSFSheet sheet) {
        int nombreBibra = resultBiBrac.getPveAgEtBr().size();
        int nombreBiBral = resultBiBral.getPveDiEtOr().size();
        Map<String, Integer> data1 = stockBibrac.getPveDiEtOr();
        Map<String, Integer> data2 = stockBibrac.getPveAgEtBr();
        Map<String, Integer> data3 = stockBibrac.getPve();
        Map<String, Integer> data4 = stockBibrac.getPvmDiEtOr();
        Map<String, Integer> data5 = stockBibrac.getPvmAgEtBr();
        Map<String, Integer> data6 = stockBibrac.getPvm();
        Map<String, Integer> data7 = stockBibrac.getPdv();
        Map<String, Integer> data1a = stockBibral.getPveDiEtOr();
        Map<String, Integer> data2a = stockBibral.getPveAgEtBr();
        Map<String, Integer> data3a = stockBibral.getPve();
        Map<String, Integer> data4a = stockBibral.getPvmDiEtOr();
        Map<String, Integer> data5a = stockBibral.getPvmAgEtBr();
        Map<String, Integer> data6a = stockBibral.getPvm();
        Map<String, Integer> data7a = stockBibral.getPdv();

        Map<String, Integer> datai1 = stockDispoBibrac.getPveDiEtOr();
        Map<String, Integer> datai2 = stockDispoBibrac.getPveAgEtBr();
        Map<String, Integer> datai3 = stockDispoBibrac.getPve();
        Map<String, Integer> datai4 = stockDispoBibrac.getPvmDiEtOr();
        Map<String, Integer> datai5 = stockDispoBibrac.getPvmAgEtBr();
        Map<String, Integer> datai6 = stockDispoBibrac.getPvm();
        Map<String, Integer> datai7 = stockDispoBibrac.getPdv();
        Map<String, Integer> data1ia = stockDispoBibral.getPveDiEtOr();
        Map<String, Integer> datai2a = stockDispoBibral.getPveAgEtBr();
        Map<String, Integer> datai3a = stockDispoBibral.getPve();
        Map<String, Integer> datai4a = stockDispoBibral.getPvmDiEtOr();
        Map<String, Integer> datai5a = stockDispoBibral.getPvmAgEtBr();
        Map<String, Integer> datai6a = stockDispoBibral.getPvm();
        Map<String, Integer> datai7a = stockDispoBibral.getPdv();
        int rowId = 1;
        int colId = 1;
        Row row = sheet.createRow(rowId);
        Cell cell = row.createCell(colId);
        cell.setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("");
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("BIÈRES BRACONGO");
        cell.setCellStyle(gold);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + nombreBibra - 1 //last column  (0-based)
        ));
        colId = colId + nombreBibra;
        cell = row.createCell(colId);
        cell.setCellValue("BIÈRES BRALIMA");
        cell.setCellStyle(blue);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + nombreBiBral - 1 //last column  (0-based)
        ));
        colId = 2;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("NOMBRE DE PDV");
        cell.setCellStyle(myStyle3);
        colId++;
        BorderStyle border = BorderStyle.THICK;
        for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
            String key = entrySet.getKey();
            cell = row.createCell(colId++);
            cell.setCellValue(key + " CL");
            cell.setCellStyle(myStyle);
        }
        for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
            String key = entrySet.getKey();
            cell = row.createCell(colId++);
            cell.setCellValue(key + " CL");
            cell.setCellStyle(myStyle2);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE (Di & Or)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombrePveDiOr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE (Ag & Br)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombrePveArBr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : data2.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(gold);

        }
        for (Map.Entry<String, Integer> entrySet : data2a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        System.out.println("LE NOMBRE DE  PVE ================ " + stockBibrac.getNombrePve());
        cell.setCellValue(stockBibrac.getNombrePve());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : data3.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : data3a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE (Di & Or)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombreMixteDiOr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : data4.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : data4a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE (Ag & Br)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombreMixteAgBr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : data5.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : data5a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        System.out.println("LE NOMBRE DE MIXTE ================ " + stockBibrac.getNombreMixte());
        cell.setCellValue(stockBibrac.getNombreMixte());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : data6.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : data6a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("GLOBAL");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombrePdv());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : data7.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : data7a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value);
            cell.setCellStyle(blue);
        }

        rowId = 13;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("");
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("BIÈRES BRACONGO");
        cell.setCellStyle(gold);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + nombreBibra - 1 //last column  (0-based)
        ));
        colId = colId + nombreBibra;
        cell = row.createCell(colId);
        cell.setCellValue("BIÈRES BRALIMA");
        cell.setCellStyle(blue);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + nombreBiBral - 1 //last column  (0-based)
        ));
        colId = 2;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("NOMBRE DE PDV");
        cell.setCellStyle(myStyle3);
        colId++;
        for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
            String key = entrySet.getKey();
            cell = row.createCell(colId++);
            cell.setCellValue(key + " CL");
            cell.setCellStyle(myStyle);
        }
        for (Map.Entry<String, Integer> entrySet : data1ia.entrySet()) {
            String key = entrySet.getKey();
            cell = row.createCell(colId++);
            cell.setCellValue(key + " CL");
            cell.setCellStyle(myStyle2);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE (Di & Or)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        System.out.println("LE NOMBRE DE DIOR PVE ================ " + stockBibrac.getNombrePveDiOr());
        cell.setCellValue(stockBibrac.getNombrePveDiOr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : data1ia.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE (Ag & Br)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        System.out.println("LE NOMBRE DE ARBR PVE ================ " + stockBibrac.getNombrePveArBr());
        cell.setCellValue(stockBibrac.getNombrePveArBr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : datai2.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(gold);

        }
        for (Map.Entry<String, Integer> entrySet : datai2a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        System.out.println("LE NOMBRE DE  PVE ================ " + stockBibrac.getNombrePve());
        cell.setCellValue(stockBibrac.getNombrePve());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : datai3.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : datai3a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE (Di & Or)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombreMixteDiOr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : datai4.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : datai4a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE (Ag & Br)");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombreMixteAgBr());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : datai5.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : datai5a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        System.out.println("LE NOMBRE DE MIXTE ================ " + stockBibrac.getNombreMixte());
        cell.setCellValue(stockBibrac.getNombreMixte());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : datai6.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : datai6a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(blue);
        }
        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("GLOBAL");
        cell.setCellStyle(grey);
        cell = row.createCell(colId++);
        cell.setCellValue(stockBibrac.getNombrePdv());
        cell.setCellStyle(yellow);
        for (Map.Entry<String, Integer> entrySet : datai7.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(gold);
        }
        for (Map.Entry<String, Integer> entrySet : datai7a.entrySet()) {
            //  String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
            cell.setCellStyle(blue);
        }

    }

    private void produceExcelClassement(XSSFSheet sheet) {
        Map<String, Integer> pveDiOrSup = getclassementRegimeCategorieSup(true, true, true);
        Map<String, Integer> pveDiOrInf = getclassementRegimeCategorieSup(true, true, false);
        Map<String, Integer> pveAgBzSup = getclassementRegimeCategorieSup(true, false, true);
        Map<String, Integer> pveAgBzInf = getclassementRegimeCategorieSup(true, false, false);
        Map<String, Integer> pvmDiOrSup = getclassementRegimeCategorieSup(false, true, true);
        Map<String, Integer> pvmDiOrInf = getclassementRegimeCategorieSup(false, true, false);
        Map<String, Integer> pvmAgBzSup = getclassementRegimeCategorieSup(false, false, true);
        Map<String, Integer> pvmAgBzInf = getclassementRegimeCategorieSup(false, false, false);

        int rowId = 1;
        int colId = 1;
        Row row = sheet.createRow(rowId);
        Cell cell = row.createCell(colId);
        cell.setCellValue("PVE");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 4 //last column  (0-based)
        ));

        colId = 1;
        rowId = 2;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("PVE Di&Or >= 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pveDiOrSup.entrySet()) {
            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        colId = 1;
        rowId++;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Dispo < 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pveDiOrInf.entrySet()) {
            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        colId = 4;
        rowId = 2;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("PVE Ag&Br >= 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pveAgBzSup.entrySet()) {
            colId = 4;
            rowId++;
            row = sheet.getRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        colId = 4;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Dispo < 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pveAgBzInf.entrySet()) {
            colId = 4;
            rowId++;
            row = sheet.getRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        ////////////////////////////
        rowId = 1;
        colId = 7;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Mixte");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 4 //last column  (0-based)
        ));

        colId = 7;
        rowId = 2;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("MIXTE Di&Or >= 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pvmDiOrSup.entrySet()) {
            colId = 7;
            rowId++;
            row = sheet.getRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        colId = 7;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Dispo < 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pvmDiOrInf.entrySet()) {
            colId = 7;
            rowId++;
            row = sheet.getRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        colId = 10;
        rowId = 2;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("MIXTE Ag&Br >= 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pvmAgBzSup.entrySet()) {
            colId = 10;
            rowId++;
            row = sheet.getRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        colId = 10;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Dispo < 50%");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        for (Map.Entry<String, Integer> entrySet : pvmAgBzInf.entrySet()) {
            colId = 10;
            rowId++;
            row = sheet.getRow(rowId);
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            cell = row.createCell(colId++);
            cell.setCellValue(key + "CL");
            cell = row.createCell(colId++);
            cell.setCellValue(value + "%");
        }

        colId = 14;
        rowId = 1;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Disponibilité moyenne générale");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 4 //last column  (0-based)
        ));
        colId = 14;
        rowId = 2;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Disponibilité moyenne générale Bières");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));
        colId = 17;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Disponibilité moyenne générale BG");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        colId = 15;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("Bracongo");
        cell = row.createCell(colId++);
        cell.setCellValue("Bralima");
        cell = row.createCell(colId++);
        cell.setCellValue("Bracongo");
        cell = row.createCell(colId++);
        cell.setCellValue("Bralima");

        colId = 14;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE (Di&Or)");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBrac.getPveDiEtOr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBral.getPveDiEtOr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBrac.getPveDiEtOr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBral.getPveDiEtOr()) + "%");

        colId = 14;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE (Ag&Br)");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBrac.getPveAgEtBr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBral.getPveAgEtBr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBrac.getPveAgEtBr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBral.getPveAgEtBr()) + "%");

        colId = 14;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("PVE");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBrac.getPve()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBral.getPve()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBrac.getPve()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBral.getPve()) + "%");

        colId = 14;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE (Di&Or)");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBrac.getPvmDiEtOr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBral.getPvmDiEtOr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBrac.getPvmDiEtOr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBral.getPvmDiEtOr()) + "%");

        colId = 14;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE (Ag&Br)");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBrac.getPvmAgEtBr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBral.getPvmAgEtBr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBrac.getPvmAgEtBr()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBral.getPvmAgEtBr()) + "%");

        colId = 14;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE ");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBrac.getPvm()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBral.getPvm()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBrac.getPvm()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBral.getPvm()) + "%");

        colId = 14;
        rowId++;
        row = sheet.getRow(rowId);
        cell = row.createCell(colId++);
        cell.setCellValue("MIXTE ");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBrac.getPdv()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBiBral.getPdv()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBrac.getPdv()) + "%");
        cell = row.createCell(colId++);
        cell.setCellValue(getMoyenne(resultBgBral.getPdv()) + "%");

    }

    private void produceExcelRespect(XSSFSheet sheet) {
        int rowId = 1;
        int colId = 1;
        int taille1 = getMapSize(prixStatBrac);

        Row row = sheet.createRow(rowId);
        Cell cell = row.createCell(colId);
        cell.setCellValue("SECTEURS");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("REGIME");
        cell.setCellStyle(gold);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
            Integer key = entrySet.getKey();
            BoissonPrixStat value = entrySet.getValue();
            cell = row.createCell(colId);
            cell.setCellValue(key + " CL");
            int step = value.getPrix().size();
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + step - 1 //last column  (0-based)
            ));
            colId += step;
        }

        rowId++;
        colId = 3;
        row = sheet.createRow(rowId);
        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixPve();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                cell = row.createCell(colId++);
                cell.setCellValue(key);
                cell.setCellStyle(myStyle);
            }
        }

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Tous");
        cell.setCellStyle(gold);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 2, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("PVE");
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixPve();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 2;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Mixte");
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixMixte();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 2;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Global");
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixGlobal();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("");
        colId++;
        cell.setCellStyle(black);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + taille1 + 1 //last column  (0-based)
        ));
        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Prix conseillés");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        colId += 2;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrix();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Écart (%)");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        colId += 2;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBrac.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Double> noms = value.getEcart();
            for (Map.Entry<String, Double> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                double ecart = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(calculateEcart(ecart) + "%");

            }
        }

        ///////////////////// BRALIMA ///////////////////////////////        
        rowId = 13;
        colId = 1;

        int taille2 = getMapSize(prixStatBral);
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("SECTEURS");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("REGIME");
        cell.setCellStyle(gold);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 1, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
            Integer key = entrySet.getKey();
            BoissonPrixStat value = entrySet.getValue();
            cell = row.createCell(colId);
            cell.setCellValue(key + " CL");
            int step = value.getPrix().size();
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + step - 1 //last column  (0-based)
            ));
            colId += step;
        }

        rowId++;
        colId = 3;
        row = sheet.createRow(rowId);
        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixPve();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                cell = row.createCell(colId++);
                cell.setCellValue(key);
                cell.setCellStyle(myStyle2);
            }
        }

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Tous");
        cell.setCellStyle(gold);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId + 2, //last row  (0-based)
                colId, //first column (0-based)
                colId //last column  (0-based)
        ));
        colId++;
        cell = row.createCell(colId);
        cell.setCellValue("PVE");
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixPve();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 2;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Mixte");
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixMixte();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 2;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Global");
        colId++;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrixGlobal();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("");
        colId++;
        cell.setCellStyle(black);
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + taille2 + 1 //last column  (0-based)
        ));
        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Prix conseillés");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        colId += 2;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Integer> noms = value.getPrix();
            for (Map.Entry<String, Integer> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                int prix = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(prix);
            }
        }

        rowId++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue("Écart (%)");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));

        colId += 2;

        for (Map.Entry<Integer, BoissonPrixStat> entrySet : prixStatBral.entrySet()) {
            BoissonPrixStat value = entrySet.getValue();
            Map<String, Double> noms = value.getEcart();
            for (Map.Entry<String, Double> entrySet1 : noms.entrySet()) {
                String key = entrySet1.getKey();
                double ecart = entrySet1.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(calculateEcart(ecart) + "%");

            }
        }

    }

    private void produceExcelParc(XSSFSheet sheet) {

        try {
            int rowId = 1;
            int colId = 1;
            Row row = sheet.createRow(rowId);
            Cell cell = row.createCell(colId);
            cell.setCellValue("");

            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId + 1, //last row  (0-based)
                    colId, //first column (0-based)
                    colId //last column  (0-based)
            ));
            colId++;
            cell = row.createCell(colId);
            cell.setCellValue("PDV");

            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId + 1, //last row  (0-based)
                    colId, //first column (0-based)
                    colId //last column  (0-based)
            ));

            colId++;
            cell = row.createCell(colId);
            cell.setCellValue("PARC D'EMBALLAGES BRACONGO");
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + 1 //last column  (0-based)
            ));
            colId += 2;
            cell = row.createCell(colId);
            cell.setCellValue("PARC D'EMBALLAGES BRALIMA");
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + 1 //last column  (0-based)
            ));
            colId += 2;
            cell = row.createCell(colId);
            cell.setCellValue("Nombre de jours moyens écoulés depuis la dernière visite du Délégué");
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + 1 //last column  (0-based)
            ));
            colId += 2;
            cell = row.createCell(colId);
            cell.setCellValue("SERVICE SRD BRACONGO");
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + 2 //last column  (0-based)
            ));
            colId += 3;
            cell = row.createCell(colId);
            cell.setCellValue("SERVICE SRD BRALIMA");
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + 2 //last column  (0-based)
            ));

            colId = 3;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("Total");
            cell = row.createCell(colId++);
            cell.setCellValue("Parc moyen");
            cell = row.createCell(colId++);
            cell.setCellValue("Total");
            cell = row.createCell(colId++);
            cell.setCellValue("Parc moyen");
            cell = row.createCell(colId++);
            cell.setCellValue("Délégué Bracongo");
            cell = row.createCell(colId++);
            cell.setCellValue("Délégué Bralima");
            cell = row.createCell(colId++);
            cell.setCellValue("Heure moyenne de passage");
            cell = row.createCell(colId++);
            cell.setCellValue("PDV SERVIS");
            cell = row.createCell(colId++);
            cell.setCellValue("%");
            cell = row.createCell(colId++);
            cell.setCellValue("Heure moyenne de passage");
            cell = row.createCell(colId++);
            cell.setCellValue("PDV SERVIS");
            cell = row.createCell(colId++);
            cell.setCellValue("%");

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("PVE");
            cell = row.createCell(colId++);
            cell.setCellValue(nombrePVE);

            int nombreparcPveBrac = reponseService.parcEmballage(true, true, debut, fin);
            int nombreparcMixteBrac = reponseService.parcEmballage(true, false, debut, fin);
            int nombreparcPveBral = reponseService.parcEmballage(false, true, debut, fin);
            int nombreparcMixteBral = reponseService.parcEmballage(false, false, debut, fin);
            cell = row.createCell(colId++);
            cell.setCellValue(nombreparcPveBrac);
            cell = row.createCell(colId++);
            cell.setCellValue(nombrePVE > 0 ?(nombreparcPveBrac / nombrePVE):-1);
            cell = row.createCell(colId++);
            cell.setCellValue(nombreparcPveBral);
            cell = row.createCell(colId++);
            cell.setCellValue(nombrePVE > 0 ? (nombreparcPveBral / nombrePVE):- 1);

            double jourBrac = reponseService.jourMoyenEcoule(true, true, debut, fin);
            cell = row.createCell(colId++);
            cell.setCellValue(calculateEcart(jourBrac));
            double jourBral = reponseService.jourMoyenEcoule(false, true, debut, fin);
            cell = row.createCell(colId++);
            cell.setCellValue(calculateEcart(jourBral));

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

            cell = row.createCell(colId++);
            cell.setCellValue(heurePveBrac.get(DateTimeFieldType.hourOfDay()) + ":" + heurePveBrac.get(DateTimeFieldType.minuteOfHour()));
            cell = row.createCell(colId++);
            cell.setCellValue(visitePveBrac);
            cell = row.createCell(colId++);
            cell.setCellValue(nombrePVE > 0 ? ((int) ((visitePveBrac * 1.0) / nombrePVE * 100)): -1);
            cell = row.createCell(colId++);
            cell.setCellValue(heurePveBral.get(DateTimeFieldType.hourOfDay()) + ":" + heurePveBral.get(DateTimeFieldType.minuteOfHour()));
            cell = row.createCell(colId++);
            cell.setCellValue(visitePveBral);
            cell = row.createCell(colId++);
            cell.setCellValue(nombrePVE > 0 ?((int) ((visitePveBral * 1.0) / nombrePVE * 100)): -1);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("MIXTE");
            cell = row.createCell(colId++);
            cell.setCellValue(nombreMixte);

            cell = row.createCell(colId++);
            cell.setCellValue(nombreparcMixteBrac);
            cell = row.createCell(colId++);
            cell.setCellValue(nombreMixte > 0 ?(nombreparcMixteBrac / nombreMixte) : -1);
            cell = row.createCell(colId++);
            cell.setCellValue(nombreparcMixteBral);
            cell = row.createCell(colId++);
            cell.setCellValue(nombreMixte > 0 ? (nombreparcMixteBral / nombreMixte) : -1);
            double jourMixteBrac = reponseService.jourMoyenEcoule(true, false, debut, fin);
            cell = row.createCell(colId++);
            cell.setCellValue(calculateEcart(jourMixteBrac));
            double jourMixteBral = reponseService.jourMoyenEcoule(false, false, debut, fin);
            cell = row.createCell(colId++);
            cell.setCellValue(calculateEcart(jourMixteBral));

            cell = row.createCell(colId++);
            cell.setCellValue(heureMixteBrac.get(DateTimeFieldType.hourOfDay()) + ":" + heureMixteBrac.get(DateTimeFieldType.minuteOfHour()));
            cell = row.createCell(colId++);
            cell.setCellValue(visiteMixteBrac);
            cell = row.createCell(colId++);
            cell.setCellValue(nombreMixte > 0 ? ((int) ((visiteMixteBrac * 1.0) / nombreMixte * 100)) : -1);
            cell = row.createCell(colId++);
            cell.setCellValue(heureMixteBral.get(DateTimeFieldType.hourOfDay()) + ":" + heureMixteBral.get(DateTimeFieldType.minuteOfHour()));
            cell = row.createCell(colId++);
            cell.setCellValue(visiteMixteBral);
            cell = row.createCell(colId++);
            cell.setCellValue(nombreMixte > 0 ? ((int) ((visiteMixteBral * 1.0) / nombreMixte * 100)) : -1);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("GLOBAL");
            cell = row.createCell(colId++);
            cell.setCellValue((nombreMixte + nombrePVE));

            cell = row.createCell(colId++);
            cell.setCellValue((nombreparcPveBrac + nombreparcMixteBrac));
            cell = row.createCell(colId++);
            cell.setCellValue(((nombreMixte + nombrePVE) > 0) ?(nombreparcPveBrac + nombreparcMixteBrac) / (nombreMixte + nombrePVE) : -1);
            cell = row.createCell(colId++);
            cell.setCellValue((nombreparcPveBral + nombreparcMixteBral));
            cell = row.createCell(colId++);
            cell.setCellValue((nombreMixte + nombrePVE) > 0 ?(nombreparcPveBral + nombreparcMixteBral) / (nombreMixte + nombrePVE) : - 1);
            double jourGlobalBrac = reponseService.jourMoyenEcoule(true, null, debut, fin);
            cell = row.createCell(colId++);
            cell.setCellValue(calculateEcart(jourGlobalBrac));
            double jourGlobalBral = reponseService.jourMoyenEcoule(false, null, debut, fin);
            cell = row.createCell(colId++);
            cell.setCellValue(calculateEcart(jourGlobalBral));

            cell = row.createCell(colId++);
            cell.setCellValue(heureGlobalBrac.get(DateTimeFieldType.hourOfDay()) + ":" + heureGlobalBrac.get(DateTimeFieldType.minuteOfHour()));
            cell = row.createCell(colId++);
            cell.setCellValue(visiteGlobalBrac);
            cell = row.createCell(colId++);
            cell.setCellValue((nombrePVE + nombreMixte) > 0 ?((int) ((visiteGlobalBrac * 1.0) / (nombrePVE + nombreMixte) * 100)) : - 1);
            cell = row.createCell(colId++);
            cell.setCellValue(heureGlobalBral.get(DateTimeFieldType.hourOfDay()) + ":" + heureGlobalBral.get(DateTimeFieldType.minuteOfHour()));
            cell = row.createCell(colId++);
            cell.setCellValue(visiteGlobalBral);
            cell = row.createCell(colId++);
            cell.setCellValue((nombrePVE + nombreMixte) > 0 ?((int) ((visiteGlobalBral * 1.0) / (nombrePVE + nombreMixte) * 100)) : - 1);

        } catch (ServiceException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void produceExcelDispo(XSSFSheet sheet, XSSFWorkbook workbook) {
        try {
            int nombreBibra = resultBiBrac.getPveAgEtBr().size();
            int nombreBgBrac = resultBgBrac.getPveDiEtOr().size();
            int nombreBgBral = resultBgBral.getPveAgEtBr().size();
            int nombreBiBral = resultBiBral.getPveDiEtOr().size();
            DisponibiliteNumeriqueStat numeriqueStatBiBrac = reponseService.getAllDispoStat(debut, fin, Boolean.TRUE, Boolean.TRUE);
            DisponibiliteNumeriqueStat numeriqueStatBgBrac = reponseService.getAllDispoStat(debut, fin, Boolean.FALSE, Boolean.TRUE);
            DisponibiliteNumeriqueStat numeriqueStatBiBral = reponseService.getAllDispoStat(debut, fin, Boolean.TRUE, Boolean.FALSE);
            DisponibiliteNumeriqueStat numeriqueStatBgBral = reponseService.getAllDispoStat(debut, fin, Boolean.FALSE, Boolean.FALSE);

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
            
            Map<String, Integer> datai1a = resultBgBrac.getPveDiEtOr();
            Map<String, Integer> datai2a = resultBgBrac.getPveAgEtBr();
            Map<String, Integer> datai3a = resultBgBrac.getPve();
            Map<String, Integer> datai4a = resultBgBrac.getPvmDiEtOr();
            Map<String, Integer> datai5a = resultBgBrac.getPvmAgEtBr();
            Map<String, Integer> datai6a = resultBgBrac.getPvm();
            Map<String, Integer> datai7a = resultBgBrac.getPdv();

            Map<String, Integer> data1a = resultBiBral.getPveDiEtOr();
            Map<String, Integer> data2a = resultBiBral.getPveAgEtBr();
            Map<String, Integer> data3a = resultBiBral.getPve();
            Map<String, Integer> data4a = resultBiBral.getPvmDiEtOr();
            Map<String, Integer> data5a = resultBiBral.getPvmAgEtBr();
            Map<String, Integer> data6a = resultBiBral.getPvm();
            Map<String, Integer> data7a = resultBiBral.getPdv();

            int rowId = 1;
            int colId = 1;
            Row row = sheet.createRow(rowId);
            Cell cell = row.createCell(colId);
            cell.setCellValue("");
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId + 1, //last row  (0-based)
                    colId, //first column (0-based)
                    colId //last column  (0-based)
            ));
            colId++;
            cell = row.createCell(colId);
            cell.setCellValue("BIÈRES BRACONGO");
            cell.setCellStyle(gold);
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + nombreBibra //last column  (0-based)
            ));
            colId = colId + nombreBibra + 1;
            cell = row.createCell(colId);
            cell.setCellValue("BIÈRES BRALIMA");
            cell.setCellStyle(blue);
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + nombreBiBral //last column  (0-based)
            ));
            colId = 2;
            rowId++;
            row = sheet.createRow(rowId);

            BorderStyle border = BorderStyle.THICK;

            /* J'entre les noms des bières bracongo*/
            for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
                String key = entrySet.getKey();
                cell = row.createCell(colId++);
                cell.setCellValue(key + " CL");
                cell.setCellStyle(myStyle);
            }

            cell = row.createCell(colId++);
            cell.setCellValue("TAUX DES PDV D.N. = 1");
            cell.setCellStyle(myStyle3);

            /* J'entre les noms des bières bralima*/
            for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
                String key = entrySet.getKey();
                cell = row.createCell(colId++);
                cell.setCellValue(key + " CL");
                cell.setCellStyle(myStyle2);
            }
            cell = row.createCell(colId++);
            cell.setCellValue("TAUX DES PDV D.N. = 1");
            cell.setCellStyle(myStyle3);
            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("PVE (Di & Or)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBrac.getPveDiO() + "%");
            cell.setCellStyle(yellow);

            for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBral.getPveDiO() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("PVE (Ag & Br)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data2.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);

            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBrac.getPveArBz() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : data2a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }

            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBral.getPveArBz() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("PVE");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve */
            for (Map.Entry<String, Integer> entrySet : data3.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBrac.getPve() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : data3a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBral.getPve() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("MIXTE (Di & Or)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data4.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBrac.getPvmDiOr() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : data4a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBral.getPvmDiOr() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("MIXTE (Ag & Br)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pvm Ag et Bz*/
            for (Map.Entry<String, Integer> entrySet : data5.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBrac.getPvmArBz() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : data5a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBral.getPvmArBz() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("MIXTE");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data6.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBrac.getPvm() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : data6a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBral.getPvm() + "%");
            cell.setCellStyle(yellow);
            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("GLOBAL");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data7.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBrac.getPdv() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : data7a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBiBral.getPdv() + "%");
            cell.setCellStyle(yellow);

            /////////////////////////////////////////////////////////////////////
            rowId = 13;
            colId = 1;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId);
            cell.setCellValue("");
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId + 1, //last row  (0-based)
                    colId, //first column (0-based)
                    colId //last column  (0-based)
            ));
            colId++;
            cell = row.createCell(colId);
            cell.setCellValue("BG BRACONGO");
            cell.setCellStyle(gold);
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + nombreBgBrac //last column  (0-based)
            ));
            colId = colId + nombreBibra + 1;
            cell = row.createCell(colId);
            cell.setCellValue("BG BRALIMA");
            cell.setCellStyle(blue);
            sheet.addMergedRegion(new CellRangeAddress(
                    rowId, //first row (0-based)
                    rowId, //last row  (0-based)
                    colId, //first column (0-based)
                    colId + nombreBgBral //last column  (0-based)
            ));
            colId = 2;
            rowId++;
            row = sheet.createRow(rowId);

            
            /* J'entre les noms des bières bracongo*/
            for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
                String key = entrySet.getKey();
                cell = row.createCell(colId++);
                cell.setCellValue(key + " CL");
                cell.setCellStyle(myStyle);
            }

            cell = row.createCell(colId++);
            cell.setCellValue("TAUX DES PDV D.N. = 1");
            cell.setCellStyle(myStyle3);

            /* J'entre les noms des bières bralima*/
            for (Map.Entry<String, Integer> entrySet : datai1a.entrySet()) {
                String key = entrySet.getKey();
                cell = row.createCell(colId++);
                cell.setCellValue(key + " CL");
                cell.setCellStyle(myStyle2);
            }
            cell = row.createCell(colId++);
            cell.setCellValue("TAUX DES PDV D.N. = 1");
            cell.setCellStyle(myStyle3);
            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("PVE (Di & Or)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBrac.getPveDiO() + "%");
            cell.setCellStyle(yellow);

            for (Map.Entry<String, Integer> entrySet : datai1a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBral.getPveDiO() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("PVE (Ag & Br)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai2.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);

            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBrac.getPveArBz() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : datai2a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }

            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBral.getPveArBz() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("PVE");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve */
            for (Map.Entry<String, Integer> entrySet : datai3.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBrac.getPve() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : datai3a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBral.getPve() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("MIXTE (Di & Or)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai4.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBrac.getPvmDiOr() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : datai4a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBral.getPvmDiOr() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("MIXTE (Ag & Br)");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pvm Ag et Bz*/
            for (Map.Entry<String, Integer> entrySet : datai5.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBrac.getPvmArBz() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : datai5a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBral.getPvmArBz() + "%");
            cell.setCellStyle(yellow);

            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("MIXTE");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai6.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBrac.getPvm() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : datai6a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBral.getPvm() + "%");
            cell.setCellStyle(yellow);
            colId = 1;
            rowId++;
            row = sheet.createRow(rowId);
            cell = row.createCell(colId++);
            cell.setCellValue("GLOBAL");
            cell.setCellStyle(grey);
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai7.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(gold);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBrac.getPdv() + "%");
            cell.setCellStyle(yellow);
            for (Map.Entry<String, Integer> entrySet : datai7a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                cell = row.createCell(colId++);
                cell.setCellValue(value + "%");
                cell.setCellStyle(blue);
            }
            cell = row.createCell(colId++);
            cell.setCellValue(numeriqueStatBgBral.getPdv() + "%");
            cell.setCellStyle(yellow);

        } catch (ServiceException ex) {
            Logger.getLogger(DocumentBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void produceExcelAction(XSSFSheet sheet) {

        int rowId = 1;
        int colId = 1;
        Row row = sheet.createRow(rowId);
        Cell cell = row.createCell(colId);
        cell.setCellValue("Action");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));
        colId += 4;
        
        cell = row.createCell(colId);
        cell.setCellValue("Qui");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));
        
        colId = 1;
        rowId++;
        StringBuilder stb = new StringBuilder();
        List<String> value = ActionParseUtil.besoinContrat(actions);
        stb.append("BESOIN DE CONTRAT");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.besoinRenouvellementContrat(actions);
        stb.append("PDV AYANT BESOIN DE RENOUVELLEMENT DE CONTRATS");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.contratPartiel(actions);
        stb.append("CONTRATS EXÉCUTÉS PARTIELLEMENT");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.ReclamationRemise(actions);
        stb.append("RÉCLAMATION REMISE");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvFerme(actions);
        stb.append("PDV FERMÉS, NON OPÉRATIONNELS ET MATÉRIELS À RECUPÉRER");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvSollicitantConversion(actions);
        stb.append("PDV MIXTES SOLLICITANT LA CONVERSION EN PVE");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.besoin3BacContre1(actions);
        stb.append("PDV AYANT BESOIN DE L'OPÉRATION 3 BACS CONTRE 1");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvDemenage(actions);
        stb.append("PDV AYANT DÉMÉNAGÉ SANS PREVENIR BRACONGO --> LES RETROUVER");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvRenforcer(actions);
        stb.append("PDV À RENFORCER EN CAPACITÉ D'ACCUEIL");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvBesoinPlv(actions);
        stb.append("BESOIN DE PLV");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvConsignation(actions);
        stb.append("BESOIN DE CONSIGNATION EMBALLAGES");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvAdresseErrone(actions);
        stb.append("PDV AVEC ADRESSES ÉRRONNÉES");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvBesoin5Chaises(actions);
        stb.append("PDV AYANT BESOIN DE L'OPÉRATION 5 CHAISES CONTRE 1");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        
        colId = 1;
        rowId++;
        stb = new StringBuilder();
        value = ActionParseUtil.pdvPhnCapsule(actions);
        stb.append("PDV AVEC PHN CAPSULÉS");
        stb.append("\n");
        addActioncell(rowId, colId, value,sheet, stb.toString());
        

    }
    
    private void addActioncell(int rowId, int colId, List<String> value, XSSFSheet sheet, String header){
       Row row = sheet.createRow(rowId);
        Cell cell = row.createCell(colId);
        StringBuilder stb = new StringBuilder();
        stb.append(header);
        stb.append(value.get(0));
        cell.setCellValue(stb.toString());
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));
        colId += 4;        
        cell = row.createCell(colId);
        cell.setCellValue(value.get(1));
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));
    }

    private void produceExcelCommentaire(XSSFSheet sheet){
        int rowId = 1;
        int colId = 1;
        Row row = sheet.createRow(rowId);
        Cell cell = row.createCell(colId);
        cell.setCellValue("Commentaires");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));
        colId += 4;
        
        cell = row.createCell(colId);
        cell.setCellValue("Point de Vente");
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));
        
        for (Commentaire commentaire : commentaires) {
            
        rowId  ++;
        colId = 1;
        row = sheet.createRow(rowId);
        cell = row.createCell(colId);
        cell.setCellValue(commentaire.getContenu());
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 3 //last column  (0-based)
        ));
        colId += 4;
        
        cell = row.createCell(colId);
        StringBuilder stb = new StringBuilder();
        stb.append(commentaire.getReponse().getPointDeVente().getNom());
        stb.append(" ");
        stb.append(commentaire.getReponse().getPointDeVente().getCircuit().getZone().getSecteur().getCode());
        stb.append("-");
        stb.append(commentaire.getReponse().getPointDeVente().getCircuit().getCode());
        cell.setCellValue(stb.toString());
        sheet.addMergedRegion(new CellRangeAddress(
                rowId, //first row (0-based)
                rowId, //last row  (0-based)
                colId, //first column (0-based)
                colId + 1 //last column  (0-based)
        ));
        }
    }
}
