import example.TestBase;
import models.InternalTransferInfo;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EB_03 extends TestBase {
    @Test
    public void verifyOTPEmailReceivedAndErrorShownForInvalidOTP() {
        goToAdminPage();
        adminLoginPage.Login("1", "admin");
        adminHomePage.goToDepositPage();
        int account = 100002265;
        depositPage.enterDepositForm(account, 500000, "deposit");
        goToUserPage();
        loginPage.Login(User.STANDARD_USER);
        accountPage.goToPersonalInformationPage();
        String email = personalInformationPage.getEmailAddress();
        accountPage.goToInternalTransferPage();
        InternalTransferInfo info = new InternalTransferInfo(account, 100002266, 100000, "transfer");
        internalTransferUserPage.enterTransferInformation(info);
        internalTransferUserPage.goToTransferInformationReviewPage();
        transferInformationReviewPage.goToConfirmationPage();
        goToEmailPage();
        String OTP = emailPage.getEmail(email);
        backToBankPage();
        confirmationPage.enterOTP(OTP);
        confirmationPage.clickConfirmButton();
        Assert.assertTrue(confirmationPage.isTransactionCodeLabelDisPlay(), "Field Mã giao dịch is invisible");
    }

}
