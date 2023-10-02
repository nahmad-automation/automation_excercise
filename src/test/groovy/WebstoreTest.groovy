package test;

import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import pages.HomePage;
import pages.SearchResultsPage;
import pages.CartPage;
import utils.DriverUtils;

@Feature("Webstore Testing")
@Owner("Nabeel")
public class WebstoreTest {
    WebDriver driver;
    HomePage homePage;
    SearchResultsPage searchResultsPage;
    CartPage cartPage;

    @BeforeEach
    @Step("Setting up the test environment")
    void setUp() {
        driver = DriverUtils.getDriver();
        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    @Story("Search and Cart Operations")
    @Description("Test the functionality of searching for a product, adding it to the cart, and emptying the cart.")
    @Severity(SeverityLevel.CRITICAL)
    void testWebstore() throws InterruptedException {
        navigateToHomePage();
        searchForProduct("stainless work table");
        verifyProductTitles();
        navigateToLastPageOfSearchResults();
        verifyProductAddedToCart();
        viewAndEmptyCart();
        verifyCartIsEmpty();
    }

    @Step("Navigate to home page")
    void navigateToHomePage() {
        homePage.navigateToHomePage();
    }

    @Step("Search for product: {0}")
    void searchForProduct(String product) throws InterruptedException {
        homePage.searchForProduct(product);
    }

    @Step("Verify product titles contain 'Table' or 'table'")
    void verifyProductTitles() throws InterruptedException {
        searchResultsPage.getProductTitles().each { title ->
            def text = title.getText();
            assert text.contains("Table") || text.contains("table");
        }
    }

    @Step("Navigate to the last page of search results")
    void navigateToLastPageOfSearchResults() throws InterruptedException {
        searchResultsPage.navigateToLastPage();
    }

    @Step("Add the last product to cart and verify")
    void verifyProductAddedToCart() throws InterruptedException {
        String actualCartMessage = searchResultsPage.addLastProductToCart();
        String expectedCartMessage = "1 item added to your cart";
        assert actualCartMessage.equals(expectedCartMessage) : "Expected cart message: " + expectedCartMessage + ", but got: " + actualCartMessage;
    }

    @Step("View and empty the cart")
    void viewAndEmptyCart() throws InterruptedException {
        cartPage.viewCart();
        cartPage.emptyCart();
    }

    @Step("Verify the cart is empty")
    void verifyCartIsEmpty() throws InterruptedException {
        String expectedEmptyCartMessage = "Your cart is empty.";
        String actualEmptyCartMessage = cartPage.getEmptyCartMessage();
        assert actualEmptyCartMessage.equals(expectedEmptyCartMessage) : "Expected message: " + expectedEmptyCartMessage + ", but got: " + actualEmptyCartMessage;
    }

    @AfterEach
    @Step("Tearing down the test environment")
    void tearDown() {
        driver.quit();
    }
}
