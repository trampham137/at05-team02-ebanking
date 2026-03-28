package pages.email;

import base.BasePage;
import models.OtpEmailData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailinatorEmailPage extends BasePage {
    private final By subjectLocator = By.xpath("//div[contains(@class,'wrapper-title')]/div[last()]");
    private final By bodyFrameLocator = By.id("texthtml_msg_body");
    private final By bodyTextLocator = By.tagName("body");
    private final By activationLinkLocator = By.xpath("//a[contains(@href,'EBankingWebsite')]");

    public MailinatorEmailPage(WebDriver driver) {
        super(driver);
    }

    public String getSubject() {
        waitVisible(subjectLocator);
        return getText(subjectLocator).trim();
    }

    public String getBody() {
        waitVisible(bodyFrameLocator);
        waitForFrameAndSwitch(bodyFrameLocator);

        String body = getText(bodyTextLocator).trim();

        switchToDefaultContent();
        return body;
    }

    public String getOtpCode(String body) {
        Pattern pattern = Pattern.compile("OTP:\\s*([A-Z0-9]+)");
        Matcher matcher = pattern.matcher(body);

        if (matcher.find()) {
            return matcher.group(1);
        }

        throw new RuntimeException("OTP code not found in email body");
    }

    public OtpEmailData getOtpEmailData() {
        String subject = getSubject();
        String body = getBody();
        String otpCode = getOtpCode(body);

        return new OtpEmailData(subject, body, otpCode);
    }

    public void clickActivationLink() {
        waitVisible(bodyFrameLocator);
        waitForFrameAndSwitch(bodyFrameLocator);

        waitClickable(activationLinkLocator);
        click(activationLinkLocator);

        switchToDefaultContent();
    }
}
