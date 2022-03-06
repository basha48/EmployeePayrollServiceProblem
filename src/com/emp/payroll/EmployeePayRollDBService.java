package com.emp.payroll;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

public class EmployeePayRollDBService {
	private PreparedStatement employeepayRollDataStatement;
	private static EmployeePayRollDBService employeePayRollDBService;
	
	private EmployeePayRollDBService() {
		
	}
	
	public static EmployeePayRollDBService getInstance() {
		if(employeePayRollDBService == null)
			employeePayRollDBService = new EmployeePayRollDBService();
		return employeePayRollDBService;
		
	}
	
	private Connection getConnection() throws SQLException {
		Connection connection;
		String jdbcUrl = "jdbc:mysql://localhost:3306/payroll_service?useSSL=false";
		String userName = "root";
		String Password = "Basha@1997";
		connection = DriverManager.getConnection(jdbcUrl, userName, Password);
		System.out.println("connection is successfull" + connection);
		
		// TODO Auto-generated method stub
		return connection;
	}

	public List<EmployeePayRollData> readData() {
		String sql = "select * from employee_payroll";
		List<EmployeePayRollData> employeePayROllData = new ArrayList<>();
		try {
			Connection con = this.getConnection();
			java.sql.Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			employeePayROllData = this.getEmployeePayRollData(result);
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employeePayROllData;
	}

	public int updateEmployeeData(String name, double salary) {
		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) {
		int result = 0;
		String sql = String.format("update employee_payroll set BasicPay = %.2f where name = '%s'",salary,name);
		try {
			Connection con = this.getConnection();
			java.sql.Statement stmt = con.createStatement();
			 result = stmt.executeUpdate(sql);
			
			}
		  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public List<EmployeePayRollData> getEmployeePayRollData(String name) {
		List<EmployeePayRollData> employeePayROllData =null;
		if(this.employeepayRollDataStatement == null)	
	      this.prepareStatementForEmployeeData();
		try {
			employeepayRollDataStatement.setString(1, name);
			ResultSet resultSet = employeepayRollDataStatement.executeQuery();
			employeePayROllData = this.getEmployeePayRollData(resultSet);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return employeePayROllData;
	}

	private List<EmployeePayRollData> getEmployeePayRollData(ResultSet result) {
		List<EmployeePayRollData> employeePayROllData = new ArrayList<>();
		try{
			while(result.next()) {
		
			int id = result.getInt("id");
			String name = result.getString("name");
			double salary =result.getDouble("BasicPay");
			LocalDate startDate = result.getDate("StartDate").toLocalDate();
			employeePayROllData.add( new EmployeePayRollData(id,name,salary,startDate));
		}
		}
			catch(SQLException e) {
				e.printStackTrace();
			}
		 return employeePayROllData;
	}

	private void prepareStatementForEmployeeData() {
		try {
			Connection con = this.getConnection();
			String sql = "select * from employee_payroll where name = ?";
			employeepayRollDataStatement =  con.prepareStatement(sql);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	

}
