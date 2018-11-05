package pdi

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword
import com.mysql.jdbc.Connection
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable

public class Database {
	private static Connection connection = null;
	//Declare Database connection properties
	private String db_ServerName 	= "127.0.0.1"
	private String db_Port			= '3306'
	private String db_Name			= 'TSQL2012'
	private String db_Username		= 'thangctran'
	private String db_Password		= 'Kms@2018'
	
	/*
	 * Close database connection
	 */
	private closeConnection(){
		if(connection != null && !connection.isClosed()){
			connection.close();
		}
		connection = null;
	}
	
//	/**
//	 * Open and return a connection to database
//	 * @param dataFile absolute file path
//	 * @return an instance of java.sql.Connection
//	 */
//	
//	@Keyword
//	def connectDB(String dataFile){
//		//Load driver class for your specific database type
//		Class.forName("oracle.jdbc.driver.OracleDriver")
//		String connectionString = "jdbc:sqlite:" + dataFile
//		if(connection != null && !connection.isClosed()){
//			connection.close()
//		}
//		connection = DriverManager.getConnection(connectionString)
//		return connection.createStatement();
//	}
	
	/**
	 * Initial database connection and statement
	 */
	private Statement initStatement(){
		Class.forName("com.mysql.jdbc.Driver");
		closeConnection();
		String conString = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", db_ServerName, db_Port, db_Name)
		connection = DriverManager.getConnection(conString, db_Username, db_Password);
		return connection.createStatement();
	}
	
	/**
	 * execute a SQL query on database (usually INSERT/UPDATE/DELETE/COUNT/SUM...)
	 * @param queryString: SQL query string
	 * @return a reference to returned data collection, an instance of java.sql.ResultSet
	 */
	@Keyword
	def executeQuery(String queryString) {
		Statement stm = initStatement();
		ResultSet rs = stm.executeQuery(queryString)
		return rs
	}
	
	/**
	 * Get total row count of a query
	 * @param: rs: The Result set of a query
	 * @return total row number
	 */
	@Keyword
	def getRowCount(ResultSet rs){
		rs.last()
		return rs.getRow()
	}
	
	/**
	 * Get the string value at row index of the column label in result set
	 * @param rs: The Result set of a query
	 * @param columnLabel: The label for the column specified
	 * @param rowIndex: The expected row index of column label
	 * @return The string value of the column label at row index
	 */
	@Keyword
	def getCellValue(ResultSet rs, String columnLabel, int rowIndex){					
		//Move the cursor to rowIndex 		
		rs.absolute(rowIndex)
		
		//Return string value of columnLabel at rowIndex
		return String.valueOf(rs.getObject(columnLabel))
	}
	
	/**
	 * Verify the string value at row index of the column label in result set
	 * @param rs: The Result set of a query
	 * @param columnLabel: The label for the column specified
	 * @param rowIndex: The expected row index of column label
	 * @param expectedValue: The expected value to verify
	 */
	@Keyword
	def verifyCellValue(ResultSet rs, String columnLabel, int rowIndex, String expectedValue){
		String currentValue = getCellValue(rs, columnLabel, rowIndex)
		WebUI.verifyEqual(currentValue, expectedValue)
	}
}
