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
import com.royken.bracongo.survey.entities.projection.DisponibiliteNumeriqueStat;
import com.royken.bracongo.survey.service.IReponseService;
import com.royken.bracongo.survey.service.ISecteurService;
import com.royken.bracongo.survey.service.ServiceException;
import java.io.IOException;
import java.io.OutputStream;
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
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
@Named(value = "documentBean")
@RequestScoped
public class DocumentBean {

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

    BoissonDispoStat resultBiBrac;

    BoissonDispoStat resultBgBrac;

    BoissonDispoStat resultBgBral;

    BoissonDispoStat resultBiBral;

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
            resultBiBrac = reponseService.getAllBoissonDispoStat(null, null, Boolean.TRUE, Boolean.TRUE);
            resultBgBrac = reponseService.getAllBoissonDispoStat(null, null, Boolean.FALSE, Boolean.TRUE);
            resultBgBral = reponseService.getAllBoissonDispoStat(null, null, Boolean.FALSE, Boolean.FALSE);
            resultBiBral = reponseService.getAllBoissonDispoStat(null, null, Boolean.TRUE, Boolean.FALSE);

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
            Chunk c1 = new Chunk("BRAC-DMC-EN-073\t\t\t", f1);
            Chunk c2 = new Chunk("              Report Market Audit .....", f2);
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

            int pveDiS1, pveOrS1, pveAgS1, pveBrS1;
            int pveDiS2, pveOrS2, pveAgS2, pveBrS2;
            int pveDiS3, pveOrS3, pveAgS3, pveBrS3;
            int pveDiS4, pveOrS4, pveAgS4, pveBrS4;

            int pvmDiS1, pvmOrS1, pvmAgS1, pvmBrS1;
            int pvmDiS2, pvmOrS2, pvmAgS2, pvmBrS2;
            int pvmDiS3, pvmOrS3, pvmAgS3, pvmBrS3;
            int pvmDiS4, pvmOrS4, pvmAgS4, pvmBrS4;
            List<Secteur> secteurs = secteurService.findAllSecteur();

            pveDiS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Di, null, null);
            pveDiS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Di, null, null);
            pveDiS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Di, null, null);
            pveDiS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Di, null, null);

            pveBrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Br, null, null);
            pveBrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Br, null, null);
            pveBrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Br, null, null);
            pveBrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Br, null, null);

            pveAgS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Ag, null, null);
            pveAgS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Ag, null, null);
            pveAgS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Ag, null, null);
            pveAgS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Ag, null, null);

            pveOrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.PVE, TypeCategorie.Or, null, null);
            pveOrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.PVE, TypeCategorie.Or, null, null);
            pveOrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.PVE, TypeCategorie.Or, null, null);
            pveOrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.PVE, TypeCategorie.Or, null, null);

            pvmDiS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Di, null, null);
            pvmDiS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Di, null, null);
            pvmDiS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Di, null, null);
            pvmDiS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Di, null, null);

            pvmBrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Br, null, null);
            pvmBrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Br, null, null);
            pvmBrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Br, null, null);
            pvmBrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Br, null, null);

            pvmAgS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Ag, null, null);
            pvmAgS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Ag, null, null);
            pvmAgS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Ag, null, null);
            pvmAgS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Ag, null, null);

            pvmOrS1 = reponseService.countReponseGlobalStat(null, secteurs.get(0), TypeRegime.Mixte, TypeCategorie.Or, null, null);
            pvmOrS2 = reponseService.countReponseGlobalStat(null, secteurs.get(1), TypeRegime.Mixte, TypeCategorie.Or, null, null);
            pvmOrS3 = reponseService.countReponseGlobalStat(null, secteurs.get(2), TypeRegime.Mixte, TypeCategorie.Or, null, null);
            pvmOrS4 = reponseService.countReponseGlobalStat(null, secteurs.get(3), TypeRegime.Mixte, TypeCategorie.Or, null, null);

            Font f3 = new Font(Font.FontFamily.HELVETICA, 16.0f, Font.BOLD);
            Font f4 = new Font(Font.FontFamily.HELVETICA, 16.0f, Font.NORMAL);
            Font f5 = new Font(Font.FontFamily.HELVETICA, 13.0f, Font.NORMAL);
            Chunk per = new Chunk("Période : ", f3);
            Chunk wee = new Chunk("Week 10", f4);
            Chunk date = new Chunk("07/03 au 13/03/2016", f4);

            Paragraph p1 = new Paragraph();
            p1.add(per);
            p1.add(date);
            p1.add(wee);
            //p1.setAlignment(Element.ALIGN_CENTER);
            document.add(p1);
            document.add(new Chunk("\n"));
            Chunk pdv = new Chunk("PDV opérationnels visités : ... ", f3);
            Chunk pdv1 = new Chunk("PDV visités au courant de la semaine 10-2016 (du 07/03 au 13/03) par les Markets Audit Officers selon la configuration dans le tableau ci-dessous.", f4);
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

            table.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f3, 1, 4, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("MIXTES", f3, 1, 4, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("TOTAL PDV", f3, 1, 4, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("TOTAL\n PAR\n SECTEUR", f3, 2, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("SECTEUR", f3, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("DI", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("OR", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("AG", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("BR", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("DI", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("OR", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("AG", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("BR", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("DI", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("OR", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("AG", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("BR", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell("S1", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS1 + pvmDiS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS1 + pvmOrS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS1 + pvmAgS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS1 + pvmBrS1 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS1 + pvmDiS1 + pveOrS1 + pvmOrS1 + pveAgS1 + pvmAgS1 + pveBrS1 + pvmBrS1 + "", f5, 1, 1, false));

            table.addCell(DocumentUtil.createDefaultHeaderCell("S2", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS2 + pvmDiS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS2 + pvmOrS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS2 + pvmAgS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS2 + pvmBrS2 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS2 + pvmDiS2 + pveOrS2 + pvmOrS2 + pveAgS2 + pvmAgS2 + pveBrS2 + pvmBrS2 + "", f5, 1, 1, false));

            table.addCell(DocumentUtil.createDefaultHeaderCell("S3", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS3 + pvmDiS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS3 + pvmOrS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS3 + pvmAgS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS3 + pvmBrS3 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS3 + pvmDiS3 + pveOrS3 + pvmOrS3 + pveAgS3 + pvmAgS3 + pveBrS3 + pvmBrS3 + "", f5, 1, 1, false));

            table.addCell(DocumentUtil.createDefaultHeaderCell("S4", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmDiS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmOrS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmAgS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pvmBrS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS4 + pvmDiS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveOrS4 + pvmOrS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveAgS4 + pvmAgS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveBrS4 + pvmBrS4 + "", f5, 1, 1, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(pveDiS4 + pvmDiS4 + pveOrS4 + pvmOrS4 + pveAgS4 + pvmAgS4 + pveBrS4 + pvmBrS4 + "", f5, 1, 1, false));

            int a, b, c, d, e, f, g, h, i, j, k, l;
            table.addCell(DocumentUtil.createDefaultHeaderCell("Total", f5, 1, 1, false));
            a = pveDiS1 + pveDiS2 + pveDiS3 + pveDiS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(a + "", f3, 1, 1, false));
            b = pveOrS1 + pveOrS2 + pveOrS3 + pveOrS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(b + "", f3, 1, 1, false));
            c = pveAgS1 + pveAgS2 + pveAgS3 + pveAgS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(c + "", f3, 1, 1, false));
            d = pveBrS1 + pveBrS2 + pveBrS3 + pveBrS4;

            table.addCell(DocumentUtil.createDefaultHeaderCell(d + "", f3, 1, 1, false));
            e = pvmDiS1 + pvmDiS2 + pvmDiS3 + pvmDiS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(e + "", f3, 1, 1, false));
            f = pvmOrS1 + pvmOrS2 + pvmOrS3 + pvmOrS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(f + "", f3, 1, 1, false));
            g = pvmAgS1 + pvmAgS2 + pvmAgS3 + pvmAgS4;
            table.addCell(DocumentUtil.createDefaultHeaderCell(g + "", f3, 1, 1, false));
            h = pvmBrS1 + pvmBrS2 + pvmBrS3 + pvmBrS4;

            table.addCell(DocumentUtil.createDefaultHeaderCell(h + "", f3, 1, 1, false));
            i = a + e;
            table.addCell(DocumentUtil.createDefaultHeaderCell(i + "", f3, 1, 1, false));
            j = b + f;
            table.addCell(DocumentUtil.createDefaultHeaderCell(j + "", f3, 1, 1, false));
            k = c + g;
            table.addCell(DocumentUtil.createDefaultHeaderCell(k + "", f3, 1, 1, false));
            l = d + h;
            table.addCell(DocumentUtil.createDefaultHeaderCell(l + "", f3, 1, 1, false));

            table.addCell(DocumentUtil.createDefaultHeaderCell(i + j + k + l + "", f3, 1, 1, false));

            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderColor(BaseColor.WHITE);
            table.addCell(cell);
            table.addCell(DocumentUtil.createDefaultHeaderCell(a + b + c + d + "", f3, 1, 4, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(e + f + g + h + "", f3, 1, 4, false));
            table.addCell(DocumentUtil.createDefaultHeaderCell(i + j + k + l + "", f3, 1, 4, false));
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
            DisponibiliteNumeriqueStat numeriqueStatBiBrac = reponseService.getAllDispoStat(null, null, Boolean.TRUE, Boolean.TRUE);
            DisponibiliteNumeriqueStat numeriqueStatBgBrac = reponseService.getAllDispoStat(null, null, Boolean.FALSE, Boolean.TRUE);
            DisponibiliteNumeriqueStat numeriqueStatBiBral = reponseService.getAllDispoStat(null, null, Boolean.TRUE, Boolean.FALSE);
            DisponibiliteNumeriqueStat numeriqueStatBgBral = reponseService.getAllDispoStat(null, null, Boolean.FALSE, Boolean.FALSE);
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
            Font f8 = new Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD);

            PdfPTable biereTable = new PdfPTable(relativewidths1);
            biereTable.setWidthPercentage(100);
            PdfPCell cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBorderColor(BaseColor.WHITE);
            biereTable.addCell(cell);
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BIÈRES BRACONGO", f7, 1, nombreBibra + 1, false));
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BIÈRES BRALIMA", f7, 1, nombreBiBral + 1, false));
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
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true));
            }

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV D.N. = 1", f8, 1, 1, true));

            /* J'entre les noms des bières bralima*/
            for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
                String key = entrySet.getKey();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV D.N. = 1", f8, 1, 1, true));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Di&Or)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data1.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPveDiO() + "%", f8, 1, 1, false));

            for (Map.Entry<String, Integer> entrySet : data1a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPveDiO() + "%", f8, 1, 1, false));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Ag&Br)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data2.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPveArBz() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : data2a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPveArBz() + "%", f8, 1, 1, false));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve */
            for (Map.Entry<String, Integer> entrySet : data3.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPve() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : data3a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPve() + "%", f8, 1, 1, false));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Di&Or)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data4.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPvmDiOr() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : data4a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPvmDiOr() + "%", f8, 1, 1, false));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Ag & Br)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pvm Ag et Bz*/
            for (Map.Entry<String, Integer> entrySet : data5.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPvmArBz() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : data5a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPvmArBz() + "%", f8, 1, 1, false));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data6.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPvm() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : data6a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPvm() + "%", f8, 1, 1, false));

            biereTable.addCell(DocumentUtil.createDefaultHeaderCell("GLOBAL", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : data7.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBrac.getPdv() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : data7a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                biereTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            biereTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBiBral.getPdv() + "%", f8, 1, 1, false));
            document.add(biereTable);
            document.add(new Chunk("\n"));

            PdfPTable bgTable = new PdfPTable(relativewidths2);
            bgTable.setWidthPercentage(100);
            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setRowspan(2);
            cell.setBorderColor(BaseColor.WHITE);
            bgTable.addCell(cell);
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BG BRACONGO", f7, 1, nombreBgBrac + 1, false));
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("DISPONIBILITE BG BRALIMA", f7, 1, nombreBgBral + 1, false));

            /* J'entre les noms des bières bracongo*/
            for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
                String key = entrySet.getKey();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true));
            }

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV\nD.N. = 1", f8, 1, 1, true));

            /* J'entre les noms des bières bralima*/
            for (Map.Entry<String, Integer> entrySet : datai1a.entrySet()) {
                String key = entrySet.getKey();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, true));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("TAUX DES PDV\n D.N. = 1", f8, 1, 1, true));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Di&Or)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai1.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPveDiO() + "%", f8, 1, 1, false));

            for (Map.Entry<String, Integer> entrySet : datai1a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPveDiO() + "%", f8, 1, 1, false));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE(Ag&Br)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai2.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPveArBz() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : datai2a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPveArBz() + "%", f8, 1, 1, false));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve */
            for (Map.Entry<String, Integer> entrySet : datai3.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPve() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : datai3a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPve() + "%", f8, 1, 1, false));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Di&Or)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai4.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPvmDiOr() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : datai4a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPvmDiOr() + "%", f8, 1, 1, false));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE(Ag & Br)", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pvm Ag et Bz*/
            for (Map.Entry<String, Integer> entrySet : datai5.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPvmArBz() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : datai5a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPvmArBz() + "%", f8, 1, 1, false));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai6.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPvm() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : datai6a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPvm() + "%", f8, 1, 1, false));

            bgTable.addCell(DocumentUtil.createDefaultHeaderCell("GLOBAL", f8, 1, 1, false));
            /* J'entre les données des bières bracongo pve di et Or*/
            for (Map.Entry<String, Integer> entrySet : datai7.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBrac.getPdv() + "%", f8, 1, 1, false));
            for (Map.Entry<String, Integer> entrySet : datai7a.entrySet()) {
                //  String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                bgTable.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            bgTable.addCell(DocumentUtil.createDefaultHeaderCell(numeriqueStatBgBral.getPdv() + "%", f8, 1, 1, false));

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
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Di&Or >= 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pveDiOrSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Di&Or < 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pveDiOrInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
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
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Ag&Br >= 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pveAgBzSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("PVE Ag&Br < 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pveAgBzInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
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
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Di&Or >= 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pvmDiOrSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Di&Or < 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pvmDiOrInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
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
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Ag&Br >= 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pvmAgBzSup.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
            }
            classement.addCell(DocumentUtil.createDefaultHeaderCell("Mixtes Ag&Br < 50 %", f8, 1, 2, false));
            for (Map.Entry<String, Integer> entrySet : pvmAgBzInf.entrySet()) {
                String key = entrySet.getKey();
                Integer value = entrySet.getValue();
                classement.addCell(DocumentUtil.createDefaultHeaderCell(key, f8, 1, 1, false));
                classement.addCell(DocumentUtil.createDefaultHeaderCell(value + "%", f8, 1, 1, false));
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

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Disponibilité moyenne générale Bières", f8, 1, 2, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Disponibilité moyenne générale BG", f8, 1, 2, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bracongo", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bralima", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bracongo", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("Bralima", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("PVE (Di&Or)", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPveDiEtOr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPveDiEtOr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPveDiEtOr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPveDiEtOr()) + "%", f8, 1, 1, false));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("PVE (Ag&Br)", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPveAgEtBr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPveAgEtBr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPveAgEtBr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPveAgEtBr()) + "%", f8, 1, 1, false));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("PVE", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPve()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPve()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPve()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPve()) + "%", f8, 1, 1, false));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE (Di&Or)", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPvmDiEtOr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPvmDiEtOr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPvmDiEtOr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPvmDiEtOr()) + "%", f8, 1, 1, false));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE (Ag&Br)", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPvmAgEtBr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPvmAgEtBr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPvmAgEtBr()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPvmAgEtBr()) + "%", f8, 1, 1, false));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("MIXTE", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPvm()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPvm()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPvm()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPvm()) + "%", f8, 1, 1, false));

            moyenne.addCell(DocumentUtil.createDefaultHeaderCell("GLOBAL", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBrac.getPdv()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBiBral.getPdv()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBrac.getPdv()) + "%", f8, 1, 1, false));
            moyenne.addCell(DocumentUtil.createDefaultHeaderCell(getMoyenne(resultBgBral.getPdv()) + "%", f8, 1, 1, false));

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

    }

    private void produceReportParcEmballage(PdfWriter writer, Document document) {

    }

    private void produceReportMarche(PdfWriter writer, Document document) {

    }

    private void produceReportAction(PdfWriter writer, Document document) {

    }
}
