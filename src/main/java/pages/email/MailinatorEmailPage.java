package pages.email;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailinatorEmailPage extends BasePage {
    private final By emailBodyFrame = By.id("texthtml_msg_body");

    public MailinatorEmailPage(WebDriver driver) {
        super(driver);
    }

    public void clickActivationLink() {
        By activationLink = By.xpath("//a[contains(@href,'EBankingWebsite')]");

        waitVisible(emailBodyFrame);
        waitForFrameAndSwitch(emailBodyFrame);

        waitClickable(activationLink);
        click(activationLink);

        switchToDefaultContent();
    }

    public String getOtpCode() {
        waitVisible(emailBodyFrame);
        waitForFrameAndSwitch(emailBodyFrame);

        String bodyText = getText(By.tagName("body"));
        System.out.println("Email body: " + bodyText);

        switchToDefaultContent();

        Pattern pattern = Pattern.compile("OTP:\\s*([A-Z0-9]+)");
        Matcher matcher = pattern.matcher(bodyText);

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new RuntimeException("OTP code not found in email body");
    }
}