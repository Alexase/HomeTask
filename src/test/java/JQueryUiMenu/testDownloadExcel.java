package JQueryUiMenu;

import com.sun.rowset.internal.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class testDownloadExcel {
    WebDriver driver;
    private static final String FILE_NAME = "./menu.xls";
    private static final String TAX_HEADER = "b";
    private static XSSFWorkbook workbook;
    private static XSSFSheet spreadsheet;
    private static XSSFRow row;
    @BeforeClass
    void openBrowser(){

        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        driver=new ChromeDriver();
        // Launch Website
        driver.navigate().to("https://the-internet.herokuapp.com/");
        //go to checkboxes page
        driver.findElement(By.xpath("//*[@id=\"content\"]/ul/li[28]/a")).click();
    }

    @Test(priority=0)
    void downloadExcel() throws InterruptedException {


        Actions builder = new Actions(driver);
        Action mouseOverMenu;
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu")));


        builder.moveToElement(driver.findElement(By.xpath("//*[@id=\"ui-id-3\"]"))).build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ui-id-4\"]")));

        builder.moveToElement(driver.findElement(By.xpath("//*[@id=\"ui-id-4\"]"))).build().perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"ui-id-7\"]/a")));
        driver.findElement(By.xpath("//*[@id=\"ui-id-7\"]/a")).click();

        Thread.sleep(5000);
    }
    @Test (priority=1)
    void testIfDownloaded() throws IOException {
//check in the dowloads folder
        File dir = new File("C:\\Users\\alexandra\\Downloads");
        File[] dirContents = dir.listFiles();
        boolean flag=false;
        for (int i = 0; i < dirContents.length; i++) {
            if (dirContents[i].getName().equals("menu.xls")) {
                // File has been found, it can now be deleted:

                dirContents[i].delete();
                flag=true;
            }
        }
       // PrintTaxValue(); Function to print the value of tax
        Assert.assertEquals(flag,true);
    }
    @AfterClass
    public void afterClass() {
        driver.close();
        System.out.println("Finished Test On Chrome Browser");
    }

    public void PrintTaxValue() throws IOException {
        FileInputStream fis = new FileInputStream(new File(FILE_NAME));
        workbook = new XSSFWorkbook(fis);
        spreadsheet = workbook.getSheetAt(0);
        row = spreadsheet.getRow(0);
        int colNum = getHeaderColNum(TAX_HEADER);
        if (colNum != -1) {
            printCell(TAX_HEADER, 1, colNum, CellType.NUMERIC);
        }
        fis.close();
    }

    public  int getHeaderColNum(String headr) {
        int colNum = -1;
        Iterator<Cell> cellIterator = row.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell.getStringCellValue().equalsIgnoreCase(headr)) {
                colNum = cell.getColumnIndex();
                break;
            }
        }
        return colNum;
    }

    public void printCell(String header, int i, int j, CellType cellType) {
        XSSFRow row = spreadsheet.getRow(i);
        Cell cell = row.getCell(j);

        if (cellType == CellType.NUMERIC) {
            System.out.println(header + ": " + cell.getNumericCellValue());
        }
        if (cellType == CellType.STRING) {
            System.out.println(cell.getStringCellValue());
        }
    }
}
