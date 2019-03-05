package pdi

import java.awt.Color
import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

/********************************* HEADER PART **********************************
 *
 * CUSTOM KEYWORDS NAME		: Utilities.groovy
 * LAST UPDATED     			: Nov 19, 2018
 *
 * CUSTOM KEYWORDS LIST
 *
 *	convertXpath(String strXpath, String dynamicValue): Convert dynamic Xpath
 *	getDataRow(TestData testData, int rowIndex): Get Data Row from Data File
 *	getUnique(String formatDate): Generate unique string format by system date
 *	getRandomInt(int min, int max): Get numeric in string
 *	parseString(String mainString, String startSub=null, String endSub=null): Parse sub string in a string
 *	verifyCountMatchs(String str, String findStr, int expectedNumber): Verify count sub string in a string
 *	verifyListValues(def listValues1, def listValues2, String operator='='): Verify two list Values
 *	getSortOrderStatus(String listValues, String dataType='string'): Get Sort Order status
 */

public class Utilities {
	@Keyword
	static void clearDriver() {
		//Get current executed browser name
		String _browser = DriverFactory.getExecutedBrowser().getName().replace('_DRIVER', '')

		switch (_browser) {
			case 'IE':
				Process _IE = Runtime.getRuntime().exec('taskkill /F /IM iexplore.exe')
				_IE.waitFor()
				Process _IED = Runtime.getRuntime().exec('taskkill /F /IM IEDriverServer.exe')
				_IED.waitFor()
				Process _ClearIECache = Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255")
				_ClearIECache.waitFor()
				break
			case 'CHROME':
				Process _Chrome = Runtime.getRuntime().exec('taskkill /F /IM chromedriver.exe')
				_Chrome.waitFor()
				break
			case 'FIREFOX':
				Process _Firefox = Runtime.getRuntime().exec('taskkill /F /IM geckodriver.exe')
				_Firefox.waitFor()
				break
			case 'EGDE':
				Process _Egde = Runtime.getRuntime().exec('taskkill /F /IM MicrosoftWebDriver.exe')
				_Egde.waitFor()
				break
		}
	}

	/******************************************************
	 * Convert dynamic Xpath
	 * @author thangctran
	 * @param strXpath : The dynamic xpath
	 * @param dynamicValue: The dynamic value to replace
	 * return: The new Xpath
	 */
	def convertXpath(String strXpath, String dynamicValue) {
		String resultXpath = strXpath
		String strTempXpath = strXpath
		if (dynamicValue != null) {
			if (dynamicValue.isNumber()) resultXpath = "(" + strTempXpath + ")[" + dynamicValue + "]"
			else {
				String findKey = ""
				String newTemp = ""
				//find dynamic keys to replace
				if (strTempXpath.indexOf("//@") > -1) findKey = parseString(strTempXpath, "//@", "]")
				if (strTempXpath.indexOf("//text()") > -1) findKey = parseString(strTempXpath, "//text()", "]")
				if (strTempXpath.indexOf("//.") > -1) findKey = parseString(strTempXpath, "//.", "]")
				// edit //@attributeName,'' to @attributeName,'dynamicName'
				if (findKey.indexOf(",") > -1) {
					newTemp = parseString(findKey, null, ",").toString().replace("//", "") + ",'" + dynamicValue + "')"
					resultXpath = strTempXpath.replace(findKey, newTemp)
				} else {
					// edit //@attributeName] to @attributeName='dynamicName']
					newTemp = findKey.replace("//", "") + "='" + dynamicValue + "'"
					resultXpath = strTempXpath.replace(findKey, newTemp)
				}
			}
		}
		return resultXpath
	}

	/******************************************************
	 * Get Data Row from Data File
	 * @author: thangctran
	 * @param dataFile : The data file name
	 * @param rowIndex : The row index to get
	 */
	@Keyword
	def static getDataRow(TestData testData, int rowIndex) {
		def _dataDict = [:]
		//		TestData data = TestDataFactory.findTestData(dataFile)
		try{
			String[] headerNames = testData.getColumnNames()
			for (header in headerNames) {
				_dataDict[header] = testData.getValue(header, rowIndex)
			}
		} catch (Exception ex){
			KeywordUtil.markWarning("WARNING :" + ex.getMessage())
			return null
		}
		return _dataDict
	}

	/******************************************************
	 * Generate unique string format by system date
	 * @author thangctran
	 * @param formatDate : The date format // "E yyyy.MM.dd 'at' HH:mm:ss a zzz" => Sat 2018.08.11 at 05:09:21 PM UTC
	 * @return The string Ex: "yyyyMMDD" => 20181025
	 */
	@Keyword
	def getUnique(String formatDate) {
		SimpleDateFormat ft = new SimpleDateFormat (formatDate);
		return ft.format(new Date( ));
	}

	/******************************************************
	 * Get numeric in string
	 * @author thangctran
	 * @param min : The min number
	 * @param max : The max number
	 * @return The numeric between min and max number 
	 */
	@Keyword
	def getRandomInt(int min, int max){
		return (int)((Math.random()*((max-min)+1)) + min);
	}

	/******************************************************
	 * Get numeric in string
	 * @author thangctran
	 * @param string : The string
	 * @return The numeric Ex: "You are 24 years old" => 24
	 */
	@Keyword
	def getNumericInString(String string) {
		return Integer.parseInt(string.replaceAll("[^0-9]", ""));
	}

	/******************************************************
	 * Parse sub string in a string
	 * @author : thangctran
	 * @param mainString: the main string
	 * @param startSub: the start sub-string to find
	 * @param endSub: the end sub-string to find
	 * @return The sub string in a string : (Ex: parseString('text abc bcz', 'ex', 'z') -> 't abc bc')
	 */
	@Keyword
	def parseString(String mainString, String startSub=null, String endSub=null){
		int findStartIndex = 0
		int startIndex = 0
		int findEndIndex = -1
		// Check if sub String exists or not
		if(startSub != null) {
			findStartIndex = mainString.indexOf(startSub)
			if(findStartIndex >= 0) startIndex = findStartIndex + startSub.length()
		}
		if(endSub != null) {
			findEndIndex = mainString.indexOf(endSub, startIndex)
		}
		if(findEndIndex > -1) return mainString.substring(findStartIndex, findEndIndex)
		else return mainString.substring(startIndex)
	}

	/******************************************************
	 * Verify count sub string in a string
	 * @author : thangctran
	 * @param str: the string
	 * @param findStr: the sub-string to find
	 * @param expectedNumber: the expected number count
	 * @return None
	 */
	@Keyword
	def verifyCountMatchs(String str, String findStr, int expectedNumber){
		int findText = StringUtils.countMatches(str, findStr)
		WebUI.verifyEqual(findText, expectedNumber)
	}

	/******************************************************
	 * Verify two list Values
	 * @author : thangctran
	 * @param listValues1: the list values 1
	 * @param listValues2: the list values 2
	 * @param operator: =(listValues1=listValues2) ; >= (listValues1>=listValues2)
	 * @return None
	 */
	@Keyword
	def verifyListValues(def listValues1, def listValues2, String operator='='){
		if (operator == '='){
			def _list1 = listValues1.sort(false)
			def _list2 = listValues2.sort(false)
			WebUI.verifyEqual(_list1, _list2)
		} else{
			listValues2.each{itemValue ->
				if(!listValues1.contains(itemValue))WebUI.verifyEqual(listValues1.toString(), itemValue.toString())
			}
		}
	}

	/******************************************************
	 * Get Sort Order status
	 * @author : thangctran
	 * @param listValues: list values: value1,value2,.....,valueN
	 * @param dataType: string; number; date ('MM.dd.yyyy HH:mm a')
	 * @return status: normal; ascending; descending
	 */
	@Keyword
	def getSortOrderStatus(String listValues, String dataType='string'){
		def _valueList = []
		def _valueItem = null
		String _sortStatus = "normal"
		//Convert dataType
		try{
			listValues.split(',').each{itemValue ->
				switch (dataType) {
					case "string":
						_valueItem = itemValue.toLowerCase()
						break
					case "number":
						_valueItem = Double.parseDouble(itemValue)
						break
					default:
						_valueItem =  new Date().parse(dataType, itemValue)
				}
				//Add to list
				_valueList.add(_valueItem)
			}
		} catch (Exception ex) {
			KeywordUtil.markWarning("WARNING :" + ex.getMessage())
			return null
		}

		//Return status
		def _sortList = _valueList
		def _sortAscendingStatus = _sortList.sort(false)
		def _sortDescendingStatus = _sortAscendingStatus.reverse()
		if(_valueList.equals(_sortAscendingStatus)){
			_sortStatus = "ascending"
		} else if (_valueList.equals(_sortDescendingStatus)){
			_sortStatus = "descending"
		}
		return _sortStatus
	}
}
