package example;

import base.BaseUserPage;
import org.testng.annotations.Test;
import page.user.*;
import models.User;

public class TestUser extends TestBase {
    LoginPage loginPage = new LoginPage();
    BaseUserPage baseUserPage = new BaseUserPage();
    OpenAccountPage openAccountPage = new OpenAccountPage();
    //InternalTransferPage internalTransferPage = new InternalTransferPage();
    TransferInformationReviewPage transferInformationReviewPage = new TransferInformationReviewPage();
    TransferConfirmationPage transferConfirmationPage = new TransferConfirmationPage();
   // ExternalTransferPage externalTransferPage = new ExternalTransferPage();
    RegisterPage registerPage = new RegisterPage();

    @Test
    public void verifyLoginSucessful() {
        //  registerPage.clickCreateAccountButton();
        //  registerPage.select();
        //  registerPage.enterRegisterForm(55556767, "123456789", "123456789", "Ly", 905453824, LocalDate.of(2000, 5, 20), "Male", "Da Nang", 1345678, "ly1@mailinator.com");
        //   registerPage.clickRegisterButton();
        loginPage.Login(User.STANDARD_USER);
        baseUserPage.goToInternalTransferPage();
      //  internalTransferPage.enterTransferInformation(12345,5000.0,"abc");
        // homePage.goToOpenAccountPage();
        // openAccountPage.openAccountNonTermDepositAccountType();
        //  internalTransferPage.enterTransferInformation(100002267,3000,"ck");
        //  internalTransferPage.goToTransferConfirmationPage();
        //  transferInformationReviewPage.goToConfirmationPage();
        //  transferConfirmationPage.enterOTP("abc");
        // transferConfirmationPage.clickTransferButton();
       // externalTransferPage.enterFormInformation(10001111, "Nguyen Van A", "ck", 5000.0);
        //   externalTransferPage.goToTransferInformationReviewPage();
        //   transferInformationReviewPage.goToConfirmationPage();
        //   transferConfirmationPage.enterOTP("abc");
        //  transferConfirmationPage.clickBtnExternalTransfer();

    }
}
