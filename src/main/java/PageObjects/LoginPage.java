package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

//This class holds all the locators of the login page
public class LoginPage {
    @FindBy(how = How.CSS, using = "input[name=\"email\"]")
    public static WebElement loginUsername;

    @FindBy(how = How.CSS, using = "input[id=\"passwordField\"]")
    public static WebElement loginPassword;

    @FindBy(how = How.CSS, using = "button[type=\"submit\"]")
    public static WebElement loginButton;

}
