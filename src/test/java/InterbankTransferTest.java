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
import org.testng.asserts.SoftAssert;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.email.MailinatorEmailPage;
import pages.transfer.InterbankTransferPage;
import pages.transfer.TransferConfirmPage;
import pages.transfer.TransferOtpPage;

import java.time.LocalDateTime;
import java.util.List;

public class InterbankTransferTest extends BaseTest {
    private static final long DEPOSIT_AMOUNT = 100_000;
    private static final long TRANSFER_AMOUNT = 10_000;

    private static final RegisterData FIXED_SENDER = TestData.TRANSFER_SENDER;
    protected DashboardPage dashboardPage;

    @Feature("Interbank Transfer")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can complete an interbank transfer successfully via OTP and transaction is recorded correctly.")
    @Test(description = "EB-07 Verify user can complete an interbank transfer via OTP and the transaction is recorded in GIAO DỊCH GẦN NHẤT.")
    public void EB07_user_can_transfer_interbank_successfully() {

        dashboardPage = loginAsUser(FIXED_SENDER);
        String senderAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(senderAccountNumber, DEPOSIT_AMOUNT);

        String userATab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(FIXED_SENDER);

        AccountDetailPage senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceBeforeTransfer = senderAccountDetail.getBalance();

        Allure.step("Verify sender balance before transfer equals deposited amount");
        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Sender account balance before transfer is incorrect."
        );

        InterbankTransferData transferData = TestData.validInterbankTransferFrom(senderAccountNumber, TRANSFER_AMOUNT);

        InterbankTransferPage transferPage = senderAccountDetail.goToInterbankTransfer();
        transferPage.fillTransferForm(transferData);
        TransferConfirmPage confirmPage = transferPage.clickConfirm();

        Allure.step("Verify transfer confirm information is correct");
        Assert.assertEquals(
                confirmPage.getTransferData(),
                new InternalTransferData(
                        transferData.getSourceAccount(),
                        transferData.getReceiverAccount(),
                        transferData.getAmount(),
                        transferData.getDescription()
                ),
                "Transfer confirm data is incorrect."
        );

        LocalDateTime transferConfirmStartTime = LocalDateTime.now();
        TransferOtpPage otpPage = confirmPage.clickConfirm();
        LocalDateTime transferConfirmEndTime = LocalDateTime.now();

        otpPage.waitForOtpPageLoaded();

        Allure.step("Verify OTP page is displayed");
        Assert.assertTrue(
                otpPage.isOtpInputDisplayed(),
                "OTP input is not displayed."
        );

        MailinatorEmailPage otpEmail = openOtpEmail(FIXED_SENDER.getEmail());
        String otpCode = otpEmail.getOtpEmailData().getOtpCode();

        switchToTab(userATab);
        otpPage.submitOtp(otpCode);

        Allure.step("Verify transfer success message displayed");
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

        Allure.step("Verify latest transaction datetime is within expected range");
        Assert.assertTrue(
                !actualMinute.isBefore(startMinute) && !actualMinute.isAfter(endMinute),
                "Latest transaction datetime is incorrect."
        );

        Allure.step("Verify latest transaction account number and amount");
        Assert.assertEquals(
                latestTransaction.getAccountNumber(),
                senderAccountNumber,
                "Account number is incorrect."
        );

        Assert.assertEquals(
                latestTransaction.getAmountValue(),
                -TRANSFER_AMOUNT,
                "Transaction amount is incorrect."
        );

        senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceAfter = senderAccountDetail.getBalance();

        Allure.step("Verify sender balance after transfer is deducted correctly");
        Assert.assertEquals(
                balanceAfter,
                balanceBeforeTransfer - TRANSFER_AMOUNT - TransferType.INTERBANK.getFee(),
                "Balance after interbank transfer is incorrect."
        );
    }


    @Feature("Interbank Transfer")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify validation messages are displayed when required fields are empty in interbank transfer form.")
    @Test(description = "EB-08 Verify validation is displayed when required fields are empty in interbank transfer")
    public void EB08_verify_required_fields_when_empty() {
        SoftAssert softAssert = new SoftAssert();

        dashboardPage = loginAsUser(FIXED_SENDER);
        InterbankTransferPage transferPage = dashboardPage.goToInterbankTransfer();

        transferPage.clickConfirm();

        Allure.step("Verify system stays on transfer page");
        softAssert.assertTrue(
                transferPage.isStillOnTransferPage(),
                "System should not navigate to confirmation page."
        );

        Allure.step("Verify all required fields are highlighted invalid");
        softAssert.assertTrue(transferPage.isSourceAccountInvalid());
        softAssert.assertTrue(transferPage.isReceiverAccountInvalid());
        softAssert.assertTrue(transferPage.isReceiverNameInvalid());
        softAssert.assertTrue(transferPage.isBankInvalid());
        softAssert.assertTrue(transferPage.isBranchInvalid());
        softAssert.assertTrue(transferPage.isAmountInvalid());
        softAssert.assertTrue(transferPage.isDescriptionInvalid());

        List<String> actualToastMessages = transferPage.getToastMessages();

        Allure.step("Verify all required validation toast messages are displayed");
        softAssert.assertTrue(actualToastMessages.contains("Chọn tài khoản"));
        softAssert.assertTrue(actualToastMessages.contains("Nhập số tài khoản"));
        softAssert.assertTrue(actualToastMessages.contains("Nhập tên người nhận"));
        softAssert.assertTrue(actualToastMessages.contains("Mời chọn Ngân hàng"));
        softAssert.assertTrue(actualToastMessages.contains("Mời chọn chi nhánh"));
        softAssert.assertTrue(actualToastMessages.contains("Nhập số tiền"));
        softAssert.assertTrue(actualToastMessages.contains("Nhập nội dung"));

        softAssert.assertAll();
    }
}