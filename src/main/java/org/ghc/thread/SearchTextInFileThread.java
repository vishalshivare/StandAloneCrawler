package org.ghc.thread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.ghc.model.FindAndReplaceTextModel;
import org.ghc.model.SearchResult;
import org.ghc.utility.CSVConfigFileReader;

public class SearchTextInFileThread implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(SearchTextInFileThread.class);
	static {

		try {
			FileOutputStream fileOutputStream = new FileOutputStream("./searchResult.txt", true);

			PrintStream printStream = new PrintStream(fileOutputStream);
			System.setOut(printStream);
		} catch (Exception e) {
			LOGGER.error("Exception occured in static_block{}::SearchTextInFileThread");
		}
	}
	/**
	 * 
	 *
	 */
	public static CopyOnWriteArrayList<SearchResult> searchResults = new CopyOnWriteArrayList<>();

	private File file;

	public SearchTextInFileThread(File file) {
		this.file = file;

	}

	public void run() {
		LOGGER.info("Entering method run()::SearchTextInFileThread");
		String fileExtension = FilenameUtils.getExtension(file.getName());
		try {
			if (fileExtension.equalsIgnoreCase("pdf")) {
				searchTextInPDFBasedFile(file,
						CSVConfigFileReader.fileExtensionMaptoSearchAndReplaceContent.get(fileExtension));

			} else {

				searchTextInTextBasedFile(file,
						CSVConfigFileReader.fileExtensionMaptoSearchAndReplaceContent.get(fileExtension));

			}
		} catch (Exception exception) {

			try {
				LOGGER.error("Exception occured in threadName=" + Thread.currentThread().getName() + " due to ="
						+ exception.getMessage() + " and the file path=" + file.getCanonicalPath(), exception);
			} catch (IOException e) {

				LOGGER.error("exceptin occured in catch block of run()::SearchTextInFileThread");
			}
		}
		LOGGER.info("Exiting method run()::SearchTextInFileThread");

	}

	/**
	 * @param file
	 * @param findAndReplaceTexts
	 * @throws IOException
	 */
	private void searchTextInPDFBasedFile(File file, List<FindAndReplaceTextModel> findAndReplaceTexts)
			throws IOException {
		LOGGER.info("Entering method searchTextInPDFBasedFile()::SearchTextInFileThread");

		if (file.canRead()) {

			PDDocument document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String fileContents = pdfStripper.getText(document);

			for (FindAndReplaceTextModel findReplaceTextModel : findAndReplaceTexts) {
				Pattern r = Pattern.compile(findReplaceTextModel.getFindText());
				Matcher m = r.matcher(fileContents);

				if (m.find()) {
					LOGGER.info("The searchContent = " + findReplaceTextModel.getFindText() + " is found in this file= "
							+ file.getName() + " and file Path = " + file.getCanonicalPath());
					SearchResult searchResult = new SearchResult(file.getCanonicalPath(), findReplaceTextModel);
					searchResults.add(searchResult);
					System.out.println(searchResult.getFilePath() + "," + findReplaceTextModel.getFindText() + ","
							+ findReplaceTextModel.getReplaceText());

				}
			}
		} else {
			LOGGER.info("Read permission is not allowed for this file= " + file.getName() + " and file path= "
					+ file.getCanonicalPath());

		}
		LOGGER.info("Exiting method searchTextInPDFBasedFile()::SearchTextInFileThread");

	}

	/**
	 * @param file
	 * @param findAndReplaceTexts
	 * @throws IOException
	 */
	public void searchTextInTextBasedFile(File file, List<FindAndReplaceTextModel> findAndReplaceTexts)
			throws IOException {
		LOGGER.info("Entering method searchTextInTextBasedFile()::SearchTextInFileThread");

		if (file.canRead()) {

			String line;
			int representingLineNumberOfFile = 0;

			try (FileReader fileReader = new FileReader(file);
					BufferedReader bufferReader = new BufferedReader(fileReader)) {
				while ((line = bufferReader.readLine()) != null) {
					++representingLineNumberOfFile;
					if (findAndReplaceTexts.size() == 1) {
						FindAndReplaceTextModel findReplaceTextModel = findAndReplaceTexts.get(0);

						Pattern pattern = Pattern.compile(findReplaceTextModel.getFindText());
						Matcher matcher = pattern.matcher(line);
						if (matcher.find()) {
							LOGGER.info("The searchContent = " + findReplaceTextModel.getFindText()
									+ " is found in lineNumber=" + representingLineNumberOfFile + " in this file= "
									+ file.getName() + " and file Path = " + file.getCanonicalPath());
							SearchResult searchResult = new SearchResult(file.getCanonicalPath(), findReplaceTextModel);
							searchResults.add(searchResult);
							System.out.println(searchResult.getFilePath() + "," + findReplaceTextModel.getFindText()
									+ "," + findReplaceTextModel.getReplaceText());
							break;

						}
					} else {

						for (FindAndReplaceTextModel findReplaceTextModel : findAndReplaceTexts) {
							Pattern r = Pattern.compile(findReplaceTextModel.getFindText());
							Matcher m = r.matcher(line);
							if (m.find()) {
								LOGGER.info("The searchContent = " + findReplaceTextModel.getFindText()
										+ " is found in lineNumber=" + representingLineNumberOfFile + " in this file= "
										+ file.getName() + " and file Path = " + file.getCanonicalPath());
								SearchResult searchResult = new SearchResult(file.getCanonicalPath(),
										findReplaceTextModel);
								searchResults.add(searchResult);
								System.out.println(searchResult.getFilePath() + "," + findReplaceTextModel.getFindText()
										+ "," + findReplaceTextModel.getReplaceText());

							}
						}
					}
				}
			}

		} else {
			LOGGER.info("Read permission is not allowed for this file= " + file.getName() + " and file path= "
					+ file.getCanonicalPath());
		}
		LOGGER.info("Exiting method searchTextInTextBasedFile()::SearchTextInFileThread");

	}
}
