package org.ghc.utility;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.ghc.model.FindAndReplaceTextModel;

public class CSVConfigFileReader {
	private static final Logger LOGGER = Logger.getLogger(CSVConfigFileReader.class);

	private static final String[] FILE_HEADER = { "FILE_EXTENSION", "FIND_CONTENT", "REPLACE_CONTENT" };
	public static ConcurrentHashMap<String, List<FindAndReplaceTextModel>> fileExtensionMaptoSearchAndReplaceContent = new ConcurrentHashMap<String, List<FindAndReplaceTextModel>>();
	public static HashSet<String> fileExtensions = new HashSet<String>();

	public static void readCSVFile() {
		LOGGER.info("Entering method readCSVFile()::CSVConfigFileReader");

		try (FileReader fileReader = new FileReader(".\\config\\multiple_searchOption_config.csv");
				CSVParser csvFileParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withHeader(FILE_HEADER))) {

			List<CSVRecord> csvRecords = csvFileParser.getRecords();

			for (CSVRecord record : csvRecords.subList(1, csvRecords.size())) {

				fileExtensions.add(record.get(FILE_HEADER[0]));

				if (fileExtensionMaptoSearchAndReplaceContent.get(record.get(FILE_HEADER[0])) == null) {
					FindAndReplaceTextModel searchAndReplace = new FindAndReplaceTextModel(record.get(FILE_HEADER[1]),
							record.get(FILE_HEADER[2]));
					List<FindAndReplaceTextModel> list = new ArrayList<FindAndReplaceTextModel>();
					list.add(searchAndReplace);
					fileExtensionMaptoSearchAndReplaceContent.put(record.get(FILE_HEADER[0]), list);

				} else {
					FindAndReplaceTextModel searchAndReplace = new FindAndReplaceTextModel(record.get(FILE_HEADER[1]),
							record.get(FILE_HEADER[2]));
					fileExtensionMaptoSearchAndReplaceContent.get(record.get(FILE_HEADER[0])).add(searchAndReplace);
				}

			}
			LOGGER.info("Exiting method readCSVFile()::CSVConfigFileReader");
		} catch (Exception exception) {
			LOGGER.error("Exception occered in readCSVFile()::CSVConfigFileReader and reason=" + exception.getMessage(),
					exception);
			throw new RuntimeException(
					"Either the CSV file is not present in currentDirectory or does not have access permission");
		}

	}

}
