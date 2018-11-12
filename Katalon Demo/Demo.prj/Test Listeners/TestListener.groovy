import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

class TestListener {
	private String startDate = ''
	private long startTime = 0
	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def sampleBeforeTestCase(TestCaseContext testCaseContext) {	
		startDate = CustomKeywords.'pdi.Utilities.getUnique'("yyyy/MM/dd HH:mm:ss")
		startTime = System.currentTimeMillis()
		GlobalVariable.IssueInfo = ''
//		println testCaseContext.getTestCaseId()
//		println testCaseContext.getTestCaseVariables()
	}

	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	@AfterTestCase
	def sampleAfterTestCase(TestCaseContext testCaseContext) {
		if(GlobalVariable.UploadTestStatus) {
			String _testCaseName =  testCaseContext.getTestCaseId().toString().split("/").last()
			String _testCaseStatus =  testCaseContext.getTestCaseStatus().toLowerCase()
			long _millis = System.currentTimeMillis() - startTime
			long _seconds = (int)(_millis/1000)
			// Convert duration format ("HH:mm:ss.SSS")
			String duration = String.format("%02d:%02d:%02d.%03d", (int)(_millis / 3600000), (int)((_seconds % 3600) / 60), _seconds % 60, _millis % 1000)
			//https://docs.google.com/spreadsheets/d/15rw3-1vVkPqDKTik69Y5-6tUXQMyLhBZL1YDMAEnkT0/edit#gid=1136287657
			WS.callTestCase(findTestCase('API/GoogleSheet/Update result to GoogleSheet'), [('p_TCName') : _testCaseName
				, ('p_CurrentBuild') : GlobalVariable.CurrentBuild, ('p_StartTime') : startDate, ('p_Duration') : duration, ('p_Issue') : GlobalVariable.IssueInfo, ('p_TCStatus') : _testCaseStatus])
		}	
	}

	/**
	 * Executes before every test suite starts.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@BeforeTestSuite
	def sampleBeforeTestSuite(TestSuiteContext testSuiteContext) {
//		println testSuiteContext.getTestSuiteId()
		if (GlobalVariable.SheetName.contains("UI-Report")){
			String _browser = DriverFactory.getExecutedBrowser().getName().replace('_DRIVER', '')
			GlobalVariable.SheetName = "UI-Report-" + _browser
		}
		if(GlobalVariable.ReportGoogleSheet){
			WS.callTestCase(findTestCase('API/GoogleSheet/Insert daily result on GoogleSheet'), [:])
			GlobalVariable.ReportGoogleSheet = false
			GlobalVariable.UploadTestStatus = true
		}
	}

	/**
	 * Executes after every test suite ends.
	 * @param testSuiteContext: related information of the executed test suite.
	 */
	@AfterTestSuite
	def sampleAfterTestSuite(TestSuiteContext testSuiteContext) {
		println testSuiteContext.getTestSuiteId()
	}
}