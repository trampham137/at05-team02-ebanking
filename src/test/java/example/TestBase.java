package example;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.DriverUtils;

import java.util.HashMap;

public class TestBase {

    @BeforeClass(description = "Set up the test environment")
    public void setUp() {
        // Set up the test environment
        HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("profile.password_manager_leak_detection", false);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("prefs", prefs);

        DriverUtils.DRIVER = new ChromeDriver(chromeOptions);
        //  DriverUtils.DRIVER.get("http://14.176.232.213:8080/EBankingWebsite/");
        DriverUtils.DRIVER.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
    }


    @AfterClass(description = "Tear down the test environment")
    public void tearDown() {
        // Tear down the test environment
        // DriverUtils.DRIVER.quit();
    }

}
