package d19_09_2022;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BootstrapTableTests {
//	1.Zadatak
//	Kreirati BootstrapTableTests klasu koja ima:
//	Base url: https://s.bootsnipp.com

	private WebDriver driver;
	private WebDriverWait wait;
	String baseUrl = "https://s.bootsnipp.com";
	
	@BeforeClass 
	public void setup () {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
	}
	
	@BeforeMethod 
	public void beforeMethod () {
	driver.get(baseUrl);
	}
	
//	Test #1: Edit Row
//	Podaci:
//	First Name: ime polaznika
//	Last Name: prezime polaznika
//	Middle Name: srednje ime polanzika
//	Koraci:
//	Ucitati stranu /iframe/K5yrx
//	Verifikovati naslov stranice Table with Edit and Update Data - Bootsnipp.com
//	Klik na Edit dugme prvog reda
//	Sacekati da dijalog za Editovanje bude vidljiv
//	Popuniti formu podacima. 
//	Bice potrebno da pre unosa tekst pobrisete tekst koji vec postoji, za to se koristi metoda clear. Koristan link
//	Klik na Update dugme
//	Sacekati da dijalog za Editovanje postane nevidljiv
//	Verifikovati da se u First Name celiji prvog reda tabele javlja uneto ime
//	Verifikovati da se u Last Name celiji prvog reda tabele javlja uneto prezime
//	Verifikovati da se u Middle Name celiji prvog reda tabele javlja uneto srednje ime
//	Za sve validacije ispisati odgovarajuce poruke u slucaju greske

	@Test (priority = 100)
	public void editRow () {
	
		String firstName = "Petar";
		String lastName = "Petrovic";
		String middleName = "Jovan";
		
		driver.get(baseUrl + "/iframe/K5yrx");
		String actualTitle = driver.getTitle();
		String expectedTitle = "Table with Edit and Update Data - Bootsnipp.com";
		Assert.assertEquals(actualTitle, expectedTitle, "ERROR: incorrect title");
		
		driver.findElement(By.xpath("//*[@data-uid = '1']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit")));
		driver.findElement(By.xpath("//*[@id = 'fn']")).clear();
		
		driver.findElement(By.xpath("//*[@id = 'fn']")).sendKeys(firstName);
		driver.findElement(By.xpath("//*[@id = 'ln']")).clear();
		driver.findElement(By.xpath("//*[@id = 'ln']")).sendKeys(lastName);
		driver.findElement(By.xpath("//*[@id = 'mn']")).clear();
		driver.findElement(By.xpath("//*[@id = 'mn']")).sendKeys(middleName);
		driver.findElement(By.xpath("//*[@id = 'up']")).click();
		
		driver.findElement(By.id("up")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("edit")));
		
		Assert.assertEquals(driver.findElement(By.id("f1")).getText(), "Petar", "ERROR - incorrect first name");
		Assert.assertEquals(driver.findElement(By.id("l1")).getText(), "Petrovic", "ERROR - incorrect last name");
		Assert.assertEquals(driver.findElement(By.id("m1")).getText(), "Jovan", "ERROR - incorrect middle name");
	}
	
//	Test #2: Delete Row
//	Podaci:
//	First Name: ime polaznika
//	Last Name: prezime polaznika
//	Middle Name: srednje ime polanzika
//	Koraci:
//	Ucitati stranu /iframe/K5yrx
//	Verifikovati naslov stranice Table with Edit and Update Data - Bootsnipp.com
//	Klik na Delete dugme prvog reda
//	Sacekati da dijalog za brisanje bude vidljiv
//	Klik na Delete dugme iz dijaloga 
//	Sacekati da dijalog za Editovanje postane nevidljiv
//	Verifikovati da je broj redova u tabeli za jedan manji
//	Za sve validacije ispisati odgovarajuce poruke u slucaju greske

	
	@Test (priority = 200)
	public void deleteRow () {
		String firstName = "Petar";
		String lastName = "Petrovic";
		String middleName = "Jovan";
		
		driver.get(baseUrl + "/iframe/K5yrx");
		String actualTitle = driver.getTitle();
		String expectedTitle = "Table with Edit and Update Data - Bootsnipp.com";
		Assert.assertEquals(actualTitle, expectedTitle, "ERROR: incorrect title");
		
		driver.findElement(By.xpath("//*[@data-target = '#delete']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete")));
		
		driver.findElement(By.xpath("//*[@id = 'del']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id = 'del']")));
		driver.findElement(By.xpath("//*[@id = 'del']")).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id = 'del']")));
		
		Assert.assertFalse(driver.findElement(By.xpath("//tbody/tr")).isDisplayed(), 
				"ERROR - the number of rows is not lawer for the value of 1");
	}

//	Test #3: Take a Screenshot
//	Koraci:
//	Ucitati stranu  /iframe/K5yrx
//	Verifikovati naslov stranice Table with Edit and Update Data - Bootsnipp.com
//	Kreirati screenshot stranice. Koristan link https://www.guru99.com/take-screenshot-selenium-webdriver.html
//	Fajl cuvajte na putanji gde su vam bile slike od proslog domaceg. Na putanji: src/paket_za_domaci/nazivslike.png

	@Test (priority = 300)
	public void takeAScreenshot () throws IOException {
	
	driver.get(baseUrl + "/iframe/K5yrx");
	String actualTitle = driver.getTitle();
	String expectedTitle = "Table with Edit and Update Data - Bootsnipp.com";
	Assert.assertEquals(actualTitle, expectedTitle, "ERROR: incorrect title");

	TakesScreenshot scrceenShot = ((TakesScreenshot)driver);
	File scrceenShotFile = scrceenShot.getScreenshotAs(OutputType.FILE);
	File destinationFile = new File ("img/Screenshot_8.png");
	FileHandler.copy(scrceenShotFile, destinationFile);
}
	
  @AfterMethod
  public void afterMethod() {
  }
  
  @AfterClass
  public void afterClass () throws InterruptedException {
  Thread.sleep(5000);
  driver.close();
  }}