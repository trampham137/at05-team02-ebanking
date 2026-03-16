import models.User;
import org.testng.annotations.Test;
import pages.auth.LoginPage;

public class TestExample extends BaseTest {
    @Test
    public void user_can_login_successfully() {
        User user = new User("tram_test_01", "123456789");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(user);
    }
}
