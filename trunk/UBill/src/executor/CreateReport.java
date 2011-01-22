package executor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class CreateReport {

	public CreateReport(String filename, String month, String year) {
		Document document = new Document();
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filename));
		} catch (FileNotFoundException e1) {
			System.err.println("File not found");
		} catch (DocumentException e1) {
			System.out.println("Error while creating file");
		}
		document.open();
		
		Font title = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
		Font subtitle = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);		
		Font emph = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
		Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
		Font data = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
		
		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		PdfPCell h1 = new PdfPCell(new Paragraph(new Chunk("Day", subtitle)));
		PdfPCell h2 = new PdfPCell(new Paragraph(new Chunk("Amount", subtitle)));
		table.addCell(h1);
		table.addCell(h2);
		
		try {
			document.add(new Paragraph(new Chunk("Report for "+month+" "+year, title)));
			document.add(table);
		} catch (DocumentException e) {
			System.out.println("Error while adding paragraph: "+e);
		}
		
		document.close();
	}
	
}

