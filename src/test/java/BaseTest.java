package base;

import models.User;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.account.DashboardPage;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminLoginPage;
import pages.auth.LoginPage;
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
        // driver.get(USER_BASE_URL);
    }

    // @AfterMethod
    // public void quitDriver() {
    //     DriverUtils.quitDriver();
    // }

    @AfterMethod
    public void quitDriver() throws InterruptedException {
        Thread.sleep(3000);
        DriverUtils.quitDriver();
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
}
