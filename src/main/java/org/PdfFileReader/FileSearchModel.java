package org.PdfFileReader;

public class FileSearchModel {
	String fileExtension;
	String findContent;
	String replaceContent;

	public FileSearchModel(String fileExtension, String findContent, String replaceContent) {
		super();
		this.fileExtension = fileExtension;
		this.findContent = findContent;
		this.replaceContent = replaceContent;
	}

	public String getFileExtension() {
		return fileExtension;
	}

	public String getFindContent() {
		return findContent;
	}

	public String getReplaceContent() {
		return replaceContent;
	}

}
