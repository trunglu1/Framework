package steps.fe;

import java.util.List;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import commons.CustomWebUI;
import constants.Controls;
import utilities.Utility;

public class HotelsPage {
    @When("^User filter search Hotels$")
    public void filerSearch(){
        Utility.logInfo("STEP", "User filter search Hotels", 1);
        List<String> listPropertyTypes = Utility.convertStringToList(Utility.testData.get("propertyTypes"), ",");
        List<String> listAmenities = Utility.convertStringToList(Utility.testData.get("amenities"), ",");
        Commons.filerSearch(Utility.testData.get("star"), listPropertyTypes, listAmenities, null, null );
    }

    @Then("^Verify Number Star of each Hotels displayed correctly$")
    public void verifyNumberStartHotels() {
        Utility.logInfo("STEP", "Verify Number Star of each Hotels displayed correctly", 1);
        CustomWebUI.verifyStartOnTable(Controls.table, "bgwhite table table-striped", "1",null, Utility.testData.get("star"));
    }
}