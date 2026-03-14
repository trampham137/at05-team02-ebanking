package page;

import org.openqa.selenium.By;
import untils.DriverUtils;

public class TransferConfirmationPage {
    private final By transactionTextBoxLocator = By.xpath("//td[label[text()='Mã giao dịch']]//following-sibling::td//input");
    private final By transerferButtonLocator = By.xpath("//input[@type='submit']");


    // td//input[@value="Chuyển tiền"] >> remove
    // type=submit

    public void enterOTP(String OTP) {
        DriverUtils.DRIVER.findElement(transactionTextBoxLocator).sendKeys(OTP);
    }

    public void clickConfirmButton() {
        DriverUtils.DRIVER.findElement(transerferButtonLocator).click();
    }


}
