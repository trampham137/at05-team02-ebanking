package page.user;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.DriverUtils;

import java.util.List;

import static utils.WaitUtils.waitForElementVisible;

public class AccountPage extends BasePage {
    private final By accountRowsLocator = By.xpath("//tbody[@id='j_idt27_data']//tr");
    private final By accountNumberCellsLocator = By.xpath("//tbody[@id='j_idt27_data']//tr//td[1]");

    private final By accountNumberCellInLastRowLocator = By.xpath("//tbody[@id='j_idt27_data']//tr[last()]//td[1]");
    private final By currencyCellInLastRowLocator = By.xpath("//tbody[@id='j_idt27_data']//tr[last()]//td[2]");
    private final By accountTypeCellInLastRowLocator = By.xpath("//tbody[@id='j_idt27_data']//tr[last()]//td[3]");


    public int countNumberOfAccounts() {
        waitForElementVisible(accountRowsLocator);
        return DriverUtils.DRIVER.findElements(accountRowsLocator).size();
    }

    public List<WebElement> getAccountRows() {
        return DriverUtils.DRIVER.findElements(accountNumberCellsLocator);
    }

    public boolean isAccountNumberUnique(String accountNumber) {

        List<WebElement> rows = getAccountRows();
        int count = 0;

        for (WebElement row : rows) {
            if (row.getText().trim().equals(accountNumber)) {
                count++;
            }
        }

        return count == 1;
    }

    public String getAccountNumber() {
        return DriverUtils.DRIVER.findElement(accountNumberCellInLastRowLocator).getText();
    }

    public String getAccountType() {
        return DriverUtils.DRIVER.findElement(accountTypeCellInLastRowLocator).getText();
    }

    public String getCurrency() {
        return DriverUtils.DRIVER.findElement(currencyCellInLastRowLocator).getText();
    }

    public void goToAccountDetailPage() {
        DriverUtils.DRIVER.findElement(accountNumberCellInLastRowLocator).click();
    }


}
