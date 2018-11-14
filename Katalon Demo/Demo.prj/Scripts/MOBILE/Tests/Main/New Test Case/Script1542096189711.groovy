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

Mobile.startApplication(GlobalVariable.Application, true)

Mobile.tap(findTestObject('Trip001/android.widget.Button1 - Sign In'), 0)

Mobile.setText(findTestObject('Trip001/android.widget.EditText0 - Enter your email'), 'collect.report2018@gmail.com', 0)

Mobile.setText(findTestObject('Trip001/android.widget.EditText1 - Enter your password'), 'kms@2018', 0)

Mobile.tap(findTestObject('Trip001/android.widget.Button0 - Sign In'), 0)

Mobile.switchToPortrait()

Mobile.tap(findTestObject('Trip001/android.widget.Button0 - Got it'), 0)

Mobile.closeApplication()

