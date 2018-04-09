package pages;

import base.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class UserAccount extends PageBase {

	public UserAccount(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}

	WebDriver driver;

	@FindBy(xpath = "//div[@class='tabcontainer']//a[contains(text(),'searches')]")
	WebElement srearchesTab;

	@FindBy(css="table#savedsearchlist tbody tr")
	List<WebElement> searchRow;

	@FindBy(css="table#savedsearchlist tbody tr td:nth-of-type(3)")
	WebElement searchName;


	public UserAccount navigateToSearchesTab(){
		log.info("Select searched tab");
		srearchesTab.click();
		return this;
	}

	public int getSavedSearchCount(){
		return searchRow.size();
	}

	public void editSearch(String savedSearch){
		log.info("Edit saved search");
		driver.findElement(By.xpath("//table[@id='savedsearchlist']//tbody//tr//td[text()='"+savedSearch+"']" +
				"/following-sibling::td//button[text()='edit']")).click();
	}

	public String getSavedSearchName(){
		return searchName.getText();
	}

	public void deleteSavedSearch(String savedSearch){
		driver.findElement(By.xpath("//table[@id='savedsearchlist']//tbody//tr//td[text()='"+savedSearch+"']" +
				"/following-sibling::td//button[text()='delete']")).click();
	}
}
