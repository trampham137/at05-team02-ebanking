import enums.AccountType;
import example.TestBase;
import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EB_01 extends TestBase {
    @Test
    public void verifyNewBankAccountIsCreatedWithCorrectInitialBalance() {
        goToUserPage();
        loginPage.Login(User.STANDARD_USER);
        int accountCountBefore = accountPage.countNumberOfAccounts();
        accountPage.goToOpenAccountPage();
        AccountType accounttype = AccountType.NON_TERM;
        openAccountPage.selectAccountType(accounttype);
        openAccountPage.goToAccountPage();
        int accountCountAfter = accountPage.countNumberOfAccounts();
        Assert.assertEquals(accountCountAfter, accountCountBefore + 1, "Account creation failed. The number of accounts did not increase.");
        Assert.assertTrue(accountPage.isAccountNumberUnique(accountPage.getAccountNumber()), "Account number already exists.");
        Assert.assertEquals(accountPage.getCurrency(), "VNĐ", "Currency type is not VND.");
        Assert.assertEquals(accountPage.getAccountType(), accounttype.getValue(), "Account type does not match the selected account type.");
        accountPage.goToAccountDetailPage();
        Assert.assertTrue(accountDetailsPage.isAccountBalanceLabelDisplayed(), "The account balance label is not displayed");
        Assert.assertEquals(accountDetailsPage.getAccountBalanceValue(), 0, "Initial account balance is incorrect");
        Assert.assertEquals(accountDetailsPage.getCurrencyValue(), "VNĐ", "Currency value is not VND");
    }


}

