package testgroup;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Epic("DemoQA Testing")
@Feature("Text Box Form")
public class TextBoxPageTest {
    private static final Logger log = LogManager.getLogger(TextBoxPageTest.class);
    private WebDriver driver;
    private TextBoxPage textBoxPage;
    private By output = By.xpath("//div[@id='output']");

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
    @Step("Настройка теста: открытие браузера и страницы")
    public void setUp() {
        log.info("========== НАЧАЛО НАСТРОЙКИ ==========");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        textBoxPage = new TextBoxPage(driver);
        log.info("Открываем сайт https://demoqa.com/text-box");
        driver.get("https://demoqa.com/text-box");
        log.info("Сайт успешно открыт");
        log.info("Настройка завершена");
    }

    @DataProvider(name = "textBoxData")
    public Object[][] getTextBoxData() {
        return new Object[][]{
                {"Иван Петров", "ivan@test.ru", "Москва, ул. Ленина 1", "Москва, ул. Ленина 1"},
                {"", "", "", ""},
                {"Anna", "anna@test", "Самара", "Пермь"},
                {"petr", "petr", "petr", "petr"},
                {"VeryLongNameForTestingPurpose", "long@test.ru", "Очень длинный адрес для проверки", "Ещё один длинный адрес"}
        };
    }

    @Test(dataProvider = "textBoxData")
    @Description("Заполнение и отправка формы Text Box с разными данными")
    @Story("Пользователь заполняет форму")
    public void testTextBoxForm(String nameValue, String emailValue, String currentAddressValue, String permanentAddressValue) {
        log.info("========== ПАРАМЕТРИЗОВАННЫЙ ТЕСТ ==========");
        log.info("Данные: Name='{}', Email='{}', CurrentAddress='{}', PermanentAddress='{}'", nameValue, emailValue, currentAddressValue, permanentAddressValue);
        textBoxPage.textBoxForm(nameValue, emailValue, currentAddressValue, permanentAddressValue);
        log.info("Тест с данными '{}' выполнен", nameValue);

        log.info("Проверяем появление блока output");
        WebElement outputElement = driver.findElement(output);
        Assert.assertTrue(outputElement.isDisplayed(), "Блок с результатами не появился");

        if (!nameValue.isEmpty()) {
            String submittedName = textBoxPage.getSubmittedName();
            String expectedName = "Name:" + nameValue;
            Assert.assertEquals(submittedName, expectedName, "Имя не совпадает");
            log.info("Имя проверено: {}", submittedName);
        }

        if (!emailValue.isEmpty()) {
            String submittedEmail = textBoxPage.getSubmittedEmail();
            String expectedEmail = "Email:" + emailValue;
            Assert.assertEquals(submittedEmail, expectedEmail, "Email не совпадает");
            log.info("Email проверен: {}", submittedEmail);
        }

        if (!currentAddressValue.isEmpty()) {
            String currentAddress = textBoxPage.getSubmittedCurrentAddress();
            String expectedAddress = "Current Address :" + currentAddressValue;
            Assert.assertEquals(currentAddress, expectedAddress, "Current Address не совпадает");
            log.info("Current Address проверен: {}", currentAddress);
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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