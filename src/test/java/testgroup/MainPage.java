package testgroup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private static final Logger log = LogManager.getLogger(MainPage.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private By textLogo = By.xpath("//img[contains(@alt, 'Selenium Online Training')]");
    private  By elements = By.xpath("//h5[text()='Elements']");
    private  By forms = By.xpath("//h5[text()='Forms']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("MainPage инициализирован");
    }

    public String getTextLogo(){
        log.debug("Ищем логотип...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(textLogo));
        String text = driver.findElement(textLogo).getDomAttribute("alt");
        log.info("Текст логотипа: {}", text);
        return text;
    }

    public ElementsPage clickElements (){
        log.info("Нажимаем на кнопку 'Elements'");
        wait.until(ExpectedConditions.elementToBeClickable(elements));
        driver.findElement(elements).click();
        log.info("Перешли на страницу 'Elements'");
        return new ElementsPage(driver);
    }

    public FormsPage clickForms (){
        log.info("Нажимаем на кнопку 'Forms'");
        wait.until(ExpectedConditions.elementToBeClickable(forms));
        driver.findElement(forms).click();
        log.info("Перешли на страницу 'Forms'");
        return new FormsPage(driver);
    }
}