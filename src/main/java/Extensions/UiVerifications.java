package Extensions;

import Utilities.CommonOperations;
import Utilities.HelpersMethods;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.function.BooleanSupplier;

import static PageObjects.TotalCallsHotlinesPage.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class UiVerifications extends CommonOperations {

    private static int RETRY_INTERVAL_IN_SECONDS = 10;
    @Step("Verify text in element")
    public static void validateTextInElement(WebElement element, String expectedValue) {
        Assert.assertEquals(element.getText(), expectedValue);
    }

    //This method read the previous version image logo and compare it to the new version image logo
    @Step("Compare main image logo")
    public static void visualElement(WebElement imageElement, String expectedImageName)
    {
        BufferedImage expectedImage = null;     //initializing expected image to 'BufferImage' object
        try
        {
            //Read the image that already taken (previous version) and store in 'BufferImage' object
            expectedImage = ImageIO.read(new File(getData("ImageRepo")+ expectedImageName + ".png"));
        }
        catch (Exception e)
        {
            System.out.println("Error reading image file: " + e);
        }
        //Take the new image in the current page version and compare it to the image from previous version
        Screenshot imageScreenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver, imageElement);
        BufferedImage actualImage = imageScreenshot.getImage();
        dif = imgDif.makeDiff(actualImage, expectedImage);  // compare the actual image to the expected image
        assertFalse(dif.hasDiff(), "images are not equals");
    }

    @Step("Validate table is empty")
    public static boolean validateTableIsEmpty(List<WebElement> elements) {
        if (elements.isEmpty()) {
            FileLogger.info("Table is empty");
            return true;
        } else {
            FileLogger.info("Table is not empty");
            return false;
        }
    }

    @Step("Validate that the transaction table display customer transaction data")
    public static void validateTransactionTableDisplayData(List<WebElement> elements) {
        try {
            BooleanSupplier transactionDataDisplayed = () -> {
                boolean tableDataExist = !validateTableIsEmpty(elements);
                // If table data doesn't exist, refresh the page and wait
                if (!tableDataExist) {
                    UiActions.refreshPageAndWait(RETRY_INTERVAL_IN_SECONDS);
                }

                return tableDataExist;
            };

            // Wait until the condition (table data displayed) is met or timeout occurs
            UiActions.waitUntilConditionMet(transactionDataDisplayed, 30);
        } catch (Exception e) {
            FileLogger.info("Failed to wait for table data to be displayed");
            e.printStackTrace();
        }
    }


    @Step("Validate amount and transaction type in transaction table")
    public static void validateAmountAndTransactionTypeInTransactionsTable(String amount, String expectedTransactionType) {
        FileLogger.info("Validating customers' transactions in the transaction table");

        // Wait until the conditions are met within the specified timeout
        UiActions.waitUntilConditionMet(() -> {
            // Refresh the page and wait
            UiActions.refreshPageAndWait(RETRY_INTERVAL_IN_SECONDS);

            // Iterate through table rows
            for (WebElement row : tableRows) {
                // Find the amount and transaction type columns for each row
                List<WebElement> amountColumns = amountColumn;
                List<WebElement> transactionTypeColumns = transactionTypeColumn;

                // Check if any amount column contains the expectedWithdrawAmount
                boolean amountMatched = amountColumns.stream()
                        .anyMatch(column -> column.getText().contains(amount));

                // Check if any transaction type column contains "Debit"
                boolean transactionTypeMatched = transactionTypeColumns.stream()
                        .anyMatch(column -> column.getText().equals(expectedTransactionType));

                // If both conditions are met for the row, return true
                if (amountMatched && transactionTypeMatched) {
                    return true;
                }
            }

            // If no matching row is found, return false
            return false;
        }, 30);
    }

    @Step("Validate price order after sorting")
    public static boolean validatePriceOrder() {
        FileLogger.info("Validating prices after sorting");
        //First validate the both list are equals:
        boolean listsAreEqual = ScenarioContext.getFromDataStore(ScenarioContext.pricesBeforeSorting)
                .equals(ScenarioContext.getFromDataStore(ScenarioContext.pricesAfterSorting));
        //Now let's validate that the prices after sorting are ordered from low to high
        return
        HelpersMethods.isSorted(ScenarioContext.getFromDataStore(ScenarioContext.pricesAfterSorting));
    }

    @Step("Verify text with text")
    public static void text(String actualText, String expectedText)
    {
        assertEquals(actualText, expectedText);
    }

    @Step("Validate response status code 'ok'")
    public static void validateStatusCodeOk(ValidatableResponse response) {
         Assert.assertEquals(response.statusCode(200), 200);
    }
}
