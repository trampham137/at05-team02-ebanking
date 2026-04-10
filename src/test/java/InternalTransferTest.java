import base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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

    @Feature("Internal Transfer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify system shows OTP error when invalid OTP is entered during internal transfer.")
    @Test(description = "EB-03 Verify OTP email is received and system shows error when an invalid OTP is entered")
    public void EB03_verify_invalid_otp_shows_error() {

        dashboardPage = loginAsUser(FIXED_RECEIVER);
        String receiverAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        dashboardPage = loginAsUser(FIXED_SENDER);
        String senderAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(senderAccountNumber, DEPOSIT_AMOUNT);

        String senderTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(FIXED_SENDER);

        AccountDetailPage senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceBeforeTransfer = senderAccountDetail.getBalance();

        Allure.step("Verify sender balance before transfer");
        Assert.assertEquals(balanceBeforeTransfer, DEPOSIT_AMOUNT);

        InternalTransferData transferData = new InternalTransferData(
                senderAccountNumber,
                receiverAccountNumber,
                TRANSFER_AMOUNT,
                "Invalid OTP test"
        );

        InternalTransferPage transferPage = senderAccountDetail.goToInternalTransfer();
        transferPage.fillTransferForm(transferData);

        TransferConfirmPage confirmPage = transferPage.clickConfirm();

        Allure.step("Verify transfer confirm data");
        Assert.assertEquals(confirmPage.getTransferData(), transferData);

        TransferOtpPage otpPage = confirmPage.clickConfirm();

        otpPage.waitForOtpPageLoaded();

        Allure.step("Verify OTP page displayed");
        Assert.assertTrue(otpPage.isOtpInputDisplayed());

        MailinatorEmailPage otpEmailPage = openOtpEmail(FIXED_SENDER.getEmail());
        OtpEmailData otpEmailData = otpEmailPage.getOtpEmailData();

        Allure.step("Verify OTP email content");
        Assert.assertEquals(otpEmailData.getSubject(), OTP_EMAIL_SUBJECT);
        Assert.assertTrue(otpEmailData.getBody().contains("OTP: "));
        Assert.assertTrue(otpEmailData.getOtpCode().matches("^[A-Z0-9]{10}$"));

        switchToTab(senderTab);

        String invalidOtp = "AAAAAAAAAA";

        Assert.assertNotEquals(otpEmailData.getOtpCode(), invalidOtp);

        otpPage.submitOtp(invalidOtp);

        Allure.step("Verify OTP error messages displayed");
        Assert.assertEquals(otpPage.getToastMessage().trim(), "Sai mã OTP");
        Assert.assertEquals(otpPage.getInlineErrorMessage().trim(), "Sai mã OTP");

        dashboardPage = otpPage.goToAccounts();

        senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceAfterFailedTransfer = senderAccountDetail.getBalance();

        Allure.step("Verify balance remains unchanged after failed transfer");
        Assert.assertEquals(balanceAfterFailedTransfer, balanceBeforeTransfer);
    }


    @Feature("Internal Transfer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can complete internal transfer successfully and balances/transaction logs are updated correctly.")
    @Test(description = "EB-04 Verify user can complete an internal transfer via OTP and the transaction is recorded in GIAO DỊCH GẦN NHẤT.")
    public void EB04_user_can_transfer_internally_successfully() {

        RegisterData userARegisterData = TestData.validRegister("user_a");
        RegisterData userBRegisterData = TestData.validRegister("user_b");

        registerAndActivateUser(userARegisterData);
        dashboardPage = loginAsUser(userARegisterData);
        String accountNumberA = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        registerAndActivateUser(userBRegisterData);
        dashboardPage = loginAsUser(userBRegisterData);
        String accountNumberB = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        String userBTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userBRegisterData);

        AccountDetailPage userBAccountDetailBeforeTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceBeforeTransfer = userBAccountDetailBeforeTransfer.getBalance();

        Allure.step("Verify sender balance before transfer");
        Assert.assertEquals(balanceBeforeTransfer, DEPOSIT_AMOUNT);

        InternalTransferData transferData = new InternalTransferData(
                accountNumberB,
                accountNumberA,
                TRANSFER_AMOUNT,
                "Internal transfer test"
        );

        InternalTransferPage internalTransferPage = userBAccountDetailBeforeTransfer.goToInternalTransfer();
        internalTransferPage.fillTransferForm(transferData);

        TransferConfirmPage confirmPage = internalTransferPage.clickConfirm();

        Allure.step("Verify transfer confirm data");
        Assert.assertEquals(confirmPage.getTransferData(), transferData);

        LocalDateTime transferConfirmStartTime = LocalDateTime.now();
        TransferOtpPage otpPage = confirmPage.clickConfirm();
        LocalDateTime transferConfirmEndTime = LocalDateTime.now();

        otpPage.waitForOtpPageLoaded();

        Allure.step("Verify OTP page displayed");
        Assert.assertTrue(otpPage.isOtpInputDisplayed());

        MailinatorEmailPage otpEmail = openOtpEmail(userBRegisterData.getEmail());
        String otpCode = otpEmail.getOtpEmailData().getOtpCode();

        switchToTab(userBTab);

        otpPage.submitOtp(otpCode);

        Allure.step("Verify transfer success message");
        Assert.assertEquals(
                otpPage.getTransferSuccessMessage(),
                "Chuyển tiền thành công"
        );

        otpPage.closeSuccessPopup();

        dashboardPage = otpPage.goToAccounts();

        RecentTransactionData latestTransaction = dashboardPage.getLatestRecentTransaction();

        LocalDateTime actualMinute = latestTransaction.getDateTime().withSecond(0).withNano(0);
        LocalDateTime startMinute = transferConfirmStartTime.minusMinutes(1).withSecond(0).withNano(0);
        LocalDateTime endMinute = transferConfirmEndTime.plusMinutes(1).withSecond(0).withNano(0);

        Allure.step("Verify latest transaction information");
        Assert.assertTrue(!actualMinute.isBefore(startMinute) && !actualMinute.isAfter(endMinute));
        Assert.assertEquals(latestTransaction.getAccountNumber(), accountNumberB);
        Assert.assertEquals(latestTransaction.getAmountValue(), -TRANSFER_AMOUNT);

        AccountDetailPage userBAccountDetailAfterTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceAfterTransfer = userBAccountDetailAfterTransfer.getBalance();

        Allure.step("Verify sender balance deducted correctly");
        Assert.assertEquals(
                balanceAfterTransfer,
                balanceBeforeTransfer - TRANSFER_AMOUNT - TransferType.INTERNAL.getFee()
        );

        userBAccountDetailAfterTransfer.logout();

        openNewTab(USER_BASE_URL);

        dashboardPage = loginAsUser(userARegisterData);

        AccountDetailPage userAAccountDetail = dashboardPage.openAccountDetail(accountNumberA);

        Allure.step("Verify receiver received transferred amount");
        Assert.assertEquals(userAAccountDetail.getBalance(), TRANSFER_AMOUNT);
    }


    @Feature("Internal Transfer")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify validation messages appear when required fields are empty.")
    @Test(description = "EB-05 Verify validation is displayed when required fields are empty")
    public void EB05_verify_required_fields_when_empty() {

        dashboardPage = loginAsUser(FIXED_SENDER);

        InternalTransferPage transferPage = dashboardPage.goToInternalTransfer();

        transferPage.clearReceiverAccount();
        transferPage.clickConfirm();

        Allure.step("Verify system stays on transfer page");
        Assert.assertTrue(transferPage.isStillOnTransferPage());

        Allure.step("Verify invalid field highlights");
        Assert.assertTrue(transferPage.isSourceAccountInvalid());
        Assert.assertTrue(transferPage.isReceiverAccountInvalid());
        Assert.assertTrue(transferPage.isAmountInvalid());
        Assert.assertTrue(transferPage.isDescriptionInvalid());

        List<String> actualToastMessages = transferPage.getToastMessages();

        Allure.step("Verify validation toast messages");
        Assert.assertTrue(actualToastMessages.contains("Mời chọn tài khoản"));
        Assert.assertTrue(actualToastMessages.contains("Nhập số tiền"));

        Assert.assertEquals(
                java.util.Collections.frequency(actualToastMessages, "Nhập nội dung"),
                2
        );
    }


    @Feature("Internal Transfer")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify transfer is prevented when amount exceeds available balance.")
    @Test(description = "EB-06 Verify system prevents internal transfer when transfer amount is greater than available balance")
    public void EB06_verify_transfer_amount_greater_than_available_balance() {

        dashboardPage = loginAsUser(FIXED_RECEIVER);
        String receiverAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        dashboardPage = loginAsUser(FIXED_SENDER);
        String senderAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(senderAccountNumber, DEPOSIT_AMOUNT);

        openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(FIXED_SENDER);

        AccountDetailPage senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceBeforeTransfer = senderAccountDetail.getBalance();

        Allure.step("Verify sender balance before transfer");
        Assert.assertEquals(balanceBeforeTransfer, DEPOSIT_AMOUNT);

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

        Allure.step("Verify transfer is blocked");
        Assert.assertTrue(transferPage.isStillOnTransferPage());

        Allure.step("Verify exceeded amount toast displayed");
        Assert.assertTrue(
                transferPage.getToastMessages().contains("Số tiền vượt mức")
        );
    }
}