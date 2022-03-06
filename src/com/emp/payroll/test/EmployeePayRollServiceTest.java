package com.emp.payroll.test;

import java.util.Arrays;
import java.util.List;

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
	
	@Test
	public void givenEmployeePayrollInDB_when_Retrieved_shouldMatchEmployeeCount() {
		EmployeePayRollService employeePayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayRollData = employeePayRollService.readEmployeePayroll(IOService.DB_IO);
		Assert.assertEquals(5,empPayRollData.size());
	}
	
	@Test
	public void givenNewSalaryForEmployeeWhenUpdatedShouldMatch() {
		EmployeePayRollService employeePayRollService = new EmployeePayRollService();
		List<EmployeePayRollData> empPayRollData = employeePayRollService.readEmployeePayroll(IOService.DB_IO);
		employeePayRollService.updateEmployeeSalary("Terisa",30000.00);
		boolean result = employeePayRollService.checkEmployeePayRollSyncWithDataBase("Terisa");
		Assert.assertTrue(result);
	}
    
	
}
