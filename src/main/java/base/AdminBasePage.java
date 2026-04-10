package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.admin.DepositMoneyPage;
import io.qameta.allure.Step;

public class AdminBasePage extends BasePage {
    private final By depositMoneyMenuLocator = By.linkText("Nộp Tiền");
    private final By logoutButtonLocator = By.xpath("//input[@type='submit' and @value='Thoát']");

    public AdminBasePage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Deposit Money page")
    public DepositMoneyPage goToDepositMoney() {
        click(depositMoneyMenuLocator);
        return new DepositMoneyPage(driver);
    }

    @Step("Logout admin account")
    public void logout() {
        click(logoutButtonLocator);
    }
}
