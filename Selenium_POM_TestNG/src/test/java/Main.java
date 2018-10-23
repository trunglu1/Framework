import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import constants.Environments;
import helpers.FileHelper;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        Map<String,String> a = FileHelper.getTestDataCSV("login\\BE002.csv", "," , 1);
//        Map<String,String> a = FileHelper.getTestDataJSON("testData.json");
////        Map<String,String> b = FileHelper.getTestDataCSV("login\\BE002.csv", ",", 2);
//        System.out.println(a.get("client_id"));
//        System.out.println(a.get("project_id"));
//        System.out.println(a.get("auth_provider_x509_cert_url"));
//        System.out.println(a.get("redirect_uris"));
//        UIElements.convertToElement("adasds", "dasd", "dad", 1)
        //https://www.tutorialspoint.com/gson/gson_tree_model.htm
        String jsonString = FileHelper.getTextFile(Environments.CREDENTIALS_FILE, null);
        System.out.println("Content \n" + jsonString);
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(jsonString);
        if (rootNode.isJsonObject()) {
            JsonObject details = rootNode.getAsJsonObject();
            JsonObject details_1 = details.getAsJsonObject("installed");
            JsonElement project_id = details_1.get("project_id");
            System.out.println("project_id: " + project_id.getAsString());
            System.out.println("installed: " + details_1.toString());
//            JsonElement installed = details.get("installed");
//            System.out.println("installed: " + installed.getAsString());
//            JsonObject next_installed = installed.getAsJsonObject();
//            JsonElement ageNode = next_installed.get("project_id");
//            System.out.println("Age: " + ageNode.getAsString());
//
//            JsonElement verifiedNode = details.get("verified");
//            System.out.println("Verified: " + (verifiedNode.getAsBoolean() ? "Yes":"No"));
//            JsonArray marks = next_installed.getAsJsonArray("redirect_uris");
//
//            for (int i = 0; i < marks.size(); i++) {
//                JsonPrimitive value = marks.get(i).getAsJsonPrimitive();
//                System.out.print(value.getAsString() + " ");
//            }
        }

        }
}
