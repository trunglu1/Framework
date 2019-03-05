package pdi

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import com.kms.katalon.core.annotation.Keyword
import java.sql.Connection
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable
import com.kms.katalon.core.testdata.TestData
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import java.sql.PreparedStatement;
import java.sql.Driver;
import com.kms.katalon.core.configuration.RunConfiguration
import org.apache.commons.io.FileUtils
import org.apache.commons.lang.StringUtils
import org.apache.commons.lang3.ArrayUtils

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
	private String db_ServerName 	= "localhost\\SQLEXPRESS"
	private String db_Port			= '1433'
	private String db_Name			= 'TSQL2012'
	private String db_Username		= 'test'
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
		String conString = String.format("jdbc:sqlserver:%s:%s;databaseName=%s;user=%s;password=%s", db_ServerName, db_Port, db_Name, db_Username, db_Password)
		connection = DriverManager.getConnection(conString);
		return connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	/******************************************************
	 * Execute a SQL query on database (usually INSERT/UPDATE/DELETE/COUNT/SUM...)
	 * @param queryString: SQL query string
	 * @return A reference to returned data collection, an instance of java.sql.ResultSet
	 */
	@Keyword
	def executeQuery1(String queryString) {
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
	def getRowCount1(ResultSet rs){
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
	def getCellValue1(ResultSet rs, String columnLabel, int rowIndex){
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
		String currentValue = getCellValue1(rs, columnLabel, rowIndex)
		WebUI.verifyEqual(currentValue, expectedValue)
	}

	/******************************************************
	 * Get the list string values at the column label in result set
	 * @param rs: The Result set of a query
	 * @param columnLabel: The label for the column specified
	 * @return The list string values of the column label
	 */
	@Keyword
	def getListValuesAtColumn1(ResultSet rs, String columnLabel){
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
	def verifyCellValue1(ResultSet rs, String columnLabel, String expectedValues){
		def currentValues = getListValuesAtColumn1(rs, columnLabel)
		WebUI.verifyEqual(currentValues.toString(), expectedValues.toString())
	}

	/******************************************************
	 * Execute a SQL query on database (usually INSERT/UPDATE/DELETE/COUNT/SUM...)
	 * @param queryString: SQL query string
	 * @return A reference to returned test data collection
	 */
	@Keyword
	def executeQuery(String queryString){
		File sourceFile = new File(RunConfiguration.getProjectDir() + "/Data Files/Order.dat")
		String contentFile = FileUtils.readFileToString(sourceFile, "utf-8")
		String oldQuery = StringUtils.substringBetween(contentFile, "<query>", "</query>")
		String newContentFile = contentFile.replace("<query>" + oldQuery + "</query>", "<query>" + queryString + "</query>")
		FileUtils.writeStringToFile(sourceFile, newContentFile, "utf-8", false)
		TestData _testData = findTestData('Order')
		return _testData
	}

	/******************************************************
	 * Get the string value at row index of the column label in result set
	 * @param queryString: SQL query string
	 * @param column: The label or index for the column specified
	 * @param rowIndex: The row index
	 * @return The string value of the cell
	 */
	@Keyword
	def getCellValue(String queryString, def column, int rowIndex){
		TestData _getResult = executeQuery(queryString)
		int columnIndex = 0
		if (column instanceof String) {
			columnIndex = ArrayUtils.indexOf(_getResult.getColumnNames(), column) + 1
		} else columnIndex = column
		return _getResult.getObjectValue(columnIndex, rowIndex).toString()
	}

	/******************************************************
	 * Get the map string values at row index
	 * @param queryString: SQL query string
	 * @param rowIndex: The row index
	 * @return The list string values at row index
	 */
	@Keyword
	def getDataRow(String queryString, int rowIndex){
		TestData _getResult = executeQuery(queryString)
		def _dataDict = [:]
		String[] headerNames = _getResult.getColumnNames()
		for(int i = 0; i < headerNames.size() ; i++){
			_dataDict[headerNames[i]] = _getResult.getObjectValue(i + 1, rowIndex).toString()
		}
		return _dataDict
	}


	/******************************************************
	 * Get the list string values at the column
	 * @param queryString: SQL query string
	 * @param column: The label or index for the column specified
	 * @return The list string values at the column
	 */
	@Keyword
	def getDataColumn(String queryString, def column){
		TestData _getResult = executeQuery(queryString)
		def _listValues = []
		int columnIndex = 0
		if (column instanceof String) {
			columnIndex = ArrayUtils.indexOf(_getResult.getColumnNames(), column) + 1
		} else columnIndex = column
		int totalRow = _getResult.getRowNumbers()
		for(int i = 1; i <= totalRow ; i++) {
			_listValues.add(_getResult.getObjectValue(columnIndex, i).toString())
		}
		return _listValues
	}
}