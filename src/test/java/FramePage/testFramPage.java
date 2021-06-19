package FramePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testFramPage {


    WebDriver driver;
    @BeforeMethod
    void openBrowser(){

        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        driver=new ChromeDriver();
        // Launch Website
        driver.navigate().to("https://the-internet.herokuapp.com/");
        //go to checkboxes page
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[22]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/ul/li[2]/a")).click();
    }

    @Test
    void changeText(){
        //wait to the page
        WebDriverWait wait = new WebDriverWait(driver, 20);

        String myText="Alexandra";
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id=\"mce_0_ifr\"]")));
        //when paragrah is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tinymce\"]")));
        //clean the previos text
        driver.findElement(By.xpath("//*[@id=\"tinymce\"]")).clear();
        //send new text to paragraph
        driver.findElement(By.xpath("//*[@id=\"tinymce\"]")).sendKeys(myText);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tinymce\"]")));
        //read new text
        String text= driver.findElement(By.xpath("//*[@id=\"tinymce\"]")).getText();
        //compare expected text with result
        Assert.assertEquals(text,myText);
    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
        System.out.println("Finished Test On Chrome Browser");
    }
}
