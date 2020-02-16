package Documents;

import BiologicalPark.InterestPoint;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Gera um bilhete em PDF com as informações do percurso e uma referencia e QRCode únicos. Extende da classe "PDFCreator" para que tenha benefício do padrão Template.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class Ticket extends PDFCreator implements Serializable{
    private final String reference, clientName;
    private final File qrCode;
    private final LocalDateTime dateTime;
    private final int ticketNumb, totalNumbTickets, invoice;
    private boolean availableToUse = true, isBikePath;
    
    public Ticket(List<InterestPoint> pointsToVisit, int ticketNumb, int totalNumbTickets, String clientName, boolean isBikePath) throws IOException, WriterException{
        super(pointsToVisit, 60, 564, 16, 60, 445);
        reference = UUID.randomUUID().toString();
        dateTime = LocalDateTime.now();
        this.ticketNumb = ticketNumb;
        this.totalNumbTickets = totalNumbTickets;
        this.clientName = clientName;
        this.invoice = Invoice.lastInvoiceNumb();
        this.isBikePath = isBikePath;
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(reference, BarcodeFormat.QR_CODE, 350, 350);

        qrCode = new File("purchases/" + (invoice) + "/" + reference + ".jpg");
        MatrixToImageWriter.writeToFile(bitMatrix, "PNG", qrCode);
        writeOnPDF();
    }
    
    public boolean canBeUsed(){
        return availableToUse;
    }
    
    public boolean isBikePath(){
        return isBikePath;
    }
    
    public void setAsUsed(){
        availableToUse = false;
    }
    
    public String getReference(){
        return reference;
    }

    public File getQrCode() {
        return qrCode;
    }

    public List<InterestPoint> getPointsToVisit() {
        return pointsToVisit;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getInvoice() {
        return invoice;
    }
    
    @Override
    public void closePDF(){
        try{
            content.close();
            document.save("purchases/" + invoice + "/ticket" + ticketNumb + ".pdf");
        } catch(IOException e){
            System.out.println(e.getMessage());
        } finally{
            try {
                document.close();
            } catch (IOException ex) {
                Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            Desktop.getDesktop().open(new File("purchases/" + invoice + "/ticket" + ticketNumb + ".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void print(){
        
            try {
            ////Header
                PDImageXObject logoImage = PDImageXObject.createFromFile("src/AppDisplay/icons/logo.png", document);
                final float width = 130f;
                final float scale = width / logoImage.getWidth();
                content.drawImage(logoImage, 120, 670, width, logoImage.getHeight()*scale);
                
                PDFPrinter printer = new PDFPrinter(content, PDType1Font.TIMES_BOLD, 30, new Color(168, 205, 149));
                printer.putText(230, 710, "Biological Park");
                printer.setFontSize(24);
                printer.setColor(new Color(200, 200, 200));
                printer.putText(455, 745, "TICKET");
                
            ////Body
                printer.setFont(PDType1Font.TIMES_ROMAN);
                printer.setFontSize(18);
                printer.putText(60, 635, "Name");
                int widthOfText = (int)Math.round((PDType1Font.TIMES_ROMAN.getStringWidth("Parque Biológico") / 1000f) * 16);
                int startX = 610 - widthOfText - 100;
                printer.putText(startX, 445, "Location");
                printer.putText(startX, 635, "Type Of Route");
                
                printer.setFontSize(25);
                printer.setColor(Color.BLACK);
                printer.putText(60, 605, clientName);
                printer.setFontSize(16);
                printer.putText(startX, 423, "Parque Biológico");
                printer.putText(startX, 400, "Setúbal, Portugal");
                if(isBikePath)
                    printer.putText(startX, 610, "By bike");
                else printer.putText(startX, 610, "On foot");
                
                writeFotter(widthOfText, startX);
                
            } catch (IOException ex) {
                Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public void writeFotter(int widthOfText, int startX) throws IOException {
        PDFPrinter printer = new PDFPrinter(content, PDType1Font.HELVETICA, 12, Color.BLACK);

        ////Footer
        PDImageXObject QRImage = PDImageXObject.createFromFileByContent(qrCode, document);
        final float QRwidth = 270f;
        final float QRscale = QRwidth / QRImage.getWidth();
        final float QRstartX = (610 - QRwidth) / 2;
        content.drawImage(QRImage, QRstartX, 60, QRwidth, QRImage.getHeight() * QRscale);
        qrCode.delete();
        printer.setColor(Color.BLACK);
        widthOfText = (int) Math.round((PDType1Font.HELVETICA.getStringWidth(reference) / 1000f) * 12);
        startX = (610 - widthOfText) / 2;
        printer.putText(startX, 75, reference);

        ////Clipping
        printer.setColor(new Color(120, 120, 120));
        printer.setFontSize(18);
        widthOfText = (int) Math.round((PDType1Font.HELVETICA.getStringWidth("Ticket " + ticketNumb + " of " + totalNumbTickets) / 1000f) * 18);
        startX = (610 - widthOfText) / 2;
        printer.putText(startX, 335, "Ticket " + ticketNumb + " of " + totalNumbTickets);
        content.setLineDashPattern(new float[]{3}, 0);
        content.setStrokingColor(120, 120, 120);
        content.drawLine(0, 343, 245, 343);
        content.drawLine(365, 343, 610, 343);
    }
    
    
}
