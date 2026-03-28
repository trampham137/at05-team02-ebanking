import base.BaseTest;
import models.InternalTransferData;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.transfer.internal.InternalTransferConfirmPage;
import pages.transfer.internal.InternalTransferOtpPage;
import pages.transfer.internal.InternalTransferPage;

import static org.assertj.core.api.Assertions.assertThat;

public class InternalTransferTest extends BaseTest {
    private static final long DEPOSIT_AMOUNT = 100_000;
    private static final long TRANSFER_AMOUNT = 10_000;
    private static final long TRANSFER_FEE = 1100;


    @Test(description = "EB-04 Verify user can successfully perform an internal transfer using OTP")
    public void EB05_user_can_transfer_internally_successfully() {
        RegisterData registerDataA = TestData.validRegister("tram_test_a");
        RegisterData registerDataB = TestData.validRegister("tram_test_b");

        User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
        User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());

        DashboardPage dashboardPage;

        // PHASE 1: Register, activate, and open account for user A
        registerAndActivateUser(registerDataA);
        dashboardPage = loginAsUser(userA);
        String accountNumberA = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        // PHASE 2: Register, activate, and open account for user B
        registerAndActivateUser(registerDataB);
        dashboardPage = loginAsUser(userB);
        String accountNumberB = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 3: Admin deposits money into account B
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);

        // PHASE 4: User B logs in and performs internal transfer
        String userBTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userB);

        AccountDetailPage accountBDetailBeforeTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceBeforeTransfer = accountBDetailBeforeTransfer.getBalance();

        Assert.assertEquals(
                balanceBeforeTransfer,
                DEPOSIT_AMOUNT,
                "Account B balance before transfer is incorrect."
        );

        // transfer
        InternalTransferData transferData = new InternalTransferData(
                accountNumberB,
                accountNumberA,
                TRANSFER_AMOUNT,
                "Internal transfer test"
        );

        InternalTransferPage internalTransferPage = accountBDetailBeforeTransfer.goToInternalTransfer();
        internalTransferPage.fillTransferForm(transferData);
        InternalTransferConfirmPage confirmPage = internalTransferPage.clickConfirm();

        // TODO: compare object transferData >> DONE
        assertThat(confirmPage.getTransferData())
                .usingRecursiveComparison()
                .isEqualTo(transferData);

        // PHASE 5: Get OTP from Mailinator
        InternalTransferOtpPage otpPage = confirmPage.clickConfirm();
        String otpCode = getOtpCodeFromMailinator(registerDataB.getEmail());

        // PHASE 6: Submit OTP and verify sender balance
        switchToTab(userBTab);
        otpPage.submitOtp(otpCode);

        Assert.assertEquals(
                otpPage.getTransferSuccessMessage(),
                "Chuyển tiền thành công",
                "Transfer success message is incorrect."
        );

        otpPage.closeSuccessPopup();

        dashboardPage = otpPage.goToAccounts();
        AccountDetailPage accountBDetailAfterTransfer = dashboardPage.openAccountDetail(accountNumberB);
        long balanceAfterTransfer = accountBDetailAfterTransfer.getBalance();

        Assert.assertEquals(
                balanceAfterTransfer,
                balanceBeforeTransfer - TRANSFER_AMOUNT - TRANSFER_FEE,
                "Account B balance after transfer is incorrect."
        );

        accountBDetailAfterTransfer.logout();

        // PHASE 7: Verify receiver account A received money
        openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userA);

        AccountDetailPage accountADetail = dashboardPage.openAccountDetail(accountNumberA);
        long balanceAAfter = accountADetail.getBalance();
        
        Assert.assertEquals(
                balanceAAfter,
                TRANSFER_AMOUNT,
                "Account A balance after receiving transfer is incorrect."
        );
    }

    // @Test(description = "EB-03 Verify OTP email is received and system shows error when an invalid OTP is entered")
    // public void EB03_verify_invalid_otp_shows_error() {
    //
    //     RegisterData registerDataA = TestData.validRegister("tram_test_a");
    //     RegisterData registerDataB = TestData.validRegister("tram_test_b");
    //     // RegisterData registerDataA = TestData.validRegister("tram_test_a_9947", false);
    //     // RegisterData registerDataB = TestData.validRegister("tram_test_b_94289", false);
    //
    //     User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
    //     User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());
    //
    //     // PHASE 1: Setup accounts
    //     String accountNumberA = registerActivateLoginAndOpenAccount(registerDataA, AccountType.CURRENT_ACCOUNT);
    //     String accountNumberB = registerActivateLoginAndOpenAccount(registerDataB, AccountType.CURRENT_ACCOUNT);
    //     // String accountNumberA = "100002359";
    //     // String accountNumberB = "100002358";
    //
    //
    //     // PHASE 2: Deposit money to B
    //     openNewTab(ADMIN_BASE_URL);
    //     depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT);
    //
    //     // PHASE 3: User B transfer to OTP page
    //     String userBTab = openNewTab(USER_BASE_URL);
    //     DashboardPage dashboardPageB = loginAsUser(userB);
    //
    //     AccountDetailPage accountDetailPageB = dashboardPageB.openAccountDetail(accountNumberB);
    //     long balanceBefore = accountDetailPageB.getBalance();
    //
    //     InternalTransferPage transferPage = accountDetailPageB.goToInternalTransfer();
    //
    //     InternalTransferData transferData = new InternalTransferData(
    //             accountNumberB,
    //             accountNumberA,
    //             TRANSFER_AMOUNT,
    //             "Invalid OTP test"
    //     );
    //
    //     transferPage.fillTransferForm(transferData);
    //     InternalTransferConfirmPage confirmPage = transferPage.clickConfirmButton();
    //
    //     InternalTransferOtpPage otpPage = confirmPage.clickConfirm();
    //
    //     // PHASE 4: Verify OTP email
    //     String otpCode = getOtpCodeFromMailinator(registerDataB.getEmail());
    //
    //     // verify OTP format
    //     // TODO: no need check 10 character
    //     // Assert.assertEquals(otpCode.length(), 10, "OTP length should be 10 characters.");
    //     Assert.assertTrue(
    //             otpCode.matches("^[A-Z0-9]{10}$"),
    //             "OTP format is invalid. Must be uppercase letters and digits."
    //     );
    //
    //     // PHASE 5: Enter INVALID OTP
    //     switchToTab(userBTab);
    //
    //     String invalidOtp = "AAAAAAAAAA";
    //     otpPage.enterOtp(invalidOtp);
    //     otpPage.clickTransfer();
    //
    //     // PHASE 6: Verify error
    //     Assert.assertTrue(
    //             otpPage.isToastMessageDisplayed(),
    //             "Toast message is not displayed."
    //     );
    //
    //     Assert.assertEquals(
    //             otpPage.getToastMessage(),
    //             "Sai mã OTP",
    //             "Toast message is incorrect."
    //     );
    //
    //     Assert.assertTrue(
    //             otpPage.isInlineErrorDisplayed(),
    //             "Inline error message is not displayed."
    //     );
    //
    //     Assert.assertEquals(
    //             otpPage.getInlineErrorMessage(),
    //             "Sai mã OTP",
    //             "Inline error message is incorrect."
    //     );
    // }
}
