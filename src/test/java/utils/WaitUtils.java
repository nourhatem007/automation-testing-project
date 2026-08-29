package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {
    private WaitUtils() {
    }

    private static WebDriverWait wait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicit.wait")));
    }

    public static WebElement visible(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(WebDriver driver, By locator) {
        return wait(driver).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static boolean urlContains(WebDriver driver, String value) {
        return wait(driver).until(ExpectedConditions.urlContains(value));
    }

    public static boolean textPresent(WebDriver driver, By locator, String value) {
        return wait(driver).until(ExpectedConditions.textToBePresentInElementLocated(locator, value));
    }

    public static void invisibility(WebDriver driver, By locator) {
        wait(driver).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
}