package pages.account;

import base.BasePage;
import components.AccountTableComponent;
import components.RecentTransactionTableComponent;
import components.SidebarComponent;
import org.openqa.selenium.WebDriver;

public class DashboardPage extends BasePage {
    private final SidebarComponent sidebar;
    private final AccountTableComponent accountTable;
    private final RecentTransactionTableComponent recentTransactionTable;

    public DashboardPage(WebDriver driver) {
        super(driver);

        // TODO: move to BasePage
        this.sidebar = new SidebarComponent(driver);
        this.accountTable = new AccountTableComponent(driver);
        this.recentTransactionTable = new RecentTransactionTableComponent(driver);
    }

    public SidebarComponent sidebar() {
        return sidebar;
    }

    public AccountTableComponent accountTable() {
        return accountTable;
    }

    public RecentTransactionTableComponent recentTransactionTable() {
        return recentTransactionTable;
    }

    public String getLastAccountNumber() {
        return accountTable.getLastAccountNumber();
    }

    public AccountDetailPage openAccountDetail(String accountNumber) {
        accountTable.openAccountDetail(accountNumber);
        return new AccountDetailPage(driver);
    }

}
