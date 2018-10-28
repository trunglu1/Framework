import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

//a = "wqw/wqw/dss/e/rer/yu"
//b = a.split("/")
//
//println(b + "==" +  b.size() + "---" + b.last())
//WebUI.verifyEqual(1, 2)
//println(System.currentTimeMillis())
//WebUI.delay(1)
//println(CustomKeywords.'helper.Utilities.getUnique'('yyyy.MM-dd'))
//println(CustomKeywords.'helper.Utilities.getRandomInt'(30, 78))
//println(CustomKeywords.'helper.Utilities.getNumericInString'('You 23 dad47.21'))
//println(CustomKeywords.'helper.Utilities.parseString'('text abc bcz', 'ex', 'z'))
//a = "//div[//@class]/a"
//b = CustomKeywords.'helper.Utilities.convertXpath'(a, 'hello')
//WebUI.comment(b)
//WebUI.openBrowser('https://www.google.com.vn/')
//
//WebUI.comment(DriverFactory.getExecutedBrowser().getName())
_data = CustomKeywords.'helper.Utilities.getDataRow'('Data Files/GoogleSheets', 1)

a = 'UI-Report-Chrome'

b = (_data[a])
WebUI.delay(3)
WebUI.comment(b)

//WebUI.callTestCase(findTestCase('Common/GoogleSheet/Update result to GoogleSheet'), [('sheetName') : 'UI-Report-Chrome', ('testCaseName') : 'TC3113 Hello How are you'
//        , ('currentBuild') : '123', ('startTime') : '2018/10/29 09:55:07', ('duration') : '00:00:11.167', ('testCaseStatus') : 'FAILED'])

