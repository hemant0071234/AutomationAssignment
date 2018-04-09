package pages;

import base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditSearch extends PageBase {

	public EditSearch(WebDriver driver) {
		super(driver);
	}

	WebDriver driver;

	@FindBy(xpath = "//input[@name='subName']")
	WebElement savedSearchName;

	@FindBy(xpath = "//button[text()='save']")
	WebElement save;

	public EditSearch EditSavedSearch(String updatedName){
		log.info("Update search name to: "+updatedName);
		PageBase.wait(5);
		savedSearchName.clear();
		savedSearchName.sendKeys(updatedName);
		save.click();
		return this;
	}
}
