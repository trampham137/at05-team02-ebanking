package pages.account;

import base.BasePage;
import base.UserBasePage;
import models.RecentTransactionData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverUtils;
import utils.WaitUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DashboardPage extends UserBasePage {
    private final By accountPanelLocator = By.xpath("//div[div/span[text()='THÔNG TIN TÀI KHOẢN']]");
    private final By accountRowsLocator = By.xpath(".//table[@role='grid']/tbody/tr");
    // //span[normalize-space()='THÔNG TIN TÀI KHOẢN']/parent::div/following-sibling::div//table[@role='grid']/tbody/tr

    private final By recentTransactionPanelLocator = By.xpath("//div[div/span[text()='GIAO DỊCH GẦN NHẤT']]");
    private final By recentTransactionRowsLocator = By.xpath(".//table[@role='grid']/tbody/tr");

    private static final DateTimeFormatter TRANSACTION_DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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

    // transaction log
    public RecentTransactionData getLatestRecentTransaction() {
        WebElement firstRow = getLatestRecentTransactionRow();
        List<WebElement> cells = firstRow.findElements(By.tagName("td"));

        String dateTimeText = cells.get(0).getText().trim();
        String accountNumber = cells.get(1).getText().trim();
        String amountText = cells.get(2).getText().trim();

        return new RecentTransactionData(
                dateTimeText,
                LocalDateTime.parse(dateTimeText, TRANSACTION_DATETIME_FORMAT),
                accountNumber,
                amountText
        );
    }

    private WebElement getLatestRecentTransactionRow() {
        WebElement transactionTable = find(recentTransactionPanelLocator);
        List<WebElement> rows = transactionTable.findElements(recentTransactionRowsLocator);

        if (rows.isEmpty()) {
            throw new IllegalStateException("No recent transaction rows found.");
        }

        return rows.getFirst();
    }

}