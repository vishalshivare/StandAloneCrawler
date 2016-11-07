package org.PdfFileReader;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.bouncycastle.util.Strings;

public class ReplacingTextInPDF {

	public static void main(String[] args) throws IOException {
		PDDocument doc = null;

		File file = new File("C:\\Users\\pitambarm\\Documents\\groupHealth\\Crawler_utility\\ROM2016AUGM07_1952946697_0200440399.pdf");
		doc = PDDocument.load(file);
		replaceText(doc, "pitambar", "C:/Users/pitambarm/Documents/groupHealth/Crawler_utility");

		//doIt(doc, "m.pdf", "FOOD", "olaola");

	}

	public static PDDocument replaceText(PDDocument document, String searchString, String replacement)
			throws IOException {

		PDPageTree pages = document.getDocumentCatalog().getPages();
		for (PDPage page : pages) {
			PDFStreamParser parser = new PDFStreamParser(page);
			parser.parse();
			List tokens = parser.getTokens();
			for (int j = 0; j < tokens.size(); j++) {
				Object next = tokens.get(j);
				if (next instanceof Operator) {
					Operator op = (Operator) next;
					// Tj and TJ are the two operators that display strings in a
					// PDF
					if (op.getName().equals("Tj")) {
						// Tj takes one operator and that is the string to
						// display so lets update that operator
						COSString previous = (COSString) tokens.get(j - 1);
						String string = previous.getString();
						string = string.replaceFirst(searchString, replacement);
						previous.setValue(string.getBytes());
					} else if (op.getName().equals("TJ")) {
						COSArray previous = (COSArray) tokens.get(j - 1);
						// previous.setString(, string);

						String combineLetters = "";
						List<COSString> coList = new ArrayList<>();
						for (int k = 0; k < previous.size(); k++) {
							Object arrElement = previous.getObject(k);
							if (arrElement instanceof COSString) {
								COSString cosString = (COSString) arrElement;
								String string = cosString.getString();
								if(string.equalsIgnoreCase("FOOD")){
									System.out.println("Find food text");
								}
								combineLetters += string;

								// cosString.setValue(string.getBytes());

							}
						}
						Pattern r = Pattern.compile(searchString);
						Matcher m = r.matcher(combineLetters);
						String replacedLine="";
						if (m.find()) {
							replacedLine = combineLetters.replaceAll(searchString, replacement);
							for (int k = 0; k < previous.size(); k++) {
								Object arrElement = previous.getObject(k);
								if (arrElement instanceof COSString) {
									COSString cosString = (COSString) arrElement;
									String string = cosString.getString();
									combineLetters += string;
									String s = "";

									cosString.setValue(s.getBytes());
									if (k == (previous.size() - 1)) {
										cosString.setValue(replacedLine.getBytes());
									} else {
										cosString.setValue(s.getBytes());
									}

								}
							}

						}else{
							System.out.println("Text="+combineLetters);
						}
					}
				}
			}
			// now that the tokens are updated we will replace the page content
			// stream.
			PDStream updatedStream = new PDStream(document);
			OutputStream out = updatedStream.createOutputStream();
			ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
			tokenWriter.writeTokens(tokens);
			page.setContents(updatedStream);
			out.close();
		}
		document.save("v.pdf");
		return document;
	}

	public static void doIt(PDDocument doc, String outputFile, String strToFind, String message) throws IOException {

		PDPageTree pages = doc.getDocumentCatalog().getPages();
		for (PDPage page : pages) {
			{

				PDFStreamParser parser = new PDFStreamParser(page);
				parser.parse();
				List tokens = parser.getTokens();
				for (int j = 0; j < tokens.size(); j++) {
					Object next = tokens.get(j);
					if (next instanceof Operator) {
						Operator op = (Operator) next;
						// Tj and TJ are the two operators that display
						// strings in a PDF
						if (op.getName().equals("Tj")) {
							// Tj takes one operator and that is the string
							// to display so lets update that operator
							COSString previous = (COSString) tokens.get(j - 1);
							String string = previous.getString();
							string = string.replaceFirst(strToFind, message);
							/*
							 * previous.reset(); previous.append(
							 * string.getBytes() );
							 */
						} else if (op.getName().equals("TJ")) {
							COSArray previous = (COSArray) tokens.get(j - 1);
							for (int k = 0; k < previous.size(); k++) {
								Object arrElement = previous.getObject(k);
								if (arrElement instanceof COSString) {
									COSString cosString = (COSString) arrElement;
									String string = cosString.getString();
									string = string.replaceFirst(strToFind, message);
									cosString.setValue(string.getBytes());
								}
							}
						}
					}
				}
				// now that the tokens are updated we will replace the
				// page content stream.
				PDStream updatedStream = new PDStream(doc);
				OutputStream out = updatedStream.createOutputStream();
				ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
				tokenWriter.writeTokens(tokens);
				page.setContents(updatedStream);
				out.close();
			}
			doc.save("b.pdf");
			doc.close();
		}

	}
}
