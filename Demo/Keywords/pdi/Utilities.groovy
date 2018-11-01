package pdi

import java.awt.Color
import java.text.SimpleDateFormat

import org.apache.commons.lang.StringUtils
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class Utilities {
	/******************************************************
	 * convert dynamic Xpath
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
	def getDataRow(TestData testData, int rowIndex) {
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
	 * generate unique string by system date
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
	 * get numeric in string
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
	 * get numeric in string
	 * @author thangctran
	 * @param string : The string
	 * @return The numeric Ex: "You are 24 years old" => 24
	 */
	@Keyword
	def getNumericInString(String string) {
		return Integer.parseInt(string.replaceAll("[^0-9]", ""));
	}

	/******************************************************
	 * convert rgb to hex code color
	 * @author thangctran
	 * @param rgbFormat : The rgb format (Ex: rgb(255,255,255))
	 * @return the hex color (Ex: #ffffff)
	 */
	@Keyword
	def convertRGBtoHEX(String rgbFormat) {
		def arrRGB = rgbFormat.replace("(", ",").replace(")", "").split(",");
		Color c = new Color(Integer.valueOf(arrRGB[1].trim()),Integer.valueOf(arrRGB[2].trim()),Integer.valueOf(arrRGB[3].trim()));
		String hexFormat = '#' + Integer.toHexString( c.getRGB() & 0x00ffffff );
		return hexFormat.toLowerCase();
	}

	/******************************************************
	 * parse sub string in a string
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
	 * verify count sub string in a string
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
	 * verify two list Values
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
	 * Get Sort Order Status
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
