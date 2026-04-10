import base.BaseTest;
import io.qameta.allure.*;
import models.RegisterData;
import models.User;
import models.enums.AccountType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.AccountDetailPage;
import pages.account.DashboardPage;
import pages.account.OpenAccountPage;

public class AccountManagementTest extends BaseTest {

    @Feature("Account Management")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can create a new bank account successfully and the initial balance is displayed as 0.")
    @Test(description = "EB-01 Verify user can create a new bank account and the initial balance is displayed correctly.")
    public void EB01_user_can_open_new_bank_account() {
        RegisterData registerData = TestData.validRegister("tram_test");
        User userData = new User(registerData.getUsername(), registerData.getPassword());

        registerAndActivateUser(registerData);

        DashboardPage dashboardPage = loginAsUser(userData);

        String newAccountNumber = openBankAccount(dashboardPage, AccountType.CURRENT_ACCOUNT);

        int afterRowCount = dashboardPage.getAccountRowCount();

        Allure.step("Verify total account row count is 1 after opening a new account");
        Assert.assertEquals(afterRowCount, 1, "Account row count should be 1.");

        AccountDetailPage detailPage = dashboardPage.openAccountDetail(newAccountNumber);

        Allure.step("Verify initial account balance is 0");
        Assert.assertEquals(detailPage.getBalance(), 0L, "Initial account balance should be 0.");
    }
}
