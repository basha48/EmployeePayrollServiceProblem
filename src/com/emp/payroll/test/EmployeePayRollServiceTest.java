package com.emp.payroll.test;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.emp.payroll.EmployeePayRollData;
import com.emp.payroll.EmployeePayRollService;
import com.emp.payroll.EmployeePayRollService.IOService;

public class EmployeePayRollServiceTest {
	
	@Test
	public void givenThreeEmployeesWritenToFile() {
	EmployeePayRollData [] empdata = {
			new EmployeePayRollData(1,"Modi",1000),
			new EmployeePayRollData(2,"Sachin",1000),
			new EmployeePayRollData(3,"Kohli",1000),
	};
	EmployeePayRollService employeePayRollService;
	
	employeePayRollService = new EmployeePayRollService(Arrays.asList(empdata));
	employeePayRollService.writeEmployeePayRollData(IOService.FILE_IO);
	employeePayRollService.printData(IOService.FILE_IO);
	long entries = employeePayRollService.countEntries(IOService.FILE_IO);
	Assert.assertEquals(3,entries);
	
	}
	
}
