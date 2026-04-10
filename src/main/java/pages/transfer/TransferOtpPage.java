package pages.transfer;

import base.UserBasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TransferOtpPage extends UserBasePage {
    private final By otpInputLocator = By.xpath("//td[label[text()='Mã giao dịch']]/following-sibling::td/input");
    private final By transferButtonLocator = By.cssSelector("input[type=submit]");
    private final By successPopupMsgLocator = By.xpath("//div[@id='primefacesmessagedlg']//*[contains(text(),'Chuyển tiền thành công')]");
    private final By closePopupButtonLocator = By.cssSelector("#primefacesmessagedlg a.ui-dialog-titlebar-close");

    private final By toastMessageLocator = By.xpath("//div[@id='j_idt8:messages_container']//span[text()='Sai mã OTP']");
    private final By inlineMessageLocator = By.xpath("//div[contains(@class, 'ui-messages')]//*[text()='Sai mã OTP']");

    public TransferOtpPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for OTP page loaded")
    public void waitForOtpPageLoaded() {
        waitVisible(otpInputLocator);
    }

    @Step("Check OTP input displayed")
    public boolean isOtpInputDisplayed() {
        return isDisplayed(otpInputLocator);
    }

    @Step("Submit OTP code")
    public void submitOtp(String otp) {
        type(otpInputLocator, otp);
        click(transferButtonLocator);
    }

    @Step("Close transfer success popup")
    public void closeSuccessPopup() {
        click(closePopupButtonLocator);
    }

    @Step("Get transfer success message")
    public String getTransferSuccessMessage() {
        return getText(successPopupMsgLocator).trim();
    }

    @Step("Get OTP toast error message")
    public String getToastMessage() {
        return getText(toastMessageLocator);
    }

    @Step("Get OTP inline error message")
    public String getInlineErrorMessage() {
        return getText(inlineMessageLocator);
    }
}
