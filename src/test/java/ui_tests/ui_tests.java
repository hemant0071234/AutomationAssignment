package ui_tests;

import base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;


public class ui_tests extends TestBase{

	@Test(priority=1, description = "Log in to https://sfbay.craigslist.org/")
	public void login(){
		log.info("Open application URL.");
		driver.get(data.getProperty("base.url"));

		log.info("Navigate to login screen and login");
		new Landing_page(driver).navigateToMyAccount();
		new LoginPage(driver).validLogin(data.getProperty("user.name"), data.getProperty("user.password"));

		log.info("Open 'searches' tab");
		UserAccount userAccount = new UserAccount(driver);
		userAccount.navigateToSearchesTab();

		Assert.assertTrue(userAccount.getSavedSearchCount()>0,"There are not search saved");
		String oldSavedSearch = userAccount.getSavedSearchName();

		log.info("Click 'edit' on toyota search");
		userAccount.editSearch("toyota");

		log.info("Change name to 'toyota prius'");
		new EditSearch(driver).EditSavedSearch("toyota prius");

		Assert.assertTrue(!oldSavedSearch.equalsIgnoreCase(userAccount.getSavedSearchName()),"Search is not edited correctly");

		log.info("Delete edited search");
		userAccount.deleteSavedSearch("toyota prius");
		Assert.assertTrue(userAccount.getSavedSearchCount()==0,"Saved search is not deleted properly");
	}
}
