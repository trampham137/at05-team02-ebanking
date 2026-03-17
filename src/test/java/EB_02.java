import example.TestBase;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EB_02 extends TestBase {
    @Test
    public void verifyAdminDepositMoneyAndBalanceUpdated() {
        goToUserPage();
        loginPage.Login(User.STANDARD_USER);
        accountPage.goToAccountDetailPageByAccountNumber("100002265");
        int balanceValueBefore = accountDetailsPage.getAccountBalanceValue();
        goToAdminPage();
        adminLoginPage.Login("1", "admin");
        adminHomePage.goToDepositPage();
        depositPage.enterDepositForm(100002265, 100000, "deposit");
        depositPage.clickConfirmButton();
        goToUserPage();
        accountPage.goToAccountDetailPageByAccountNumber("100002265");
        int balanceValueAfter = accountDetailsPage.getAccountBalanceValue();
        Assert.assertTrue(accountDetailsPage.isAccountBalanceLabelDisplayed(), "The account balance label is not displayed");
        Assert.assertEquals(balanceValueAfter, balanceValueBefore + 100000, "Account balance did not increase correctly after deposit");
    }

}
