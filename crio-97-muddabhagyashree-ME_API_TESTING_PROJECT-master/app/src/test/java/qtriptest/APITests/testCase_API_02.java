package qtriptest.APITests;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import io.restassured.http.Method;
import io.restassured.response.Response; 
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;
public class testCase_API_02 {

    private static final String BASE_URL = "https://content-qtripdynamic-qa-backend.azurewebsites.net";

    @Test
    public void testCityListAPI() {
        // Making the GET request
        Response searchResponse = RestAssured.given().param("id", "").get(BASE_URL + "/api/v1/cities?q=kol");

        System.out.println(searchResponse.asPrettyString());

        // Assertion for status code
        System.out.println("Status code : " + searchResponse.getStatusCode());
        Assert.assertEquals(searchResponse.getStatusCode(), 200, "Executed city search");

        // Assertion for array length
        int resultCount = searchResponse.jsonPath().getList("results").size();
        Assert.assertEquals(resultCount, 1, "Expected number of results");
        

        //Assertion for description
        String description = searchResponse.jsonPath().getString("[0].description");
        System.out.println("Description : " + description);
        

        Assert.assertEquals(description, "100+ Places", "Expected description in the response");

   }

}