package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.apache.commons.lang3.ObjectUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.openqa.selenium.chrome.ChromeOptions;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import Utilities.Base;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.openqa.selenium.remote.http.FormEncodedData.getData;

public class CommonOperations extends Base {

    // Read XML Function
    public static String getData (String nodeName)
    {
        DocumentBuilder dBuilder;
        Document doc = null;
        File fXmlFile = new File("./Configuration/Configuration.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
        }
        catch(Exception e) {
            System.out.println("Exception in reading XML file: " + e);
        }
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(nodeName).item(0).getTextContent();
    }

    //Initializing web platform with browser selection: Google Chrome/FireFox/Microsoft Edge
    public static void initBrowser(String BrowserType)
    {
        if(BrowserType.equalsIgnoreCase("chrome"))
            driver = initChromeDriver();
        else if(BrowserType.equalsIgnoreCase("firefox"))
            driver = initFFDriver();
        else if(BrowserType.equalsIgnoreCase("edge"))
            driver = initEdgeDriver();
        else
            throw new RuntimeException(("Invalid browser name"));
        driver.manage().window().maximize();
        driver.get(getData("url"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(getData("TimeOut"))));
        wait = new WebDriverWait(driver, Duration.ofSeconds(Long.parseLong(getData("TimeOut"))));
        action = new Actions(driver);

    }
    //Initializing Google Chrome browser
    public static WebDriver initChromeDriver()
    {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);
        return driver;
    }
    //Initializing FireFox browser
    public static WebDriver initFFDriver()
    {
        WebDriverManager.firefoxdriver().setup();
        WebDriver driver = new FirefoxDriver();
        return driver;
    }

    //Initializing Edge browser
    public static WebDriver initEdgeDriver()
    {
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();
        return driver;
    }

    public static void initAPI() throws IOException {
        RestAssured.baseURI = getData("endPoint");
        httpRequest = RestAssured.given();
    }

    //Get the selected testing platform and initializing the corresponding function
    @BeforeClass
    public void startSession() throws IOException {
        if(getData("PlatformName").equalsIgnoreCase("web"))
            initBrowser(getData("BrowserName"));
        else if (getData("PlatformName").equalsIgnoreCase("api"))
            initAPI();
        else
            throw new RuntimeException(("Invalid platform name"));
        ManagePages.init();
    }

    @AfterMethod
    public void afterMethod()
    {
        if(getData("PlatformName").equalsIgnoreCase("web"))
            driver.get(getData("url"));
    }
    //Closing the session
    @AfterClass
    public void closeSession()
    {
        if(!getData("PlatformName").equalsIgnoreCase("api"))
            driver.quit();
    }
}
