package page;

import org.openqa.selenium.By;
import untils.DriverUtils;
import untils.User;

public class LoginPage {
    private final By AccountTextBoxLocator = By.xpath("//p[@class=\"user_name_text1\"][text()='Tài Khoản']//following-sibling::p[1]//input");
    private final By PassWordTextBoxLocator = By.xpath("//p[@class=\"user_name_text1\"][text()='Mật Khẩu']//following-sibling::p[1]//input");
    private final By LoginButtonLocator = By.xpath("//p//input[@value=\"Đặng nhập\"]");

    public void enterAccount(String account){
        DriverUtils.DRIVER.findElement(AccountTextBoxLocator).sendKeys(account);
    }
    public void enterPassWord(String password){
        DriverUtils.DRIVER.findElement(PassWordTextBoxLocator).sendKeys(password);
    }
    public void clickLoginButton(){
        DriverUtils.DRIVER.findElement(LoginButtonLocator).click();
    }
    public void Login(User user){
        enterAccount(user.getAccount());
        enterPassWord(user.getPassword());
        clickLoginButton();
    }
}
