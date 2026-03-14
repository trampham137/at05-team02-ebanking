package example;

import org.testng.annotations.Test;
import page.*;
import untils.User;

public class TestUser extends TestBase {
    LoginPage loginPage = new LoginPage();
    HomePage homePage = new HomePage();
    OpenAccountPage openAccountPage = new OpenAccountPage();
    InternalTransferPage internalTransferPage = new InternalTransferPage();
    TransferInformationReviewPage transferInformationReviewPage = new TransferInformationReviewPage();
    TransferConfirmationPage transferConfirmationPage = new TransferConfirmationPage();
    ExternalTransferPage externalTransferPage = new ExternalTransferPage();

    @Test
    public void verifyLoginSucessful() {
        loginPage.Login(User.STANDARD_USER);
        homePage.goToInternalTransferPage();
        // homePage.goToOpenAccountPage();
        // openAccountPage.openAccountNonTermDepositAccountType();
        //  internalTransferPage.enterTransferInformation(100002267,3000,"ck");
        //  internalTransferPage.goToTransferConfirmationPage();
        //  transferInformationReviewPage.goToConfirmationPage();
        //  transferConfirmationPage.enterOTP("abc");
        // transferConfirmationPage.clickTransferButton();
        //  externalTransferPage.enterFormInformation(10001111,"Nguyen Van A","ck",5000.0);
        //  externalTransferPage.goToTransferInformationReviewPage();
        //  transferInformationReviewPage.goToConfirmationPage();
        // transferConfirmationPage.enterOTP("abc");
        // transferConfirmationPage.clickBtnExternalTransfer();
    }
}
