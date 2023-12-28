package Utilities;

import PageObjects.LoginPage;
import PageObjects.AdminPortalMainPage;
import PageObjects.TotalCallsHotlinesPage;
import org.openqa.selenium.support.PageFactory;

//This class is the management of the page objects
public class ManagePages extends Base {

    public static void init() {
        adminPortalLogin = PageFactory.initElements(driver, LoginPage.class);
        adminPortalMainPage = PageFactory.initElements(driver, AdminPortalMainPage.class);
        totalCallsHotlinesPage = PageFactory.initElements(driver, TotalCallsHotlinesPage.class);
    }
}
