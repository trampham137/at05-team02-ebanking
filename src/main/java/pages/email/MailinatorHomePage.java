package pages.email;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MailinatorHomePage extends BasePage {
    private final By inboxTextbox = By.id("search");
    private final By goButton = By.cssSelector("input#search + button");

    public MailinatorHomePage(WebDriver driver) {
        super(driver);
    }

    public MailinatorInboxPage openInbox(String email) {
        type(inboxTextbox, email);
        click(goButton);
        return new MailinatorInboxPage(driver);
    }
}