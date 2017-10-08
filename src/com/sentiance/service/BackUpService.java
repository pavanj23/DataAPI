package com.sentiance.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class BackUpService {

	public void zipFile(File file, String name, ZipOutputStream zipOut)
			throws IOException {

		FileInputStream fileIO = null;
		try  {
			fileIO = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(name + file.getName());
			zipOut.putNextEntry(zipEntry);

			byte[] buf = new byte[1024];
			int bytes;

			while ((bytes = fileIO.read(buf)) > 0) {
				zipOut.write(buf, 0, bytes);
			}
			zipOut.closeEntry();
			System.out.println("File : " + file + ", Zipped ");
		} catch (Exception e) {
			System.out.println("Error during ZipFile : "+ e.getMessage());
		}finally{
			fileIO.close();
		}
	}

	public void zipFolder(ZipOutputStream zipOut, File masterDir,String folderName, String name) throws IOException {
		
		String fileName = name+"/" + masterDir.getName() + "/";
		ZipEntry folderZip = new ZipEntry(fileName);
		zipOut.putNextEntry(folderZip);
		
		File listOfFiles[] = masterDir.listFiles();

		Arrays.stream(listOfFiles).forEach(x -> {
			if (x.isFile()) {
				try {
					zipFile(x, fileName, zipOut);
				} catch (Exception e) {
					System.out.println("Error during file zip : " + e.getMessage());
				}
			} else if (x.isDirectory()) {
				try {
					zipFolder(zipOut, x,x.getName(), name);
				} catch (Exception e) {
					System.out.println("Error during folder zip : " + e.getMessage());
				}
			}
		});
		zipOut.closeEntry();
	}
	
	

}
