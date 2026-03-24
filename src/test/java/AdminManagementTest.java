import base.BaseTest;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.DashboardPage;

public class AdminManagementTest extends BaseTest {
    // TODO: Question - should verify again case open account?
    @Test(description = "EB-02 Verify admin can deposit money to a user account and user balance is increased correctly.")
    public void EB02_admin_can_deposit_money_to_user_account() {
        RegisterData registerData = TestData.validRegister("tram_test");
        User userData = new User(registerData.getUsername(), registerData.getPassword());

        registerAndActivateUser(registerData);

        // openNewTab(USER_BASE_URL);
        DashboardPage dashboardPage = loginAsUser(userData);
        String newAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);

        long balanceBefore = dashboardPage.openAccountDetail(newAccountNumber).getBalance();
        dashboardPage.logout();
        clearSession();

        // admin
        depositMoneyAndLogout(newAccountNumber, 100_000);
        clearSession();

        DashboardPage userDashboardPageAfter = loginAsUser(userData);
        long balanceAfter = userDashboardPageAfter.openAccountDetail(newAccountNumber).getBalance();

        Assert.assertEquals(balanceAfter, balanceBefore + 100_000);
    }
}
