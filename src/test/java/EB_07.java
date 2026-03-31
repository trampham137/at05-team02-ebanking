import base.BaseTest;
import models.InternalTransferData;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.transfer.InternalTransferPage;

public class EB_07 extends BaseTest {
    private static final long DEPOSIT_AMOUNT = 100_000;
    DashboardPage dashboardPage;

    @Test(description = "EB-06 Verify system prevents internal transfer when amount is greater than available balance")
    public void EB06_verify_transfer_amount_greater_than_balance() {

        RegisterData registerDataA = TestData.validRegister("tram_test_a");
        RegisterData registerDataB = TestData.validRegister("tram_test_b");

        User userA = new User(registerDataA.getUsername(), registerDataA.getPassword());
        User userB = new User(registerDataB.getUsername(), registerDataB.getPassword());

        // PHASE 1: Register + open account for user A
        registerAndActivateUser(registerDataA);
        dashboardPage = loginAsUser(userA);
        String accountNumberA = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();

        // PHASE 2: Register + open account for user B
        registerAndActivateUser(registerDataB);
        dashboardPage = loginAsUser(userB);
        String accountNumberB = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);
        dashboardPage.logout();
        clearSession();

        // PHASE 3: Admin nạp tiền ít vào account B
        openNewTab(ADMIN_BASE_URL);
        depositMoneyAndLogout(accountNumberB, DEPOSIT_AMOUNT); // ít tiền

        // PHASE 4: Login user B
        String userBTab = openNewTab(USER_BASE_URL);
        dashboardPage = loginAsUser(userB);

        AccountDetailPage accountBDetail = dashboardPage.openAccountDetail(accountNumberB);
        long balanceBefore = accountBDetail.getBalance();

        Assert.assertEquals(
                balanceBefore,
                DEPOSIT_AMOUNT,
                "Initial balance is incorrect."
        );

        // Transfer nhiều hơn số dư
        long invalidAmount = 200_000;

        InternalTransferPage transferPage = accountBDetail.goToInternalTransfer();

        transferPage.selectSourceAccount(accountNumberB);
        transferPage.enterReceiverAccount(accountNumberA);
        transferPage.enterAmount(invalidAmount);
        transferPage.enterDescription("Exceed balance test");
        transferPage.clickConfirm();

        // PHASE 5: Verify error message
        Assert.assertTrue(transferPage.isAmountGreaterThanBalanceMessageDisplayed(), "Error message for insufficient balance is incorrect.");
        // PHASE 6: Verify balance KHÔNG thay đổi
        dashboardPage = transferPage.goToAccounts();
        accountBDetail = dashboardPage.openAccountDetail(accountNumberB);
        long balanceAfter = accountBDetail.getBalance();

        Assert.assertEquals(
                balanceAfter,
                balanceBefore,
                "Balance should remain unchanged when transfer fails."
        );
    }
}
