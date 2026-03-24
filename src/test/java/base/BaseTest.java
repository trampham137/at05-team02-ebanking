package base;

import models.DepositData;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.account.DashboardPage;
import pages.account.OpenAccountPage;
import pages.admin.AdminDashboardPage;
import pages.admin.AdminLoginPage;
import pages.admin.DepositMoneyPage;
import pages.auth.AccountActivatedPage;
import pages.auth.LoginPage;
import pages.auth.RegisterPage;
import pages.email.MailinatorEmailPage;
import pages.email.MailinatorHomePage;
import pages.email.MailinatorInboxPage;
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

    @AfterMethod
    public void quitDriver() {
        DriverUtils.quitDriver();
    }

    protected void clearSession() {
        driver.get("about:blank");
        driver.manage().deleteAllCookies();
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

    protected RegisterPage goToRegister() {
        LoginPage loginPage = openUserLoginPage();
        loginPage.clickRegisterLink();
        return new RegisterPage(driver);
    }

    protected String openNewTab(String url) {
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(url);
        return driver.getWindowHandle();
    }

    protected void switchToTab(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    protected void closeCurrentTab() {
        driver.close();
    }

    // reusable business flows
    protected void registerAndActivateUser(RegisterData registerData) {
        RegisterPage registerPage = goToRegister();
        registerPage.register(registerData);

        String mailinatorTab = openNewTab(MAILINATOR_URL);

        MailinatorHomePage mailinatorHomePage = new MailinatorHomePage(driver);
        MailinatorInboxPage inboxPage = mailinatorHomePage.openInbox(registerData.getEmail());
        inboxPage.waitForInboxLoaded();

        MailinatorEmailPage activationEmailPage = inboxPage.openEmailBySubject("Kich hoat tai khoan");
        activationEmailPage.clickActivationLink();

        activationEmailPage.switchToNewestWindow();
        String activationSuccessBTab = driver.getWindowHandle();

        AccountActivatedPage activatedPage = new AccountActivatedPage(driver);
        Assert.assertTrue(activatedPage.isActivationSuccessDisplayed());
    }

    protected String openBankAccount(DashboardPage dashboardPage, AccountType accountType) {
        String lastAccountBefore = dashboardPage.getLastAccountNumber();

        OpenAccountPage openAccountPage = dashboardPage.goToOpenAccount();
        openAccountPage.createAccount(accountType);

        Assert.assertTrue(
                openAccountPage.isOpenAccountSuccessPopupDisplayed(),
                "Open account success popup is not displayed."
        );
        Assert.assertEquals(
                openAccountPage.getOpenAccountSuccessPopupMessage(),
                "Mở tài khoản thành công"
        );

        openAccountPage.closeSuccessPopup();

        DashboardPage refreshedDashboardPage = openAccountPage.goToAccounts();
        String lastAccountAfter = refreshedDashboardPage.getLastAccountNumber();

        // TODO: check position >> DONE
        Assert.assertNotEquals(
                lastAccountAfter,
                lastAccountBefore,
                "Last account should be newly created account"
        );

        return lastAccountAfter;
    }

    protected void depositMoneyAndLogout(String accountNumber, long amount) {
        AdminDashboardPage adminDashboardPage = loginAsAdmin();
        DepositMoneyPage depositMoneyPage = adminDashboardPage.goToDepositMoney();

        DepositData depositData = new DepositData(accountNumber, amount, "Testing");
        depositMoneyPage.depositToAccount(depositData);

        Assert.assertTrue(
                depositMoneyPage.isDepositSuccessful(),
                "Deposit success message is not displayed."
        );
        Assert.assertEquals(
                depositMoneyPage.getSuccessMessage(),
                "nộp tiền thành công"
        );

        depositMoneyPage.logout();
    }

}
