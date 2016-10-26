package org.ghc;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class CommonFileUtility {

	/**
	 * @param rootDirectoryPath
	 * @param fileExtentionPattern
	 * @return
	 */
	public static List<File> getAllFilesFromRootDirectoryAccordingToFileExtensions(String rootDirectoryPath,
			String fileExtentionPattern) {
		File dir = new File(rootDirectoryPath);
		String[] fileExtensions = fileExtentionPattern.split("/");

		return (List<File>) FileUtils.listFiles(dir, fileExtensions, true);
	}

}
