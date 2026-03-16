package pages.transfer.internal;

import models.InternalTransferData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;


import java.time.Duration;

public class InternalTransferPage extends BasePage {
    private final By sourceAccountDropdownLocator = By.xpath("");
    private final By availableBalanceValueLocator = By.xpath("");
    private final By receiverAccountTextboxLocator = By.xpath("");
    private final By amountTextboxLocator = By.xpath("");
    private final By descriptionTextboxLocator = By.xpath("");
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Xác nhận']");

    public InternalTransferPage(WebDriver driver) {
        super(driver);
    }

    public void selectSourceAccount(String sourceAccount) {
        selectByVisibleText(sourceAccountDropdownLocator, sourceAccount);
    }

    public long getAvailableBalance() {
        return parseCurrencyToLong(getText(availableBalanceValueLocator));
    }

    public void enterReceiverAccount(String receiverAccount) {
        type(receiverAccountTextboxLocator, receiverAccount);
    }

    public void enterAmount(String amount) {
        type(amountTextboxLocator, amount);
    }

    public void enterDescription(String description) {
        type(descriptionTextboxLocator, description);
    }

    // TODO: Them ... luu bien?
    public void fillTransferForm(InternalTransferData data) {
        selectSourceAccount(data.getSourceAccount());
        enterReceiverAccount(data.getReceiverAccount());
        enterAmount(data.getAmount());
        enterDescription(data.getDescription());
    }

    public void clickConfirmButton() {
        click(confirmButtonLocator);
    }

}
