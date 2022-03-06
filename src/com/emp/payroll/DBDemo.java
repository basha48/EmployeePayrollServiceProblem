package com.emp.payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Enumeration;

import com.mysql.cj.jdbc.Driver;
public class DBDemo {
	
	public static void main(String[] args) {
	String jdbcUrl = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
	String userName = "root";
	String Password = "Basha@1997";
	Connection connection;
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Driver is loaded");
		
	}
	catch(Exception ex) {
		throw new IllegalStateException("Cannot found driver in class path",ex);
	}
	
	listDrivers();
	try {
		System.out.println("connectiong to database:" +jdbcUrl);
		connection = DriverManager.getConnection(jdbcUrl, userName, Password);
		System.out.println("connection is successfull" + connection);
		
	}
	catch(Exception ex) {
		ex.printStackTrace();
		
	}
	
	}

	private static void listDrivers() {
		Enumeration<java.sql.Driver> driverList = DriverManager.getDrivers();
		while(driverList.hasMoreElements()) {
			Driver driverClass 	= (Driver) driverList.nextElement();
			System.out.println(" " + driverClass.getClass().getName());
		}
		
	}  
}
