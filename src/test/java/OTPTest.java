import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.DashboardPage;
import pages.account.UserProfilePage;
import pages.admin.AdminDashboardPage;
import pages.admin.DepositMoneyPage;
import pages.email.MailInboxPage;
import pages.transfer.internal.InternalTransferConfirmPage;
import pages.transfer.internal.InternalTransferOtpPage;
import pages.transfer.internal.InternalTransferPage;

public class OTPTest extends BaseTest {
    @Test(description = "Verify OTP email is received and system shows error when an invalid OTP is entered.")
    public void verifyOTPEmailReceived() {

        AdminDashboardPage adminDashboardPage = loginAsAdmin();
        DepositMoneyPage depositMoneyPage = adminDashboardPage.goToDepositMoney();
        depositMoneyPage.depositToAccount(TestData.validDeposit());

        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);
        UserProfilePage userProfilePage = dashboardPage.goToUserProfile();
        String email = userProfilePage.getEmailAddress();

        InternalTransferPage internalTransferPage = dashboardPage.goToInternalTransfer();
        internalTransferPage.fillTransferForm(TestData.validInternalTransfer());
        InternalTransferConfirmPage internalTransferConfirmPage = internalTransferPage.clickConfirmButton();
        InternalTransferOtpPage internalTransferOtpPage = internalTransferConfirmPage.goToInternalTransferOTPPage();
        Assert.assertTrue(internalTransferOtpPage.isTransactionIdDisplayed(), "Field Mã giao dịch is invisible");

        MailInboxPage mailInboxPage = goToEmailPage();
        mailInboxPage.openEmail(email);
        mailInboxPage.refreshInbox();
        Assert.assertTrue(mailInboxPage.isSubjectSendCode(), "Email subject is not 'Send Code'");

        mailInboxPage.clickSendCodeOTP();
        Assert.assertTrue(mailInboxPage.isContentContainOTP(), "Email content does not contain 'OTP:'");

        String otp = mailInboxPage.getOtpFromEmail();
        Assert.assertTrue(mailInboxPage.isOtpValid(otp), "OTP must be exactly 10 characters and contain only uppercase letters (A-Z) and digits (0-9)");

        backToBankPage();
        internalTransferOtpPage.enterOTP(otp + "A");
        internalTransferOtpPage.clickConfirmButton();
        Assert.assertEquals(internalTransferOtpPage.getInlineMess(), "Sai mã OTP", "Inline message 'Sai mã OTP' is NOT displayed");
        Assert.assertEquals(internalTransferOtpPage.getToastMess(), "Sai mã OTP", "Toast message 'Sai mã OTP' is NOT displayed");
    }
}
