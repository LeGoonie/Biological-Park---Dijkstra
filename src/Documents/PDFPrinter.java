
package Documents;

import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 * Classe auxiliar para escrever texto num pdf.
 * @author André Reis 170221035 e Bruno Alves 170221041
 */
public class PDFPrinter {
    private PDPageContentStream content;
    private PDFont font;
    private int fontSize;
    private Color color;
    
    public PDFPrinter(PDPageContentStream contents, PDFont font, int fontSize) {		
        this(contents, font, fontSize, Color.BLACK);
    }

    public PDFPrinter(PDPageContentStream contents, PDFont font, int fontSize, Color color) {		
        this.content = contents;
        this.font = font;
        this.fontSize = fontSize;
        this.color = color;
    }

    public void setFont(PDFont font) {
        this.font = font;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void putText(int x, int y, String text) throws IOException {
        content.beginText();
        content.newLineAtOffset(x, y);
        content.setFont(font, fontSize);
        content.setNonStrokingColor(color);
        content.showText(text);
        content.endText();	
    }
}
