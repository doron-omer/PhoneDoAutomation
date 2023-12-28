package Utilities;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

//This is an Event listeners class definition, each event defined the desired behavior by selenium
public class Listeners extends CommonOperations implements ITestListener
{

    public void onFinish(ITestContext test) {
        System.out.println("------------------- Test " + test.getName() +  " Completed! -------------------");
    }

    public void onStart(ITestContext test)
    {
        System.out.println("------------------- Test " + test.getName() + " is Starting! -------------------");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult test)
    {
        // TODO Auto-generated method stub
    }

    public void onTestFailure(ITestResult test)
    {
        System.out.println("------------------- The test " + test.getName() +  "has failed! -------------------");
        if(!getData("PlatformName").equalsIgnoreCase("api"))
            saveScreenshot();
    }

    public void onTestSkipped(ITestResult test) {
        System.out.println("------------------- Test " + test.getName() + "is Skipping! -------------------");
    }

    public void onTestStart(ITestResult test)
    {
        System.out.println("------------------- Test " + test.getName() + " successfully started! -------------------");
    }

    public void onTestSuccess(ITestResult test)
    {
        System.out.println("------------------- Test successfully completed! -------------------");
    }
    @Attachment(value="Page screenshot", type = "image/png")
    public byte[] saveScreenshot() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
