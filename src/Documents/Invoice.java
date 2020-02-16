/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Documents;

import BiologicalPark.Client;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

/**
 * Gera uma fatura em PDF com as informações do cliente, tickets e percurso. Extende da classe "PDFCreator" para que tenha benefício do padrão Template.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public final class Invoice extends PDFCreator implements Serializable{
    private int invoceNumber = 999;
    private final int ticketsCost;
    private final LocalDateTime dateTime;
    private final Client client;
    private final List<Ticket> tickets;
    
    public Invoice(List<Ticket> tickets, int ticketsCost, Client client) throws IOException{
        super(tickets.get(0).getPointsToVisit(), 50, 290, 10, 450, 710);
        invoceNumber = lastInvoiceNumb();
        
        this.tickets = tickets;
        this.ticketsCost = ticketsCost;
        dateTime = LocalDateTime.now();
        this.client = client;
        writeOnPDF();
    }

    public int getTicketsCost() {
        return ticketsCost;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    
    @Override
    public void closePDF(){
        try{
            content.close();
            document.save("purchases/" + invoceNumber + "/invoice " + invoceNumber + ".pdf");
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
            Desktop.getDesktop().open(new File("purchases/" + invoceNumber + "/invoice " + invoceNumber + ".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void print(){
        
            try {
            ////Header
                PDImageXObject logoImage = PDImageXObject.createFromFile("src/AppDisplay/icons/logo.png", document);
                final float width = 60f;
                final float scale = width / logoImage.getWidth();
                content.drawImage(logoImage, 55, 720, width, logoImage.getHeight()*scale);

                PDFont headerFont = PDType1Font.TIMES_BOLD;
                PDFPrinter printer = new PDFPrinter(content, headerFont, 16, new Color(168, 205, 149));
                printer.putText(120, 740, "Biological Park");
                
                printer.setFont(PDType1Font.TIMES_ROMAN);
                printer.setFontSize(10);
                printer.setColor(Color.BLACK);
                printer.putText(120, 720, "Parque Biológico");
                printer.putText(120, 708, "Apartado 120 | 1111-111 Setúbal, Portugal");
                printer.putText(120, 696, "Tel. 034 034 034 | parque_biologico@parque.pt");
                
                printer.putText(450, 678, "INVOICE");
                printer.putText(500, 678, "INV" + invoceNumber);
                
                printer.setFont(PDType1Font.TIMES_BOLD);
                printer.setFontSize(24);
                printer.setColor(new Color(200, 200, 200));
                printer.putText(450, 740, "INVOICE");

            ////Client
                printer.setFontSize(10);
                printer.setColor(Color.BLACK);
                printer.putText(65, 640, "Client Info:");
                printer.setFont(PDType1Font.TIMES_ROMAN);
                if(!client.getName().isEmpty()) printer.putText(120, 640, client.getName() + " (" + client.getUsername() + ")");
                else printer.putText(120, 640, client.getUsername());
                printer.putText(120, 628, client.getAddress() + " " + client.getCity());
                printer.putText(120, 616, client.getCountry() + " | " + client.getPostalCode());
                if(!client.getNif().isEmpty()) printer.putText(120, 604, client.getNif());
                printer.putText(120, 592, client.getEmail());
                
                writeTicketsTable();
                
                writeFotter();
                
            } catch (IOException ex) {
                Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void writeTicketsTable() throws IOException {
        PDFPrinter printer = new PDFPrinter(content, PDType1Font.TIMES_ROMAN, 10, Color.BLACK);
        ////Tickets table
        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);
        content.setStrokingColor(strokeColor);
        content.setNonStrokingColor(fillColor);
        content.addRect(50, 540, 520, 20);
        content.fillAndStroke();
        content.stroke();

        int y = 547;
        printer.setFont(PDType1Font.TIMES_BOLD);
        printer.setFontSize(12);
        printer.putText(235, y, "Ticket Ref.");
        printer.putText(490, y, "Ticket Cost");

        int rectsY = 520;
        printer.setFont(PDType1Font.TIMES_ROMAN);
        content.setStrokingColor(strokeColor);
        content.setNonStrokingColor(fillColor);
        for (int i = 0; i < 5; i++) {
            content.addRect(50, rectsY, 520, 20);
            content.stroke();
            rectsY -= 20;
            content.addRect(50, rectsY, 520, 20);
            content.fillAndStroke();
            rectsY -= 20;
        }

        y = 527;
        int cost = 0;
        int widthOfText = (int) Math.round((PDType1Font.TIMES_ROMAN.getStringWidth(ticketsCost + ".00") / 1000f) * 12);
        for (Ticket t : tickets) {
            printer.putText(160, y, t.getReference());
            printer.putText(565 - widthOfText, y, ticketsCost + ".00");
            y -= 20;
        }

        ////Total cost and tickets qty.
        content.setStrokingColor(strokeColor);
        content.setNonStrokingColor(fillColor);
        content.addRect(50, rectsY, 150, 20);
        content.fillAndStroke();
        content.addRect(450, rectsY, 120, 20);
        content.addRect(200, rectsY, 50, 20);
        content.stroke();
        int totalCost = ticketsCost * tickets.size();
        widthOfText = (int) Math.round((PDType1Font.TIMES_ROMAN.getStringWidth(totalCost + ".00") / 1000f) * 12);
        printer.putText(565 - widthOfText, rectsY + 6, (totalCost) + ".00");
        printer.putText(212, rectsY + 6, "" + tickets.size());
        printer.setFont(PDType1Font.TIMES_BOLD);
        printer.putText(380, rectsY + 6, "Total price");
        printer.putText(65, rectsY + 6, "Tickets Quantity");
    }

    public void writeFotter() throws IOException {
        PDFPrinter printer = new PDFPrinter(content, PDType1Font.TIMES_ROMAN, 15, Color.BLACK);
        
        ////Footer
        content.setStrokingColor(new Color(100, 100, 100));
        content.setNonStrokingColor(new Color(230, 230, 230));
        content.addRect(50, 100, 520, 100);
        content.stroke();
        content.addRect(50, 50, 520, 30);
        content.fillAndStroke();
        printer.putText(50, 210, "Notes:");
        printer.setFontSize(13);
        printer.setFont(PDType1Font.TIMES_ITALIC);
        printer.setColor(new Color(100, 100, 100));
        printer.putText(220, 59, "THANK YOU FOR YOUR PURCHASE!");
    }
    
    public static int lastInvoiceNumb(){
        File folder = new File("purchases/");
        File[] listOfFiles = folder.listFiles();
        int biggerInvoiceNumb = 999;
        
        for (int i = 0; i < listOfFiles.length; i++) {
            
          if (listOfFiles[i].isDirectory()) {
            biggerInvoiceNumb = Integer.parseInt(listOfFiles[i].getName());
          }
        }
        return biggerInvoiceNumb;
    }
    
}
