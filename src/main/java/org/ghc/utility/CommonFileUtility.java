package org.ghc.utility;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author pitambarm
 *
 */
public class CommonFileUtility {
	private static final Logger LOGGER = Logger.getLogger(CommonFileUtility.class);

	/**
	 * @param rootDirectoryPath
	 * @return
	 */
	public static List<File> getAllFilesFromRootDirectoryAccordingToFileExtensions(String rootDirectoryPath) {
		LOGGER.info("Entering method getAllFilesFromRootDirectoryAccordingToFileExtensions()::CommonFileUtility");
		CSVConfigFileReader.readCSVFile();
		String fileExts[] = new String[CSVConfigFileReader.fileExtensions.size()];
		CSVConfigFileReader.fileExtensions.toArray(fileExts);
		File dir = new File(rootDirectoryPath);
		LOGGER.info("Exiting method getAllFilesFromRootDirectoryAccordingToFileExtensions()::CommonFileUtility");
		return (List<File>) FileUtils.listFiles(dir, fileExts, true);
	}

}
