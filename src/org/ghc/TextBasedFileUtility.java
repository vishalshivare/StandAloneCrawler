package org.ghc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextBasedFileUtility {

	private Map<String, String> originalFileMapToTemporaryFile = null;
	private int fileIdentifier = 0;

	public TextBasedFileUtility() {
		originalFileMapToTemporaryFile = new LinkedHashMap<String, String>();
	}

	public void processAllFiles(String rootDirectoryPath, String searchText, String replaceText, File backUpFile,
			String fileExtensions) throws IOException {
		List<File> files = CommonFileUtility.getAllFilesFromRootDirectoryAccordingToFileExtensions(rootDirectoryPath,
				fileExtensions);
		if (replaceText == null) {
			for (File file : files) {
				searchTextInFileAndLogIt(file, searchText);
			}
		} else {
			for (File file : files) {
				searchAndReplaceContentInFile(file, searchText, replaceText, backUpFile);
			}

			try {
				for (java.util.Map.Entry<String, String> entry : originalFileMapToTemporaryFile.entrySet()) {

					Files.move(Paths.get(entry.getKey()), Paths.get(entry.getValue()),
							StandardCopyOption.REPLACE_EXISTING);
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}

		}

	}

	public void searchTextInFileAndLogIt(File file, String searchText) throws IOException {

		if (file.canRead()) {
			FileReader fileReader = new FileReader(file);

			String line;
			int representingLineNumberOfFile = 0;

			try (BufferedReader bufferReader = new BufferedReader(fileReader)) {
				while ((line = bufferReader.readLine()) != null) {
					++representingLineNumberOfFile;
					Pattern r = Pattern.compile("\\b" + searchText);
					Matcher m = r.matcher(line);
					if (m.find()) {
						System.out.println("The searchContent = " + searchText + " is found in lineNumber="
								+ representingLineNumberOfFile + " in this file= " + file.getName()
								+ " and file Path = " + file.getCanonicalPath());
					}
				}
			}
			fileReader.close();

		} else {
			System.out.println("Read permission is not allowed for this file= " + file.getName() + " and file path= "
					+ file.getCanonicalPath());
		}

	}

	/**
	 * @param file
	 * @param searchText
	 * @param replaceText
	 * @param backUpFile
	 */
	private void searchAndReplaceContentInFile(File file, String searchText, String replaceText, File backUpFile) {

		try {

			if (file.canWrite()) {

				String absolutePath = file.getAbsolutePath();
				String parentDirOfCurrentFile = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));

				/*
				 * The below 3 lines are used to take the backup of the files
				 * ,which file content is replaced . The all backup files are
				 * present in "backup" folder of current directory.
				 */
				String rootFolders[] = file.getAbsolutePath().split("\\\\");
				new File(backUpFile.getCanonicalPath() + "/" + rootFolders[rootFolders.length - 2]).mkdir();
				FileWriter fileWriterForBackup = new FileWriter(backUpFile.getCanonicalPath() + "/"
						+ rootFolders[rootFolders.length - 2] + "/" + file.getName());

				/**
				 * Here we are creating a temporary file ,which contains the
				 * replaced content.
				 */
				File tempFile = new File(parentDirOfCurrentFile + "/temp" + fileIdentifier + ".txt");
				FileWriter temporaryFileWriter = new FileWriter(tempFile, true);

				FileReader fileReader = new FileReader(file);

				String line;
				String replacedLine = "";
				boolean isContentFoundinFile = false;
				int representingLineNumberOfFile = 0;

				try (BufferedReader bufferReader = new BufferedReader(fileReader)) {

					while ((line = bufferReader.readLine()) != null) {

						replacedLine = line.replaceAll("\\b" + searchText, replaceText);
						++representingLineNumberOfFile;
						if (!replacedLine.equalsIgnoreCase(line)) {
							System.out.println("The searchContent = " + searchText + " is found in lineNumber="
									+ representingLineNumberOfFile + " in this file= " + file.getName()
									+ " and replaced with = " + replaceText + "  content");
							temporaryFileWriter.write(replacedLine + System.lineSeparator());
							isContentFoundinFile = true;

						} else {
							temporaryFileWriter.write(line + System.lineSeparator());
						}
						fileWriterForBackup.write(line + "\n");
					}
					temporaryFileWriter.close();
					fileWriterForBackup.close();

					if (isContentFoundinFile) {

						originalFileMapToTemporaryFile.put(tempFile.getCanonicalPath(), file.getCanonicalPath());
						++fileIdentifier;

					} else {
						new File(backUpFile.getCanonicalPath() + "/" + rootFolders[rootFolders.length - 2] + "/"
								+ file.getName()).delete();
						tempFile.delete();
					}

				}
				fileReader.close();

			} else {
				System.out.println("Write permission is not allowed for this file= " + file.getName()
						+ " and file path= " + file.getCanonicalPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
