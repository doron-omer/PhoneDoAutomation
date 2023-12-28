package Extensions;
import java.util.logging.*;

public class FileLogger {
    private static final Logger logger = Logger.getLogger(FileLogger.class.getName());

    public static void info(String message) {
        logger.info(addMetaData(message));
    }

    public static void error(String message) {
        logger.severe(addMetaData(message));
    }

    public static void error(String message, Throwable throwable) {
        logger.log(Level.SEVERE, addMetaData(message), throwable);
    }

    public static void debug(String message) {
        logger.fine(addMetaData(message));
    }

    public static void noMatch(String expected, String actual) {
        String logMessage = String.format("No match. Expected '%s', Actual '%s'", expected, actual);
        logger.info(addMetaData(logMessage));
    }

    private FileLogger() {
    }

    private static String addMetaData(String message) {
        return "[" + ProcessHandle.current().pid() + " " + Thread.currentThread().getName() + "] " + message;
    }
}
