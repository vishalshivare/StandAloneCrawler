package org.ghc.model;

import java.io.File;

public class FindTask {
	File file;
	String searchText;

	public FindTask(File file, String searchText) {
		this.file = file;
		this.searchText = searchText;
	}

	public File getFile() {
		return file;
	}

	public String getSearchText() {
		return searchText;
	}

}
