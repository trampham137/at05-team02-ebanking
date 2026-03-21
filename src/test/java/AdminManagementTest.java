import base.BaseTest;
import models.DepositData;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.account.OpenAccountPage;
import pages.admin.AdminDashboardPage;
import pages.admin.DepositMoneyPage;

public class AdminManagementTest extends BaseTest {
    // TODO: Question - should verify again case open account?
    @Test(description = "EB-02 Verify admin can deposit money to a user account and user balance is increased correctly.")
    public void EB02_admin_can_deposit_money_to_user_account() {
        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);
        OpenAccountPage openAccountPage = dashboardPage.goToOpenAccount();
        openAccountPage.createAccount(TestData.ACCOUNT_TYPE_CURRENT);
        openAccountPage.closeSuccessPopup();

        DashboardPage refreshedDashboardPage = openAccountPage.goToAccounts();
        String newAccountNumber = refreshedDashboardPage.getLastAccountNumber();
        long balanceBefore = refreshedDashboardPage.openAccountDetail(newAccountNumber).getBalance();

        refreshedDashboardPage.logout();

        // admin
        AdminDashboardPage adminDashboardPage = loginAsAdmin();
        DepositMoneyPage depositMoneyPage = adminDashboardPage.goToDepositMoney();

        long depositAmount = 100000;
        DepositData depositData = new DepositData(newAccountNumber, depositAmount, "Testing");
        depositMoneyPage.depositToAccount(depositData);

        Assert.assertTrue(depositMoneyPage.isDepositSuccessful());
        Assert.assertEquals(depositMoneyPage.getSuccessMessage(), "nộp tiền thành công");

        depositMoneyPage.logout();

        // user
        clearSession();
        DashboardPage userDashboardPage = loginAsUser(TestData.STANDARD_USER);
        long balanceAfter = userDashboardPage.openAccountDetail(newAccountNumber).getBalance();

        Assert.assertEquals(balanceAfter, balanceBefore + depositAmount, "Balance should be increased correctly after deposit.");
    }
}
