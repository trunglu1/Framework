package constants;

import helpers.FileHelper;

public class Environments {
    public final static String PROJECT_PATH = System.getProperty("user.dir");
    public final static String RESOURCES_PATH = PROJECT_PATH + "\\src\\main\\resources\\";
    public final static String DRIVER_PATH = RESOURCES_PATH + "drivers\\";
    public final static String CONFIG_FILE = RESOURCES_PATH + "configuration\\config.xml";
    public final static String DATA_PATH = PROJECT_PATH + "\\src\\test\\resources\\data files\\";
    public final static String REPORTS_PATH = PROJECT_PATH + "\\src\\test\\resources\\reports\\";
    public final static String CREDENTIALS_FILE = RESOURCES_PATH  + "credentials.json";

    //Get list variables from cmd
    public final static String BROWSER_TEST = (System.getProperty("browser") != null)? System.getProperty("browser"):
            FileHelper.getXmlNodeValue("//Configuration/WebDriverType/text()",0);

    public final static boolean REPORT_GOOGLE_SHEET = (System.getProperty("reportGoogleSheet") != null)? System.getProperty("reportGoogleSheet").equalsIgnoreCase("true"):
            FileHelper.getXmlNodeValue("//Configuration/ReportGoogleSheet/text()",0).equalsIgnoreCase("true");

    public static boolean INSERT_NEW_RESULT = (System.getProperty("insertNewResult") != null)? System.getProperty("insertNewResult").equalsIgnoreCase("true"):
            FileHelper.getXmlNodeValue("//Configuration/InsertNewResult/text()",0).equalsIgnoreCase("true");

//    public final static String BROWSER = (System.getProperty("browser") != null)? System.getProperty("browser"):
//            FileHelper.getXmlNodeValue("//Configuration/WebDriverType/text()",0);
//
//    public final static String BROWSER = (System.getProperty("browser") != null)? System.getProperty("browser"):
//            FileHelper.getXmlNodeValue("//Configuration/WebDriverType/text()",0);
//
//    public final static String BROWSER = (System.getProperty("browser") != null)? System.getProperty("browser"):
//            FileHelper.getXmlNodeValue("//Configuration/WebDriverType/text()",0);
}
