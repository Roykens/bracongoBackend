package com.royken.bracongo.survey.web.beans;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

/**
 *
 * @author Kenfack Valmy-Roi <roykenvalmy@gmail.com>
 */
public class DocumentUtil {
    
    
     public static PdfPCell createDefaultHeaderCell(String message, Font bf, int rowspan, int colspan, boolean rotation, BaseColor color) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        //cell.setBackgroundColor(new BaseColor(230, 230, 230));
        cell.setBackgroundColor(color);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRowspan(rowspan);
        cell.setColspan(colspan);
        cell.setPaddingBottom(2f);
        cell.setPaddingTop(2f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        if(rotation){
            cell.setRotation(90);
        }
        return cell;
    }
     
   public static PdfPCell createDefaultClassementCell(String message, Font bf, int rowspan, int colspan, boolean rotation) {
        PdfPCell cell = new PdfPCell(new Phrase(message, bf));
        cell.setBackgroundColor(new BaseColor(230, 230, 230));
        //cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setRowspan(rowspan);
        cell.setColspan(colspan);
        cell.setPaddingBottom(0.5f);
        cell.setPaddingTop(0.5f);
        cell.setBorderWidth(0.01f);
        cell.setBorderColor(BaseColor.BLACK);
        if(rotation){
            cell.setRotation(90);
        }
        return cell;
    }
}
