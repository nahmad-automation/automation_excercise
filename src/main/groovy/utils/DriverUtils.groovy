package utils

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.util.concurrent.TimeUnit;

import java.util.logging.Logger
import java.util.logging.Level

class DriverUtils {
    private static final Logger LOGGER = Logger.getLogger(DriverUtils.class.name)

    static WebDriver getDriver() {
        String projectPath = new File("").getAbsolutePath()
        String os = System.getProperty("os.name").toLowerCase()

        if (os.contains("win")) {
            // Windows setup
            System.setProperty("webdriver.chrome.driver", projectPath + "\\drivers\\chromedriver.exe")
            LOGGER.info("Set ChromeDriver path for Windows.")
        } else if (os.contains("mac")) {
            // macOS setup
            System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver")
            LOGGER.info("Set ChromeDriver path for macOS.")
        } else {
            System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver")
            LOGGER.info("Set ChromeDriver path for other OS.")
        }

        if (System.getProperty("webdriver.chrome.driver") == null || System.getProperty("webdriver.chrome.driver").isEmpty()) {
            try {
                WebDriverManager.chromedriver().setup()
                LOGGER.info("WebDriverManager set up successfully.")
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error setting up WebDriverManager.", e)
            }
        }

        // Create a new ChromeDriver instance
        WebDriver driver = new ChromeDriver()
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        LOGGER.info("ChromeDriver instance created.")

        // Optional: Maximize the browser window
        driver.manage().window().maximize()
        LOGGER.info("Browser window maximized.")

        return driver
    }
}
