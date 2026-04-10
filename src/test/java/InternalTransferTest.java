import base.BaseTest;
import models.*;
import models.enums.AccountType;
import models.enums.TransferType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.email.MailinatorEmailPage;
import pages.transfer.TransferConfirmPage;
import pages.transfer.TransferOtpPage;
import pages.transfer.InternalTransferPage;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalTransferTest extends BaseTest {
    // TODO: should be global constants >> DONE
    private static final long DEPOSIT_AMOUNT = 100_000;
    private static final long TRANSFER_AMOUNT = 10_000;
    DashboardPage dashboardPage;

    @Test(description = "EB-03 Verify OTP email is received and system shows error when an invalid OTP is entered")
    public void EB03_verify_invalid_otp_shows_error() {
        RegisterData registerDataA = TestData.validRegister("tram_test_a");
        RegisterData registerDataB = TestData.validRegister("tram_test_b");

        User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
        User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());

        DashboardPage dashboardPage;

        // PHASE 1: Register, activate, and open account for user A
        registerAndActivateUser(registerDataA);
        dashboardPage = loginAsUser(userA);
        String accountNumberA = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        // PHASE 2: Register, activate, and open account for user B
        registerAndActivateUser(registerDataB);
        dashboardPage = loginAsUser(userB);
        String accountNumberB = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 3: Admin deposits money into account B
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        // PHASE 4: User B logs in and performs transfer until OTP page
        String userBTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userB);

        AccountDetailPage accountBDetail = dashboardPage.openAccountDetail(accountNumberB);
        long balanceBeforeTransfer = accountBDetail.getBalance();

        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Account B balance before transfer is incorrect."
        );

        InternalTransferData transferData = new InternalTransferData(
                accountNumberB,
                accountNumberA,
                TRANSFER_AMOUNT,
                "Invalid OTP test"
        );

        InternalTransferPage transferPage = accountBDetail.goToInternalTransfer();
        transferPage.fillTransferForm(transferData);
        TransferConfirmPage confirmPage = transferPage.clickConfirm();

        // TODO: assertJ
        assertThat(confirmPage.getTransferData())
                .usingRecursiveComparison()
                .isEqualTo(transferData);

        TransferOtpPage otpPage = confirmPage.clickConfirm();

        // PHASE 5: Verify OTP email
        MailinatorEmailPage otpEmailPage = openOtpEmail(registerDataB.getEmail());
        OtpEmailData otpEmailData = otpEmailPage.getOtpEmailData();

        Assert.assertEquals(
                otpEmailData.getSubject(),
                OTP_EMAIL_SUBJECT,
                "OTP email subject is incorrect."
        );

        Assert.assertTrue(
                otpEmailData.getBody().contains("OTP: "),
                "OTP email body should contain 'OTP: '"
        );

        Assert.assertTrue(
                otpEmailData.getOtpCode().matches("^[A-Z0-9]{10}$"),
                "OTP format is invalid."
        );

        // PHASE 6: Enter invalid OTP
        switchToTab(userBTab);

        String invalidOtp = "AAAAAAAAAA";
        Assert.assertNotEquals(
                invalidOtp,
                otpEmailData.getOtpCode(),
                "Invalid OTP must be different from actual OTP."
        );

        otpPage.submitOtp(invalidOtp);

        // PHASE 7: Verify error messages
        Assert.assertEquals(
                otpPage.getToastMessage().trim(),
                "Sai mã OTP",
                "Toast message is incorrect."
        );
        Assert.assertEquals(
                otpPage.getInlineErrorMessage().trim(),
                "Sai mã OTP",
                "Inline error message is incorrect."
        );

        // PHASE 8: Verify balance is unchanged
        dashboardPage = otpPage.goToAccounts();
        accountBDetail = dashboardPage.openAccountDetail(accountNumberB);
        long balanceAfterFailed = accountBDetail.getBalance();

        Assert.assertEquals(
                balanceAfterFailed,
                balanceBeforeTransfer,
                "Balance should remain unchanged after failed transfer."
        );
    }

    @Test(description = "Verify user can complete an internal transfer via OTP and the transaction is recorded in GIAO DỊCH GẦN NHẤT.")
    public void EB04_user_can_transfer_internally_successfully() {
        RegisterData registerDataA = TestData.validRegister("user_a");
        RegisterData registerDataB = TestData.validRegister("user_b");
        // RegisterData registerDataA = TestData.validRegister("user_a_101437550", false);
        // RegisterData registerDataB = TestData.validRegister("user_b_101446376", false);
        // String accountNumberA = "100002424";
        // String accountNumberB = "100002426";

        User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
        User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());

        // PHASE 1: Register, activate, and open account for user A
        registerAndActivateUser(registerDataA);
        dashboardPage = loginAsUser(userA);
        String accountNumberA = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        // PHASE 2: Register, activate, and open account for user B
        registerAndActivateUser(registerDataB);
        dashboardPage = loginAsUser(userB);
        String accountNumberB = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 3: Admin deposits money into account B
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        // PHASE 4: User B logs in and performs internal transfer
        String userBTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userB);

        AccountDetailPage accountBDetailBeforeTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceBeforeTransfer = accountBDetailBeforeTransfer.getBalance();

        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Account B balance before transfer is incorrect."
        );

        InternalTransferData transferData = new InternalTransferData(
                accountNumberB,
                accountNumberA,
                TRANSFER_AMOUNT,
                "Internal transfer test"
        );

        InternalTransferPage internalTransferPage = accountBDetailBeforeTransfer.goToInternalTransfer();
        internalTransferPage.fillTransferForm(transferData);
        TransferConfirmPage confirmPage = internalTransferPage.clickConfirm();

        Assert.assertEquals(
                confirmPage.getTransferData(),
                transferData,
                "Transfer confirm data is incorrect."
        );
        // Assert.assertEquals(actual, expected)
        // actual.equals(expected)

        // PHASE 5: Get OTP from Mailinator
        LocalDateTime transferConfirmStartTime = LocalDateTime.now();
        TransferOtpPage otpPage = confirmPage.clickConfirm();
        LocalDateTime transferConfirmEndTime = LocalDateTime.now();

        MailinatorEmailPage otpEmail = openOtpEmail(registerDataB.getEmail());
        String otpCode = otpEmail.getOtpEmailData().getOtpCode();

        // PHASE 6: Submit OTP and verify sender balance + transaction log
        switchToTab(userBTab);
        otpPage.submitOtp(otpCode);

        Assert.assertEquals(
                otpPage.getTransferSuccessMessage(),
                "Chuyển tiền thành công",
                "Transfer success message is incorrect."
        );

        otpPage.closeSuccessPopup();

        dashboardPage = otpPage.goToAccounts();

        RecentTransactionData latestTransaction = dashboardPage.getLatestRecentTransaction();

        LocalDateTime actualMinute = latestTransaction.getDateTime().withSecond(0).withNano(0);
        LocalDateTime startMinute = transferConfirmStartTime.minusMinutes(1).withSecond(0).withNano(0);
        LocalDateTime endMinute = transferConfirmEndTime.plusMinutes(1).withSecond(0).withNano(0);

        Assert.assertTrue(
                !actualMinute.isBefore(startMinute) && !actualMinute.isAfter(endMinute),
                "Latest transaction datetime is incorrect. Actual: " + latestTransaction.getDateTimeText()
        );

        Assert.assertEquals(
                latestTransaction.getAccountNumber(),
                accountNumberB,
                "Latest transaction account number is incorrect."
        );

        Assert.assertEquals(
                latestTransaction.getAmountValue(),
                -TRANSFER_AMOUNT,
                "Latest transaction amount is incorrect."
        );

        AccountDetailPage accountBDetailAfterTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceAfterTransfer = accountBDetailAfterTransfer.getBalance();

        Assert.assertEquals(
                balanceAfterTransfer,
                balanceBeforeTransfer - TRANSFER_AMOUNT - TransferType.INTERNAL.getFee(),
                "Account B balance after transfer is incorrect."
        );

        accountBDetailAfterTransfer.logout();

        // PHASE 7: Verify receiver account A received money
        openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userA);

        AccountDetailPage accountADetail = dashboardPage.openAccountDetail(accountNumberA);
        long balanceAAfter = accountADetail.getBalance();

        Assert.assertEquals(
                balanceAAfter,
                TRANSFER_AMOUNT,
                "Account A balance after receiving transfer is incorrect."
        );
    }
}
