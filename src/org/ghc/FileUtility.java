package org.ghc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtility {

	public static List<File> getAllFilesFromRootDirectory(String directoryName) {
		File directory = new File(directoryName);

		List<File> resultList = new ArrayList<File>();

		// get all the files from a directory
		File[] fList = directory.listFiles();
		resultList.addAll(Arrays.asList(fList));
		for (File file : fList) {
			if (file.isDirectory()) {
				resultList.addAll(getAllFilesFromRootDirectory(file.getAbsolutePath()));
			}
		}

		return resultList;
	}

	public void processAllFiles(List<File> files, String searchContent, String replaceContent) {
		for (File file : files) {
			if (file.isFile()) {
				searchAndReplaceContentInFile(file, searchContent, replaceContent);
			}
		}
	}

	private void searchAndReplaceContentInFile(File file, String searchContent, String replaceContent) {
		try {
			FileReader fileReader = new FileReader(file);
			String line;
			String allContentInFile = "";
			String backupContentInFile = "";
			try (BufferedReader br = new BufferedReader(fileReader)) {

				while ((line = br.readLine()) != null) {
					allContentInFile += line;
				}
				backupContentInFile = allContentInFile.replaceAll(searchContent, replaceContent);
				if (!backupContentInFile.equalsIgnoreCase(allContentInFile)) {
					// content is found and replaced so log into log file
					System.out.println("The searchContent = " + searchContent + " is found in this file= "
							+ file.getName() + " and replaced with = " + replaceContent + "  content");

				}
				FileWriter fw = new FileWriter(file);
				fw.write(backupContentInFile);
				fw.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
