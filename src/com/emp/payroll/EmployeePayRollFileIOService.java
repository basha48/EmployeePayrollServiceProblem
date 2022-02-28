package com.emp.payroll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EmployeePayRollFileIOService {
	public static String PAY_ROLL_FILE = "C:\\Users\\basha\\eclipse-workspace\\IoStreams\\src\\TextFolder\\empInfo.txt";

	public void writeData(List<EmployeePayRollData> employeePayRollList) {
		StringBuffer empBuffer = new StringBuffer();
		employeePayRollList.forEach(employee ->{
			String empDataString = employee.toString().concat("\n");
			empBuffer.append(empDataString);
			
		});
		try {
			Files.write(Paths.get(PAY_ROLL_FILE), empBuffer.toString().getBytes());
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}

	public void printData() {
		try {
			Files.lines(new File(PAY_ROLL_FILE).toPath()).forEach(System.out::println);
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}
		
	}

	public long countEntries() {
		long entries  = 0;
		try {
			entries = Files.lines(new File(PAY_ROLL_FILE).toPath()).count();
		}
		catch(IOException e) {
			e.printStackTrace();
			
		}
		return entries;
		
	} 
	
}
