package com.git.sgit.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.commons.lang3.SystemUtils;

public class SGitRunner {

	public String run(File repository, String command) throws Exception {

			String start = getStartCommand();
			
			ProcessBuilder processBuilder = new ProcessBuilder(start);
			processBuilder.redirectErrorStream(true);
			
			Process process = processBuilder.start();
			
			SGitRunnerResultThread resultThread = new SGitRunnerResultThread(process.getInputStream());
			resultThread.start();
			
			try(OutputStream outputStream = process.getOutputStream()){
				
				BufferedWriter prompt = new BufferedWriter(new OutputStreamWriter(outputStream));
				
				run(prompt, "cd " + repository.getAbsolutePath());
				run(prompt, command);
				run(prompt, "exit");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			process.waitFor();
			Thread.sleep(100);
			return resultThread.getResult();


	}

	private String getStartCommand() {
		if (SystemUtils.IS_OS_WINDOWS) return "cmd";
		if (SystemUtils.IS_OS_LINUX) return "/bin/bash";
		throw new RuntimeException("Your OS is not supported");
	}

	private void run(BufferedWriter prompt, String command) throws IOException {
		prompt.write(command);
		prompt.newLine();
		prompt.flush();
	}

}
