package pages;

import base.PageBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageBase {

    public LoginPage(WebDriver driver) {
        super(driver);

    }

    WebDriver driver;

    @FindBy(id = "inputEmailHandle")
    public WebElement email;

    @FindBy(id = "inputPassword")
    public WebElement password;

    @FindBy(xpath = "//button[text()='Log in']")
    public WebElement logIn;

    public void validLogin(String userName, String passWord){
        log.info("Enter username to email: "+userName);
        email.sendKeys(userName);
        log.info("Enter password to password: "+passWord);
        password.sendKeys(passWord);
        log.info("Click on submit");
        logIn.submit();
    }

}
