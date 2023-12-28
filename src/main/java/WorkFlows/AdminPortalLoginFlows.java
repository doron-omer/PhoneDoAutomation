package WorkFlows;

import Extensions.UiActions;
import PageObjects.LoginPage;
import Utilities.CommonOperations;
import io.qameta.allure.Step;

import static PageObjects.LoginPage.*;


public class AdminPortalLoginFlows extends CommonOperations {

    private static int TIMEOUT_IN_MILLIS = 20000;
    @Step("Login to admin portal web application")
    public static void loginToAdminPortal() {
        //Fill in username field
        UiActions.click(loginUsername);
        UiActions.fillInputField(loginUsername, getData("login_user"));

        //Fill in password field
        UiActions.click(loginPassword);
        UiActions.fillInputField(loginPassword, getData("password"));

        UiActions.click(LoginPage.loginButton);
    }

}
