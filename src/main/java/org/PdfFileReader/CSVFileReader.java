package org.PdfFileReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVFileReader {

	public static void main(String args[]) {
		try {
			readCSVFile();
			showCSVFileContent();
			String fileExts[] = convertToStringArray();
			for (String st : fileExts) {
				System.out.println("file ext=" + st);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void showCSVFileContent() {
		for (String st : fileExtensions) {
			System.out.println("The key=" + st);
			List<SearchAndReplace> list = fileExtensionMaptoSearchAndReplaceContent.get(st);
			for (SearchAndReplace pojo : list) {
				System.out.println("find content= " + pojo.getFindText() + " replaceText= " + pojo.getReplaceText());
			}
		}

	}

	private static final String[] FILE_HEADER = { "FILE_EXTENSION", "FIND_CONTENT", "REPLACE_CONTENT" };

	private static ConcurrentHashMap<String, List<SearchAndReplace>> fileExtensionMaptoSearchAndReplaceContent = new ConcurrentHashMap<String, List<SearchAndReplace>>();
	private static HashSet<String> fileExtensions = new HashSet<String>();

	public static void readCSVFile() throws IOException {
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER);

		fileReader = new FileReader("C:\\Users\\pitambarm\\Desktop\\multiple_searchOption_config.csv");
		csvFileParser = new CSVParser(fileReader, csvFileFormat);
		List<CSVRecord> csvRecords = csvFileParser.getRecords();

		for (CSVRecord record : csvRecords.subList(1, csvRecords.size())) {

			fileExtensions.add(record.get(FILE_HEADER[0]));

			if (fileExtensionMaptoSearchAndReplaceContent.get(record.get(FILE_HEADER[0])) == null) {
				SearchAndReplace searchAndReplace = new SearchAndReplace(record.get(FILE_HEADER[1]),
						record.get(FILE_HEADER[2]));
				List<SearchAndReplace> list = new ArrayList<SearchAndReplace>();
				list.add(searchAndReplace);
				fileExtensionMaptoSearchAndReplaceContent.put(record.get(FILE_HEADER[0]), list);

			} else {
				SearchAndReplace searchAndReplace = new SearchAndReplace(record.get(FILE_HEADER[1]),
						record.get(FILE_HEADER[2]));
				fileExtensionMaptoSearchAndReplaceContent.get(record.get(FILE_HEADER[0])).add(searchAndReplace);
			}

		}

	}

	public static String[] convertToStringArray() {
		String st[] = new String[fileExtensions.size()];
		fileExtensions.toArray(st);
		return st;

	}

}
