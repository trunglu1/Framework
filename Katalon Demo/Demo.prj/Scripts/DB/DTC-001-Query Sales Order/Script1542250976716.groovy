import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
//import org.apache.commons.lang3.ArrayUtils

//abc = CustomKeywords.'pdi.Database.executeQuery'('select empid,shipaddress,freight from Sales.Orders where custid = 71')
//
//WebUI.comment(CustomKeywords.'pdi.Database.getRowCount'(abc))
//
//WebUI.comment(CustomKeywords.'pdi.Database.getCellValue'(abc, 'shipaddress', 5 ))
//TestData _testData = findTestData('Order')

//_testData.getValue(3, 4)
//
//_data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, 3)
//
//WebUI.comment(_testData.getObjectValue(3, 4).toString())
//WebUI.comment(_testData.getObjectValue('freight', 4).toString())
//TestData _data = CustomKeywords.'pdi.Database.getData'(findTestData('Order'), ['headerName': 'shipaddress', 'id' : '70'])
//_testData.getObjectValue(0, 0)
//
//abc = _testData.getColumnNames()
//WebUI.comment(abc.toString())
//
//WebUI.comment(ArrayUtils.indexOf(abc, 'freight').toString())
//a = CustomKeywords.'pdi.Database.getCellValue'("select empid,shipaddress,freight from Sales.Orders where custid = 71", 3, 4)
//WebUI.comment(a)
//b = CustomKeywords.'pdi.Database.getCellValue'("select empid,shipaddress,freight from Sales.Orders where custid = 71", 'freight', 4)
//WebUI.comment(b)

a = CustomKeywords.'pdi.Database.getDataRow'("select empid,shipaddress,freight from Sales.Orders where custid = 71", 4)
WebUI.comment(a['freight'])