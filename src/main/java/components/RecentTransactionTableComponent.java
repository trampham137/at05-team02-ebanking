package components;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RecentTransactionTableComponent extends BasePage {
    private final By transactionRowsLocator = By.xpath("");

    public RecentTransactionTableComponent(WebDriver driver) {
        super(driver);
    }

    public boolean hasAnyTransaction() {
        return !finds(transactionRowsLocator).isEmpty();
    }

    public String getLatestTransactionDate() {
        List<WebElement> rows = finds(transactionRowsLocator);
        return rows.isEmpty() ? "" : rows.get(0).findElements(By.tagName("td")).get(0).getText().trim();
    }

    public String getLatestTransactionAccountNumber() {
        List<WebElement> rows = finds(transactionRowsLocator);
        return rows.isEmpty() ? "" : rows.get(0).findElements(By.tagName("td")).get(1).getText().trim();
    }

    public String getLatestTransactionAmount() {
        List<WebElement> rows = finds(transactionRowsLocator);
        return rows.isEmpty() ? "" : rows.get(0).findElements(By.tagName("td")).get(1).getText().trim();
    }

}
