package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.DriverUtils;

import java.util.List;

public class BasePage {
    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> finds(By locator) {
        return driver.findElements(locator);
    }

    protected void type(By locator, String text) {
        WebElement element = this.driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void selectByVisibleText(By locator, String visibleText) {
        Select select = new Select(find(locator));
        select.selectByVisibleText(visibleText);
    }

    protected void click(By locator) {
        this.driver.findElement(locator).click();
    }

    protected String getText(By locator) {
        return this.driver.findElement(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        return !driver.findElements(locator).isEmpty() && driver.findElement(locator).isDisplayed();
    }

    protected long parseCurrencyToLong(String text) {
        String digits = text.replaceAll("[^0-9-]", "");
        if (digits == null || digits.isBlank() || digits.equals("-")) {
            return 0L;
        }

        return Long.parseLong(digits);
    }
}
