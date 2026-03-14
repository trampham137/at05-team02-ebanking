package example;

import org.testng.annotations.Test;
import models.User;
import base.BasePage;
import enums.Bank;
import enums.Branch;
import page.user.*;

public class TestUser extends TestBase {
    // TODO: tao class model them user trong util vao

    LoginPage loginPage = new LoginPage();
    BasePage basePage = new BasePage();
    OpenAccountPage openAccountPage = new OpenAccountPage();
    InternalTransferPage internalTransferPage = new InternalTransferPage();
    TransferInformationReviewPage transferInformationReviewPage = new TransferInformationReviewPage();
    TransferConfirmationPage transferConfirmationPage = new TransferConfirmationPage();
    ExternalTransferPage externalTransferPage = new ExternalTransferPage();

    @Test
    public void verifyLoginSucessful() {
        loginPage.Login(User.STANDARD_USER);
        openAccountPage.goToExternalTransfer();

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
        //  internalTransferPage.enterTransferInformation("100002265", 100002267, 500.0, "ck");
        externalTransferPage.enterInformation("100002265", 10001111, "Nguyen Van A", Bank.DONGA, Branch.DANANG, "ck", 5000.0);
    }
}
