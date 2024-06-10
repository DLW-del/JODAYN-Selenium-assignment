package bookingtest;

import com.booking.BasePage;
import com.booking.BookingPage;
import com.booking.FunctionLibrary;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ReserveTest extends BasePage {
    WebDriver driver;
    BookingPage bookingPage;
    FunctionLibrary functionLibrary;
    Actions actions;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        actions = new Actions(driver);
        bookingPage = new BookingPage(driver);
        functionLibrary = new FunctionLibrary(driver);
    }

    @Test(priority = 1) // Navigate to Booking.com
    public void navigateAndAssert(){
        bookingPage.navigateToBooking();
        bookingPage.assertTextDisplayed();
    }

    @Test(priority = 2, dependsOnMethods ="navigateAndAssert")
    public void searchTargetHotelWithInfo() throws InterruptedException {
        bookingPage.selectDestination("Aqaba/Jordan");
        bookingPage.selectDates();

        try {
            bookingPage.setTravelDetails();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            bookingPage.hitSearch();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 3, dependsOnMethods ="searchTargetHotelWithInfo")
    public void printNumberOfProperty(){
        bookingPage.printPropertiesFound();
        //Assert.assertTrue(true);
    }

    @Test(priority = 4, dependsOnMethods ="printNumberOfProperty")
    public void scrollToTargetPropertyAndGetName(){
        bookingPage.scrollAndDisplayProperty();
     //   Assert.assertTrue(true);
    }

    @Test(priority = 5, dependsOnMethods ="scrollToTargetPropertyAndGetName")
    public void savePropertyAndOpenMyTrip(){
        bookingPage.saveProperty();
        bookingPage.navigateToSavedProperties();

    }

    @Test(priority = 6, dependsOnMethods ="savePropertyAndOpenMyTrip")
    public void assertHotelPriceAndViewProperty(){
        try {bookingPage.assertPrice();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {bookingPage.viewProperty();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 7, dependsOnMethods ="assertHotelPriceAndViewProperty")
    public void reservePropertyAndSelectAmount(){
        bookingPage.reserveProperty();
        bookingPage.selectAmountAndConfirm();
    }

    @Test(priority = 8, dependsOnMethods ="reservePropertyAndSelectAmount")
    public void reserveVerify(){
        Assert.assertTrue(bookingPage.verifyReserve());
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }

}
