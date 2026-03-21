import base.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.account.UserProfilePage;
import pages.admin.AdminDashboardPage;
import pages.admin.DepositMoneyPage;
import pages.email.MailInboxPage;
import pages.transfer.internal.InternalTransferConfirmPage;
import pages.transfer.internal.InternalTransferOtpPage;
import pages.transfer.internal.InternalTransferPage;

public class EB_04 extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(EB_04.class);

    @Test(description = "Verify user can successfully perform an internal transfer using OTP.")
    public void verifyInternalTransferSuccess() {
        /*
        AdminDashboardPage adminDashboardPage = loginAsAdmin();
        DepositMoneyPage depositMoneyPage = adminDashboardPage.goToDepositMoney();
        depositMoneyPage.depositToAccount(TestData.validDeposit());*/

        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);

        UserProfilePage userProfilePage = dashboardPage.goToUserProfile();
        String email = userProfilePage.getEmailAddress();

        DashboardPage dashboardPage2 = userProfilePage.goToAccounts();

        int index = 1;
        String accountSource = dashboardPage.getAccountByIndex(index);
        AccountDetailPage accountDetailPage = dashboardPage.openAccountDetail(accountSource);
        long balanceSource = accountDetailPage.getBalance();
        System.out.println(balanceSource);

        DashboardPage dashboardPage1 = accountDetailPage.goToAccounts();
        int index1 = 2;
        String accountReceiver = dashboardPage1.getAccountByIndex(index1);
        AccountDetailPage accountDetailPage1 = dashboardPage1.openAccountDetail(accountReceiver);
        long balanceReceiver = accountDetailPage1.getBalance();
        System.out.println(balanceReceiver);

        InternalTransferPage internalTransferPage = accountDetailPage1.goToInternalTransfer();
        internalTransferPage.selectSourceAccount(accountSource);
        internalTransferPage.enterReceiverAccount(accountReceiver);
        internalTransferPage.enterAmount(10000);
        internalTransferPage.enterDescription("ck");

        InternalTransferConfirmPage internalTransferConfirmPage = internalTransferPage.clickConfirmButton();
        InternalTransferOtpPage internalTransferOtpPage = internalTransferConfirmPage.goToInternalTransferOTPPage();
        Assert.assertTrue(internalTransferOtpPage.isTransactionIdDisplayed(), "Field Mã giao dịch is invisible");

        MailInboxPage mailInboxPage = goToEmailPage();
        mailInboxPage.openEmail(email);
        mailInboxPage.refreshInbox();
        mailInboxPage.clickSendCodeOTP();
        String otp = mailInboxPage.getOtpFromEmail();

        backToBankPage();
        internalTransferOtpPage.enterOTP(otp);
        internalTransferOtpPage.clickConfirmButton();

        Assert.assertEquals(internalTransferOtpPage.getTransferSuccessMessage(), "Chuyển tiền thành công", "Transfer message is incorrect!");
        internalTransferOtpPage.closeNotificationPopup();

        DashboardPage dashboardPage3 = dashboardPage.goToAccounts();
        AccountDetailPage accountDetailPage2 = dashboardPage.openAccountDetail(accountSource);
        long balanceSourceAfter = accountDetailPage2.getBalance();
        System.out.println(balanceSourceAfter);
        Assert.assertTrue(accountDetailPage2.isInternalTransferSourceBalanceCorrect(balanceSource, balanceSourceAfter, 10000), "Source account balance is not decreased correctly");

        DashboardPage dashboardPage4 = accountDetailPage2.goToAccounts();
        AccountDetailPage accountDetailPage3 = dashboardPage4.openAccountDetail(accountReceiver);
        long balanceReceiverAfter = accountDetailPage3.getBalance();
        System.out.println(balanceReceiverAfter);
        Assert.assertTrue(accountDetailPage3.isReceiverBalanceIncreasedCorrectly(balanceReceiver, balanceReceiverAfter, 10000), "Receiver account balance is not increased correctly");

    }
}
