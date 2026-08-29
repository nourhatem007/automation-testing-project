package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.JsonDataReader;
import utils.RetryAnalyzer;

public class LoginTests extends BaseTest {

    @Test(dataProvider = "loginCredentials", retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that valid credentials redirect the user to the dashboard")
    public void validLoginTest(String username, String password) {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.login(username, password);

        Assert.assertTrue(DriverFactory.getDriver().getCurrentUrl().contains("/dashboard/index"),
                "URL should contain /dashboard/index after successful login");
        Assert.assertTrue(new DashboardPage(DriverFactory.getDriver()).isDashboardDisplayed(),
                "Dashboard header should be displayed after successful login");
    }

    @DataProvider(name = "loginCredentials")
    public Object[][] loginCredentials() {
        return new Object[][]{{
                JsonDataReader.get("login.validUsername"),
                JsonDataReader.get("login.validPassword")
        }};
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify invalid username/password shows Invalid credentials")
    public void invalidLoginTest() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.login(
                JsonDataReader.get("login.invalidUsername"),
                JsonDataReader.get("login.invalidPassword")
        );

        Assert.assertTrue(loginPage.isInvalidCredentialsDisplayed(),
                "Invalid credentials error should be displayed");
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify Required validation appears for both empty login fields")
    public void emptyFieldsLoginTest() {
        LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.clickLogin();

        Assert.assertEquals(loginPage.getRequiredMessageCount(), 2,
                "Exactly two Required messages should be displayed for empty username and password");
    }
}
