package constants;

public class Environments {
    public final static String PROJECT_PATH = System.getProperty("user.dir");
    public final static String RESOURCES_PATH = PROJECT_PATH + "\\src\\main\\resources\\";
    public final static String DRIVER_PATH = RESOURCES_PATH + "drivers\\";
    public final static String CONFIG_FILE = RESOURCES_PATH + "configuration\\config.xml";
    public final static String DATA_PATH = PROJECT_PATH + "\\src\\test\\resources\\data files\\";
    public final static String REPORTS_PATH = PROJECT_PATH + "\\src\\test\\resources\\reports\\";
}