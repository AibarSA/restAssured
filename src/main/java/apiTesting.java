import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class apiTesting {
    Response response;

    @BeforeTest
    public void initTest() {
        response = get("http://services.groupkt.com/country/get/all");
    }

    @Test
    public void checkStatusCode() {
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void checkResponseBody() {
        response.then().body("RestResponse.result.alpha2_code", hasItems("US", "DE", "GB"));
    }

    @Test
    public void checkResponseBody1() {
        response.then().body("RestResponse.result.alpha2_code", hasItem("US"));
    }

    @Test
    public void checkResponseBody2() {
        response.then().body("RestResponse.result.alpha2_code", hasItem("DE"));
    }

    @Test
    public void checkResponseBodyNot() {
        response.then().body("RestResponse.result.alpha2_code", not(hasItem("NOT")));
    }


    @Test
    public void addData() {
        RestAssured.baseURI = "http://services.groupkt.com/country";
        RequestSpecification request = RestAssured.given();
        JSONObject json = new JSONObject();
        json.put("name", "TestCountry");
        json.put("alpha2_code",  "TC");
        json.put("alpha3_code", "TCY");

        request.body(json.toJSONString());

        Response response = request.post("/register");
        Assert.assertEquals(201, response.getStatusCode());
    }
}
