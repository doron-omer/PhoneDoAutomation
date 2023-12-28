package Extensions;

import org.openqa.selenium.WebElement;
import java.util.function.Predicate;

public class ColumnExactValuePredicate implements Predicate<WebElement> {

    private WebElement columnLocator;
    private String expected;

    public ColumnExactValuePredicate(WebElement columnLocator, String expected) {
        this.columnLocator = columnLocator;
        this.expected = expected;
    }

    @Override
    public boolean test(WebElement row) {
        String actual = columnLocator.getText();
        if (actual.isEmpty()) {
            FileLogger.noMatch(expected, actual);
            return false;
        }

        boolean result = actual.equalsIgnoreCase(expected);
        if (result) {
            return true;
        }

        FileLogger.noMatch(expected, actual);
        return false;
    }
}
