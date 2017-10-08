package com.sentiance.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileService {

	RandomDataService randomService = new RandomDataService();

	public void loadFileData(final File path, final long fileSize) {

		int localFileSize = 0;
		long filesizebytes = fileSize;

		StringBuilder builder = new StringBuilder();

		while (localFileSize <= filesizebytes) {

			final String randomString = randomService.generateRandomString();

			localFileSize += randomString.length();

			builder.append(randomString);
			builder.append(System.getProperty("line.separator"));

		}

		try {
			System.out.println("Processng file : " + path.getAbsolutePath());
			Files.write(Paths.get(path.getPath()), builder.toString()
					.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Error while writing to file : "
					+ e.getMessage());
		}

	}

	public void checkAndCreateFile(File file) throws IOException {

		if (!file.exists()) {
			file.createNewFile();
		}

	}

}
