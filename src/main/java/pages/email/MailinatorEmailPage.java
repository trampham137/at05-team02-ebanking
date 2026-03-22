package pages.email;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MailinatorEmailPage extends BasePage {
    private final By emailBodyFrame = By.id("texthtml_msg_body");

    public MailinatorEmailPage(WebDriver driver) {
        super(driver);
    }

    public void clickActivationLink() {
        By activationLink = By.xpath("//a[contains(@href,'EBankingWebsite')]");

        waitVisible(emailBodyFrame);
        switchToFrame(emailBodyFrame);

        waitClickable(activationLink);
        click(activationLink);

        switchToDefaultContent();
    }

    // public String getOtpCode() {
    //     waitForElementVisible(emailBodyFrame);
    //     switchToFrame(emailBodyFrame);
    //
    //     String bodyText = driver.findElement(By.tagName("body")).getText();
    //
    //     switchToDefaultContent();
    //
    //     // ví dụ OTP 6 số
    //     Pattern pattern = Pattern.compile("\\b\\d{6}\\b");
    //     Matcher matcher = pattern.matcher(bodyText);
    //
    //     if (matcher.find()) {
    //         return matcher.group();
    //     }
    //
    //     throw new RuntimeException("OTP code not found in email body");
    // }
}