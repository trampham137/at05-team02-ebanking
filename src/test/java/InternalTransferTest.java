import base.BaseTest;
import models.InternalTransferData;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.auth.AccountActivatedPage;
import pages.auth.RegisterPage;
import pages.email.MailinatorEmailPage;
import pages.email.MailinatorHomePage;
import pages.email.MailinatorInboxPage;
import pages.transfer.internal.InternalTransferConfirmPage;
import pages.transfer.internal.InternalTransferOtpPage;
import pages.transfer.internal.InternalTransferPage;

public class InternalTransferTest extends BaseTest {
    private static final long DEPOSIT_AMOUNT = 100_000;
    private static final long TRANSFER_AMOUNT = 10_000;
    private static final long TRANSFER_FEE = 1100;

    @Test(description = "EB-05 Verify user can successfully perform an internal transfer using OTP")
    public void EB05_user_can_transfer_internally_successfully() {
        String rootTab;
        String tabA;
        String tabB;
        String tabAdmin;
        String tabOtpMail;

        RegisterData registerDataA = TestData.validRegister("tram_test_a");
        RegisterData registerDataB = TestData.validRegister("tram_test_b");

        User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
        User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());
        // User userA = new User("tram_test_a_29635", "1234567889");
        // User userB = new User("tram_test_b_12760", "1234567889");

        rootTab = driver.getWindowHandle();

        // PHASE 1: Register + Activate A >> Login A + open account
        registerAndActivateUser(registerDataA);

        DashboardPage dashboardPageA = loginAsUser(userA);
        String accountNumberA = openBankAccount(dashboardPageA, AccountType.CURRENT_ACCOUNT);

        Assert.assertFalse(accountNumberA.isBlank());
        dashboardPageA.logout();
        clearSession();

        // PHASE 2: Register + Activate B >> Login B + open account
        registerAndActivateUser(registerDataB);

        DashboardPage dashboardPageB = loginAsUser(userB);
        String accountNumberB = openBankAccount(dashboardPageB, AccountType.CURRENT_ACCOUNT);

        Assert.assertFalse(accountNumberB.isBlank());
        dashboardPageB.logout();
        clearSession();

        // PHASE 3: Admin deposit to B
        tabAdmin = openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        // String accountNumberA = "100002347";
        // String accountNumberB = "100002350";

        // PHASE 4: Login B again + verify balance >> transfer money
        tabB = openNewTab(USER_BASE_URL);
        DashboardPage dashboardPageB1 = loginAsUser(userB);

        AccountDetailPage accountDetailPage = dashboardPageB1.openAccountDetail(accountNumberB);
        long balanceBefore = accountDetailPage.getBalance();

        Assert.assertEquals(balanceBefore, DEPOSIT_AMOUNT);

        // transfer
        InternalTransferPage internalTransferPage = accountDetailPage.goToInternalTransfer();
        InternalTransferData transferData = new InternalTransferData(
                accountNumberB,
                accountNumberA,
                TRANSFER_AMOUNT,
                "Internal transfer test"
        );

        internalTransferPage.fillTransferForm(transferData);
        internalTransferPage.clickConfirmButton();

        // confirm
        InternalTransferConfirmPage confirmPage = new InternalTransferConfirmPage(driver);

        Assert.assertEquals(confirmPage.getSourceAccount(), accountNumberB);
        Assert.assertEquals(confirmPage.getTargetAccount(), accountNumberA);
        Assert.assertEquals(confirmPage.getAmount(), TRANSFER_AMOUNT);

        InternalTransferOtpPage otpPage = confirmPage.clickConfirm();

        // PHASE 5: Get OTP
        tabOtpMail = openNewTab(MAILINATOR_URL);

        MailinatorHomePage otpHome = new MailinatorHomePage(driver);
        MailinatorInboxPage otpInbox = otpHome.openInbox(registerDataB.getEmail());
        // MailinatorInboxPage otpInbox = otpHome.openInbox("tram_test_b_12760@@mailinator.com");

        otpInbox.waitForInboxLoaded();
        MailinatorEmailPage otpEmail = otpInbox.waitAndOpenEmailBySubject("Send Code OPT", 90);
        String otpCode = otpEmail.getOtpCode();

        // PHASE 6: Transfer
        switchToTab(tabB);

        otpPage.enterOtp(otpCode);
        otpPage.clickTransfer();

        Assert.assertTrue(otpPage.isTransferSuccessPopupDisplayed());
        Assert.assertEquals(otpPage.getTransferSuccessMessage(), "Chuyển tiền thành công");
        otpPage.closeSuccessPopup();

        DashboardPage dashboardAfterTransfer = otpPage.goToAccounts();
        AccountDetailPage detailAfter = dashboardAfterTransfer.openAccountDetail(accountNumberB);

        long balanceAfter = detailAfter.getBalance();

        Assert.assertEquals(
                balanceAfter,
                balanceBefore - TRANSFER_AMOUNT - TRANSFER_FEE
        );

        detailAfter.logout();
        clearSession();

        // PHASE 7: Verify account A
        tabA = openNewTab(USER_BASE_URL);

        DashboardPage userADashboard = loginAsUser(userA);
        AccountDetailPage accountADetail = userADashboard.openAccountDetail(accountNumberA);

        long balanceAAfter = accountADetail.getBalance();
        Assert.assertEquals(balanceAAfter, TRANSFER_AMOUNT);
    }
}
