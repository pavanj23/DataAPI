package com.sentiance.service;

import java.util.Random;

public class RandomDataService {
	
	final char[] aplhaNumeric = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F',
			'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	final static int RANDOM_STRING_LENGTH = 100;
	
	public String generateRandomString(){
		
		Random random = new Random();
		
		int strLength = random.nextInt(RANDOM_STRING_LENGTH)+1;
		
		StringBuilder builder = new StringBuilder();
		
		for(int i =0;i<strLength;i++){
		
			builder.append(aplhaNumeric[random.nextInt(36)]);
			
		}
		
		return builder.toString();
	}
	
}
