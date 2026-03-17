package example;

import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import page.admin.AdminLoginPage;
import page.admin.DepositPage;
import page.admin.HomePage;
import page.user.*;
import utils.DriverUtils;
import utils.WaitUtils;

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
        // DriverUtils.DRIVER.get("http://14.176.232.213:8080/EBankingWebsite/");
        //  DriverUtils.DRIVER.get("http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml");
    }

    protected final String USER_URL = "http://14.176.232.213:8080/EBankingWebsite/";

    protected final String ADMIN_URL = "http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml";
    protected final String Email_URL = "https://www.mailinator.com/";
    protected String bankTab;

    protected void goToUserPage() {
        DriverUtils.DRIVER.get(USER_URL);
    }

    protected void goToAdminPage() {
        DriverUtils.DRIVER.get(ADMIN_URL);
    }

    protected void goToEmailPage() {
        bankTab = DriverUtils.DRIVER.getWindowHandle();
        DriverUtils.DRIVER.switchTo().newWindow(WindowType.TAB);
        DriverUtils.DRIVER.get(Email_URL);
    }

    protected void backToBankPage() {
        DriverUtils.DRIVER.switchTo().window(bankTab);
    }

    @AfterClass(description = "Tear down the test environment")
    public void tearDown() {
        // Tear down the test environment
        // DriverUtils.DRIVER.quit();
    }

    protected LoginPage loginPage = new LoginPage();
    protected AccountUserPage accountPage = new AccountUserPage();
    protected OpenAccountUserPage openAccountPage = new OpenAccountUserPage();
    protected AccountDetailsPage accountDetailsPage = new AccountDetailsPage();
    protected AdminLoginPage adminLoginPage = new AdminLoginPage();
    protected HomePage adminHomePage = new HomePage();
    protected DepositPage depositPage = new DepositPage();
    protected InternalTransferUserPage internalTransferUserPage = new InternalTransferUserPage();
    protected TransferInformationReviewPage transferInformationReviewPage = new TransferInformationReviewPage();
    protected PersonalInformationPage personalInformationPage = new PersonalInformationPage();
    protected EmailPage emailPage = new EmailPage();
    protected TransferConfirmationPage confirmationPage = new TransferConfirmationPage();
}
