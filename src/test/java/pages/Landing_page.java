package pages;

import base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Landing_page extends PageBase {

	public Landing_page(WebDriver driver) {
		super(driver);
	}

	WebDriver driver;
	@FindBy(xpath = "//ul[@id='postlks']//a[text()='my account']")
	WebElement myAccount;

	public void navigateToMyAccount(){
		log.info("Navigate to my account");
		myAccount.click();
	}

}
