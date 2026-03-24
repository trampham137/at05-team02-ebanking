package pages.transfer.internal;

import base.UserBasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.account.DashboardPage;
import utils.DriverUtils;
import utils.WaitUtils;

public class InternalTransferOtpPage extends UserBasePage {
    private By valueByLabel(String label) {
        return By.xpath("//td[label[text()='" + label + "']]/following-sibling::td/label");
    }

    private final By otpInputLocator = By.xpath("//td[label[text()='Mã giao dịch']]/following-sibling::td/input");
    private final By transferButtonLocator = By.cssSelector("input[type=submit]");
    private final By successPopupMsgLocator = By.xpath("//div[@id='primefacesmessagedlg']//*[contains(text(),'Chuyển tiền thành công')]");
    private final By closePopupButtonLocator = By.cssSelector("#primefacesmessagedlg a.ui-dialog-titlebar-close");

    private final By toastMessageLocator = By.xpath("//div[@id='j_idt8:messages_container']//span[text()='Sai mã OTP']");
    private final By inlineMessageLocator = By.xpath("//div[contains(@class, 'ui-messages')]//*[text()='Sai mã OTP']");

    public InternalTransferOtpPage(WebDriver driver) {
        super(driver);
    }

    public void enterOtp(String otp) {
        type(otpInputLocator, otp);
    }

    public void clickTransfer() {
        click(transferButtonLocator);
    }

    public boolean isTransferSuccessPopupDisplayed() {
        return isDisplayed(successPopupMsgLocator);
    }

    public String getTransferSuccessMessage() {
        return getText(successPopupMsgLocator).trim();
    }

    public void closeSuccessPopup() {
        click(closePopupButtonLocator);
    }

    public String getToastMessage() {
        return getText(toastMessageLocator).trim();
    }

    public boolean isToastMessageDisplayed() {
        return isDisplayed(toastMessageLocator);
    }

    public String getInlineErrorMessage() {
        return getText(toastMessageLocator).trim();
    }

    public boolean isInlineErrorDisplayed() {
        return isDisplayed(inlineMessageLocator);
    }
}
