package testgroup;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testgroup.ElementsPage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Epic("DemoQA Testing")
@Feature("Elements Page Navigation")
public class ElementsPageTest {
    private static final Logger log = LogManager.getLogger(ElementsPageTest.class);
    private WebDriver driver;
    private ElementsPage elementsPage;

    @Step("Делаем скриншот: {testName}")
    private void takeScreenshot(String testName) {
        if (driver != null) {
            try {
                File screenshotsDir = new File("screenshots");
                if (!screenshotsDir.exists()) {
                    screenshotsDir.mkdir();
                }
                String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destination = new File("screenshots/" + testName + "_" + timestamp + ".png");
                FileUtils.copyFile(screenshot, destination);
                log.info("Скриншот сохранён: {}", destination.getAbsolutePath());
            } catch (Exception e) {
                log.error("Не удалось сохранить скриншот: {}", e.getMessage());
            }
        }
    }

    @BeforeMethod
    @Step("Настройка теста: открытие браузера и страницы Elements")
    public void setUp() {
        log.info("========== НАЧАЛО НАСТРОЙКИ ==========");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        elementsPage = new ElementsPage(driver);
        log.info("Открываем сайт https://demoqa.com/elements");
        driver.get("https://demoqa.com/elements");
        log.info("Сайт успешно открыт");
        log.info("Настройка завершена");
    }

    @Test
    @Description("Клик по пункту меню Text Box и проверка перехода на страницу")
    @Story("Навигация по меню Elements")
    public void clickTextBoxMenuItemTest(){
        log.info("========== ТЕСТ: clickTextBoxMenuItemTest ==========");
        elementsPage.clickTextBoxMenuItem();
        String currentUrl = driver.getCurrentUrl();
        log.info("Текущий URL: {}", currentUrl);
        Assert.assertTrue(currentUrl.contains("/text-box"));
        log.info("Переход на страницу Text Box выполнен успешно");
    }

    @AfterMethod
    @Step("Завершение теста: скриншот при падении и закрытие браузера")
    public void afterTest(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Тест упал: {}", result.getName());
            takeScreenshot(result.getName());

        }

        log.info("========== ЗАВЕРШЕНИЕ ТЕСТА ==========");
        if (driver != null) {
            driver.quit();
            log.info("Браузер закрыт");
        }
    }
}

