package base;

import models.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.account.DashboardPage;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminLoginPage;
import pages.auth.LoginPage;
import pages.email.MailInboxPage;
import utils.DriverUtils;

public class BaseTest {
    protected WebDriver driver;

    protected static final String USER_BASE_URL = "http://14.176.232.213:8080/EBankingWebsite";
    protected static final String ADMIN_BASE_URL = "http://14.176.232.213:8080/EBankingWebsite/faces/admin/Login.xhtml";
    protected static final String MAILINATOR_URL = "https://www.mailinator.com/";
    protected String bankTab;

    @BeforeMethod
    public void setUp() {
        DriverUtils.initDriver();
        driver = DriverUtils.getDriver();
        // driver.get(USER_BASE_URL);
    }

    // @AfterMethod
    // public void quitDriver() {
    //     DriverUtils.quitDriver();
    // }

    @AfterMethod
    public void quitDriver() {

        //  DriverUtils.quitDriver();
    }

    protected LoginPage openUserLoginPage() {
        driver.get(USER_BASE_URL);
        return new LoginPage(driver);
    }

    protected AdminLoginPage openAdminLoginPage() {
        driver.get(ADMIN_BASE_URL);
        return new AdminLoginPage(driver);
    }

    protected DashboardPage loginAsUser(User user) {
        LoginPage loginPage = openUserLoginPage();
        loginPage.login(user);
        return new DashboardPage(driver);
    }

    protected AdminDashboardPage loginAsAdmin() {
        AdminLoginPage adminLoginPage = openAdminLoginPage();
        adminLoginPage.login("1", "admin");
        return new AdminDashboardPage(driver);
    }


    protected MailInboxPage goToEmailPage() {
        bankTab = DriverUtils.getDriver().getWindowHandle();
        DriverUtils.getDriver().switchTo().newWindow(WindowType.TAB);
        DriverUtils.getDriver().get(MAILINATOR_URL);
        return new MailInboxPage();
    }

    protected void backToBankPage() {
        DriverUtils.getDriver().switchTo().window(bankTab);
    }

}
