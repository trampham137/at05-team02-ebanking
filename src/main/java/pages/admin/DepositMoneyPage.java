package pages.admin;

import base.BasePage;
import models.DepositData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverUtils;

public class DepositMoneyPage extends BasePage {
    private final By receiverAccountTextboxLocator = By.name("j_idt23:j_idt27");
    private final By amountTextboxLocator = By.name("j_idt23:j_idt29");
    private final By descriptionTextboxLocator = By.name("j_idt23:j_idt31");
    private final By confirmButtonLocator = By.xpath("//input[@value='Xác nhận']");
    private final By successMessageLocator = By.xpath("");

    public DepositMoneyPage(WebDriver driver) {
        super(driver);
    }

    public void depositToAccount(DepositData data) {
        type(receiverAccountTextboxLocator, data.getReceiverAccount());
        type(amountTextboxLocator, data.getAmount());
        type(descriptionTextboxLocator, data.getDescription());
        click(confirmButtonLocator);
    }

    public boolean isDepositSuccessful() {
        return isDisplayed(successMessageLocator);
    }

    public String getSuccessMessage() {
        return getText(successMessageLocator);
    }
}
