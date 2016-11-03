package org.ghc;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class CommonFileUtility {
	private static final Logger LOGGER = Logger.getLogger(CommonFileUtility.class);

	/**
	 * @param rootDirectoryPath
	 * @param fileExtentionPattern
	 * @return
	 */
	public static List<File> getAllFilesFromRootDirectoryAccordingToFileExtensions(String rootDirectoryPath,
			String fileExtentionPattern) {
		LOGGER.info("Entering method getAllFilesFromRootDirectoryAccordingToFileExtensions()::CommonFileUtility");
		File dir = new File(rootDirectoryPath);
		String[] fileExtensions = fileExtentionPattern.split("/");
		LOGGER.info("Exiting method getAllFilesFromRootDirectoryAccordingToFileExtensions()::CommonFileUtility");
		return (List<File>) FileUtils.listFiles(dir, fileExtensions, true);
	}

}
