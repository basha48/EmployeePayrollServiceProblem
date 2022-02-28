package com.emp.payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.emp.payroll.EmployeePayRollService.IOService;

public class EmployeePayRollService {
	
	public EmployeePayRollService() {
		
	};
	public enum IOService{CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
	public List<EmployeePayRollData> employeePayRollList = new ArrayList<EmployeePayRollData>();
	public EmployeePayRollService(List<EmployeePayRollData> list) {
		employeePayRollList = list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<EmployeePayRollData> employeePayRollList = new ArrayList<>();
		EmployeePayRollService empPayRollService = new EmployeePayRollService(employeePayRollList);
		Scanner consoleReader = new Scanner(System.in);
		empPayRollService.readEmployeePayRollData(consoleReader);
		empPayRollService.writeEmployeePayRollData();
		empPayRollService.writeEmployeePayRollData(IOService.FILE_IO);
	}
	
	public void readEmployeePayRollData(Scanner consoleReader) {
		System.out.println("Enter emp id :");
		int id = consoleReader.nextInt();
		System.out.println("Enter emp name :");
		String name = consoleReader.next();
		System.out.println("Enter emp salary :");
		double salary = consoleReader.nextDouble();
		employeePayRollList.add(new EmployeePayRollData(id,name,salary));
	}

	public void writeEmployeePayRollData() {
		System.out.println("writing emp pay roll to console :" + employeePayRollList);
	}

	public void writeEmployeePayRollData(IOService ioService) {
		
		if(ioService.equals(ioService.CONSOLE_IO)) {
		System.out.println("writing emp pay roll to console :" + employeePayRollList);
		}
		else if(ioService.equals(ioService.FILE_IO)) {
			new EmployeePayRollFileIOService().writeData(employeePayRollList);
			
		}
	}

	public void printData(IOService ioService) {
		
		if(ioService.equals(ioService.FILE_IO)) {
			new EmployeePayRollFileIOService().printData();
			}
		
	}

	public long countEntries(IOService ioService) {
		if(ioService.equals(ioService.FILE_IO)) {
		    return new EmployeePayRollFileIOService().countEntries();
			}
		return 0;
	}

}
