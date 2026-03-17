package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;
import utils.WaitUtils;

public class EmailPage {
    private final By searchTextBoxLocator = By.id("search");
    private final By goButtonLocator = By.xpath("//button[text()='GO']");
    private final By sendCodeOTPLocator = By.xpath("//tr[1]//td[contains(text(),'Send Code OPT')]");
    private final By otpTextLocator = By.xpath("//body[contains(.,'OTP')]");

    public void enterEmail(String email) {
        WaitUtils.waitForElementVisible(searchTextBoxLocator);
        DriverUtils.DRIVER.findElement(searchTextBoxLocator).sendKeys(email);
    }

    public void clickButtonGo() {
        WaitUtils.waitForElementVisible(goButtonLocator);
        DriverUtils.DRIVER.findElement(goButtonLocator).click();
    }

    public void clickSendCodeOTP() {
        WaitUtils.waitForElementVisible(sendCodeOTPLocator);
        DriverUtils.DRIVER.findElement(sendCodeOTPLocator).click();
    }

    public String getOtpFromEmail() {
        DriverUtils.DRIVER.switchTo().frame("texthtml_msg_body");
        WaitUtils.waitForElementVisible(otpTextLocator);
        String otp = DriverUtils.DRIVER.findElement(otpTextLocator).getText().replace("OTP:", "").trim();
        DriverUtils.DRIVER.switchTo().defaultContent();
        return otp;
    }

    public String getEmail(String email) {
        enterEmail(email);
        clickButtonGo();
        clickSendCodeOTP();
        return getOtpFromEmail();
    }


}
