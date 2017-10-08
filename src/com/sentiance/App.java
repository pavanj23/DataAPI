package com.sentiance;

import java.io.IOException;

import com.sentiance.service.DataService;

public class App {
	
	public static void main(String[] args) throws IOException {
		
		DataService service = new DataService();
		
		service.generateMasterData("C:/tmp/MasterDir",12L,"locations,64,sensors,138,devices,24");
		
		service.updateMasterData("C:/tmp/MasterDir","locations,12,sensors,23,devices,10");
		
		service.zipMasterData("C:/tmp/MasterDir","C:/tmp/MasterDir.zip");
		
	}

}
