package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {
    public static void waitForElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForElementClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForFrameAndSwitch(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }
}
