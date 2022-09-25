package d20_09_2022_tests;


import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import d20_09_2022_pages.BuyBoxPage;
import d20_09_2022_pages.HeaderPage;
import d20_09_2022_pages.LayerCartPage;
import d20_09_2022_pages.TopMenuPage;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutomationPracticeTests {
	
//	Kreirati klasu AutomationPracticeTests 
//	baseUrl: http://automationpractice.com/

	private WebDriver driver;
	private WebDriverWait wait;
	private String baseUrl = "http://automationpractice.com/";
	private BuyBoxPage buyBoxPage;
	private TopMenuPage topMenuPage;
	private HeaderPage headerPage;
	private LayerCartPage layerCartPage;
	private Actions actions;
	private SoftAssert softAssert;
	
	@BeforeClass
	public void setup () {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		driver.get(baseUrl);
		buyBoxPage = new BuyBoxPage(driver, wait);
		topMenuPage = new TopMenuPage(driver, wait);
		headerPage = new HeaderPage(driver, wait);
		layerCartPage = new LayerCartPage(driver, wait);
		actions = new Actions (driver);
		softAssert = new SoftAssert();
	}
	
//	Test #1:  Adding product to the cart
//	Ucitati stranicu /index.php?id_product=1&controller=product
//	Odskrolati do buy box-a
//	Postavite kolicinu na 3
//	Izaberite velicinu L
//	Izaberite plavu boju
//	Kliknite na dugme add to cart
//	Cekajte da dijalog layer cart bude vidljiv
//	Verifikovati da je kolicina iz layer cart dijalog 3
//	Verifikovati da je velicina L
//	Verifikovati da je izabran proizvod sa plavom bojom
//	Verifikovati da je total price 3 puta veci od cene proizvoda
//	Kliknite na dugme continue shopping
//	cekajte da dijalog layer cart postane nevidljiv
//	Izaberite novi proizvod sa kolicinom 1, velicinom S i bojom Organe
//	Kliknite na dugme add to cart
//	Cekajte da dijalog bude vidljiv
//	kliknite na dugme proceed to checkout
//	Verifikujte da je naslov stranice Order - My Store

    @Test (priority=100)
    public void addingProductToTheCart() {
    driver.get(baseUrl + "/index.php?id_product=1&controller=product");
    buyBoxPage.scrollToBuyBoxElement();
    
    buyBoxPage.getQuantityInput().clear();
    buyBoxPage.getQuantityInput().sendKeys("3");
    
    buyBoxPage.getSize().selectByVisibleText("L");
    buyBoxPage.getColors("Blue").click();
    buyBoxPage.getAddToCartButton().click();
    
    layerCartPage.waitForElementVisibility();
    
    String expectedQuantity = "3";
    String actualQuantity = layerCartPage.getQuantityNumber().getText();
    Assert.assertEquals(actualQuantity, expectedQuantity, "Error - Quantity should be 3");
    
    String expectedSize = "L";
    String actualSize = layerCartPage.getProductAttribute().getText();
    Assert.assertTrue(actualSize.contains(expectedSize), "Error - Size should be L");
    
    String totalPriceText = layerCartPage.getTotalPrice().getText().replaceAll("[$]", "");
    double totalPrice = Double.parseDouble(totalPriceText);
    String productPriceText = buyBoxPage.getProductPrice().getText().replaceAll("[$]", "");
    double productPrice = Double.parseDouble(productPriceText);
    Assert.assertTrue(totalPrice == productPrice * 3,"Error - total price should be 3 times the Product price");
    
    layerCartPage.getContiueShoppingButton().click();
    layerCartPage.waitForElementVisibility();
    
    buyBoxPage.getQuantityInput().clear();
    buyBoxPage.getQuantityInput().sendKeys("1");
    
    buyBoxPage.getSize().selectByVisibleText("S");
    buyBoxPage.getColors("Orange").click();
    buyBoxPage.getAddToCartButton().click();
    layerCartPage.waitForElementVisibility();
    layerCartPage.getProceedToCheckoutButton().click();
    
    String expectedTitle = "Order - My Store";
	String actualTitle = driver.getTitle();
	Assert.assertEquals(actualTitle, expectedTitle, "Error - Title should be 'Order - My Store'");
	}
    

//    Test #2:  Top menu mouse over
//    Predjite misem preko women linka. Koristan link kako da predjete misem preko nekog elementa link
//    Verifikujte da je podmeni za women deo vidljiv
//    Predjite misem preko dresses linka. 
//    Verifikujte da je podmeni za dresses deo vidljiv
//    Predjite misem preko t-shirts linka. 
//    Verifikujte podmeniji za womens i dresses nevidljivi
//    Ukoliko je potrebno ukljucite odgovarajuca cekanja, kojim bi se sacekalo da stranica dodje u odgovarajuce stanje
//    Provera preko za vidljivost preko soft assert-a

    @Test
    public void topMenuMouseOver () {
    actions.moveToElement(topMenuPage.getWomenLink()).perform();
    softAssert.assertTrue(topMenuPage.getWomenSubMenuLink().isDisplayed(), "Error - Women submenu should be displayed");
    
    actions.moveToElement(topMenuPage.getDressesLink()).perform();
    softAssert.assertTrue(topMenuPage.getDressesSubMenuLink().isDisplayed(), "Error - Dresses submenu should be displayed");
    
    actions.moveToElement(topMenuPage.getTshirtsLink()).perform();
    softAssert.assertFalse(topMenuPage.getDressesSubMenuLink().isDisplayed() 
    		&& topMenuPage.getWomenSubMenuLink().isDisplayed(),
    		"Error - Both submenus should NOT be displayed");
    }
    
//    Test #3:  Phone number visibility check on resize
//    Maksimizujte prozor
//    Proverite da je element za broj telefona vidljiv
//    Smanjite dimenziju pretrazivaca na velicinu 767 x 700
//    Proverite element za broj telefona nije vidljiv
//    Promenite dimenziju pretrazivaca na 768 x 700
//    Proverite da je broj telefona vidljiv
//    Maksimizujte prozor
//    Provera preko soft asserta
    
    @Test (priority=300)
    public void phoneNumberVisibilityCheckOnResize () {
    driver.manage().window().maximize();
    softAssert.assertTrue(headerPage.getShopPhoneNumber().isDisplayed(), "Error - Phone number should be displayed");
    
    driver.manage().window().setSize(new Dimension (680, 636));
    softAssert.assertFalse(headerPage.getShopPhoneNumber().isDisplayed(), "Error - Phone number should be displayed");
    
    driver.manage().window().setSize(new Dimension(768, 700));
	softAssert.assertTrue(headerPage.getShopPhoneNumber().isDisplayed(), "Error - Phone number should be displayed");
	driver.manage().window().maximize();
    }

//    Test #4:  Header links check
//    Kliknite na contact us link
//    Verifikujte da je naslov stranice Contact us - My Store
//    Kliknite na sign in link
//    Verifikujte da je naslov stranice Login - My Store
//    Provera preko soft asserta
    
    @Test (priority=400)
    public void headerLinksCheck () {
    	headerPage.getContactUsLink().click();
		String expectedContactTitle = "Contact us - My Store";
		String actualContactTitle = driver.getTitle();
		softAssert.assertEquals(actualContactTitle, expectedContactTitle, "Error - Title should be 'Contact us - My Store'");
		
		headerPage.getSignInLink().click();
		String expectedLoginTitle = "Login - My Store";
		String actualLoginTitle = driver.getTitle();
		softAssert.assertEquals(actualLoginTitle, expectedLoginTitle, "Error - Title should be 'Login - My Store'");
    }
    
        @AfterMethod
    	public void afterMethod() {
    	}
        
        @AfterClass
    	public void afterClass() {
    		driver.quit();
    	}
}
