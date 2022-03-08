package com.emp.payroll;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		return this.getEmployeePayRollDataUsingDB(sql);
	
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

	public List<EmployeePayRollData> getEmployeePayRollForDateRange(LocalDate startDate, LocalDate endDate) {
	
		String sql = String.format("select * from employee_payroll where StartDate between '%s' AND '%s';", Date.valueOf(startDate) , Date.valueOf(endDate));
	
		return this.getEmployeePayRollDataUsingDB(sql);
	}

	private List<EmployeePayRollData> getEmployeePayRollDataUsingDB(String sql) {
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

	public Map<String, Double> getAvgSalaryByGender() {
		String sql = "select gender,avg(BasicPay) as avgsalary from employee_payroll group by gender";
		Map<String, Double> genderToAvgSalaryMap = new HashMap<>();
		try {
			Connection con = this.getConnection();
			java.sql.Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while(result.next()) {
				String gender = result.getString("gender");
				double salary = result.getDouble("avgsalary");
				genderToAvgSalaryMap.put(gender, salary);
				
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return genderToAvgSalaryMap;
	}

	public EmployeePayRollData addEmployeePayRollUC7(String name, double salary, LocalDate startDate, String gender) {
		int employeeId = -1;
		EmployeePayRollData employeePayRollData = null;
		String sql = String.format("Insert into employee_payroll (name,gender, BasicPay,startDate) values" +
						"('%s','%s',%s,'%s')",name,gender,salary,Date.valueOf(startDate));
		try {
			Connection con = this.getConnection();
			java.sql.Statement stmt = con.createStatement();
			int rowAffected = stmt.executeUpdate(sql,stmt.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet resultSet = stmt.getGeneratedKeys();
				if(resultSet.next()) {
					employeeId = resultSet.getInt(1);
				}
			}
			employeePayRollData = new EmployeePayRollData(employeeId,name,salary,startDate);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employeePayRollData;
	}

	public EmployeePayRollData addEmployeePayRoll(String name, double salary, LocalDate startDate, String gender) {
		Connection connection = null;
		try {
			connection = this.getConnection();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int employeeId = -1;
		EmployeePayRollData employeePayRollData = null;
		String sql = String.format("Insert into employee_payroll (name,gender, BasicPay,startDate) values" +
						"('%s','%s',%s,'%s')",name,gender,salary,Date.valueOf(startDate));
		try {
			 
			java.sql.Statement stmt = connection.createStatement();
			connection.setAutoCommit(false);
			int rowAffected = stmt.executeUpdate(sql,stmt.RETURN_GENERATED_KEYS);
			if(rowAffected == 1) {
				ResultSet resultSet = stmt.getGeneratedKeys();
				if(resultSet.next()) {
					employeeId = resultSet.getInt(1);
				}
			}
			employeePayRollData = new EmployeePayRollData(employeeId,name,salary,startDate);
		}
		catch (SQLException e) {
			try {
				connection.rollback();
				return employeePayRollData;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			java.sql.Statement stmt = connection.createStatement();
			
			double deduction = salary * 0.2;
			double taxable_pay = salary - deduction;
			double tax = taxable_pay * 0.1;
			double netPay =  salary - tax;
			String sqlQuery = String.format("Insert into payroll_details (employee_id,basic_pay, deductions,taxable_pay,tax,net_pay) values" +
					"(%s,%s,%s,%s,%s,%s)",employeeId,salary,deduction,taxable_pay,tax,netPay);
			
			int rowAffected = stmt.executeUpdate(sqlQuery);
			if(rowAffected == 1) {
				employeePayRollData = new EmployeePayRollData(employeeId,name,salary,startDate);
			}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return employeePayRollData;
	}
}
