package steps.be;

import java.util.List;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import commons.CustomWebUI;
import constants.Controls;
import utilities.Enums;
import utilities.Utility;

public class HotelsManagementPage {

    @When("^User deletes Hotel by Button$")
    public void deleteHotelByButton() {
        String rowIndex = Utility.testData.get("rowIndex");
        Utility.logInfo("STEP", "User deletes Hotel by Button at row " + rowIndex, 1);
        Utility.testData.put("hotelName", Commons.deleteRowByButton(rowIndex, "Name"));
    }

    @When("^User deletes Hotel by Icon$")
    public void deleteHotelByIcon() {
        String rowIndex = Utility.testData.get("rowIndex");
        Utility.logInfo("STEP", "User deletes Hotel by Icon at row " + rowIndex, 1);
        Utility.testData.put("hotelName", Commons.deleteRowByIcon(rowIndex, "Name"));
    }

    @Then("^Verify User deleted a Hotel$")
    public void verifyHotelNameDeleted() {
        String hotelName = Utility.testData.get("hotelName");
        String rowIndex = Utility.testData.get("rowIndex");
        Utility.logInfo("STEP", "Verify User deleted '" + hotelName + "' hotel at row " + rowIndex, 1);
        List<String> listName = CustomWebUI.getCellValuesOnTable(Controls.table, null, "Name", rowIndex);
        Utility.verifyValues("verifyHotelNameDeleted", listName.get(0), hotelName, Enums.OPERATOR.notEqual);
    }

    @Then("^Verify Image Number is uploaded$")
    public void verifyImageNumberUploaded() {
        Utility.logInfo("STEP", "User deletes Hotel by Button at row " + Utility.testData.get("rowIndex"), 1);
        int currentNumber = CustomWebUI.getImageNumberUpload(Utility.testData.get("rowIndex"));
        int expectedImageNumber = Integer.valueOf(Utility.testData.get("imageNumber")) + 1;
        Utility.verifyValues("verifyImageNumberUploaded", String.valueOf(currentNumber), String.valueOf(expectedImageNumber), Enums.OPERATOR.equal);
    }

    @When("^User upload a image to Hotel$")
    public void uploadGallery() {
        Utility.logInfo("STEP", "User upload a image to Hotel at row " + Utility.testData.get("rowIndex"), 1);
        int currentNumber = CustomWebUI.getImageNumberUpload(Utility.testData.get("rowIndex"));
        Utility.testData.put("imageNumber", String.valueOf(currentNumber));
        Commons.uploadGallery(Utility.testData.get("rowIndex"), Utility.testData.get("imageUpload"));
    }
}
