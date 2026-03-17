import base.BaseTest;
import org.testng.annotations.Test;
import pages.account.DashboardPage;

public class TestExample extends BaseTest {
    // @Test
    // public void user_can_login_successfully() {
    //     User user = new User("tram_test_01", "123456789");
    //     LoginPage loginPage = new LoginPage(driver);
    //     loginPage.login(user);
    // }

    @Test
    public void user_can_go_to_account_page_from_sidebar() {
        DashboardPage dashboardPage = loginAsUser(TestData.STANDARD_USER);

        // SidebarComponent sidebar = new SidebarComponent(driver);
        // AccountPage accountPage = sidebar.goToAccount();
        //
        // Assert.assertTrue(accountPage.isDisplayed());
    }
}