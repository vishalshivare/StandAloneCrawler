
package org.ghc;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ghc.thread.SearchTextInFileThread;
import org.ghc.utility.CommonFileUtility;

public class FindAndReplaceThreadPool {
	private static final Logger LOGGER = Logger.getLogger(FindAndReplaceThreadPool.class);

	public void startCrawlingInRootDir(String rootDirectory) {

		LOGGER.info("Entering method startCrawlingInRootDir()::FindAndReplaceThreadPool");
		try {

			List<File> files = CommonFileUtility.getAllFilesFromRootDirectoryAccordingToFileExtensions(rootDirectory);
			if (files.size() == 0) {
				LOGGER.info("There is no file present in rootDirectory= " + rootDirectory
						+ " in method startCrawlingInRootDir()::FindAndReplaceThreadPool");
				throw new RuntimeException("There is no file present in rootDirectory= " + rootDirectory
						+ " to process ,please choose valid root directory");
			}

			int noOfThreads = 500;
			if (files.size() < noOfThreads) {
				noOfThreads = files.size();
			}
			LOGGER.info("The total No. of thread created =" + noOfThreads + " to  process total no. of file="
					+ files.size() + " in startCrawlingInRootDir()::FindAndReplaceThreadPool");
			ExecutorService threadExecutor = Executors.newFixedThreadPool(noOfThreads);
			for (File file : files) {

				SearchTextInFileThread searchTextInFileThread = new SearchTextInFileThread(file);
				threadExecutor.execute(searchTextInFileThread);

			}
			LOGGER.info("Exiting method startCrawlingInRootDir()::FindAndReplaceThreadPool");

			threadExecutor.shutdown();

			if (!threadExecutor.awaitTermination(950, TimeUnit.MILLISECONDS)) {
				threadExecutor.shutdownNow();
			}

		} catch (Exception exception) {
			LOGGER.error("Exception occured in startCrawlingInRootDir()::FindAndReplaceThreadPool due to="
					+ exception.getMessage(), exception);
		}
	}

}
