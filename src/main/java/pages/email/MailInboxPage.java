package pages.email;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.DriverUtils;
import utils.WaitUtils;

import java.util.List;

import static utils.WaitUtils.waitForFrameAndSwitch;

public class MailInboxPage {
    private final By searchTextBoxLocator = By.id("search");
    private final By goButtonLocator = By.xpath("//button[text()='GO']");
    private final By headerTableLocator = By.xpath("//tr[@style='cursor: pointer;height: 55px;']");
    private final By emailRowLocator = By.xpath("//tr[@ng-repeat='email in emails']");
    private final By sendCodeOTPLocator = By.xpath("//tr[1]//td[contains(text(),'Send Code OPT')]");
    private final By emailBodyFrame = By.id("texthtml_msg_body");
    private final By otpTextLocator = By.xpath("//body[contains(.,'OTP')]");

    public void enterEmail(String email) {
        WaitUtils.waitForElementVisible(searchTextBoxLocator);
        DriverUtils.getDriver().findElement(searchTextBoxLocator).sendKeys(email);
    }

    public void clickButtonGo() {
        WaitUtils.waitForElementVisible(goButtonLocator);
        DriverUtils.getDriver().findElement(goButtonLocator).click();
    }

    public void openEmail(String email) {
        enterEmail(email);
        clickButtonGo();
    }

    public void refreshInbox() {
        DriverUtils.getDriver().navigate().refresh();
    }

    public int getSubjectColumnIndex() {
        WebElement headerRow = DriverUtils.getDriver().findElement(headerTableLocator);
        List<WebElement> columns = headerRow.findElements(By.tagName("td"));
        for (int i = 0; i < columns.size(); i++) {
            String headerText = columns.get(i).getText().trim();
            if (headerText.equalsIgnoreCase("Subject")) {
                return i + 2;
            }
        }
        return -1;
    }

    public String getCellTextByColumn(int columnIndex) {
        WaitUtils.waitForElementVisible(emailRowLocator);
        WebElement emailRow = DriverUtils.getDriver().findElement(emailRowLocator);

        String information = emailRow.findElement(By.xpath("./td[" + columnIndex + "]")).getText().trim();
        System.out.println(information);
        return information;

    }

    public boolean isSubjectSendCode() {
        int subjectColumn = getSubjectColumnIndex();
        String subjectText = getCellTextByColumn(subjectColumn);
        if (subjectColumn == -1) {
            return false;
        }
        return subjectText.equals("Send Code OPT");

    }

    public void clickSendCodeOTP() {
        WaitUtils.waitForElementVisible(sendCodeOTPLocator);
        DriverUtils.getDriver().findElement(sendCodeOTPLocator).click();
    }

    public boolean isContentContainOTP() {
        waitForFrameAndSwitch(emailBodyFrame);
        WaitUtils.waitForElementVisible(otpTextLocator);
        String value = DriverUtils.getDriver().findElement(otpTextLocator).getText();
        DriverUtils.getDriver().switchTo().defaultContent();
        return value.contains("OTP:");
    }

    public String getOtpFromEmail() {
        DriverUtils.getDriver().switchTo().frame("texthtml_msg_body");
        WaitUtils.waitForElementVisible(otpTextLocator);
        String otp = DriverUtils.getDriver().findElement(otpTextLocator).getText().replace("OTP:", "").trim();
        DriverUtils.getDriver().switchTo().defaultContent();
        return otp;
    }

    public boolean isOtpValid(String otp) {
        return otp.matches("[A-Z0-9]{10}");
    }

}
