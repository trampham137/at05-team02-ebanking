package pages.transfer.internal;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class InternalTransferOtpPage {

    private final By transactionId = By.xpath("//label[text()='Mã giao dịch']");
    private final By transactionTextBoxLocator = By.xpath("//td[label[text()='Mã giao dịch']]//following-sibling::td//input");
    private final By transferButtonLocator = By.xpath("//input[@type='submit']");
    private final By inlineMessLocator = By.xpath("//span[text()='Sai mã OTP']");
    private final By toastMessage = By.xpath("//span[@class='ui-growl-title']");

    public boolean isTransactionIdDisplayed() {
        DriverUtils.getDriver().findElement(transactionId);
        return DriverUtils.getDriver().findElement(transactionId).isDisplayed();
    }

    public void enterOTP(String OTP) {
        DriverUtils.getDriver().findElement(transactionTextBoxLocator);
        DriverUtils.getDriver().findElement(transactionTextBoxLocator).sendKeys(OTP);
    }

    public void clickConfirmButton() {
        DriverUtils.getDriver().findElement(transferButtonLocator);
        DriverUtils.getDriver().findElement(transferButtonLocator).click();
    }

    public String getInlineMess() {
        return DriverUtils.getDriver().findElement(inlineMessLocator).getText();
    }

    public String getToastMess() {
        return DriverUtils.getDriver().findElement(toastMessage).getText();
    }


}
