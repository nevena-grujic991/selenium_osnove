package d20_09_2022_pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderPage {
	
//	HeaderPage koja pribavlja:
//		shop phone element - gde je prikazan broj telefona
//		contact us link
//		sign in link

	private WebDriver driver;
	private WebDriverWait wait;

	public HeaderPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
}
	public WebElement getShopPhoneNumber() {
	return driver.findElement(By.className("shop-phone"));
	}
	
	public WebElement getContactUsLink() {
	return driver.findElement(By.id("contact-link")).findElement(By.tagName("a"));
	}

	public WebElement getSignInLink() {
	return driver.findElement(By.className("login"));
	}
}
