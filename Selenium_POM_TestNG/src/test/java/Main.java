import helpers.FileHelper;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Map<String,String> a = FileHelper.getTestDataCSV("login\\BE002.csv", "," , 1);
        Map<String,String> a = FileHelper.getTestDataJSON("testData.json");
//        Map<String,String> b = FileHelper.getTestDataCSV("login\\BE002.csv", ",", 2);
        System.out.println(a.get("client_id"));
        System.out.println(a.get("project_id"));
        System.out.println(a.get("auth_provider_x509_cert_url"));
        System.out.println(a.get("redirect_uris"));
//        UIElements.convertToElement("adasds", "dasd", "dad", 1)
    }
}
