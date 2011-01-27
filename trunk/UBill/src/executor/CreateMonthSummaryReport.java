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


public class CreateMonthSummaryReport {
	
	private Color active = new Color(0, 128, 0);
	private Color neutro = new Color(128, 128, 128);
	private Color passive = new Color(128, 0, 0);
	private double entranceTot = 0.0;
	private double entranceTransf = 0.0;
	private double exitTot = 0.0;
	private double exitTransf = 0.0;
	private String month = null;
	private String year = null;
	private Font title = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
	private Font parBig = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
	private Font parBigActive = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(active));
	private Font parBigPassive = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(passive));
	private Font parBigBold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	private Font header = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC);
	private Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(Color.BLACK));
	private Font dataPassive = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, new BaseColor(passive));
	private Font dataActive = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, new BaseColor(active));
	private Font dataNeutro = new Font(Font.FontFamily.HELVETICA, 11, Font.ITALIC, new BaseColor(neutro));
	private Font dataPassiveBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(passive));
	private Font dataActiveBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(active));
	private Font dataNeutroBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(neutro));

	public CreateMonthSummaryReport(String filename, String month, String year) {		
		this.month = month;
		this.year = year;
		
		Document document = new Document();
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filename));
		} catch (FileNotFoundException e1) {
			System.err.println("File not found");
		} catch (DocumentException e1) {
			System.out.println("Error while creating file");
		}
		document.open();		

		PdfPTable t0 = generateTable();
		PdfPTable t1 = generateTotalsTable();
		Paragraph p0 = new Paragraph(new Chunk("Report of "+Login.getAccount().getAccount()+" for "+month+" "+year, title));
		Paragraph p1 = new Paragraph(new Chunk("Account balance: ", parBig));
		p1.setSpacingBefore(15);

		Font f = null;
		if (Login.getAccount().getBalance() > 0)
			f = parBigActive;
		else if (Login.getAccount().getBalance() < 0)
			f = parBigPassive;
		else
			 f = parBigBold;

		Paragraph p2 = new Paragraph(new Chunk(Login.getAccount().getBalance()+" "+Login.getAccount().getCurrency(), f));
		
		try {
			document.add(p0);	
			document.add(p1);
			document.add(p2);
			document.add(t0);
			document.add(t1);
		} catch (DocumentException e) {
			System.out.println("Error while adding paragraph: "+e);
		}
		
		document.close();
	}
	
	private PdfPTable generateTable() {
		PdfPTable table = new PdfPTable(4);
		int width[] = {100, 200, 200, 400};
		table.setWidthPercentage(100);
		try {			
			table.setWidths(width);
		} catch (DocumentException e) {
			System.out.println("Error while setting columns width: "+e);
		}
		table.setSpacingBefore(20);
		
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
		
		return table;
	}
	
	private PdfPTable generateTotalsTable() {
		PdfPTable table = new PdfPTable(4);
		int width[] = {100, 200, 200, 400};
		table.setWidthPercentage(100);
		try {			
			table.setWidths(width);
		} catch (DocumentException e) {
			System.out.println("Error while setting columns width: "+e);
		}
		table.setSpacingBefore(20);
		
		PdfPCell c1 = new PdfPCell();
		PdfPCell c2 = new PdfPCell();		
		PdfPCell c3 = new PdfPCell();
		PdfPCell c4 = new PdfPCell();
		c1.setBorder(PdfPCell.NO_BORDER);
		c2.setBorder(PdfPCell.NO_BORDER);
		c3.setBorder(PdfPCell.NO_BORDER);
		c4.setBorder(PdfPCell.NO_BORDER);
		
		c1.setPhrase(new Paragraph(new Chunk("Total", bold)));
		c2.setPhrase(new Paragraph(new Chunk("+"+String.valueOf(FieldParser.roundDouble(entranceTot))+" "+Login.getAccount().getCurrency(), dataActiveBold)));
		c3.setPhrase(new Paragraph(new Chunk("-"+String.valueOf(FieldParser.roundDouble(exitTot))+" "+Login.getAccount().getCurrency(), dataPassiveBold)));
		Font f = null;
		String text = "";
		double tot = entranceTot-exitTot;
		if (tot > 0) {
			text = "+";		
			f = dataActiveBold;
		}
		else if (tot < 0)
			f = dataPassiveBold;
		else
			f = bold;
		c4.setPhrase(new Paragraph(new Chunk(""+text+String.valueOf(FieldParser.roundDouble(tot))+" "+Login.getAccount().getCurrency(), f)));
		table.addCell(c1);
		table.addCell(c2);
		table.addCell(c3);
		table.addCell(c4);		
		tot = entranceTransf-exitTransf;
		text = "";
		if (tot > 0) {
			text = "+";
		}
		c1.setPhrase(new Paragraph(new Chunk("Transf.", dataNeutroBold)));
		c2.setPhrase(new Paragraph(new Chunk("+"+String.valueOf(FieldParser.roundDouble(entranceTransf))+" "+Login.getAccount().getCurrency(), dataNeutroBold)));
		c3.setPhrase(new Paragraph(new Chunk("-"+String.valueOf(FieldParser.roundDouble(exitTransf))+" "+Login.getAccount().getCurrency(), dataNeutroBold)));
		c4.setPhrase(new Paragraph(new Chunk(""+text+String.valueOf(FieldParser.roundDouble(tot))+" "+Login.getAccount().getCurrency(), dataNeutroBold)));
		table.addCell(c1);
		table.addCell(c2);
		table.addCell(c3);
		table.addCell(c4);
		f = null;
		tot = (entranceTot-exitTot) + (entranceTransf-exitTransf);
		text = "";
		if (tot > 0) {
			text = "+";
			f = dataActiveBold;
		}
		else if (tot < 0)
			f = dataPassiveBold;
		else
			 f = bold;
		c1.setPhrase(new Paragraph(new Chunk("", dataNeutroBold)));
		c2.setPhrase(new Paragraph(new Chunk("", dataNeutroBold)));
		c3.setPhrase(new Paragraph(new Chunk("", dataNeutroBold)));
		c4.setPhrase(new Paragraph(new Chunk("= "+text+String.valueOf(FieldParser.roundDouble(tot))+" "+Login.getAccount().getCurrency(), f)));
		table.addCell(c1);
		table.addCell(c2);
		table.addCell(c3);
		table.addCell(c4);
		
		return table;
	}
}

