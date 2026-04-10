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
import java.util.List;

public class InternalTransferTest extends BaseTest {
    private static final long DEPOSIT_AMOUNT = 100_000;
    private static final long TRANSFER_AMOUNT = 10_000;

    private static final RegisterData FIXED_SENDER = TestData.TRANSFER_SENDER;
    private static final RegisterData FIXED_RECEIVER = TestData.TRANSFER_RECEIVER;

    private DashboardPage dashboardPage;

    @Test(description = "EB-03 Verify OTP email is received and system shows error when an invalid OTP is entered")
    public void EB03_verify_invalid_otp_shows_error() {
        // PHASE 1: Login fixed receiver and open account
        dashboardPage = loginAsUser(FIXED_RECEIVER);
        String receiverAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        // PHASE 2: Login fixed sender and open account
        dashboardPage = loginAsUser(FIXED_SENDER);
        String senderAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 3: Admin deposits money into sender account
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(senderAccountNumber, DEPOSIT_AMOUNT);

        // PHASE 4: Sender logs in and performs transfer until OTP page
        String senderTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(FIXED_SENDER);

        AccountDetailPage senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceBeforeTransfer = senderAccountDetail.getBalance();

        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Sender account balance before transfer is incorrect."
        );

        InternalTransferData transferData = new InternalTransferData(
                senderAccountNumber,
                receiverAccountNumber,
                TRANSFER_AMOUNT,
                "Invalid OTP test"
        );

        InternalTransferPage transferPage = senderAccountDetail.goToInternalTransfer();
        transferPage.fillTransferForm(transferData);
        TransferConfirmPage confirmPage = transferPage.clickConfirm();

        Assert.assertEquals(
                confirmPage.getTransferData(),
                transferData,
                "Transfer confirm data is incorrect."
        );

        TransferOtpPage otpPage = confirmPage.clickConfirm();

        otpPage.waitForOtpPageLoaded();
        Assert.assertTrue(
                otpPage.isOtpInputDisplayed(),
                "OTP input is not displayed."
        );

        // PHASE 5: Verify OTP email
        MailinatorEmailPage otpEmailPage = openOtpEmail(FIXED_SENDER.getEmail());
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
        switchToTab(senderTab);

        String invalidOtp = "AAAAAAAAAA";
        Assert.assertNotEquals(
                otpEmailData.getOtpCode(),
                invalidOtp,
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
        senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceAfterFailedTransfer = senderAccountDetail.getBalance();

        Assert.assertEquals(
                balanceAfterFailedTransfer,
                balanceBeforeTransfer,
                "Balance should remain unchanged after failed transfer."
        );
    }

    @Test(description = "EB-04 Verify user can complete an internal transfer via OTP and the transaction is recorded in GIAO DỊCH GẦN NHẤT.")
    public void EB04_user_can_transfer_internally_successfully() {
        RegisterData userARegisterData = TestData.validRegister("user_a");
        RegisterData userBRegisterData = TestData.validRegister("user_b");
        // RegisterData registerDataA = TestData.validRegister("user_a_101437550", false);
        // RegisterData registerDataB = TestData.validRegister("user_b_101446376", false);
        // String accountNumberA = "100002424";
        // String accountNumberB = "100002426";

        // PHASE 1: Register, activate, and open account for user A
        registerAndActivateUser(userARegisterData);
        dashboardPage = loginAsUser(userARegisterData);
        String accountNumberA = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        // PHASE 2: Register, activate, and open account for user B
        registerAndActivateUser(userBRegisterData);
        dashboardPage = loginAsUser(userBRegisterData);
        String accountNumberB = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 3: Admin deposits money into account B
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        // PHASE 4: User B logs in and performs internal transfer
        String userBTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userBRegisterData);

        AccountDetailPage userBAccountDetailBeforeTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceBeforeTransfer = userBAccountDetailBeforeTransfer.getBalance();

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

        InternalTransferPage internalTransferPage = userBAccountDetailBeforeTransfer.goToInternalTransfer();
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

        otpPage.waitForOtpPageLoaded();
        Assert.assertTrue(
                otpPage.isOtpInputDisplayed(),
                "OTP input is not displayed."
        );

        MailinatorEmailPage otpEmail = openOtpEmail(userBRegisterData.getEmail());
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

        AccountDetailPage userBAccountDetailAfterTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceAfterTransfer = userBAccountDetailAfterTransfer.getBalance();

        Assert.assertEquals(
                balanceAfterTransfer,
                balanceBeforeTransfer - TRANSFER_AMOUNT - TransferType.INTERNAL.getFee(),
                "Account B balance after transfer is incorrect."
        );

        userBAccountDetailAfterTransfer.logout();

        // PHASE 7: Verify receiver account A received money
        openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userARegisterData);

        AccountDetailPage userAAccountDetail = dashboardPage.openAccountDetail(accountNumberA);
        long balanceAAfter = userAAccountDetail.getBalance();

        Assert.assertEquals(
                balanceAAfter,
                TRANSFER_AMOUNT,
                "Account A balance after receiving transfer is incorrect."
        );
    }

    @Test(description = "EB-05 Verify validation is displayed when required fields are empty")
    public void EB05_verify_required_fields_when_empty() {
        dashboardPage = loginAsUser(FIXED_SENDER);
        InternalTransferPage transferPage = dashboardPage.goToInternalTransfer();

        // Leave all required fields empty and click confirm
        transferPage.clearReceiverAccount();
        transferPage.clickConfirm();

        // Verify system does not navigate to confirmation page
        Assert.assertTrue(
                transferPage.isStillOnTransferPage(),
                "System should not navigate to confirmation page."
        );

        // Verify all required fields are highlighted invalid
        Assert.assertTrue(
                transferPage.isSourceAccountInvalid(),
                "Source account field is not highlighted invalid."
        );

        Assert.assertTrue(
                transferPage.isReceiverAccountInvalid(),
                "Receiver account field is not highlighted invalid."
        );

        Assert.assertTrue(
                transferPage.isAmountInvalid(),
                "Amount field is not highlighted invalid."
        );

        Assert.assertTrue(
                transferPage.isDescriptionInvalid(),
                "Description field is not highlighted invalid."
        );

        // Verify all validation toast messages are displayed
        List<String> actualToastMessages = transferPage.getToastMessages();

        Assert.assertTrue(
                actualToastMessages.contains("Mời chọn tài khoản"),
                "Toast message 'Mời chọn tài khoản' is not displayed."
        );

        Assert.assertTrue(
                actualToastMessages.contains("Nhập số tiền"),
                "Toast message 'Nhập số tiền' is not displayed."
        );

        // Count how many times the toast message appears
        Assert.assertEquals(
                java.util.Collections.frequency(actualToastMessages, "Nhập nội dung"),
                2,
                "Toast message 'Nhập nội dung' should be displayed twice."
        );
    }

    @Test(description = "EB-06 Verify system prevents internal transfer when transfer amount is greater than available balance")
    public void EB06_verify_transfer_amount_greater_than_available_balance() {
        // PHASE 1: Login fixed receiver and open account
        dashboardPage = loginAsUser(FIXED_RECEIVER);
        String receiverAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        // PHASE 2: Login fixed sender and open account
        dashboardPage = loginAsUser(FIXED_SENDER);
        String senderAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 3: Admin deposits money into sender account
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(senderAccountNumber, DEPOSIT_AMOUNT);

        // PHASE 4: Sender logs in and performs transfer until OTP page
        openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(FIXED_SENDER);

        AccountDetailPage senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceBeforeTransfer = senderAccountDetail.getBalance();

        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Sender account balance before transfer is incorrect."
        );

        long invalidTransferAmount = balanceBeforeTransfer + 1;

        InternalTransferData transferData = new InternalTransferData(
                senderAccountNumber,
                receiverAccountNumber,
                invalidTransferAmount,
                "Amount greater than balance test"
        );

        InternalTransferPage transferPage = senderAccountDetail.goToInternalTransfer();
        transferPage.fillTransferForm(transferData);
        transferPage.clickConfirm();

        // PHASE 5: Verify system does not navigate to confirmation page
        Assert.assertTrue(
                transferPage.isStillOnTransferPage(),
                "System should not navigate to the confirmation page."
        );

        // PHASE 6: Verify toast message
        Assert.assertTrue(
                transferPage.getToastMessages().contains("Số tiền vượt mức"),
                "Toast message 'Số tiền vượt mức' is not displayed."
        );
    }
}
