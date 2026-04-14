package testgroup;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class ElementsPage {
    private final static Logger log = LogManager.getLogger(ElementsPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    private By textBoxMenuItem = By.xpath("//span[text()='Text Box']");

    public ElementsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("ElementsPage инициализирован");
    }

    public ElementsPage clickTextBoxMenuItem (){
        log.info("Нажимаем на кнопку 'Text Box'");
        wait.until(ExpectedConditions.elementToBeClickable(textBoxMenuItem)).click();
        log.info("Открыта страница 'Text Box'");
        return this;
    }
}
