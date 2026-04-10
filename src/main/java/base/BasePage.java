package base;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> finds(By locator) {
        return driver.findElements(locator);
    }

    protected void type(By locator, String text) {
        WebElement element = waitVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void clear(By locator) {
        waitVisible(locator).clear();
    }

    protected void click(By locator) {
        waitClickable(locator).click();
    }

    protected void selectByVisibleText(By locator, String visibleText) {
        Select select = new Select(waitVisible(locator));
        select.selectByVisibleText(visibleText);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText().trim();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitVisible(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected String getAttribute(By locator, String attributeName) {
        return driver.findElement(locator).getAttribute(attributeName);
    }

    protected long parseCurrencyToLong(String text) {
        String digits = text.replaceAll("[^0-9-]", "");
        if (digits.isBlank() || digits.equals("-")) {
            return 0L;
        }
        return Long.parseLong(digits);
    }

    // wait
    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitInVisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitUntilTextNotEmpty(By locator) {
        wait.until(driver -> {
            String text = driver.findElement(locator).getText();
            return !text.trim().isEmpty();
        });
    }

    protected void waitForFrameAndSwitch(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }

    // switch
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    protected void switchToWindowByIndex(int index) {
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.get(index));
    }

    protected void switchToNewestWindow() {
        java.util.List<String> windows = new java.util.ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(windows.getLast());
    }
}
