package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverUtils {
    private static WebDriver DRIVER;

    // Prevent creating object because this is a utility class
    private DriverUtils() {
    }

    // static: call directly by class name, no need to create object
    public static void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        DRIVER = new ChromeDriver(options);
        DRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    public static WebDriver getDriver() {
        return DRIVER;
    }

    public static void quitDriver() {
        if (DRIVER != null) {
            DRIVER.quit();
            DRIVER = null;
        }
    }
}
