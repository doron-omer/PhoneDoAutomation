package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class AdminPortalMainPage {
    @FindBy(how = How.CSS, using = "div[class='css-epk4z6']")
    public static WebElement firstHeader;

}
