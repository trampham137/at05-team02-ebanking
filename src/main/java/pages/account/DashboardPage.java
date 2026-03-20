package pages.account;

import base.BasePage;
import base.UserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverUtils;
import utils.WaitUtils;

import java.util.List;

public class DashboardPage extends UserBasePage {
    private final By accountPanelLocator = By.xpath("//div[div/span[text()='THÔNG TIN TÀI KHOẢN']]");
    private final By accountRowsLocator = By.xpath(".//table[@role='grid']/tbody/tr");
    private final String accountNumberByIndex = "(//tbody[@id='j_idt27_data']//a)[%d]";
    // //span[normalize-space()='THÔNG TIN TÀI KHOẢN']/parent::div/following-sibling::div//table[@role='grid']/tbody/tr

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public int getAccountRowCount() {
        return find(accountPanelLocator).findElements(accountRowsLocator).size();
    }

    public String getLastAccountNumber() {
        List<WebElement> rows = find(accountPanelLocator).findElements(accountRowsLocator);
        if (rows.isEmpty()) {
            return "";
        }

        // TODO: column can be change position
        return rows.getLast().findElements(By.tagName("td")).getFirst().getText().trim();
    }

    public AccountDetailPage openAccountDetail(String accountNumber) {
        click(By.linkText(accountNumber));
        return new AccountDetailPage(driver);
    }

    public String getAccountByIndex(int index) {
        By locator = By.xpath(String.format(accountNumberByIndex, index));
        WaitUtils.waitForElementVisible(locator);
        return DriverUtils.getDriver().findElement(locator).getText();
    }


    // public int getRecentTransactionRowCount() {
    //     return find(recentTransactionPanelLocator).findElements(recentTransactionRowsLocator).size();
    // }
}