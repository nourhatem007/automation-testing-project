package base;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;
import utils.DriverFactory;

public abstract class BaseTest {
    protected final Logger log = LogManager.getLogger(getClass());

    @BeforeMethod(alwaysRun = true)
    @Step("Start a new browser session")
    public void setUp() {
        DriverFactory.initializeDriver();
        DriverFactory.getDriver().get(ConfigReader.get("base.url") + "auth/login");
        log.info("Opened OrangeHRM login page");
    }

    @AfterMethod(alwaysRun = true)
    @Step("Close browser session")
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
