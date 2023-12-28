package Extensions;

import Utilities.CommonOperations;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class UiActions extends CommonOperations {

    private static int DEFAULT_CHECK_INTERVAL_IN_MILLIS = 1000;
    private static int MAX_RETRY_NUMBER = 5;

    private static String FAILED_MESSAGE = "Condition is not met after %d seconds";

    public static void click(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    //Fill text in a text box
    public static void fillInputField(WebElement element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(text);
    }

    //Send any Keyboard key to input field
    public static void sendKeyboardKey(WebElement element, Keys key) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.sendKeys(key);
    }

    public static void clearTxt(WebElement element)
    {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
    }
    public static String getPageTitle() {
        return driver.getTitle();
    }

    public static void selectFromDropDownByValue(WebElement element, String value)
    {
        wait.until(ExpectedConditions.visibilityOf(element));
        Select valueFromList = new Select(element);
        valueFromList.selectByValue(value);
    }

    public static void selectFromDropDownByVisibleText(WebElement element, String text)
    {
        wait.until(ExpectedConditions.visibilityOf(element));
        Select valueFromList = new Select(element);
        valueFromList.selectByVisibleText(text);
    }

    //This method is selecting a value from a drop-down list by Index
    public static void selectFromDropDownByIndex(WebElement element, int index, WebElement selectedElement)
    {
        wait.until(ExpectedConditions.visibilityOf(element));
        Select selectedItem = new Select(element);
        selectedItem.selectByIndex(index);
        wait.until(ExpectedConditions.elementToBeSelected(selectedElement));
    }

    public static String getElementValue(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    //This method is hovering with a mouse over an element and click on it
    public static void mouseHoverElement(WebElement element)
    {
        action.moveToElement(element).click().build().perform();
    }

    //Wait tool until the element display in the DOM
    public static void waitUntilAppears(WebElement element, int secondsToWait) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secondsToWait));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitUntilElementIsClickable(WebElement element, int secondsToWait) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secondsToWait));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }


    //Check if text exist in a single Web element
    public static boolean checkExists(WebElement element, String text) {
        if (element == null || text == null || text.isEmpty()) {
            return false;
        }
        return element.getText().contains(text);
    }

    //Check if text exist in a list of Web elements
    public static boolean checkRowExistsInTable(List<WebElement> elements, Predicate<WebElement> rowPredicate, Consumer<WebElement> rowConsumer) {
        FileLogger.info(String.format("Looping over %s elements", elements.size()));

        for (WebElement element : elements) {
            if (rowPredicate.test(element)) {
                if (rowConsumer != null) {
                    rowConsumer.accept(element);
                }
                return true;
            }
        }

        return false;
    }

    public static boolean checkRowExistsInTable(WebElement tableElement, Predicate<WebElement> rowPredicate, Consumer<WebElement> rowConsumer) {
        List<WebElement> rows = tableElement.findElements(By.tagName("tr"));

        FileLogger.info(String.format("Looping over %s rows", rows.size()));

        for (WebElement row : rows) {
            if (rowPredicate.test(row)) {
                if (rowConsumer != null) {
                    rowConsumer.accept(row);
                }
                return true;
            }
        }

        return false;
    }


    //This method is scrolling down the page to an element
    @Step("Scroll down to element")
    public static void scrollDown(WebElement element)
    {
        wait.until(ExpectedConditions.visibilityOf(element));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    public static int countElements(List<WebElement> elements) {
        return elements.size();
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static void refreshPageAndWait(int waitTime) {
        for (int retryCount = 0; retryCount < MAX_RETRY_NUMBER; retryCount++) {
            waitWithCounter(waitTime);
            refreshPage();
        }
    }


    public void switchToIframe(WebElement element) throws InterruptedException {
        BooleanSupplier iFrameLoaded = () -> {
            try {
                waitUntilElementIsVisible(element, 20);
                return true;
            } catch (Exception e) {
                FileLogger.info("Failed to switch to iFrame. Refreshing page and trying again...");
                refreshPage();
            }
            return false;
        };
        waitUntilConditionMet(iFrameLoaded, 120);
        driver.switchTo().frame(element);
    }

    public static void waitUntilElementIsVisible(WebElement element, int secondsToWait) {
        try {
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("return document.readyState;");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(secondsToWait));
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Element not found");
        }

    }


    public static void waitUntilConditionMet(BooleanSupplier supplier, int timeoutInSeconds) {
        waitUntilConditionMet(supplier, timeoutInSeconds, String.format(FAILED_MESSAGE, timeoutInSeconds), DEFAULT_CHECK_INTERVAL_IN_MILLIS);
    }

    public static void waitUntilConditionMet(BooleanSupplier supplier, int timeoutInSeconds, String message, int intervalInMillis) {
        // Get the current time
        Date now = new Date();

        // Calculate the end time based on the timeoutInSeconds
        Date endDate = addSeconds(now, timeoutInSeconds);

        // Keep looping until the condition specified by the supplier is met
        while (!supplier.getAsBoolean()) {
            now = new Date(); // Update the current time

            // Check if the current time is after the end time
            if (now.after(endDate)) {
                // If the condition is not met within the timeout, throw an exception with the specified message
                throw new IllegalStateException(message);
            }

            try {
                // Sleep for the specified intervalInMillis before checking the condition again
                Thread.sleep(intervalInMillis);
            } catch (Exception e) {
                // Handle any exceptions from Thread.sleep, but it's usually safe to ignore them
            }
        }
    }

    // Helper method to add seconds to a Date object
    public static Date addSeconds(Date date, int amount) {
        return add(date, 13, amount);
    }

    public static Date add(Date date, int calendarField, int amount) {
        // Check if the input date is null
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        } else {
            // Create a Calendar instance and set its time to the input date
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            // Add the specified amount to the calendar field
            c.add(calendarField, amount);

            // Return the updated Date object
            return c.getTime();
        }
    }
    public static void waitWithCounter(long timeoutInMillis) {
        try {
            Thread.sleep(timeoutInMillis);
        } catch (Exception e) {
            // nothing to do
        }
    }
}