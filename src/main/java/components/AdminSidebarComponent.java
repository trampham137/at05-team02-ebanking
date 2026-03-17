package components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.admin.DepositMoneyPage;

public class AdminSidebarComponent extends BasePage {
    private final By depositMoneyMenuLocator = By.linkText("Nộp Tiền");
    private final By logoutButton = By.xpath("");

    public AdminSidebarComponent(WebDriver driver) {
        super(driver);
    }

    public DepositMoneyPage goToDepositMoney() {
        click(depositMoneyMenuLocator);
        return new DepositMoneyPage(driver);
    }

    public void logout() {
        click(logoutButton);
    }

}
