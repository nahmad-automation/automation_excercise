package pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class SearchResultsPage {
    private static final Logger logger = LoggerFactory.getLogger(SearchResultsPage.class);
    WebDriver driver
    

    SearchResultsPage(WebDriver driver) {
        this.driver = driver
    }

    // Method to get product titles
    List<WebElement> getProductTitles() {
        logger.info("Getting product titles...")
        return driver.findElements(By.xpath('//*[@data-testid="productBoxContainer"]'))
    }

    // Method to navigate to the last page
    void navigateToLastPage() {
        logger.info("Navigating to the last page...")
        
        // Scroll to the bottom of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)")

        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#paging a:last-child")))

        // Click on the last page link
        logger.info("clicking last page link")
        WebElement lastPageLink = driver.findElement(By.xpath("//li[a[contains(@aria-label, 'last page')]]"))
        lastPageLink.click()
    }

    // Method to add the last product to the cart
    String addLastProductToCart() {
        logger.info("Adding the last product to the cart...")
        
        // Check if there are any products and click on the last one
        if (!productTitles.isEmpty()) {
            logger.info("clicking last product in search")
            WebElement lastProduct = productTitles.get(productTitles.size() - 1)
            lastProduct.click()

            // Wait for the "Add to Cart" button to be clickable and then click on it
            logger.info("clicking add to cart")
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("buyButton"))).click();

            // Wait for the cart message to be visible and then retrieve its text
            logger.info("getting cart message to verify item added to cart")
            WebElement cartMessageElement = new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.amount[data-testid='amount-in-cart']")));
            return cartMessageElement.getText();

        } else {
            // Handle the case where no products are found, if necessary
            logger.warning("No products found!")
            return null;
        }
    }
}
