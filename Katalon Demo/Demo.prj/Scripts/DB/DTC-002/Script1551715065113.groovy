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
import org.apache.commons.io.FileUtils
import org.apache.commons.lang.StringUtils
import com.kms.katalon.core.configuration.RunConfiguration


//println RunConfiguration.getProjectDir() + "/Data Files/Order1.dat"
//File sourceFile = new File("D:\\Self-Study\\Framework\\Katalon Demo\\Demo.prj\\Data Files\\Order1.dat")
//String content = FileUtils.readFileToString(sourceFile, "utf-8")
//println content
//
//String returnValue = StringUtils.substringBetween(content, "<query>", "</query>")
//println returnValue
//newQSL = "select empid,shipaddress,freight from Sales.Orders where custid = 71"
//String newContent = content.replace("<query>" + returnValue + "</query>", "<query>" + newQSL + "</query>")
//
//println "@@@@:" + newContent
//FileUtils.writeStringToFile(sourceFile, newContent, "utf-8", false)
//String content1 = FileUtils.readFileToString(sourceFile, "utf-8")
//println "@@@@1111:" + content1
newQSL1 = "select empid,shipaddress,freight from Sales.Orders where custid = 70"
newQSL2 = "select empid,shipaddress,freight from Sales.Orders where custid = 71"
//TestData _testData1 = CustomKeywords.'pdi.Database.executeSQLQuery'(newQSL1)
//WebUI.comment(_testData1.getObjectValue(3, 4).toString())

//TestData _testData2 = CustomKeywords.'pdi.Database.executeSQLQuery'(newQSL2)
//WebUI.comment(_testData2.getObjectValue(3, 4).toString())

a = CustomKeywords.'pdi.Database.getDataColumn'(newQSL1, 'freight')
WebUI.comment(a.toString())

b = CustomKeywords.'pdi.Database.getDataColumn'(newQSL2, 3)
WebUI.comment(b.toString())