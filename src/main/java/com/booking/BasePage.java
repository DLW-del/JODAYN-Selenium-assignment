package com.booking;

import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BasePage {

    public static WebDriver driver;


  /*  public void setUpBrowser(String url) {
        ChromeOptions options = new ChromeOptions();

        if (SystemUtils.IS_OS_LINUX){
            options.addArguments("window-size=1200,1080");
            options.addArguments("disable-gpu");
        }
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver= new ChromeDriver(options);
        if (SystemUtils.IS_OS_MAC_OSX||SystemUtils.IS_OS_WINDOWS) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        }

        driver.get(url);
    }*/

    public void tearDown(){
        driver.close();
    }
}
