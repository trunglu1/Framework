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

a = "//div[//@class]/a"
b = CustomKeywords.'helper.Utilities.convertXpath'(a, 'hello')
WebUI.comment(b)
