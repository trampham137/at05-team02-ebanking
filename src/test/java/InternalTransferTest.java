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
        RegisterData registerDataA = TestData.validRegister("tram_test_a");
        RegisterData registerDataB = TestData.validRegister("tram_test_b");

        User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
        User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());
        // User userA = new User("tram_test_a_29635", "1234567889");
        // User userB = new User("tram_test_b_12760", "1234567889");

        // PHASE 1: Register + activate + open account for A
        String accountNumberA = registerActivateLoginAndOpenAccount(registerDataA, AccountType.CURRENT_ACCOUNT);

        // PHASE 2: Register + activate + open account for B
        String accountNumberB = registerActivateLoginAndOpenAccount(registerDataB, AccountType.CURRENT_ACCOUNT);

        // PHASE 3: Admin deposits money to account B
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        // PHASE 4: User B logs in and performs transfer
        String userBTab = openNewTab(USER_BASE_URL);
        DashboardPage dashboardPageBAfterDeposit = loginAsUser(userB);

        AccountDetailPage accountBDetailBeforeTransfer = dashboardPageBAfterDeposit.openAccountDetail(accountNumberB);
        long balanceBeforeTransfer = accountBDetailBeforeTransfer.getBalance();

        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Account B balance before transfer is incorrect."
        );

        // transfer
        InternalTransferPage internalTransferPage = accountBDetailBeforeTransfer.goToInternalTransfer();

        InternalTransferData transferData = new InternalTransferData(
                accountNumberB,
                accountNumberA,
                TRANSFER_AMOUNT,
                "Internal transfer test"
        );

        internalTransferPage.fillTransferForm(transferData);
        InternalTransferConfirmPage confirmPage = internalTransferPage.clickConfirmButton();

        Assert.assertEquals(
                confirmPage.getSourceAccount(),
                accountNumberB,
                "Source account is incorrect."
        );
        Assert.assertEquals(
                confirmPage.getTargetAccount(),
                accountNumberA,
                "Target account is incorrect."
        );
        Assert.assertEquals(
                confirmPage.getAmount(),
                TRANSFER_AMOUNT,
                "Transfer amount is incorrect."
        );

        InternalTransferOtpPage otpPage = confirmPage.clickConfirm();

        // PHASE 5: Get OTP from Mailinator
        String otpCode = getOtpCodeFromMailinator(registerDataB.getEmail());

        // PHASE 6: Submit OTP and verify account B balance
        switchToTab(userBTab);

        otpPage.enterOtp(otpCode);
        otpPage.clickTransfer();

        Assert.assertTrue(
                otpPage.isTransferSuccessPopupDisplayed(),
                "Transfer success popup is not displayed."
        );
        Assert.assertEquals(
                otpPage.getTransferSuccessMessage(),
                "Chuyển tiền thành công",
                "Transfer success message is incorrect."
        );

        otpPage.closeSuccessPopup();

        DashboardPage dashboardPageBAfterTransfer = otpPage.goToAccounts();
        AccountDetailPage accountBDetailAfterTransfer = dashboardPageBAfterTransfer.openAccountDetail(accountNumberB);

        long balanceAfterTransfer = accountBDetailAfterTransfer.getBalance();

        Assert.assertEquals(
                balanceAfterTransfer,
                balanceBeforeTransfer - TRANSFER_AMOUNT - TRANSFER_FEE,
                "Account B balance after transfer is incorrect."
        );

        accountBDetailAfterTransfer.logout();
        clearSession();

        // PHASE 7: Verify account A received money
        openNewTab(USER_BASE_URL);

        DashboardPage userADashboard = loginAsUser(userA);
        AccountDetailPage accountADetail = userADashboard.openAccountDetail(accountNumberA);

        long balanceAAfter = accountADetail.getBalance();
        Assert.assertEquals(
                balanceAAfter,
                TRANSFER_AMOUNT,
                "Account A balance after receiving transfer is incorrect."
        );
    }

    @Test(description = "EB-03 Verify OTP email is received and system shows error when an invalid OTP is entered")
    public void EB03_verify_invalid_otp_shows_error() {

        RegisterData registerDataA = TestData.validRegister("tram_test_a");
        RegisterData registerDataB = TestData.validRegister("tram_test_b");

        User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
        User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());

        // PHASE 1: Setup accounts
        String accountNumberA = registerActivateLoginAndOpenAccount(registerDataA, AccountType.CURRENT_ACCOUNT);
        String accountNumberB = registerActivateLoginAndOpenAccount(registerDataB, AccountType.CURRENT_ACCOUNT);

        // PHASE 2: Deposit money to B
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        // PHASE 3: User B transfer to OTP page
        String userBTab = openNewTab(USER_BASE_URL);
        DashboardPage dashboardPageB = loginAsUser(userB);

        AccountDetailPage accountDetailPageB = dashboardPageB.openAccountDetail(accountNumberB);
        long balanceBefore = accountDetailPageB.getBalance();

        InternalTransferPage transferPage = accountDetailPageB.goToInternalTransfer();

        InternalTransferData transferData = new InternalTransferData(
                accountNumberB,
                accountNumberA,
                TRANSFER_AMOUNT,
                "Invalid OTP test"
        );

        transferPage.fillTransferForm(transferData);
        InternalTransferConfirmPage confirmPage = transferPage.clickConfirmButton();

        InternalTransferOtpPage otpPage = confirmPage.clickConfirm();

        // PHASE 4: Verify OTP email
        String otpCode = getOtpCodeFromMailinator(registerDataB.getEmail());

        // verify OTP format
        Assert.assertEquals(otpCode.length(), 10, "OTP length should be 10 characters.");
        Assert.assertTrue(
                otpCode.matches("^[A-Z0-9]{10}$"),
                "OTP format is invalid. Must be uppercase letters and digits."
        );

        // PHASE 5: Enter INVALID OTP
        switchToTab(userBTab);

        String invalidOtp = "AAAAAAAAAA";
        otpPage.enterOtp(invalidOtp);
        otpPage.clickTransfer();

        // PHASE 6: Verify error
        Assert.assertTrue(
                otpPage.isToastMessageDisplayed(),
                "Toast message is not displayed."
        );

        Assert.assertEquals(
                otpPage.getToastMessage(),
                "Sai mã OTP",
                "Toast message is incorrect."
        );

        Assert.assertTrue(
                otpPage.isInlineErrorDisplayed(),
                "Inline error message is not displayed."
        );

        Assert.assertEquals(
                otpPage.getInlineErrorMessage(),
                "Sai mã OTP",
                "Inline error message is incorrect."
        );

        // PHASE 7: Verify transaction NOT completed
        DashboardPage dashboardAfter = otpPage.goToAccounts();
        AccountDetailPage accountDetailAfter = dashboardAfter.openAccountDetail(accountNumberB);

        long balanceAfter = accountDetailAfter.getBalance();

        Assert.assertEquals(
                balanceAfter,
                balanceBefore,
                "Balance should NOT change when OTP is invalid."
        );

        accountDetailAfter.logout();
    }
}
