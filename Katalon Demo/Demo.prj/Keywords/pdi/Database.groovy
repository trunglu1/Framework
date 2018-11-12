package pdi

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword
import java.sql.Connection
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable

import java.sql.PreparedStatement;
import java.sql.Driver;

/********************************* HEADER PART **********************************
 *
 * CUSTOM KEYWORDS NAME		: Databse.groovy
 * LAST UPDATED     		: Nov 19, 2018
 *
 * CUSTOM KEYWORDS LIST
 *
 *	closeConnection(): Close database connection
 *	initStatement(): Initial database connection and statement
 *	executeQuery(String queryString): Execute a SQL query on database
 *	getRowCount(ResultSet rs): Get total row count of a query
 *	getCellValue(ResultSet rs, String columnLabel, int rowIndex): Get the string value at row index of the column label in result set
 *	verifyCellValue(ResultSet rs, String columnLabel, int rowIndex, String expectedValue): Verify the string value at row index of the column label in result set
 *	getListValuesAtColumn(ResultSet rs, String columnLabel): Get the list string values at the column label in result set
 *	verifyCellValue(ResultSet rs, String columnLabel, String expectedValues): Verify the list string values at the column label in result set
 */

public class Database {
	//Declare Database connection properties
	private static Connection connection = null;
	private String db_ServerName 	= ".\\SQLEXPRESS"
	private String db_Port			= '1433'
	private String db_Name			= 'TSQL2012'
	private String db_Username		= 'demo'
	private String db_Password		= 'Kms@2018'

	/******************************************************
	 * Close database connection
	 */
	private closeConnection(){
		if(connection != null && !connection.isClosed()){
			connection.close();
		}
		connection = null;
	}

	/******************************************************
	 * Initial database connection and statement
	 */
	private Statement initStatement(){
		//		Class.forName("com.mysql.jdbc.Driver");
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
		closeConnection();
		String conString = String.format("jdbc:sqlserver://%s:%s;databaseName=%s;user=%s;password=%s", db_ServerName, db_Port, db_Name, db_Username, db_Password)
		connection = DriverManager.getConnection(conString);
		return connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	/******************************************************
	 * Execute a SQL query on database (usually INSERT/UPDATE/DELETE/COUNT/SUM...)
	 * @param queryString: SQL query string
	 * @return A reference to returned data collection, an instance of java.sql.ResultSet
	 */
	@Keyword
	def executeQuery(String queryString) {
		Statement stm = initStatement();
		ResultSet rs = stm.executeQuery(queryString)
		return rs
	}

	/******************************************************
	 * Get total row count of a query
	 * @param: rs: The Result set of a query
	 * @return Total row number
	 */
	@Keyword
	def getRowCount(ResultSet rs){
		rs.last()
		int returnRow = rs.getRow()
		return returnRow
	}

	/******************************************************
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

	/******************************************************
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

	/******************************************************
	 * Get the list string values at the column label in result set
	 * @param rs: The Result set of a query
	 * @param columnLabel: The label for the column specified
	 * @return The list string values of the column label
	 */
	@Keyword
	def getListValuesAtColumn(ResultSet rs, String columnLabel){
		def _listValues = []
		while (rs.next()) {
			_listValues.add(String.valueOf(rs.getObject(columnLabel)))
		}
		return _listValues
	}

	/******************************************************
	 * Verify the list string values at the column label in result set
	 * @param rs: The Result set of a query
	 * @param columnLabel: The label for the column specified
	 * @param expectedValues: The expected values to verify
	 */
	@Keyword
	def verifyCellValue(ResultSet rs, String columnLabel, String expectedValues){
		def currentValues = getListValuesAtColumn(rs, columnLabel)
		WebUI.verifyEqual(currentValues.toString(), expectedValues.toString())
	}
}