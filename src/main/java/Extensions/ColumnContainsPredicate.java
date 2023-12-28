package Extensions;

import org.openqa.selenium.WebElement;
import java.util.function.Predicate;

public class ColumnContainsPredicate implements Predicate<WebElement> {

    private WebElement columnLocator;
    private String expected;

    public ColumnContainsPredicate(WebElement columnLocator, String expected) {
        this.columnLocator = columnLocator;
        this.expected = expected;
    }

    @Override
    public boolean test(WebElement row) {
        String actual = columnLocator.getText();
        FileLogger.info(String.format("Matching %s to %s", actual, expected));

        if (actual.isEmpty()) {
            FileLogger.noMatch(expected, actual);
            return false;
        }

        boolean result = actual.toLowerCase().contains(expected.toLowerCase());
        if (result) {
            return true;
        }

        FileLogger.noMatch(expected, actual);
        return false;
    }
}

