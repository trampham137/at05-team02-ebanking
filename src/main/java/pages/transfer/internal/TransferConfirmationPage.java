package pages.transfer.internal;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class TransferConfirmationPage {
    private final By transactionTextBoxLocator = By.xpath("//td[label[text()='Mã giao dịch']]//following-sibling::td//input");
    private final By transerferButtonLocator = By.xpath("//input[@type='submit']");

    // public void enterOTP(String OTP) {
    //     DriverUtils.DRIVER.findElement(transactionTextBoxLocator).sendKeys(OTP);
    // }
    //
    // public void clickConfirmButton() {
    //     DriverUtils.DRIVER.findElement(transerferButtonLocator).click();
    // }


}
