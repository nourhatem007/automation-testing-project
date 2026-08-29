package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By username = By.cssSelector("input[name='username']");
    private final By password = By.cssSelector("input[name='password']");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By invalidCredentials = By.xpath("//*[contains(normalize-space(),'Invalid credentials')]");
    private final By requiredMessages = By.xpath("//*[normalize-space()='Required']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Enter username: {value}")
    public LoginPage enterUsername(String value) {
        type(username, value);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String value) {
        type(password, value);
        return this;
    }

    @Step("Click Login")
    public void clickLogin() {
        click(loginButton);
    }

    @Step("Login with username and password")
    public void login(String user, String pass) {
        enterUsername(user);
        enterPassword(pass);
        clickLogin();
    }

    @Step("Verify invalid credentials error is displayed")
    public boolean isInvalidCredentialsDisplayed() {
        return isDisplayed(invalidCredentials);
    }

    @Step("Count required validation messages")
    public int getRequiredMessageCount() {
        return driver.findElements(requiredMessages).size();
    }
}
