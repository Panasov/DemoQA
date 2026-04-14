package testgroup;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;

public class FormsPage {
    private final static Logger log = LogManager.getLogger(FormsPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    public FormsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("FormsPage инициализирован");
    }
}
