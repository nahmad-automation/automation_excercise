package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class CartPage {
    WebDriver driver
    private static final Logger logger = LoggerFactory.getLogger(CartPage.class);

    CartPage(WebDriver driver) {
        this.driver = driver
    }

    // Method to click on the "View Cart" button
    void viewCart() {
        logger.info("clicking view cart button");
        WebElement viewCartButton = driver.findElement(By.xpath('//a[@href="/viewcart.cfm"]'))
        viewCartButton.click()
    }

    // Method to empty the cart
    void emptyCart() {
        WebDriverWait wait = new WebDriverWait(driver, 10) // waits up to 10 seconds

        // Wait for the "Empty Cart" button to be clickable
        WebElement emptyCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.emptyCartButton")))
        emptyCartButton.click()
        logger.info("clicked empty cart button");

        // Wait for the confirmation button to be clickable and then click it
        WebElement confirmEmptyCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'btn') and contains(@class, 'align-middle') and contains(@class, 'font-semibold') and contains(text(), 'Empty')]")))
        confirmEmptyCartButton.click()
        logger.info("clicked confirm empty cart button");
    }

    // Method to get the text from the "Your cart is empty." element
    String getEmptyCartMessage() {
        logger.info("getting empty cart message");
        WebElement emptyCartMessageElement = driver.findElement(By.cssSelector("p.header-1"))
        return emptyCartMessageElement.getText()
    }
}
