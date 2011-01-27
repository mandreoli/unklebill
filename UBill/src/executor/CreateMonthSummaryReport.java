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
	private Color normal = new Color(0, 0, 0);
	private String year = null;
	private String month = null;
	private Font title = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
	private Font parBig = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL);
	private Font parBigActive = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(active));
	private Font parBigPassive = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(passive));
	private Font parBigBold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
	private Font header = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLDITALIC);
	private Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(Color.BLACK));
	private Font dataPassive = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, new BaseColor(passive));
	private Font dataActive = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, new BaseColor(active));
	private Font dataNeutro = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, new BaseColor(neutro));
	private Font data = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL, new BaseColor(normal));
	private Font dataPassiveBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(passive));
	private Font dataActiveBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(active));
	private Font dataNeutroBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(neutro));

	public CreateMonthSummaryReport(String filename, String month, String year) {
		this.year = year;
		this.month = month;
		
		Document document = new Document();
		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filename));
		} catch (FileNotFoundException e1) {
			System.err.println("File not found");
		} catch (DocumentException e1) {
			System.err.println("Error while creating file");
		}
		document.open();		

		PdfPTable t0 = generateTable();
		Paragraph p0 = new Paragraph(new Chunk(Login.getAccount().getAccount()+" report", title));
		Paragraph p1 = new Paragraph(new Chunk("Summary of "+month+" "+year, title));
		Paragraph p2 = new Paragraph(new Chunk("Account balance: ", parBig));
		p2.setSpacingBefore(15);

		Font f = null;
		if (Login.getAccount().getBalance() > 0)
			f = parBigActive;
		else if (Login.getAccount().getBalance() < 0)
			f = parBigPassive;
		else
			 f = parBigBold;

		Paragraph p3 = new Paragraph(new Chunk(Login.getAccount().getBalance()+" "+Login.getAccount().getCurrency(), f));
		
		try {
			document.add(p0);	
			document.add(p1);
			document.add(p2);
			document.add(p3);
			document.add(t0);
		} catch (DocumentException e) {
			System.err.println("Error while adding paragraph: "+e);
		}
		
		document.close();
	}
	
	private PdfPTable generateTable() {
		PdfPTable table = new PdfPTable(6);
		int width[] = {160, 250, 250, 250, 250, 250};
		table.setWidthPercentage(100);
		try {			
			table.setWidths(width);
		} catch (DocumentException e) {
			System.err.println("Error while setting columns width: "+e);
		}
		table.setSpacingBefore(20);
		
		PdfPCell c1 = new PdfPCell(new Paragraph(new Chunk("Day", header)));
		PdfPCell c2 = new PdfPCell(new PdfPCell(new Paragraph(new Chunk("Entrance", header))));		
		PdfPCell c3 = new PdfPCell(new Paragraph(new Chunk("Exit", header)));
		PdfPCell c4 = new PdfPCell(new Paragraph(new Chunk("T.Entrance", header)));
		PdfPCell c5 = new PdfPCell(new Paragraph(new Chunk("T.Exit", header)));
		PdfPCell c6 = new PdfPCell(new Paragraph(new Chunk("Total", header)));
		c1.setBorder(PdfPCell.NO_BORDER);
		c2.setBorder(PdfPCell.NO_BORDER);
		c3.setBorder(PdfPCell.NO_BORDER);
		c4.setBorder(PdfPCell.NO_BORDER);
		c5.setBorder(PdfPCell.NO_BORDER);
		c6.setBorder(PdfPCell.NO_BORDER);
		table.addCell(c1);
		table.addCell(c2);
		table.addCell(c3);
		table.addCell(c4);
		table.addCell(c5);
		table.addCell(c6);
		
		int days = Date.getNumDays(Date.getMonth(month));
		double entrance = 0.0;
		double exit = 0.0;
		double entranceTransf = 0.0;
		double exitTransf = 0.0;
		double entranceTot = 0.0;
		double exitTot = 0.0;
		double entranceTransfTot = 0.0;
		double exitTransfTot = 0.0;
		double total = 0.0;
		Transactions ts = null;
		
		for (int day = 1; day <= days; day++) {
			entrance = 0.0;
			exit = 0.0;
			entranceTransf = 0.0;
			exitTransf = 0.0;		
			ts = Transactions.loadTransactions(Login.getUser().getUser(), Login.getAccount().getAccount(), Integer.valueOf(year), Date.getMonth(month), day);
			
			for (Transaction t : ts.getTransactions()) {				
				if (t.getRefid() != 0) {
					if (t.getType() == '+') {
						entranceTransf += t.getPayment();
						entranceTransfTot += entranceTransf;
					}
					else {
						exitTransf += t.getPayment();
						exitTransfTot += exitTransf;
					}
				}
				else {
					if (t.getType() == '+') {
						entrance += t.getPayment();
						entranceTot += entrance;
					}
					else {
						exit += t.getPayment();
						exitTot += exit;
					}
				}
			}
			
			entrance = FieldParser.roundDouble(entrance);
			exit = FieldParser.roundDouble(exit);
			entranceTransf = FieldParser.roundDouble(entranceTransf);
			exitTransf = FieldParser.roundDouble(exitTransf);
			total = FieldParser.roundDouble(entrance - exit + entranceTransf - exitTransf);
			
			c1.setPhrase(new Paragraph(new Chunk(Date.getDay(day), data)));
			c2.setPhrase(new Paragraph(new Chunk(hideZero(entrance, "+"), dataActive)));
			c3.setPhrase(new Paragraph(new Chunk(hideZero(exit, "-"), dataPassive)));
			c4.setPhrase(new Paragraph(new Chunk(hideZero(entranceTransf, "+"), dataNeutro)));
			c5.setPhrase(new Paragraph(new Chunk(hideZero(exitTransf, "-"), dataNeutro)));
			String sign = "";
			Font f = null;
			if (total > 0.0) {
				sign = "+";
				f = dataActiveBold;
			}
			else if (total < 0.0)
				f = dataPassiveBold;
			else
				 f = dataNeutroBold;
			c6.setPhrase(new Paragraph(new Chunk(sign+total+" "+Login.getAccount().getCurrency(), f)));
			table.addCell(c1);
			table.addCell(c2);
			table.addCell(c3);
			table.addCell(c4);
			table.addCell(c5);
			table.addCell(c6);
		}
		
		entranceTot = FieldParser.roundDouble(entranceTot);
		exitTot = FieldParser.roundDouble(exitTot);
		entranceTransfTot = FieldParser.roundDouble(entranceTransfTot);
		exitTransfTot = FieldParser.roundDouble(exitTransfTot);
		total = FieldParser.roundDouble(entranceTot - exitTot + entranceTransfTot - exitTransfTot);
		
		c1.setPhrase(new Paragraph(new Chunk("Total", bold)));
		c2.setPhrase(new Paragraph(new Chunk("+"+entranceTot+" "+Login.getAccount().getCurrency(), dataActiveBold)));
		c3.setPhrase(new Paragraph(new Chunk("-"+exitTot+" "+Login.getAccount().getCurrency(), dataPassiveBold)));
		c4.setPhrase(new Paragraph(new Chunk("+"+entranceTransfTot+" "+Login.getAccount().getCurrency(), dataNeutroBold)));
		c5.setPhrase(new Paragraph(new Chunk("-"+exitTransfTot+" "+Login.getAccount().getCurrency(), dataNeutroBold)));
		String sign = "";
		Font f = null;
		if (total > 0.0) {
			sign = "+";
			f = dataActiveBold;
		}
		else if (total < 0.0)
			f = dataPassiveBold;
		else {
			sign = "+";
			f = dataNeutroBold;
		}
		c6.setPhrase(new Paragraph(new Chunk(sign+total+" "+Login.getAccount().getCurrency(), f)));
		table.addCell(c1);
		table.addCell(c2);
		table.addCell(c3);
		table.addCell(c4);
		table.addCell(c5);
		table.addCell(c6);
		
		return table;
	}
	
	private String hideZero(double value, String sign) {
		if (value == 0.0)
			return "";
		else
			return sign+String.valueOf(value)+" "+Login.getAccount().getCurrency();
	}
}

