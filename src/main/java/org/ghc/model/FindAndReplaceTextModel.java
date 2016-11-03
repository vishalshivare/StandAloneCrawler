package org.ghc.model;

public class FindAndReplaceTextModel {
	@Override
	public String toString() {
		return "FindAndReplaceTextModel [findText=" + findText + ", replaceText=" + replaceText + "]";
	}

	private String findText;
	private String replaceText;

	public FindAndReplaceTextModel(String findText, String replaceText) {
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
