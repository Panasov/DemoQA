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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Epic("DemoQA Testing")
@Feature("Main Page Navigation")
public class MainPageTest {

    private static final Logger log = LogManager.getLogger(MainPageTest.class);
    private WebDriver driver;
    private MainPage mainPage;

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
    @Step("Настройка теста: открытие браузера и главной страницы")
    public void setUp() {
        log.info("========== НАЧАЛО НАСТРОЙКИ ==========");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        mainPage = new MainPage(driver);
        log.info("Открываем сайт https://demoqa.com/");
        driver.get("https://demoqa.com/");
        log.info("Сайт успешно открыт");
        log.info("Настройка завершена");
    }

    @Test
    @Description("Проверка текста логотипа на главной странице")
    @Story("Пользователь видит логотип")
    public void textLogo() {
        log.info("========== ТЕСТ: textLogo ==========");
        String logo = mainPage.getTextLogo();
        Assert.assertEquals(logo, "Selenium Online Training");
        log.info("Логотип проверен");
    }

    @Test
    @Description("Клик по карточке Elements и проверка перехода на страницу")
    @Story("Навигация по карточкам на главной странице")
    public void clickElementsPage(){
        log.info("========== ТЕСТ: clickElements ==========");
        ElementsPage elementsPage = mainPage.clickElements();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/elements"));
        log.info("Переход на страницу ElementsPage осуществлен");
    }

    @Test
    @Description("Клик по карточке Forms и проверка перехода на страницу")
    @Story("Навигация по карточкам на главной странице")
    public void clickFormsPage(){
        log.info("========== ТЕСТ: clickFormsPage ==========");
        FormsPage formsPage = mainPage.clickForms();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/forms"));
        log.info("Переход на страницу FormsPage осуществлен");
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

