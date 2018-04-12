package com.git.sgit;

import static com.git.sgit.utils.SGitFileUtils.getGitRepositories;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.git.sgit.utils.SGitRunner;

public class SGitApplication {
	
	public static void main(String[] args) throws IOException {
		File directory = new File(".");
		
		SGitApplication application = new SGitApplication();
		application.run(directory, args);
	}

	public void run(File directory, String[] args) throws IOException {
		
		Logger logger = LogManager.getLogger(SGitApplication.class);
		
		if (args.length == 0) {
			logger.info("No arguments received. Process canceled.\n");
			return;
		}
		
		
		logger.info(String.format("Workspace: %s\n", directory.getCanonicalPath()));
		
		List<File> gitRepositories = getGitRepositories(directory);
		
		if (gitRepositories.size() == 0) {
			logger.info("No git repositories found.\n");
			return;
		}
		
		logger.info("\n--------------------\n\n");
		
		for (File repository : gitRepositories) {
			
			
			try {
				
				StringBuilder command = new StringBuilder();
				command.append("git");
				for (String arg : args) {
					if (arg.indexOf(' ') > 0) {
						command.append(String.format(" \"%s\"", arg));
					}else {
						command.append(String.format(" %s", arg));
					}
				}
				
				logger.info(String.format("Command: %s\n", command));
				
				String result = new SGitRunner().run(repository, command.toString());
				logger.info(String.format("Repository: %s\n%s\n--------------------\n\n", repository.getName(), result));
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	

}
