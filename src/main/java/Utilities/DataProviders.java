package Utilities;

import java.io.IOException;

import Extensions.FileLogger;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name="Data")
    public Object[][] getAllData() throws IOException {
        String path = "./TestData/Userdata.xlsx";
        FileLogger.info("current path is: " + path);
        ExcelUtility xl = new ExcelUtility(path);

        int rowNum = xl.getRowCount("Sheet1");
        int columnCount = xl.getCellCount("Sheet1", 1);

        Object[][] apiData = new Object[rowNum][columnCount];

        for (int i = 1; i <= rowNum; i++) {
            for (int j = 0; j < columnCount; j++) {
                apiData[i - 1][j] = xl.getCellData("Sheet1", i, j);
            }
        }
        return apiData;
    }

    @DataProvider(name="UserNames")
    public Object[][] getUserNames() throws IOException {
        String path = System.getProperty("user.dir") + "//TestData//Userdata.xlsx";
        ExcelUtility xl = new ExcelUtility(path);

        int rowNum = xl.getRowCount("Sheet1");

        Object[][] apiData = new Object[rowNum][1];  // Make it a two-dimensional array

        for (int i = 1; i <= rowNum; i++) {
            apiData[i - 1][0] = xl.getCellData("Sheet1", i, 1);
        }

        return apiData;
    }
}
