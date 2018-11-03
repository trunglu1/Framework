import com.kms.katalon.core.webui.driver.DriverFactory

a = DriverFactory.getExecutedBrowser().getName().replace('_DRIVER', '')
println(a)