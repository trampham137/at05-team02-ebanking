import base.BaseTest;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.account.OpenAccountPage;

public class AccountManagementTest extends BaseTest {

    @Test(description = "EB-01 Verify user can create a new bank account and the initial balance is displayed correctly.")
    public void EB01_user_can_open_new_bank_account() {
        RegisterData registerData = TestData.validRegister("tram_test");
        User userData = new User(registerData.getUsername(), registerData.getPassword());

        registerAndActivateUser(registerData);

        // openNewTab(USER_BASE_URL);
        DashboardPage dashboardPage = loginAsUser(userData);

        // int beforeRowCount = dashboardPage.getAccountRowCount();
        String newAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);

        DashboardPage refreshedDashboardPage = new DashboardPage(driver);
        int afterRowCount = refreshedDashboardPage.getAccountRowCount();

        Assert.assertEquals(afterRowCount, 1);

        AccountDetailPage detailPage = refreshedDashboardPage.openAccountDetail(newAccountNumber);
        Assert.assertEquals(detailPage.getBalance(), 0L);
    }

}
