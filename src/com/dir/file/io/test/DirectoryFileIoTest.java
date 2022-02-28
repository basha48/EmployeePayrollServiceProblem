package com.dir.file.io.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;

import com.utilities.FileUtils;

public class DirectoryFileIoTest {
	
	private static String HOME = System.getProperty("user.home");
	private static String FOLDER_NAME = "SampleData";
	
	@Test
	public void givenPathWhenCheckedThenConfirm() throws IOException {
		Path homePath = Paths.get(HOME);
		Assert.assertTrue(Files.exists(homePath));
		Path samplePath = Paths.get(HOME + "/"+FOLDER_NAME);
		if(Files.exists(samplePath)) {
			FileUtils.deleteFiles(samplePath.toFile());
		}
		Assert.assertTrue(Files.notExists(samplePath));
		
		Files.createDirectory(samplePath);
		Assert.assertTrue(Files.exists(samplePath));
		
		IntStream.range(1, 10).forEach(cntr ->{
			Path tempFile = Paths.get(samplePath +"/temp"+cntr);
			Assert.assertTrue(Files.notExists(tempFile));
			try {
				Files.createFile(tempFile);
			}
			catch(IOException e){
			    System.out.println(e.getMessage());
			}
			Assert.assertTrue(Files.exists(tempFile));
		});
		
		Files.list(samplePath).filter(Files :: isRegularFile).forEach(System.out::println);
		Files.newDirectoryStream(samplePath).forEach(System.out::println);
	}
	
	
}
