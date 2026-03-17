package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;
import utils.WaitUtils;

public class TransferConfirmationPage {
    private final By transactionTextBoxLocator = By.xpath("//td[label[text()='Mã giao dịch']]//following-sibling::td//input");
    private final By transerferButtonLocator = By.xpath("//input[@type='submit']");
    private final By transactionCodeLabelLocator = By.xpath("//label[text()='Mã giao dịch']");

    public void enterOTP(String OTP) {
        DriverUtils.DRIVER.findElement(transactionTextBoxLocator).sendKeys(OTP);
    }

    public void clickConfirmButton() {
        WaitUtils.waitForElementVisible(transerferButtonLocator);
        DriverUtils.DRIVER.findElement(transerferButtonLocator).click();
    }

    public boolean isTransactionCodeLabelDisPlay() {
        return DriverUtils.DRIVER.findElement(transactionCodeLabelLocator).isDisplayed();
    }

}
