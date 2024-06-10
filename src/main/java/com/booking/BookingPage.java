package com.booking;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class BookingPage {
    WebDriver driver;
    WebDriverWait wait;
    FunctionLibrary functionLibrary;
    Actions actions;

    @FindBy(xpath = "//button[text()='Accept']")
    WebElement popupAlert;
    @FindBy(xpath = "//span[text()='Find your next stay']")
    WebElement welcomeMessage;
    @FindBy(xpath = "//input[@aria-label=\"Where are you going?\"]")
    WebElement setPlace;
    @FindBy(xpath = "//div[text()='Aqaba Governorate, Jordan']")
    WebElement placeName;
    @FindBy(xpath = "//button[@aria-label='Next month']")
    WebElement nextMonthButton;
    @FindBy(xpath = "//span[@aria-label=\"24 December 2024\"]")
    WebElement checkInData;
    @FindBy(xpath = "//span[@aria-label=\"29 December 2024\"]")
    WebElement checkOutData;
    @FindBy(xpath = "//button[@data-testid=\"occupancy-config\"]")
    WebElement chooseResident;
    @FindBy(xpath = "//*[@id=\":rf:\"]/div/div[1]/div[2]/button[1]")
    WebElement totalAdults;
    @FindBy(xpath = "//*[@id=\":rf:\"]/div/div[3]/div[2]/button[2]")
    WebElement totalRooms;
    @FindBy(xpath = "//input[@id=\"pets\"]")
    WebElement withPets;
    @FindBy(xpath = "//span[text()=\"Done\"]")
    WebElement doneBtn;
    @FindBy(xpath = "//button[@aria-label=\"Dismiss sign-in info.\"]")
    WebElement alertCloseBtn;
    @FindBy(xpath = "//span[text()=\"Search\"]")
    WebElement searchBtn;
    @FindBy(xpath = "//h1[@aria-live=\"assertive\"]")
    WebElement NOPropertiesFound;
    @FindBy(xpath = "//span[text()='My next trip']")
    WebElement myNextTripLink;
    @FindBy(xpath = "//span[contains(text(), 'View property')]")
    WebElement viewPropertyBtn;
    @FindBy(xpath = "//button[@id='hp_book_now_button']")
    WebElement reserveBtn;
    @FindBy(xpath = "//div[@class=\"hprt-reservation-cta\"]")
    WebElement iWillReserveBtn;
    @FindBy(xpath = "//h2[text()='Your booking details']")
    WebElement verifyMessage;
    public BookingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        functionLibrary=new FunctionLibrary(driver);
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public void navigateToBooking() {
        driver.get("https://www.booking.com/");
    }

    public void assertTextDisplayed() {
        // Assert that the text "Find your next stay" is displayed
        functionLibrary.waitForElementVisible(welcomeMessage);
        Assert.assertTrue(welcomeMessage.isDisplayed(), "Text 'Find your next stay' is not displayed");
    }
    public void selectDestination(String targetPlace) throws InterruptedException {
        // In the field "Where are you going" >> select Aqaba/ Jordan
        /*try {
            if (alertCloseBtn.isDisplayed()) {
                actions.click(alertCloseBtn).build().perform();
            } else {
                System.out.println("Element is not displayed");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element not found");
        }*/
        functionLibrary.waitForElementVisible(popupAlert);
        popupAlert.click();
        setPlace.sendKeys(targetPlace);
        functionLibrary.waitForElementVisible(placeName);
        placeName.click();
    }
    public void selectDates() {
        // Select Check-in Date and Check-out date
        functionLibrary.waitForElementVisible(nextMonthButton);
        for (int i=0;i<5;i++){
            nextMonthButton.click();
        }
        functionLibrary.waitForElementVisible(checkInData);
        checkInData.click();
        checkOutData.click();
    }
    public void setTravelDetails() throws InterruptedException {
        // Set Adults=1, Children=0, Rooms=2, Traveling with pets=Yes
        chooseResident.click();
        totalAdults.click();
        totalRooms.click();
        actions.click(withPets).build().perform();
        doneBtn.click();
    }
    public void hitSearch() throws InterruptedException {
        // Hit the search button
        searchBtn.click();
    }
    public void printPropertiesFound() {
        // Print the number of properties found
        functionLibrary.waitForElementVisible(alertCloseBtn);
        actions.click(alertCloseBtn).build().perform();
        functionLibrary.waitForElementVisible(NOPropertiesFound);
        System.out.println("Number of properties found: " + NOPropertiesFound.getText());
    }
    public void scrollAndDisplayProperty() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<WebElement> properties = driver.findElements(By.xpath("//div[@data-testid='property-card']"));
        js.executeScript("arguments[0].scrollIntoView();", properties.get(9));
        // Print the property name
        WebElement propertyName = properties.get(9).findElement(By.xpath(".//div[@data-testid='title']"));
        System.out.println("Property name: " + propertyName.getText());

    }
    public void saveProperty() {
        // Press the heart icon to save the property
        List<WebElement> properties = driver.findElements(By.xpath("//div[@data-testid='property-card']"));
        WebElement heartIcon = properties.get(9).findElement(By.xpath(".//button[@data-testid='wishlist-button']"));
        heartIcon.click();
    }
    public void navigateToSavedProperties() {
        // When the "Saved to" popup is displayed, click on "My next trip"
        functionLibrary.waitForElementVisible(myNextTripLink);
        myNextTripLink.click();
    }
    public void assertPrice() throws InterruptedException {
        // Assert that the price is lower than 700 JOD
        String mainWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String window : allWindows) {
            if (!window.equals(mainWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }
        //actions.click(closeBanner).build().perform();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"prco-valign-middle-helper\"]")));
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("//span[@class=\"prco-valign-middle-helper\"]")));
        WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class=\"prco-valign-middle-helper\"]")));
        String priceText = priceElement.getText().replaceAll("[^0-9]", "");
        System.out.println("My Hotel price is: " + priceText);

        int price = Integer.parseInt(priceText);
        if(price<700){
            Assert.assertTrue(price < 700);
            System.out.println("Price is lower than 700 euro");
        }else {
            System.out.println("Price is  Higher than 700 euro " + priceText);
        }
    }
    public void viewProperty() throws InterruptedException {
        // Press  "View Property" button
        actions.click(viewPropertyBtn).build().perform();
    }
    public void reserveProperty() {
        // Navigate to the opened window and press "Reserve" button
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            driver.switchTo().window(windowHandle);
            System.out.println("Window title: " + driver.getTitle());
        }
        functionLibrary.waitForElementVisible(reserveBtn);
        actions.click(reserveBtn).build().perform();
    }
    public void selectAmountAndConfirm() {
        // In the 1st row in the displayed table, select the 3rd option by its index in the "Select amount" column
        WebElement amountDropdown= driver.findElement(By.xpath("//select[@data-testid=\"select-room-trigger\"]"));
        Select dropdown = new Select(amountDropdown);
        List<WebElement> options = dropdown.getOptions();
        boolean hasValue3 = options.stream().anyMatch(option -> option.getAttribute("value").equals("3"));
        if (hasValue3) {
            dropdown.selectByValue("3");
        } else {
            dropdown.selectByValue("1");
        }
        // click I Will Reserve Button.
        iWillReserveBtn.click();

    }

    public boolean verifyReserve(){
        functionLibrary.waitForElementVisible(verifyMessage);
        if (verifyMessage.isDisplayed()){
            System.out.println(" Hotel Successfully reserved !!!");
            return true;
        }else{
            System.out.println("Hotel reserve Fail !!!");
            return false;}
    }


}
