package pages.email;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MailinatorHomePage extends BasePage {
    private final By inboxTextbox = By.id("search");
    private final By goButton = By.cssSelector("input#search + button");

    public MailinatorHomePage(WebDriver driver) {
        super(driver);
    }

    @Step("Open Mailinator inbox for email: {email}")
    public MailinatorInboxPage openInbox(String email) {
        type(inboxTextbox, email);
        click(goButton);
        return new MailinatorInboxPage(driver);
    }
}