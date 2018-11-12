import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.internal.PathUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

'Luanhch Converter application'
if(true){
	_PathFile = "Data Files/WIN/Maindlg/maindlg.exe"
	_appPath = PathUtil.relativeToAbsolutePath(_PathFile, RunConfiguration.getProjectDir())
	
	Runtime.getRuntime().exec(_appPath)
	CustomKeywords.'pdi.WinApp.focusWindow'(findTestObject('WIN/Converter/Window'))
}

'Verify that convert FT to M'
if(true){
	CustomKeywords.'pdi.WinApp.setText'(findTestObject('WIN/Converter/txt_Source'), '50')
	WebUI.delay(GlobalVariable.ShortTime)
	CustomKeywords.'pdi.WinApp.click'(findTestObject('WIN/Converter/rad_FT to M'))
	WebUI.delay(GlobalVariable.ShortTime)
	CustomKeywords.'pdi.WinApp.click'(findTestObject('WIN/Converter/btn_Compute'))
	WebUI.delay(GlobalVariable.ShortTime)
	CustomKeywords.'pdi.WinApp.verifyText'(findTestObject('WIN/Converter/txt_Result'), '15.240000')
}

'Verify that convert M to FT'
if(true){
	CustomKeywords.'pdi.WinApp.setText'(findTestObject('WIN/Converter/txt_Source'), '25')
	WebUI.delay(GlobalVariable.ShortTime)
	CustomKeywords.'pdi.WinApp.click'(findTestObject('WIN/Converter/rad_M to FT'))
	WebUI.delay(GlobalVariable.ShortTime)
	CustomKeywords.'pdi.WinApp.click'(findTestObject('WIN/Converter/btn_Compute'))
	WebUI.delay(GlobalVariable.ShortTime)
	CustomKeywords.'pdi.WinApp.verifyText'(findTestObject('WIN/Converter/txt_Result'), '82.020997')
}

'Close application'
CustomKeywords.'pdi.WinApp.click'(findTestObject('WIN/Converter/btn_Exit'))
