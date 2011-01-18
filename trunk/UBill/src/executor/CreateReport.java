package executor;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;


public class CreateReport {

	public CreateReport(String filename) {
		Document document = new Document();
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filename+".pdf"));
		} catch (FileNotFoundException e1) {
			System.err.println("File not found");
		} catch (DocumentException e1) {
			System.out.println("Error while creating file");
		}
		document.open();
		
		document.close();
	}
	
}

