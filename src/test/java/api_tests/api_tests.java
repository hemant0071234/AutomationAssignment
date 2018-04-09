package api_tests;

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasXPath;

public class api_tests extends TestBase {

    @BeforeTest
    public void setupAPI(){
        RestAssured.baseURI="https://sfbay.craigslist.org/";
    }

    @Test (priority=1, description = "Login to the application")
    public void loginTest(){
        RestAssured.baseURI = data.getProperty("api.login.base.url");
        Response response = given().relaxedHTTPSValidation()
                .auth().form(data.getProperty("user.name"), data.getProperty("user.password"))
                .when()
                .log().uri()
                .get("login");

        Assert.assertTrue(response.getStatusCode()==200,"login failed");
    }

    @Test(priority=2, description = "Perform search for 'toyota'")
    public void performSearch(){
        Response response = given().relaxedHTTPSValidation()
                .queryParam("query", "toyota")
                .queryParam("sort", "rel")
                .when()
                .log().uri()
                .get("search/sss");

        Assert.assertTrue(response.getStatusCode()==200,"search failed");
    }

    // Below test is not working
    @Test(priority=3, description = "Print all ad titles from page 2 to console")
    public void searchResultFromSecondPage() throws IOException {
        Response response = given().relaxedHTTPSValidation()
                .queryParam("s", "120")
                .queryParam("query", "toyota")
                .queryParam("sort", "rel")
                .when()
                .get("search/sss");


        ResponseSpecification body = expect().
                body(hasXPath("//div[@id='sortable-results']//li//p//a"));


        HttpClient clientGet = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(response.getBody().toString());
        HttpResponse res = clientGet.execute(get);

        org.jsoup.nodes.Document doc = Jsoup.parse(IOUtils.toString(res.getEntity().getContent(), StandardCharsets.UTF_8));
        Elements links = (Elements) doc.select("//div[@id='sortable-results']//li//p//a");

        System.out.println("RES = "+ links);
    }

    @Test (priority=4, description = "Save search by clicking 'save search' button")//, dependsOnMethods = { "loginTest" })
    public void saveSearch(){
        RestAssured.baseURI = data.getProperty("api.login.base.url");
        Response response = given().relaxedHTTPSValidation()
                .auth().form(data.getProperty("user.name"), data.getProperty("user.password"))
                .cookie("cl_b","NF-kJLg46BGZJUJSXxksBwvxaic")
                .cookie("cl_def_hp","sfbay")
                .cookie("cl_session","uQS5vxcYCtnopoFTHx7CG1vMdz1ICbygWqqSsr8JEHZBf3ufdRbNP4R8Gi7Hipt3")
                .cookie("cl_tocmode","sss%3Agrid")
                .queryParam("URL","https://sfbay.craigslist.org/search/sss?query=toyota&sort=rel")
                .queryParam("_csrf","TBIxM4OQKfYAwrrDaP9L7auO_5F8qOB64PfvSG1rZRA")
                .when()
                .log().uri()
                .get("savesearch/save");

        Assert.assertTrue(response.getStatusCode()==200,"Search save failed");
    }

    @Test (priority=5, description = "Verify search was saved")
    public void verifySavedSearch(){
        RestAssured.baseURI = data.getProperty("api.login.base.url");
        Response response = given().relaxedHTTPSValidation()
                .auth().form(data.getProperty("user.name"), data.getProperty("user.password"))
                .cookie("cl_def_hp","sfbay")
                .cookie("cl_b","NF-kJLg46BGZJUJSXxksBwvxaic")
                .cookie("cl_session","xK8b2l72HsqjrfsYlA57xvk9FyWyvwRAFNwgkjqQqOd7YfbsqB1PUiNJ9ndAreqR")
                .cookie("cl_tocmode","sss%3Agrid")
                .when()
                .log().all()
                .get("savesearch/counts");

        Assert.assertTrue(response.getStatusCode()==200,"Searched is not saved");
    }
}
