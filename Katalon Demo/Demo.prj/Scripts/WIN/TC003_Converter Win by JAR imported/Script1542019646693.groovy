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
import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.internal.PathUtil
import ThangTest

ThangTest winApp = new ThangTest()
'Luanhch Converter application'
if(true){
	_PathFile = "Data Files/WIN/Maindlg/maindlg.exe"
	_appPath = PathUtil.relativeToAbsolutePath(_PathFile, RunConfiguration.getProjectDir())
	
	Runtime.getRuntime().exec(_appPath)
	winApp.focusWindow(findTestObject('WIN/Converter/Window'))
}

'Verify that convert FT to M'
if(true){
	winApp.setText(findTestObject('WIN/Converter/txt_Source'), "50")
	WebUI.delay(GlobalVariable.ShortTime)
	winApp.click(findTestObject('WIN/Converter/rad_FT to M'))
	WebUI.delay(GlobalVariable.ShortTime)
	winApp.click(findTestObject('WIN/Converter/btn_Compute'))
	WebUI.delay(GlobalVariable.ShortTime)
	currentResult = winApp.getText(findTestObject('WIN/Converter/txt_Result'))
	WebUI.verifyEqual(currentResult, '15.240000')
}

'Verify that convert M to FT'
if(true){
	winApp.setText(findTestObject('WIN/Converter/txt_Source'), "25")
	WebUI.delay(GlobalVariable.ShortTime)
	winApp.click(findTestObject('WIN/Converter/rad_M to FT'))
	WebUI.delay(GlobalVariable.ShortTime)
	winApp.click(findTestObject('WIN/Converter/btn_Compute'))
	WebUI.delay(GlobalVariable.ShortTime)
	currentResult = winApp.getText(findTestObject('WIN/Converter/txt_Result'))
	WebUI.verifyEqual(currentResult, '82.020997')
}

'Close application'
CustomKeywords.'pdi.WinApp.click'(findTestObject('WIN/Converter/btn_Exit'))
