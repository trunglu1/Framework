package steps.fe;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import commons.Commons;
import utilities.Utility;
import keywords.WebUI;

public class ToursPage {
    static String lstTourType = "//*[./strong[text()='Tour Type']]/a[1]";

    @When("^User filter search Tours$")
    public void filerSearch(){
        Utility.logInfo("STEP", "User filter search Tours", 1);
        Commons.filerSearch(Utility.testData.get("star"), null, null, Utility.testData.get("tourType"), null );
    }

    @Then("^Verify Tour Type each Tours displayed correctly$")
    public void verifyTourType() {
        Utility.logInfo("STEP", "Tour Type each Tours displayed correctly", 1);
        WebUI.verifyAttributeOnList(lstTourType, null, "textContent", Utility.testData.get("tourType"));
    }
}
