package org.ghc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

public class StartCrawler {
	private static Properties properties = null;

	static {

		try {
			// this is the logger file location
			FileOutputStream fileOutputStream = new FileOutputStream("./loggerFile.txt", true);

			PrintStream printStream = new PrintStream(fileOutputStream);
			System.setOut(printStream);

			// right now we are not using the logger file ,in future we can use
			// the logger file.

			/*
			 * properties = new Properties(); properties.load(new
			 * FileInputStream(
			 * "C:/Users/pitambarm/Documents/groupHealth/StandAlone_crawlerUtility/src/org/ghc/config.properties"
			 * ));
			 */
		} catch (Exception e) {
			throw new RuntimeException(
					"Config file is not loaded successfully,make sure the config file should be in src folder");
		}
	}

	public static void main(String[] args) {

		if (args.length != 3) {
			throw new RuntimeException(
					"It must have the three argument one argument contains the folder details ,2nd  argument contains searchContent and 3rd argument contains the replaceContent");
		}
		FileUtility fileUtility = new FileUtility();
		File dirFolder = new File(args[0]);

		if (dirFolder.exists() && dirFolder.isDirectory()) {
			List<File> files = FileUtility.getAllFilesFromRootDirectory(args[0]);
			fileUtility.processAllFiles(files, args[1], args[2]);

		} else {
			throw new RuntimeException("The given directory is not present and given dirName=" + args[0]);
		}

	}

}
