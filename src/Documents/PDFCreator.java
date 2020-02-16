/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Documents;

import BiologicalPark.InterestPoint;
import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Classe abstrata do padrão Template para a criação de PDFs dos tickets e faturas
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public abstract class PDFCreator {
    public static transient final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
    public static transient final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm z");
    protected transient PDDocument document;
    protected transient PDPageContentStream content;
    
    private int pathX, pathY, dateTextSize, dateX, dateY;
    protected List<InterestPoint> pointsToVisit;
    
    public PDFCreator(List<InterestPoint> pointsToVisit, int pathX, int pathY, int dateTextSize, int dateX, int dateY){
        this.pointsToVisit = pointsToVisit;
        this.pathX = pathX;
        this.pathY = pathY;
        this.dateTextSize = dateTextSize;
        this.dateX = dateX;
        this.dateY = dateY;
        this.document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        try {
            content = new PDPageContentStream(document, page);
        } catch (IOException ex) {
            Logger.getLogger(PDFCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void writeOnPDF() throws IOException{
        print();
        
        //path
        PDFPrinter printer = new PDFPrinter(content, PDType1Font.TIMES_BOLD, 15, Color.BLACK);
        printer.putText(pathX, pathY, "Path:");
        printPointsToVisitOnPDF(content, 14, 15, pathX, pathY-20);
        
        //date
        printer.setFontSize(dateTextSize);
        printer.setFont(PDType1Font.TIMES_ROMAN);
        printer.putText(dateX, dateY, "Date:");
        printer.putText(dateX + (int)Math.round((PDType1Font.TIMES_ROMAN.getStringWidth("Date: ") / 1000f) * dateTextSize), dateY, dateFormat.format(new Date()));
        if(dateTextSize > 15)
            printer.putText(dateX + (int)Math.round((PDType1Font.TIMES_ROMAN.getStringWidth("Date: ") / 1000f) * dateTextSize), 
                    dateY - ((int)Math.round(((dateX * (dateTextSize+5)) / 1000f) * dateTextSize)), hourFormat.format(new Date()));
        else
            printer.putText(dateX + (int)Math.round((PDType1Font.TIMES_ROMAN.getStringWidth("Date: ") / 1000f) * dateTextSize), 
                    dateY - ((int)Math.round(((dateX) / 1000f) * (dateTextSize+4)*2)), hourFormat.format(new Date()));
        
        closePDF();
    }
    
    public void printPointsToVisitOnPDF(PDPageContentStream content, int size, int spacement, int x, int y){
        int heigth = y, count = 0, widthOfText;
        String targets = "";
        PDFont headerFont = PDType1Font.TIMES_ROMAN;
        PDFPrinter printer = new PDFPrinter(content, headerFont, size);
        try {
            for(InterestPoint ip : pointsToVisit){
                if(count != 0) targets += " - ";
                widthOfText = (int)Math.round((PDType1Font.TIMES_ROMAN.getStringWidth(targets) / 1000f) * size);
                   if(widthOfText > 440) {
                    printer.putText(x, heigth, targets);
                    heigth -= spacement;
                    targets = "";
                }
                targets += ip.toString();
                count++;
            }
            if(heigth == y){
                printer.putText(x, heigth, targets);
            } else {
                printer.putText(x, heigth, " - " + targets);
            }
            
        } catch (IOException ex) {
                Logger.getLogger(Ticket.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public abstract void closePDF();
    public abstract void print();
}
