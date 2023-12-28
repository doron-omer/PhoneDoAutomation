package SanityTests;

import Extensions.UiVerifications;
import Utilities.CommonOperations;
import WorkFlows.AdminPortalLoginFlows;
import io.qameta.allure.Description;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static PageObjects.AdminPortalMainPage.*;

@Listeners(Utilities.Listeners.class)
public class AdminPortalWeb extends CommonOperations {
    @Test(description = "Test 01: login with registered user")
    @Description("Test Description: login and validate the user logged-in name")
    public void test01_login() {
        AdminPortalLoginFlows.loginToAdminPortal();
        //Validate the account name next to the 'welcome' text
        UiVerifications.validateTextInElement(firstHeader, getData("title"));
    }


}
