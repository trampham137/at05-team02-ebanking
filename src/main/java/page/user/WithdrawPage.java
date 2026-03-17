package page.user;

import org.openqa.selenium.By;
import utils.DriverUtils;

public class WithdrawPage {
    private final By accountTextBoxLocator = By.name("j_idt23:j_idt27");
    private final By amountTextBoxLocator = By.name("j_idt23:j_idt29");
    private final By informationTransferTextBoxLocator = By.name("j_idt23:j_idt31");
    private final By transactionButtonLocator = By.name("j_idt23:j_idt34");
    public void enterAccount(int acc){
        DriverUtils.DRIVER.findElement(accountTextBoxLocator).sendKeys(String.valueOf(acc));
    }

    public void enterAmount(double amount){
        DriverUtils.DRIVER.findElement(amountTextBoxLocator).sendKeys(String.valueOf(amount));
    }
    public void enterInformationTransfer(String desc){
        DriverUtils.DRIVER.findElement(informationTransferTextBoxLocator).sendKeys(desc);
    }
    public void clickTransactionButton(){
        DriverUtils.DRIVER.findElement(transactionButtonLocator).click();
    }
    public void enterWithdrawForm(int acc, double amount, String desc){
        enterAccount(acc);
        enterAmount(amount);
        enterInformationTransfer(desc);
    }
}
