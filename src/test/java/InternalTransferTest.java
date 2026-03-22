import base.BaseTest;
import models.RegisterData;
import models.User;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.account.DashboardPage;
import pages.auth.AccountActivatedPage;
import pages.auth.RegisterPage;
import pages.email.MailinatorEmailPage;
import pages.email.MailinatorHomePage;
import pages.email.MailinatorInboxPage;
import pages.transfer.internal.InternalTransferPage;

public class InternalTransferTest extends BaseTest {
    @Test(description = "EB-05 Verify user can successfully perform an internal transfer using OTP")
    public void EB05_user_can_transfer_internally_successfully() {
        RegisterData registerData = TestData.validRegister(
                "tram_test_5",
                "123456789",
                "trampham.test05@mailinator.com"
        );

        RegisterPage registerPage = goToRegister();
        registerPage.register(registerData);

        String mainWindow = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.mailinator.com/");

        MailinatorHomePage homePage = new MailinatorHomePage(driver);
        MailinatorInboxPage inboxPage = homePage.openInbox(registerData.getEmail());

        inboxPage.waitForInboxLoaded();
        MailinatorEmailPage emailPage = inboxPage.openEmailBySubject("Kich hoat tai khoan");

        String currentWindow = driver.getWindowHandle();
        emailPage.clickActivationLink();

        emailPage.switchToNewWindow(currentWindow);
        AccountActivatedPage successPage = new AccountActivatedPage(driver);
        Assert.assertTrue(successPage.isActivationSuccessDisplayed());
    }
}
