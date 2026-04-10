import base.BaseTest;
import io.qameta.allure.*;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.DashboardPage;

public class AdminManagementTest extends BaseTest {

    @Feature("Admin Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify admin can deposit money to a user account and user balance is updated correctly.")
    @Test(description = "EB-02 Verify admin can deposit money to a user account and user balance is increased correctly.")
    public void EB02_admin_can_deposit_money_to_user_account() {
        RegisterData registerData = TestData.validRegister("tram_test");
        User userData = new User(registerData.getUsername(), registerData.getPassword());

        registerAndActivateUser(registerData);

        DashboardPage dashboardPage = loginAsUser(userData);
        String newAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);

        long balanceBefore = dashboardPage.openAccountDetail(newAccountNumber).getBalance();
        dashboardPage.logout();
        clearSession();

        depositMoneyAndLogout(newAccountNumber, 100_000);
        clearSession();

        DashboardPage userDashboardPage = loginAsUser(userData);
        long balanceAfter = userDashboardPage.openAccountDetail(newAccountNumber).getBalance();

        Allure.step("Verify balance is increased correctly after admin deposit");
        Assert.assertEquals(
                balanceAfter,
                balanceBefore + 100_000,
                "Balance after deposit is incorrect."
        );
    }
}
