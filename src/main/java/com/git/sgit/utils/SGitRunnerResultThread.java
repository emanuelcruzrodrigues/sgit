package com.git.sgit.utils;

import java.io.InputStream;

public class SGitRunnerResultThread extends Thread{
	
	private final InputStream inputStream;
	private final StringBuilder result;
	
	public SGitRunnerResultThread(InputStream inputStream) {
		super();
		this.inputStream = inputStream;
		this.result = new StringBuilder();
	}

	@Override
	public void run() {
		
		try {
			int c;
			while((c = inputStream.read()) != -1) {
				char character = (char)c;
				result.append(character);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getResult() {
		return result.toString();
	}

}