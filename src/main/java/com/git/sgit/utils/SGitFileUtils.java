package com.git.sgit.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SGitFileUtils {
	
	public static boolean isGitRepository(File directory) {
		if (!directory.isDirectory()) return false;
		
		if (directory.getName().endsWith(".git")) return true;
		
		File gitFolder = getGitDirectory(directory);
		return gitFolder != null;
	}

	public static File getGitDirectory(File directory) {
		if (!directory.isDirectory()) return null;
		
		File[] files = directory.listFiles();
		if (files == null || files.length == 0) return null;
		
		return Arrays.asList(files).stream().filter(f -> f.getName().equals(".git")).findFirst().orElse(null);
	}
	
	public static List<File> getGitRepositories(File directory) {
		
		List<File> result = new ArrayList<>();
		if (!directory.isDirectory()) return result;
		
		for (File subDirectory : directory.listFiles()) {
			if (isGitRepository(subDirectory)) {
				result.add(subDirectory);
			}
		}
		
		return result;
	}
	
}
