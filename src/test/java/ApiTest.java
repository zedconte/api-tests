import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import pojo.User;

import static org.hamcrest.Matchers.*;
/**
 * Created by rortega.
 */
public class ApiTest {

    @Test
    public void listUsersCheckCount(){
        Response response = given().param("page","1").
                when().get("https://reqres.in/api/users").
                then().statusCode(200).extract().response();

        JSONArray j = (JSONArray)JsonPath.read(response.asString(),"$['data']");
        int totalItems = j.size();

        Assert.assertTrue(totalItems>0,"Total Items should be greater than 0");

        List<Map<String,Object>> allUsers = JsonPath.read(response.asString(),"$.['data']");
 
        List<String> lastNames = new ArrayList<String>();
        List<String> firstNames = new ArrayList<String>();
        for(Map<String,Object> user : allUsers ){
            firstNames.add(user.get("first_name").toString());
            lastNames.add(user.get("last_name").toString());

            Assert.assertTrue(firstNames.contains("George") || lastNames.contains("Bluth"), "LastNames = " + lastNames + " or FirstNames = " + Arrays.toString(firstNames.toArray()) + " should contain George Bluth");
        } 
    }

    @Test
    public void listUsers() {

        when().
                get("https://reqres.in/api/users").
        then().
                statusCode(200).
                assertThat().
                body("data[0].first_name", equalTo("George"),
                    "data.id", hasItems(1, 2));

    }

    @Test
    public void createUser() {
        given()
        .contentType(ContentType.JSON)
        .body(new User("morpheus", "leader"))
                .post("https://reqres.in/api/users")
        .then()
                .statusCode(201)
                .assertThat()
                .body("name", equalTo("morpheus"),
                    "job", equalTo("leader"));

    }

    @Test
    public void updateUser() {
        given()
        .contentType(ContentType.JSON)
        .body(new User("morpheus", "gatekeeper"))
                .put("https://reqres.in/api/users/2")
        .then()
                .statusCode(200)
                .assertThat()
                .body("name", equalTo("morpheus"),
                    "job", equalTo("gatekeeper"));

    }
   
}
