package Utilities;

import PageObjects.LoginPage;
import PageObjects.AdminPortalMainPage;
import PageObjects.TotalCallsHotlinesPage;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

//***** This class store all the global variables of the project *****
public class Base {
    public static WebDriver driver;
    public static WebDriverWait wait;
    public static Actions action;
    public static Screenshot imageScreenshot;
    public static ImageDiffer imgDif = new ImageDiffer();
    public static ImageDiff dif;

    public static String Platform;
    public static LoginPage adminPortalLogin;

    public static AdminPortalMainPage adminPortalMainPage;

    public static TotalCallsHotlinesPage totalCallsHotlinesPage;
    public static RequestSpecification httpRequest;
    public static io.restassured.response.ValidatableResponse validatableResponse;

    public static Response response;
    public static JSONObject requestParams = new JSONObject();
    public static JsonPath jsonPath;
}
