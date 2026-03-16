package components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminSidebarComponent extends BasePage {
    private final By depositMoneyMenuLocator = By.linkText("Nộp Tiền");
    private final By logoutButton = By.xpath("");

    public AdminSidebarComponent(WebDriver driver) {
        super(driver);
    }

    public void goToDepositMoney() {
        click(depositMoneyMenuLocator);
    }

    public void logout() {
        click(logoutButton);
    }

}
