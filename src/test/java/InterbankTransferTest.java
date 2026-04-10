import base.BaseTest;
import models.*;
import models.enums.AccountType;
import models.enums.TransferType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.email.MailinatorEmailPage;
import pages.transfer.InterbankTransferPage;
import pages.transfer.TransferConfirmPage;
import pages.transfer.TransferOtpPage;

import java.time.LocalDateTime;

public class InterbankTransferTest extends BaseTest {
    private static final long DEPOSIT_AMOUNT = 100_000;
    private static final long TRANSFER_AMOUNT = 10_000;

    private static final RegisterData FIXED_SENDER = TestData.TRANSFER_SENDER;
    protected DashboardPage dashboardPage;

    @Test(description = "EB-07 Verify user can complete an interbank transfer via OTP and the transaction is recorded in \"GIAO DỊCH GẦN NHẤT\".")
    public void EB07_user_can_transfer_interbank_successfully() {
        // PHASE 1: Login fixed sender and open account
        dashboardPage = loginAsUser(FIXED_SENDER);
        String senderAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 2: Admin deposits money into sender account
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(senderAccountNumber, DEPOSIT_AMOUNT);

        // PHASE 3: Sender logs in and performs transfer until OTP page
        String userATab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(FIXED_SENDER);

        AccountDetailPage senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceBeforeTransfer = senderAccountDetail.getBalance();

        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Sender account balance before transfer is incorrect."
        );

        InterbankTransferData transferData = TestData.validInterbankTransferFrom(senderAccountNumber, TRANSFER_AMOUNT);

        InterbankTransferPage transferPage = senderAccountDetail.goToInterbankTransfer();
        transferPage.fillTransferForm(transferData);
        TransferConfirmPage confirmPage = transferPage.clickConfirm();

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

        // PHASE 4: Get OTP from Mailinator
        LocalDateTime transferConfirmStartTime = LocalDateTime.now();
        TransferOtpPage otpPage = confirmPage.clickConfirm();
        LocalDateTime transferConfirmEndTime = LocalDateTime.now();

        otpPage.waitForOtpPageLoaded();
        Assert.assertTrue(
                otpPage.isOtpInputDisplayed(),
                "OTP input is not displayed."
        );

        MailinatorEmailPage otpEmail = openOtpEmail(FIXED_SENDER.getEmail());
        String otpCode = otpEmail.getOtpEmailData().getOtpCode();

        // PHASE 5: Submit OTP and verify sender balance + transaction log
        switchToTab(userATab);
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
                "Latest transaction datetime is incorrect."
        );

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

        // Check account details
        senderAccountDetail = dashboardPage.openAccountDetail(senderAccountNumber);
        long balanceAfter = senderAccountDetail.getBalance();

        Assert.assertEquals(
                balanceAfter,
                balanceBeforeTransfer - TRANSFER_AMOUNT - TransferType.INTERBANK.getFee(),
                "Balance after interbank transfer is incorrect."
        );
    }
}