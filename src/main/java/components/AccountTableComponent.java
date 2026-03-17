package components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AccountTableComponent extends BasePage {
    private final By accountPanelLocator = By.xpath("//div[div/span[text()='THÔNG TIN TÀI KHOẢN']]");
    private final By accountRowsLocator = By.xpath(".//table[@role='grid']/tbody/tr");
    // //span[normalize-space()='THÔNG TIN TÀI KHOẢN']/parent::div/following-sibling::div//table[@role='grid']/tbody/tr

    public AccountTableComponent(WebDriver driver) {
        super(driver);
    }

    public int getRowCount() {
        return find(accountPanelLocator).findElements(accountRowsLocator).size();
    }

    public String getLastAccountNumber() {
        List<WebElement> rows = find(accountPanelLocator).findElements(accountRowsLocator);
        if (rows.isEmpty()) {
            return "";
        }

        return rows.get(rows.size() - 1).findElements(By.tagName("td")).get(0).getText().trim();
    }

    public void clickAccount(String accountNumber) {
        By accountLinkLocator = By.linkText(accountNumber);
        click(accountLinkLocator);
    }

    // public String getAccountType(String accountNumber) {
    //     List<WebElement> rows = finds(accountRowsLocator);
    //     for (WebElement row : rows) {
    //         List<WebElement> cells = row.findElements(By.tagName("td"));
    //         if (cells.size() >= 3 && cells.get(0).getText().trim().equals(accountNumber)) {
    //             return cells.get(2).getText().trim();
    //         }
    //     }
    //
    //     return "";
    // }

}
