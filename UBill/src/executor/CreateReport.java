package executor;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import store.Transaction;
import datatype.Date;
import datatype.Transactions;


public class CreateReport {
	
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(128, 128, 128);
	private Color passive = new Color(128, 0, 0);
	private double entranceTot = 0.0;
	private double entranceTransf = 0.0;
	private double exitTot = 0.0;
	private double exitTransf = 0.0;

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
		
		Font title = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
		Font header = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLDITALIC);		
		Font emph = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);
		Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(Color.BLACK));
		Font boldPassive = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(passive));
		Font boldActive = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(active));
		Font dataPassive = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(passive));
		Font dataActive = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(active));
		Font dataNeutro = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, new BaseColor(neutro));
		
		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100);
		table.setSpacingBefore(14);
		table.setSpacingAfter(14);
		
		PdfPCell c1 = new PdfPCell(new Paragraph(new Chunk("Day", header)));
		PdfPCell c2 = new PdfPCell(new PdfPCell(new Paragraph(new Chunk("Entrance", header))));		
		PdfPCell c3 = new PdfPCell(new Paragraph(new Chunk("Exit", header)));
		PdfPCell c4 = new PdfPCell(new Paragraph(new Chunk("Category", header)));
		c1.setBorder(PdfPCell.NO_BORDER);
		c2.setBorder(PdfPCell.NO_BORDER);
		c3.setBorder(PdfPCell.NO_BORDER);
		c4.setBorder(PdfPCell.NO_BORDER);
		table.addCell(c1);
		table.addCell(c2);
		table.addCell(c3);
		table.addCell(c4);
		
		for (Transaction t : Transactions.loadTransactions(Login.getUser().getUser(), Login.getAccount().getAccount(), Integer.valueOf(year), Date.getMonth(month)).getTransactions()) {
			if (t.getRefid() == 0) {
				if (t.getType() == '+') {
					c1.setPhrase(new Paragraph(new Chunk(Date.getDay(t.getDay()), dataActive)));
					c2.setPhrase(new Paragraph(new Chunk("+"+String.valueOf(t.getPayment())+" "+Login.getAccount().getCurrency(), dataActive)));
					c3.setPhrase(new Paragraph(new Chunk("", dataActive)));
					c4.setPhrase(new Paragraph(new Chunk(t.getEntry(), dataActive)));
					entranceTot += t.getPayment();					
				}
				else {
					c1.setPhrase(new Paragraph(new Chunk(Date.getDay(t.getDay()), dataPassive)));
					c2.setPhrase(new Paragraph(new Chunk("", dataPassive)));
					c3.setPhrase(new Paragraph(new Chunk("-"+String.valueOf(t.getPayment())+" "+Login.getAccount().getCurrency(), dataPassive)));
					c4.setPhrase(new Paragraph(new Chunk(t.getEntry(), dataPassive)));					
					exitTot += t.getPayment();
				}
			}
			else {
				if (t.getType() == '+') {
					c1.setPhrase(new Paragraph(new Chunk(Date.getDay(t.getDay()), dataNeutro)));
					c2.setPhrase(new Paragraph(new Chunk("+"+String.valueOf(t.getPayment())+" "+Login.getAccount().getCurrency(), dataNeutro)));
					c3.setPhrase(new Paragraph(new Chunk("", dataNeutro)));
					c4.setPhrase(new Paragraph(new Chunk(t.getEntry()+" from "+t.getReference(), dataNeutro)));
					entranceTransf += t.getPayment();
				}
				else {
					c1.setPhrase(new Paragraph(new Chunk(Date.getDay(t.getDay()), dataNeutro)));
					c2.setPhrase(new Paragraph(new Chunk("", dataNeutro)));
					c3.setPhrase(new Paragraph(new Chunk("-"+String.valueOf(t.getPayment())+" "+Login.getAccount().getCurrency(), dataNeutro)));
					c4.setPhrase(new Paragraph(new Chunk(t.getEntry()+" to "+t.getReference(), dataNeutro)));
					exitTransf += t.getPayment();
				}
			}
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
		}
		c1.setPhrase(new Paragraph(new Chunk("Total", bold)));
		c2.setPhrase(new Paragraph(new Chunk("+"+String.valueOf(FieldParser.roundDouble(entranceTot))+" "+Login.getAccount().getCurrency(), bold)));
		c3.setPhrase(new Paragraph(new Chunk("-"+String.valueOf(FieldParser.roundDouble(exitTot))+" "+Login.getAccount().getCurrency(), bold)));
		c4.setPhrase(new Paragraph(new Chunk("= "+String.valueOf(FieldParser.roundDouble(entranceTot-exitTot))+" "+Login.getAccount().getCurrency(), bold)));
		table.addCell(c1);
		table.addCell(c2);
		table.addCell(c3);
		table.addCell(c4);
		
		try {
			document.add(new Paragraph(new Chunk("Report of "+Login.getAccount().getAccount()+" for "+month+" "+year, title)));
			document.add(table);
		} catch (DocumentException e) {
			System.out.println("Error while adding paragraph: "+e);
		}
		
		document.close();
	}
	
}

