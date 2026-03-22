package base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverUtils;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BasePage {
    protected WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

    public void clear(By locator) {
        DriverUtils.getDriver().findElement(locator).clear();
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
        try {
            return waitVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected long parseCurrencyToLong(String text) {
        String digits = text.replaceAll("[^0-9-]", "");
        if (digits == null || digits.isBlank() || digits.equals("-")) {
            return 0L;
        }

        return Long.parseLong(digits);
    }

    public void switchToFrame(By locator) {
        waitVisible(locator);
        driver.switchTo().frame(find(locator));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void switchToNewWindow(String oldWindow) {
        Set<String> windows = driver.getWindowHandles();
        for (String window : windows) {
            if (!window.equals(oldWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
    }
}
