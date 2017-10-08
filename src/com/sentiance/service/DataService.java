package com.sentiance.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipOutputStream;

public class DataService {

	final static String SPLIT_REGEX = "(?![a-zA-Z0-9]+),(?!\\d+)";
	FileService fileService = new FileService();
	BackUpService backUpService = new BackUpService();

	public void generateMasterData(final String path, final long size,
			final String input) throws IOException {

		String foldersWithSize[] = input.split(SPLIT_REGEX);

		File masterDir = new File(path);
		if (!masterDir.exists()) {
			masterDir.mkdir();
		}

		for (String eachFolderSize : foldersWithSize) {
			String[] folderAndSize = eachFolderSize.split(",");
			String folderName = folderAndSize[0];
			int folderSize = Integer.parseInt(folderAndSize[1]);

			long noOfFiles = folderSize / size;
			long finalFileSize = folderSize % size;
			File folderFullPath = new File(path + "/" + folderName);
			if (!folderFullPath.exists()) {
				folderFullPath.mkdir();
			}
			File file = null;
			for (int i = 1; i <= noOfFiles; i++) {
				file = new File(path + "/" + folderName + "/file" + i);
				fileService.checkAndCreateFile(file);
				fileService.loadFileData(file, size * 1024 * 1024);
			}

			if (!(finalFileSize == 0)) {
				file = new File(path + "/" + folderName + "/file"
						+ (noOfFiles + 1));
				fileService.checkAndCreateFile(file);
				fileService.loadFileData(file, finalFileSize * 1024 * 1024);
			}

		}

	}

	public void updateMasterData(String path, String input) {

		File masterDir = new File(path);
		if (!masterDir.exists()) {
			System.out.println("Master Dataset doesn't exists, please check the path.");
			System.exit(0);
		}

		String foldersWithSize[] = input.split(SPLIT_REGEX);
		for (String eachFolderSize : foldersWithSize) {
			String[] folderAndSize = eachFolderSize.split(",");
			String folderName = folderAndSize[0];
			int size = Integer.parseInt(folderAndSize[1]);

			File file = new File(path + "/" + folderName);
			if (!file.exists()) {
				System.out.println("Folder doesn't exists : " + path + "/"
						+ folderName);
			} else {
				File files[] = file.listFiles();
				int noOfFiles = files.length;
				long fileSize = (size / noOfFiles) * 1024 * 1024;

				Arrays.stream(files).forEach(
						x -> fileService.loadFileData(x, fileSize));

			}
		}

	}

	public void zipMasterData(String master, String backup)
			throws FileNotFoundException {

		File masterDir = new File(master);
		if (!masterDir.exists()) {
			System.out.println("Master Dataset doesn't exists, please check the path.");
			return ;
		}

		FileOutputStream fileOut = new FileOutputStream(backup);
		ZipOutputStream zipOut = new ZipOutputStream(fileOut);
		try {
			if (masterDir.isFile()) {

				backUpService.zipFile(masterDir, "", zipOut);

			} else if (masterDir.isDirectory()) {

				backUpService.zipFolder(zipOut, masterDir,"", backup);
				
			}
			zipOut.flush();
		} catch (Exception e) {
			System.out.println("Error while taking backup : " + master);
		}finally{
			try {
				zipOut.close();
				fileOut.close();
			} catch (IOException e) {
				System.out.println("Error while closing zipOut : " + e.getMessage());
			}
			
		}

	}

}
