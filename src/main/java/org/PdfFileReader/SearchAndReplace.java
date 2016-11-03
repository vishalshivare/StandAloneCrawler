package org.PdfFileReader;

public class SearchAndReplace {
	private String findText;
	private String replaceText;
	
	public SearchAndReplace(String findText, String replaceText) {
		super();
		this.findText = findText;
		this.replaceText = replaceText;
	}
	public String getFindText() {
		return findText;
	}
	public String getReplaceText() {
		return replaceText;
	}
	

}
