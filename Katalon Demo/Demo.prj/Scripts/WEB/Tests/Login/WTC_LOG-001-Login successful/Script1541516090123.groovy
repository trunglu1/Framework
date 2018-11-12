import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

@com.kms.katalon.core.annotation.SetUp
def SetUp() {
	 'Get test data file'
	 if (true) {
		  _testData = findTestData('WEB/Login/WTC_LOG-001-Login successful')
		  _totalRow = 1 // For running data single test
		  if (GlobalVariable.SuiteType.toUpperCase().contains('DRIVEN')) {
				_totalRow = _testData.getRowNumbers() // For running data driven test
		  }
	 }
	 
	 'Open Url'
	 WebUI.openBrowser(GlobalVariable.URL)
}

'Loop around to run script'
for (int i = 1; i <= _totalRow; i++) {
    'Get test data'
    if (true) {
        _data = CustomKeywords.'pdi.Utilities.getDataRow'(_testData, i)
    }
    
    'Login'
    if (true) {
        WebUI.callTestCase(findTestCase('WEB/Pages/Login/Login')
			  , [('p_UserName') : _data['username']
			  , ('p_Password') : _data['password']])
    }
    
    'Verify login successful'
    WebUI.verifyEqual(WebUI.getWindowTitle(), 'BASE CONTROL MENU')

    'Logout'
    if (true) {
        WebUI.callTestCase(findTestCase('WEB/Pages/Main/Select menu'), [('p_MenuItems') : 'Utility>LOGOUT'])
    }
}

@com.kms.katalon.core.annotation.TearDown
def TearDown() {
    WebUI.callTestCase(findTestCase('WEB/Pages/Common/Close Browser window'), [:])
}

