package qtriptest.APITests;
import io.restassured.http.ContentType;
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
import java.sql.Timestamp;
import java.util.UUID;



public class testCase_API_01 {

    private static final String BASE_URL = "https://content-qtripdynamic-qa-backend.azurewebsites.net";
    
    @Test()
    public void registration() {

        
        String userEmail = UUID.randomUUID() + "naga@gmail.com";
        String userPassword = "1234564";
        
       
        // Request Body
        String registrationRequestBody = "{ \"email\":\"" + userEmail + "\", \"password\":\"" + userPassword + "\", \"confirmpassword\":\"" + userPassword + "\" }";

        // API Endpoint
        String registerEndpoint = "/api/v1/register";

        // Setting up the request
        RequestSpecification http = RestAssured.given();
        http.baseUri(BASE_URL);
        http.basePath(registerEndpoint);
        http.contentType(ContentType.JSON);
        http.body(registrationRequestBody);

        // Making the POST request
        Response response = http.post();
        //System.out.println(response.asPrettyString());

        // Assertion
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 201, "User Registered Succefully");

        String loginRequestBody = "{ \"email\":\"" + userEmail + "\", \"password\":\"" + userPassword + "\" }";

        // Login API Endpoint
        String loginEndpoint = "/api/v1/login";

        // Setting up the login request
        RequestSpecification loginRequest = RestAssured.given();
        loginRequest.baseUri(BASE_URL);
        loginRequest.basePath(loginEndpoint);
        loginRequest.contentType(ContentType.JSON);
        loginRequest.body(loginRequestBody);

        // Making the login POST request
        Response loginResponse = loginRequest.post();
        //System.out.println("Login Response: " + loginResponse.asPrettyString());
        //System.out.println("Status Code : " + loginResponse.getStatusCode());

        // Assertion for login
        softAssert.assertEquals(loginResponse.getStatusCode(), 201, "User Login Successful");

        //extract the token from the login response 
        String authToken = loginResponse.jsonPath().getString("data.token");
        //System.out.println("Auth Token: " + authToken);

        JsonPath jp = new JsonPath(loginResponse.body().asString());

        softAssert.assertTrue(jp.getBoolean("success"));
        softAssert.assertAll();

    }


}
