package Day2;


import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class NegativeContactList {
  @Test(enabled=false)
  public void recordNotFound() {
	  given()
	  .when()
	  .get("http://3.13.86.142:3000/contacts/5")
	  .then()
	  .log()
	  .body()
	  .statusCode(404);
  }
  
  @Test(enabled=false, description="adding contact with missing details")
  public void addingContactMissing() {
	  JSONObject details = new JSONObject();
	  JSONObject location = new JSONObject();
	  JSONObject emp = new JSONObject();
	  
	  location.put("city", "Mumbai");
	  location.put("country", "India");
	  
	  emp.put("jobTitle", "QA");
	  emp.put("company", "LTI");
	  
	  details.put("firstName", null);
	  details.put("lastName", "Smith");
	  details.put("email", "john@email.com");
	  details.put("location", location);
	  details.put("employer", emp);
	  
	  
	  String error = given()
	  .header("Content-Type", "application/json")
	  .body(details.toJSONString())
	  .when()
	  .post("http://3.13.86.142:3000/contacts")
	  .then()
	  .log()
	  .body()
	  .statusCode(400)
	  .extract()
	  .path("err");
	  
	  Assert.assertTrue(error.contains("firstName: First Name is required"));
  }
  
  @Test(enabled=true, description="adding contact with too many character")
  public void addingContactBigSize() {
	  JSONObject details = new JSONObject();
	  JSONObject location = new JSONObject();
	  JSONObject emp = new JSONObject();
	  
	  location.put("city", "MumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbaiMumbai");
	  location.put("country", "India");
	  
	  emp.put("jobTitle", "QA");
	  emp.put("company", "LTI");
	  
	  details.put("firstName", "joe");
	  details.put("lastName", "Smith");
	  details.put("email", "john@email.com");
	  details.put("location", location);
	  details.put("employer", emp);
	  
	  
	  String error = given()
	  .header("Content-Type", "application/json")
	  .body(details.toJSONString())
	  .when()
	  .post("http://3.13.86.142:3000/contacts")
	  .then()
	  .log()
	  .body()
	  .statusCode(400)
	  .extract()
	  .path("err");

	  Assert.assertTrue(error.contains("is longer than the maximum allowed length (30)"));
  }
}