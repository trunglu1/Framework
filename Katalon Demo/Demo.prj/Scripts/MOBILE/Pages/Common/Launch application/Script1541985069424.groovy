import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.util.internal.PathUtil

//'Get full directory\'s path of android application'
//_appPath = PathUtil.relativeToAbsolutePath(p_PathFile, RunConfiguration.getProjectDir())
//
//'Launch application'	
//Mobile.startApplication(_appPath, false)
Mobile.startApplication(p_PathFile, true)