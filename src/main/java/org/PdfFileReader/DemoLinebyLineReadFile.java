package org.PdfFileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class DemoLinebyLineReadFile {
	
	/*static{
		FileOutputStream fileOutputStream=null;
		try {
			fileOutputStream = new FileOutputStream("./loggerFile.txt", true);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PrintStream printStream = new PrintStream(fileOutputStream);
		System.setOut(printStream);
	}*/
	static String contents="";

	public static String[] getFontLineByLineFromPdf(String fileName)throws IOException  
	   {  
	     PDDocument doc= PDDocument.load(new File(fileName));  
	     PDFTextStripper stripper = new PDFTextStripper() {  
	       String prevBaseFont = "";  
	       protected void writeString(String text, List<TextPosition> textPositions) throws IOException  
	       {  
	    	   System.out.println("The text="+text);
	    	   //contents=contents+text;
	         StringBuilder builder = new StringBuilder();  
	         for (TextPosition position : textPositions)  
	         {  
	           String baseFont = position.getFont().getName();
	           
	          /* System.out.println("Font size= "+position.getFontSize());
	           System.out.println("Font height= "+position.getHeight());
	           System.out.println("Font width= "+position.getWidth());*/
	           PDFont font=position.getFont();
	           // System.out.println(font.getFontDescriptor().);
	           
	          // System.out.println("Font name= "+position.getFont().getName());
	           if (baseFont != null && !baseFont.equals(prevBaseFont))  
	           {  
	             builder.append('[').append(baseFont).append(']');  
	             prevBaseFont = baseFont;  
	           }  
	           builder.append(position.getCharacterCodes());  
	         }  
	         writeString(builder.toString());  
	       }  
	     };  
	     String content=stripper.getText(doc);  
	     doc.close();  
	     String pdfLinesWithFont[]= content.split("\\r?\\n");  
	     return pdfLinesWithFont;  
	   }  
	
	public static void main(String args[]){
		System.out.println("pitambar");
		try {
			System.out.println("execution stared");
			String st[]=getFontLineByLineFromPdf("C:\\Users\\pitambarm\\Documents\\groupHealth\\PdfFileReader\\hdfcbank.pdf");
			/*for(String s:st){
				//System.out.println(s);
			}*/
			/*System.out.println(contents.contains("HDFC0000052"));
			System.out.println("The whole content="+contents);*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
