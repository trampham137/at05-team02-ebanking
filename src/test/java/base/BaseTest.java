package base;

import io.qameta.allure.Step;
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

    protected static final String ACTIVATION_EMAIL_SUBJECT = "Kich hoat tai khoan";
    protected static final String OTP_EMAIL_SUBJECT = "Send Code OPT";
    protected static final int OTP_TIMEOUT_SECONDS = 30;

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

    @Step("Clear browser session")
    protected void clearSession() {
        // TODO: no need driver.get("about:blank");
        // driver.get("about:blank");
        driver.manage().deleteAllCookies();
    }

    @Step("Open user login page")
    protected LoginPage openUserLoginPage() {
        driver.get(USER_BASE_URL);
        return new LoginPage(driver);
    }

    @Step("Open admin login page")
    protected AdminLoginPage openAdminLoginPage() {
        driver.get(ADMIN_BASE_URL);
        return new AdminLoginPage(driver);
    }

    @Step("Login as user")
    protected DashboardPage loginAsUser(User user) {
        LoginPage loginPage = openUserLoginPage();
        loginPage.login(user);
        return new DashboardPage(driver);
    }

    @Step("Login as admin")
    protected AdminDashboardPage loginAsAdmin() {
        AdminLoginPage adminLoginPage = openAdminLoginPage();
        adminLoginPage.login("1", "admin");
        return new AdminDashboardPage(driver);
    }

    @Step("Go to register page")
    protected RegisterPage goToRegister() {
        LoginPage loginPage = openUserLoginPage();
        loginPage.clickRegisterLink();
        return new RegisterPage(driver);
    }

    @Step("Open new tab: {url}")
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
    @Step("Register and activate user account")
    protected void registerAndActivateUser(RegisterData registerData) {
        RegisterPage registerPage = goToRegister();
        registerPage.register(registerData);

        openNewTab(MAILINATOR_URL);

        MailinatorHomePage mailinatorHomePage = new MailinatorHomePage(driver);
        MailinatorInboxPage inboxPage = mailinatorHomePage.openInbox(registerData.getEmail());
        inboxPage.waitForInboxLoaded();

        MailinatorEmailPage activationEmailPage = inboxPage.openEmailBySubject(ACTIVATION_EMAIL_SUBJECT);
        activationEmailPage.clickActivationLink();
        activationEmailPage.switchToNewestWindow();

        AccountActivatedPage activatedPage = new AccountActivatedPage(driver);
        Assert.assertTrue(
                activatedPage.isActivationSuccessDisplayed(),
                "Activation success message is not displayed."
        );
    }

    @Step("Open new bank account with type: {accountType}")
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
                "Mở tài khoản thành công",
                "Open account success message is incorrect."
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

    @Step("Deposit money to account: {accountNumber}, amount: {amount}")
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
                "nộp tiền thành công",
                "Deposit success message is incorrect."
        );

        depositMoneyPage.logout();
        clearSession();
    }

    @Step("Open OTP email for: {email}")
    protected MailinatorEmailPage openOtpEmail(String email) {
        openNewTab(MAILINATOR_URL);

        MailinatorHomePage otpHome = new MailinatorHomePage(driver);
        MailinatorInboxPage otpInbox = otpHome.openInbox(email);
        otpInbox.waitForInboxLoaded();

        return otpInbox.waitAndOpenEmailBySubject(OTP_EMAIL_SUBJECT, OTP_TIMEOUT_SECONDS);
    }

    @Step("Get OTP code from Mailinator for: {email}")
    protected String getOtpCodeFromMailinator(String email) {
        MailinatorEmailPage otpEmail = openOtpEmail(email);
        return otpEmail.getOtpEmailData().getOtpCode();
    }
}
