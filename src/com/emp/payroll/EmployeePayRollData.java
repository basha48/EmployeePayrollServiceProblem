package com.emp.payroll;

public class EmployeePayRollData {
	public int id;
	public String name;
	public double salary;
	
	public EmployeePayRollData(int id, String name, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "EmployeePayRollData [id=" + id + ", name=" + name + ", salary=" + salary + "]";
	}
	
	

}
