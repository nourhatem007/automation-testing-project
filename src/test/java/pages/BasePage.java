package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public abstract class BasePage {
    protected final WebDriver driver;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Click element: {locator}")
    protected void click(By locator) {
        WaitUtils.clickable(driver, locator).click();
    }

    @Step("Enter text into: {locator}")
    protected void type(By locator, String value) {
        WebElement element = WaitUtils.visible(driver, locator);
        element.clear();
        element.sendKeys(value);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return WaitUtils.visible(driver, locator).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }

    protected String text(By locator) {
        return WaitUtils.visible(driver, locator).getText();
    }
}
