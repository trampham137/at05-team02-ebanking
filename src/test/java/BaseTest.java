import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverUtils;

public class BaseTest {
    protected WebDriver driver;

    protected static final String USER_BASE_URL = "http://14.176.232.213:8080/EBankingWebsite";
    protected static final String ADMIN_BASE_URL = "http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml";
    protected static final String MAILINATOR_URL = "https://www.mailinator.com/";

    @BeforeMethod
    public void setUp() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        driver.get(USER_BASE_URL);
    }

    @AfterMethod
    public void quitDriver() {
        DriverUtils.quitDriver();
    }
}
