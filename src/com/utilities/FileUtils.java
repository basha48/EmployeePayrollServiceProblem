package com.utilities;

import java.io.File;

public class FileUtils {

	public static boolean deleteFiles(File contantsToDelete) {
		File [] allContants = contantsToDelete.listFiles();
		if(allContants != null) {
			for(File file : allContants) {
				deleteFiles(file);
			}
		}
		return contantsToDelete.delete();
		
	}

}
