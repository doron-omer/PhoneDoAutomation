package Helpers;

import Utilities.CommonOperations;
import org.testng.annotations.Test;
import WorkFlows.AdminPortalLoginFlows;

//This class demonstrate an automation visual testing

public class VisualTesting extends CommonOperations
{
    @Test
    public void takeScreenShot()
    {
        AdminPortalLoginFlows.loginToAdminPortal();
    }
}
