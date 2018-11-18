import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.util.internal.PathUtil
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import internal.GlobalVariable as GlobalVariable
import kms.WinApp

WinApp WinUI = new WinApp()
'Luanhch Converter application'
if(true){
	_PathFile = "Data Files/WIN/Maindlg/maindlg.exe"
	_appPath = PathUtil.relativeToAbsolutePath(_PathFile, RunConfiguration.getProjectDir())
	
	Runtime.getRuntime().exec(_appPath)
	WinUI.winActivate(findTestObject('WIN/Converter/Window'))
}

'Verify that convert FT to M'
if(true){
	WinUI.controlSetText(findTestObject('WIN/Converter/txt_Source'), "50")
	WinUI.delay(GlobalVariable.ShortTime)
	WinUI.controlClick(findTestObject('WIN/Converter/rad_FT to M'))
	WinUI.delay(GlobalVariable.ShortTime)
	WinUI.controlClick(findTestObject('WIN/Converter/btn_Compute'))
	WinUI.delay(GlobalVariable.ShortTime)
	WinUI.verifyControlText(findTestObject('WIN/Converter/txt_Result'), '15.240000')
}

'Verify that convert M to FT'
if(true){
	WinUI.controlSetText(findTestObject('WIN/Converter/txt_Source'), "25")
	WinUI.delay(GlobalVariable.ShortTime)
	WinUI.controlClick(findTestObject('WIN/Converter/rad_M to FT'))
	WinUI.delay(GlobalVariable.ShortTime)
	WinUI.controlClick(findTestObject('WIN/Converter/btn_Compute'))
	WinUI.delay(GlobalVariable.ShortTime)
	WinUI.verifyControlText(findTestObject('WIN/Converter/txt_Result'), '82.020997')
}

'Close application'
WinUI.controlClick(findTestObject('WIN/Converter/btn_Exit'))