package PageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class TotalCallsHotlinesPage {

    @FindBy(how = How.CSS, using = ".table.table-bordered.table-striped tbody tr")
    public static List<WebElement> tableRows;
    @FindBy(how = How.CSS, using = ".table.table-bordered.table-striped tbody td:nth-child(2)")
    public static List<WebElement> amountColumn;
    @FindBy(how = How.CSS, using = ".table.table-bordered.table-striped tbody td:nth-child(3)")
    public static List<WebElement> transactionTypeColumn;

}
