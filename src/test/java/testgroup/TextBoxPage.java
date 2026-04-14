package testgroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TextBoxPage {
    private static final Logger log = LogManager.getLogger(TextBoxPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private By fullName = By.xpath("//input[@id='userName']");
    private By email = By.xpath("//input[@id='userEmail']");
    private By currentAddress = By.xpath("//textarea[@id='currentAddress']");
    private By permanentAddress = By.xpath("//textarea[@id='permanentAddress']");
    private By submit = By.xpath("//button[@id='submit']");
    private By submittedName = By.xpath("//p[@id='name']");
    private By submittedEmail = By.xpath("//p[@id='email']");
    private By submittedCurrentAddress = By.xpath("//p[@id='currentAddress']");

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("TextBoxPage инициализирован");
    }

    public TextBoxPage typeFullName(String nameValue) {
        log.info("Вводим полное имя: {}", nameValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullName));
        driver.findElement(fullName).sendKeys(nameValue);
        return this;
    }

    public TextBoxPage typeEmail(String emailValue) {
        log.info("Вводим Email: {}", emailValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        driver.findElement(email).sendKeys(emailValue);
        return this;
    }

    public TextBoxPage typeCurrentAddress(String currentAddressValue) {
        log.info("Вводим Current Address: {}", currentAddressValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(currentAddress));
        driver.findElement(currentAddress).sendKeys(currentAddressValue);
        return this;
    }

    public TextBoxPage typePermanentAddress(String permanentAddressValue) {
        log.info("Вводим Permanent Address: {}", permanentAddressValue);
        wait.until(ExpectedConditions.visibilityOfElementLocated(permanentAddress));
        driver.findElement(permanentAddress).sendKeys(permanentAddressValue);
        return this;
    }

    public TextBoxPage clickSubmit() {
        log.info("Нажимаем кнопку Submit");
        wait.until(ExpectedConditions.visibilityOfElementLocated(submit));
        driver.findElement(submit).click();
        return this;
    }


    public TextBoxPage textBoxForm (String nameValue, String emailValue, String currentAddressValue, String permanentAddressValue) {
        log.info("Заполняем форму");
        typeFullName(nameValue);
        typeEmail(emailValue);
        typeCurrentAddress(currentAddressValue);
        typePermanentAddress(permanentAddressValue);
        log.info("Форма отправлена");
        return clickSubmit();
    }

    public String getSubmittedName() {
        log.info("Ожидаем появления результата Name");
        wait.until(ExpectedConditions.visibilityOfElementLocated(submittedName));
        String text = driver.findElement(submittedName).getText();
        log.info("Результат Name получен: {}", text);
        return text;
    }

    public String getSubmittedEmail() {
        log.info("Ожидаем появления результата Email");
        wait.until(ExpectedConditions.visibilityOfElementLocated(submittedEmail));
        String email = driver.findElement(submittedEmail).getText();
        log.info("Результат Email получен: {}", email);
        return email;
    }

    public String getSubmittedCurrentAddress() {
        log.info("Ожидаем появления результата Current Address");
        wait.until(ExpectedConditions.visibilityOfElementLocated(submittedCurrentAddress));
        String currentAddress = driver.findElement(submittedCurrentAddress).getText();
        log.info("Результат Current Address получен: {}", currentAddress);
        return currentAddress;
    }

}
