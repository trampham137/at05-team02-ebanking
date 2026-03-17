package pages.transfer.internal;

import models.InternalTransferData;
import models.OpenAccountData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;


import java.time.Duration;

public class InternalTransferPage extends BasePage {

    private By dropdownByLabel(String label) {
        return By.xpath("//td[label[normalize-space()='" + label + "']]/following-sibling::td//div[contains(@class,'ui-selectonemenu')]");
    }

    private By inputByLabel(String label) {
        return By.xpath("//td[label[normalize-space()='" + label + "']]/following-sibling::td//input[@type='text']");
    }

    private By valueByLabel(String label) {
        return By.xpath("//td[label[normalize-space()='" + label + "']]/following-sibling::td//label");
    }

    private By dropdownOption(String optionText) {
        return By.xpath("//li[normalize-space()='" + optionText + "']");
    }

    private final By sourceAccountDropdownLocator = dropdownByLabel("Tài khoản nguồn");
    private final By availableBalanceValueLocator = valueByLabel("Số dư khả dụng");
    private final By receiverAccountTextboxLocator = inputByLabel("Tài khoản nhận");
    private final By amountTextboxLocator = inputByLabel("Số tiền");
    private final By descriptionTextboxLocator = inputByLabel("Nội dung thanh toán");
    private final By confirmButtonLocator = By.xpath("//td//input[@value='Xác nhận']");

    public InternalTransferPage(WebDriver driver) {
        super(driver);
    }

    public void selectSourceAccount(String sourceAccount) {
        click(sourceAccountDropdownLocator);
        click(dropdownOption(sourceAccount));
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
