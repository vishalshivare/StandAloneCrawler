package org.PdfFileReader;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class DemoReadPDFFile {

	public static void main(String[] args) throws IOException {

		// Loading an existing document
		File file = new File("C:\\Users\\pitambarm\\Documents\\groupHealth\\PdfFileReader\\Food_exp_claim_form.pdf");
		PDDocument document = PDDocument.load(file);

		
		  PDFTextStripper pdfStripper = new PDFTextStripper();
		  
		  String text = pdfStripper.getText(document);
		  
		  System.out.println(text);
		 

		

		/*PDPageTree tree = document.getPages();
		Iterator<PDPage> it = tree.iterator();

		// page.getResources().getFonts();
		while (it.hasNext()) {
			PDPage page = it.next();
			Iterable<COSName> fontNames = page.getResources().getFontNames();
			Iterator<COSName> names = fontNames.iterator();
			while(names.hasNext()){
				COSName c=names.next();
				System.out.println(c.getName()+"mu ");
			}
		}*/

		/*
		 * while(iterator.hasNext()){
		 * 
		 * System.out.println("pitambar"); PDPage page=iterator.next();
		 * System.out.println(page); PDDocument
		 * 
		 * ExtractText text=pdfStripper. pdfStripper.processPage(page);
		 * 
		 * pdfStripper.setStartPage(1);
		 * 
		 * 
		 * InputStream stream=page.getContents(); byte b[]=new
		 * byte[stream.available()]; System.out.println(new String(b)); try {
		 * Thread.sleep(300); } catch (InterruptedException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * }
		 */
		// System.out.println("The text="+text);

		document.close();

		/*
		 * // Retrieving the pages of the document PDPage page =
		 * document.getPage(0);
		 * 
		 * 
		 * InputStream stream=page.getContents(); byte b[]=new
		 * byte[stream.available()]; for(byte by:b){ System.out.println(by); }
		 * System.out.println(new String(b));
		 */

		/*
		 * PDPageContentStream contentStream = new PDPageContentStream(document,
		 * page);
		 * 
		 * // Begin the Content stream contentStream.beginText();
		 * 
		 * // Setting the font to the Content stream
		 * contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
		 * 
		 * // Setting the position for the line
		 * contentStream.newLineAtOffset(25, 500);
		 * 
		 * String text =
		 * "This is the sample document and we are adding content to it.";
		 * 
		 * // Adding text in the form of string // contentStream.showText(text);
		 * contentStream.showText(text);
		 * 
		 * // Ending the content stream contentStream.endText();
		 * 
		 * System.out.println("Content added");
		 * 
		 * // Closing the content stream contentStream.close();
		 * 
		 * // Saving the document document.save(new File("./new.pdf"));
		 * 
		 * // Closing the document document.close();
		 */
	}

}
