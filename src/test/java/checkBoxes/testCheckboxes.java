package checkBoxes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class testCheckboxes {
    WebDriver driver;
    @BeforeMethod
    void openBrowser(){

        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        driver=new ChromeDriver();
        // Launch Website
        driver.navigate().to("https://the-internet.herokuapp.com/");
        //go to checkboxes page
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[6]/a")).click();
    }

    @Test
    void toggleCheckbox() throws InterruptedException {

        List<WebElement> checkboxes = getChecboxes();
        List<Boolean> prev = new ArrayList<>();
        List<Boolean> res = new ArrayList<>();
        prev=checkboxStatus(prev,checkboxes);

        for (int i=0;i<checkboxes.size();i++) {
            Thread.sleep(3000);
            //show message if the checkbox changed the status
            if(changeCheckboxState(checkboxes.get(i)))
            {
                //if the checkbox presed save the opposite  state of the checkbox status
                System.out.println("state of checkbox num: "+i+" changed successfully.New state: "+checkboxes.get(i).isSelected());
                res.add(!checkboxes.get(i).isSelected());
            }
            else {
                //if the checkbox doesnt pressed show message and save the opposite status to result
                System.out.println("state of checkbox num: "+i+" can't be changed");
                res.add(!checkboxes.get(i).isSelected());
            }


        }
        Assert.assertEquals(prev, res);
    }

    @AfterMethod
    public void afterMethod() {
        driver.close();
        System.out.println("Finished Test On Chrome Browser");
    }

    public  List<WebElement> getChecboxes() throws InterruptedException {
        List<WebElement> checkboxes = driver.findElements(By.cssSelector("#checkboxes input[type='checkbox']"));
        return checkboxes;
    }
    //change the state of checkbox
    public  boolean changeCheckboxState(WebElement checkbox)
    {
        boolean prev = checkbox.isSelected();
        checkbox.click();
        return prev != checkbox.isSelected();//return the prev state
    }
    //return the status of checkbox-if selected return true, if not return false
    public List<Boolean>  checkboxStatus(List <Boolean> status,List <WebElement> checkboxes) throws InterruptedException {
        for (int i=0;i<checkboxes.size();i++) {
            Thread.sleep(3000);

            if(checkboxes.get(i).isSelected())
            {
                status.add(true);
            }
            else {
                status.add(false);
            }

        }
        return status;
    }
}
