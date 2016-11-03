package org.ghc.model;

public class SearchResult {
	String filePath;
	FindAndReplaceTextModel findAndReplaceTextModel;

	public SearchResult(String filePath, FindAndReplaceTextModel findAndReplaceTextModel) {
		super();
		this.filePath = filePath;
		this.findAndReplaceTextModel = findAndReplaceTextModel;
	}

	public String getFilePath() {
		return filePath;
	}

	@Override
	public String toString() {
		return "SearchResult [filePath=" + filePath + ", findAndReplaceTextModel=" + findAndReplaceTextModel + "]";
	}

	public FindAndReplaceTextModel getFindAndReplaceTextModel() {
		return findAndReplaceTextModel;
	}

}
