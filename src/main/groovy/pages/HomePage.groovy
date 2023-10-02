package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class HomePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    WebDriver driver

    By searchInputLocator = By.id("searchval")
    By searchButtonLocator = By.xpath("//button[@type='submit']")

    HomePage(WebDriver driver) {
        this.driver = driver
    }

    /**
     * Navigates to the home page of the webstore.
     */
    void navigateToHomePage() {
        logger.info("navigating to home page");
        driver.get("https://www.webstaurantstore.com/")
    }

    void searchForProduct(String productName) {
        logger.info("searching for product");
        WebElement searchInput = driver.findElement(searchInputLocator)
        searchInput.sendKeys(productName)

        logger.info("clicking search button");
        WebElement searchButton = driver.findElement(searchButtonLocator)
        searchButton.click()
    }
}
