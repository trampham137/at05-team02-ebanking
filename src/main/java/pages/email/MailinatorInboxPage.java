package pages.email;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MailinatorInboxPage extends BasePage {
    private final By publicMessagesHeader = By.cssSelector("table.jambo_table");
    private final By firstEmailRow = By.xpath("//table[contains(@class, 'jambo_table')]//tbody/tr[1]");

    public MailinatorInboxPage(WebDriver driver) {
        super(driver);
    }

    public void waitForInboxLoaded() {
        waitVisible(publicMessagesHeader);
    }

    public MailinatorEmailPage openLatestEmail() {
        waitClickable(firstEmailRow);
        click(firstEmailRow);
        return new MailinatorEmailPage(driver);
    }

    public MailinatorEmailPage openEmailBySubject(String subject) {
        By emailBySubject = By.xpath("//tr[.//*[contains(normalize-space(),'" + subject + "')]]");
        waitClickable(emailBySubject);
        click(emailBySubject);
        return new MailinatorEmailPage(driver);
    }
}
