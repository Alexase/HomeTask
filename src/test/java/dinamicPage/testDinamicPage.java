package dinamicPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class testDinamicPage {
    WebDriver driver;
    @BeforeMethod
    void openBrowser(){
        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        driver=new ChromeDriver();
        // Launch Website
        driver.navigate().to("https://the-internet.herokuapp.com/");
        //go to Example2 link page
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[14]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/a[2]")).click();
    }

    @Test
    void checkIfAppears(){
        //find and click button
        driver.findElement(By.xpath(" //*[@id=\"start\"]/button\n")).click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated (By.xpath("//*[@id=\"finish\"]/h4")));
        String text= driver.findElement(By.xpath("//*[@id=\"finish\"]/h4")).getText();
        Assert.assertEquals(text,"Hello World!");
    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
    }
}
