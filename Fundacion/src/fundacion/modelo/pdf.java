package fundacion.modelo;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class pdf {

    public boolean generarPDF(int pr, int com, int doc, String ali, float val, float iva, float des, int cant, String ruta) {
        String name = "/factura.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(ruta+name));
            document.open();

            Paragraph p = new Paragraph("This is testing");
            document.add(p);
            
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            PdfPTable table = new PdfPTable(8);
            PdfPCell c1 = new PdfPCell(new Phrase("PROV"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("COMP"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("DOC"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("ALIM"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("VAL"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("IVA"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("DESC"));
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("CANT"));
            table.addCell(c1);
            table.setHeaderRows(1);
            
            table.addCell(pr+"");
            table.addCell(com+"");
            table.addCell(doc+"");
            table.addCell(ali);
            table.addCell(val+"");
            table.addCell(iva+"");
            table.addCell(des+"");
            table.addCell(cant+"");
            
            document.add(table);
            
            
            document.close();
            System.out.println("pdf generado");
            Runtime.getRuntime().exec("cmd /c start "+ruta+name);
            return true;
        } catch (DocumentException | IOException ex) {
            System.out.println(ex);
        } finally{
            return false;
        }
    }
}
