package basicAuth;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.util.List;

public class testBasicLogin {
    WebDriver driver;

    @BeforeMethod
    void openBrowser() {

        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        driver = new ChromeDriver();
        // Launch Website
        driver.navigate().to("https://the-internet.herokuapp.com/");
        WebElement navigateAuth = driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[3]/a"));
        navigateAuth.click();
    }

    @Test
    void loginValid() {//test for valid credential
        //Set the username
        String username = "admin";

        //Set the password
        String password = "admin";
        login(username,password);
        Assert.assertTrue(isLoggedIn());

    }

    @Test
    void loginEmpty() {//Test for empty fields
        WebDriverWait wait = new WebDriverWait(driver, 20);

        login("","");
        //Set the password

        Assert.assertFalse(isLoggedIn());


    }


    @Test
    void loginInValid() {//test for invalid credentials
        String username = "test";

        //Set the password
        String password = "test";
        login(username,password);
        Assert.assertFalse(isLoggedIn());


    }

//    @Test
//    void pressCancel() {//test for invalid credentials
//       This test should test the result if test
//
//
//    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
        System.out.println("Finished Test On Chrome Browser");
    }


    //function to check if logedin
    public boolean isLoggedIn()
    {

        List<WebElement> res=  driver.findElements(By.tagName("p"));
        String greetings ="Congratulations! You must have the proper credentials.";

        return res != null && res.size()>0 && res.get(0).getText().equals(greetings);
    }
    //function to login
    public void login(String userName,String password) {
        String URL = "https://{0}:{1}@the-internet.herokuapp.com/basic_auth";
        driver.get(MessageFormat.format(URL, userName, password));

    }
}
