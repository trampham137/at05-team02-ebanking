package pages.email;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MailinatorInboxPage extends BasePage {
    private final By publicMessagesHeader = By.cssSelector("table.jambo_table");
    private final By firstEmailRow = By.xpath("//table[contains(@class, 'jambo_table')]//tbody/tr[1]");

    public MailinatorInboxPage(WebDriver driver) {
        super(driver);
    }

    @Step("Wait for inbox loaded")
    public void waitForInboxLoaded() {
        waitVisible(publicMessagesHeader);
    }

    @Step("Open latest email")
    public MailinatorEmailPage openLatestEmail() {
        waitClickable(firstEmailRow);
        click(firstEmailRow);
        return new MailinatorEmailPage(driver);
    }

    @Step("Open email by subject: {subject}")
    public MailinatorEmailPage openEmailBySubject(String subject) {
        By emailBySubject = By.xpath("//tr[.//*[contains(normalize-space(),'" + subject + "')]]");
        waitClickable(emailBySubject);
        click(emailBySubject);
        return new MailinatorEmailPage(driver);
    }

    @Step("Wait and open email by subject: {subject}, timeout: {timeoutSeconds}s")
    public MailinatorEmailPage waitAndOpenEmailBySubject(String subject, long timeoutSeconds) {
        long endTime = System.currentTimeMillis() + timeoutSeconds * 1000;

        By emailBySubject = By.xpath("//tr[.//*[contains(normalize-space(),'" + subject + "')]]");

        while (System.currentTimeMillis() < endTime) {
            try {
                if (isDisplayed(emailBySubject)) {
                    click(emailBySubject);
                    return new MailinatorEmailPage(driver);
                }
            } catch (Exception ignored) {
            }

            driver.navigate().refresh();
            waitForInboxLoaded();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        throw new RuntimeException("Cannot find email with subject: " + subject);
    }
}
