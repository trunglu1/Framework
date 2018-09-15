package drivers;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import constants.Environments;
import helpers.FileHelper;
import utilities.Utility;
import google.GoogleSheets;

public class Driver {
    static WebDriver driver = null;

    public static void setDriver(WebDriver driverTest) {
        if (driver == null) driver = driverTest;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static WebDriver setSeleniumDrivers() {
        WebDriver driverTest = null;

        try {
            String browserName = Environments.BROWSER_TEST.toLowerCase();
            int waitTime = Integer.parseInt(FileHelper.getXmlNodeValue("//Configuration/LoadTimeOut/text()",0));
            String sheetName= null;
            String sheetId = null;
            //Initiate a new webdriver instance base on browserName
            switch (browserName) { // check our browser
                case "firefox": {
                    sheetName = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetName_firefox");
                    sheetId = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetId_firefox");
                    System.setProperty("webdriver.gecko.driver", Environments.DRIVER_PATH + "\\geckodriver.exe");
                    driverTest = new FirefoxDriver();
                    break;
                }
                case "edge": {
                    sheetName = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetName_edge");
                    sheetId = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetId_edge");
                    System.setProperty("webdriver.edge.driver", Environments.DRIVER_PATH + "\\MicrosoftWebDriver.exe");
                    driverTest = new EdgeDriver();
                    break;
                }
                case "ie": {
                    sheetName = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetName_ie");
                    sheetId = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetId_ie");
                    System.setProperty("webdriver.ie.driver", Environments.DRIVER_PATH + "\\IEDriverServer.exe");
                    driverTest = new InternetExplorerDriver();
                    break;
                }
                case "safari": {
                    sheetName = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetName_safari");
                    sheetId = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetId_safari");
                    driverTest = new SafariDriver();
                    break;
                }
                // if our browser is not listed
                default: {
                    sheetName = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetName_chrome");
                    sheetId = FileHelper.getJSONNode(Environments.CREDENTIALS_FILE,"installed.sheetId_chrome");
                    System.setProperty("webdriver.chrome.driver", Environments.DRIVER_PATH + "\\chromedriver.exe");
                    driverTest = new ChromeDriver();
                }
            }
            GoogleSheets.googleSheetInfo.put("sheetName", sheetName);
            GoogleSheets.googleSheetInfo.put("sheetId",sheetId);
            driverTest.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            driverTest.manage().window().maximize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return driverTest;
    }

    public static WebElement findElement(String description, String strXpath, String dynamicValue){
        WebElement element;
        String newXpath = Utility.convertXpath(strXpath, dynamicValue);
        try {
            Utility.logInfo("INFO", String.format("%s :: [%s]", description, newXpath), 1);
            element = driver.findElement(By.xpath(newXpath));
        } catch (Exception e){
            Utility.logInfo("ERROR", String.format("%s :: Not Found[%s]", description, newXpath), 1);
            return null;
        }
        return element;
    }
}