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

import static org.assertj.core.api.Assertions.assertThat;

public class InterbankTransferTest extends BaseTest {
    private static final long DEPOSIT_AMOUNT = 100_000;
    private static final long TRANSFER_AMOUNT = 10_000;
    private static final long TRANSFER_FEE = 3300;
    DashboardPage dashboardPage;

    // @Test(description = "EB-08 Verify user can successfully perform an interbank transfer using OTP")
    // public void EB08_user_can_transfer_interbank_successfully() {
    //     RegisterData registerDataA = TestData.validRegister("tram_test_a");
    //     User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
    //
    //     // TODO: remove register, just open account
    //     // RegisterData registerDataA = TestData.validRegister("tram_test_a_32043", false);
    //     // User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
    //     // String accountNumberA = "100002384";
    //
    //
    //     // PHASE 1: Register, activate, and open account A
    //     registerAndActivateUser(registerDataA);
    //     dashboardPage = loginAsUser(userA);
    //     String accountNumberA = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
    //     dashboardPage.logout();
    //     clearSession();
    //
    //     // PHASE 2: Admin deposits money into account A
    //     openNewTab(ADMIN_BASE_URL);
    //     depositMoneyAndLogout(accountNumberA, DEPOSIT_AMOUNT);
    //
    //     // PHASE 3: User A logs in and performs interbank transfer
    //     String userATab = openNewTab(USER_BASE_URL);
    //     dashboardPage = loginAsUser(userA);
    //
    //     AccountDetailPage accountADetailBeforeTransfer = dashboardPage.openAccountDetail(accountNumberA);
    //     long balanceBeforeTransfer = accountADetailBeforeTransfer.getBalance();
    //
    //     // Assert.assertEquals(
    //     //         balanceBeforeTransfer,
    //     //         DEPOSIT_AMOUNT,
    //     //         "Account A balance before transfer is incorrect."
    //     // );
    //
    //     InterbankTransferData transferData = TestData.validInterbankTransferFrom(accountNumberA, TRANSFER_AMOUNT);
    //
    //     InterbankTransferPage interbankTransferPage = accountADetailBeforeTransfer.goToInterbankTransfer();
    //     interbankTransferPage.fillTransferForm(transferData);
    //     TransferConfirmPage confirmPage = interbankTransferPage.clickConfirm();
    //
    //     // PHASE 5: Get OTP
    //     LocalDateTime transferConfirmStartTime = LocalDateTime.now();
    //
    //     TransferOtpPage otpPage = confirmPage.clickConfirm();
    //
    //     LocalDateTime transferConfirmEndTime = LocalDateTime.now();
    //
    //     MailinatorEmailPage otpEmail = openOtpEmail(registerDataA.getEmail());
    //     String otpCode = otpEmail.getOtpEmailData().getOtpCode();
    //
    //     switchToTab(userATab);
    //
    //     otpPage.submitOtp(otpCode);
    //
    //     Assert.assertEquals(
    //             otpPage.getTransferSuccessMessage(),
    //             "Chuyển tiền thành công",
    //             "Transfer success message is incorrect."
    //     );
    //
    //     otpPage.closeSuccessPopup();
    //
    //     dashboardPage = otpPage.goToAccounts();
    //
    //     RecentTransactionData latestTransaction = dashboardPage.getLatestRecentTransaction();
    //
    //     LocalDateTime actualMinute = latestTransaction.getDateTime().withSecond(0).withNano(0);
    //     LocalDateTime startMinute = transferConfirmStartTime.minusMinutes(1).withSecond(0).withNano(0);
    //     LocalDateTime endMinute = transferConfirmEndTime.plusMinutes(1).withSecond(0).withNano(0);
    //
    //     Assert.assertTrue(
    //             !actualMinute.isBefore(startMinute) && !actualMinute.isAfter(endMinute),
    //             "Latest transaction datetime is incorrect."
    //     );
    //
    //     Assert.assertEquals(
    //             latestTransaction.getAccountNumber(),
    //             accountNumberA,
    //             "Account number is incorrect."
    //     );
    //
    //     Assert.assertEquals(
    //             latestTransaction.getAmountValue(),
    //             -TRANSFER_AMOUNT,
    //             "Transaction amount is incorrect."
    //     );
    //
    //     AccountDetailPage accountADetailAfter = dashboardPage.openAccountDetail(accountNumberA);
    //     long balanceAfter = accountADetailAfter.getBalance();
    //
    //     Assert.assertEquals(
    //             balanceAfter,
    //             balanceBeforeTransfer - TRANSFER_AMOUNT - TransferType.INTERBANK.getFee(),
    //             "Balance after interbank transfer is incorrect."
    //     );
    //
    //     assertThat(confirmPage.getTransferData())
    //             .usingRecursiveComparison()
    //             .isEqualTo(
    //                     new InternalTransferData(
    //                             transferData.getSourceAccount(),
    //                             transferData.getReceiverAccount(),
    //                             transferData.getAmount(),
    //                             transferData.getDescription()
    //                     )
    //             );
    //
    //     // PHASE 4: Get OTP from Mailinator
    //     // TODO: check time after submit confirm
    //     LocalDateTime transferConfirmStartTime = LocalDateTime.now();
    //     TransferOtpPage otpPage = confirmPage.clickConfirm();
    //     LocalDateTime transferConfirmEndTime = LocalDateTime.now();
    //
    //     MailinatorEmailPage otpEmail = openOtpEmail(registerDataA.getEmail());
    //     String otpCode = otpEmail.getOtpEmailData().getOtpCode();
    //
    //     // PHASE 5: Submit OTP and verify sender balance + transaction log
    //     switchToTab(userATab);
    //     otpPage.submitOtp(otpCode);
    //
    //     Assert.assertEquals(
    //             otpPage.getTransferSuccessMessage(),
    //             "Chuyển tiền thành công",
    //             "Transfer success message is incorrect."
    //     );
    //
    //     otpPage.closeSuccessPopup();
    //
    //     dashboardPage = otpPage.goToAccounts();
    //
    //     RecentTransactionData latestTransaction = dashboardPage.getLatestRecentTransaction();
    //
    //     LocalDateTime actualMinute = latestTransaction.getDateTime().withSecond(0).withNano(0);
    //     LocalDateTime startMinute = transferConfirmStartTime.minusMinutes(1).withSecond(0).withNano(0);
    //     LocalDateTime endMinute = transferConfirmEndTime.plusMinutes(1).withSecond(0).withNano(0);
    //
    //     Assert.assertTrue(
    //             !actualMinute.isBefore(startMinute) && !actualMinute.isAfter(endMinute),
    //             "Latest transaction datetime is incorrect. Actual: " + latestTransaction.getDateTimeText()
    //     );
    //
    //     Assert.assertEquals(
    //             latestTransaction.getAccountNumber(),
    //             accountNumberA,
    //             "Latest transaction account number is incorrect."
    //     );
    //
    //     Assert.assertEquals(
    //             latestTransaction.getAmountValue(),
    //             -TRANSFER_AMOUNT,
    //             "Latest transaction amount is incorrect."
    //     );
    //
    //     AccountDetailPage accountADetailAfterTransfer = dashboardPage.openAccountDetail(accountNumberA);
    //     long balanceAfterTransfer = accountADetailAfterTransfer.getBalance();
    //
    //     Assert.assertEquals(
    //             balanceAfterTransfer,
    //             balanceBeforeTransfer - TRANSFER_AMOUNT - TRANSFER_FEE,
    //             "Account A balance after transfer is incorrect."
    //     );
    // }
}