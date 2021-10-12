package Day2;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class PositiveContactList {
	String id;
	@Test(enabled = false, description = "Getting all contacts List")
	public void getallContacts() {
		given()
		.when()
		.get("http://3.13.86.142:3000/contacts")
		.then()
		.log()
		.body()
		.statusCode(200);

	}
	@Test(enabled = false, description = "Getting specific contact")
	public void getSpecificContact() {
		given()
		.when()
		.get("http://3.13.86.142:3000/contacts/60eba23e170734047659ad54")
		.then()
		.log()
		.body()
		.statusCode(200);

	}
	@Test(enabled = false, description = "Adding Contact")
	public void getSpecificContact2() {
	Response res=	given()
		.when()
		.get("http://3.13.86.142:3000/contacts/60eba23e170734047659ad54");
	
      	System.out.println(res.getTime());
     	res.then()
    	.log()
     	.body()
    	.statusCode(200);

	
	}
	@Test(enabled = true, description = "adding contact")
	public void addingContact() {
		
		JSONObject details=new JSONObject();
		JSONObject loc=new JSONObject();
		JSONObject emp=new JSONObject();
		loc.put("city", "mumbai");
		loc.put("country", "India");
		emp.put("jobTitle", "QA");
		emp.put("company", "LTI");
		details.put("firstName","abc");
		details.put("lastName","xyz");
		details.put("email","abc@gmail.com");
		details.put("location",loc);
		details.put("employer",emp);



	ExtractableResponse<Response> ex=	given() 
			.header("Content-Type","application/json")
			.body(details.toJSONString())
		    .when()
	    	.post("http://3.13.86.142:3000/contacts")
	        .then()
        	.log()
	        .body()
            .statusCode(200)
        	.extract();
				id=ex.path("_id");
				System.out.println(ex.path("_id"));
				System.out.println(ex.path("firstName"));
				System.out.println(ex.path("lastName"));
				System.out.println(ex.path("location.city"));	
	}
	@Test(enabled=true,dependsOnMethods="addingContact",description="updating Contact ")
	  public void updatingContact() {
	  JSONObject details=new JSONObject();
	  JSONObject loc=new JSONObject();
	  JSONObject emp=new JSONObject();
	  
	  loc.put("city", "Mumbai");
	  loc.put("country", "India");
	  emp.put("JobTitle", "QA");
	  emp.put("company", "LTI");
	  details.put("firstName", "zyx");
	  details.put("lastName","abc");
	  details.put("email", "abc@gmail.com");
	  details.put("location", loc);
	  details.put("employer", emp);
	  
	  	  given()
		 .header("Content-Type","application/json")
		 .body(details.toJSONString())
		 .when()
		 .put("http://3.13.86.142:3000/contacts/"+id)
		 .then()
		 .log()
		 .body()
		 .statusCode(204);
}
	@Test(enabled = true,dependsOnMethods = "updatingContact", description = "Getting specific contact")
	public void getSpecificContact3() {
		given()
		.when()
		.get("http://3.13.86.142:3000/contacts/"+id)
		.then()
		.log()
		.body()
		.statusCode(200);

	}
	@Test(enabled = true,dependsOnMethods = "getSpecificContact3", description = "deleting specific contact")
	public void deleteContact() {
		given()
		.when()
		.delete("http://3.13.86.142:3000/contacts/"+id)
		.then()
		.log()
		.body()
		.statusCode(204);

	}
	@Test(enabled = true,dependsOnMethods = "deleteContact", description = "Getting specific contact")
	public void getSpecificContact4() {
		given()
		.when()
		.get("http://3.13.86.142:3000/contacts/"+id)
		.then()
		.log()
		.body()
		.statusCode(404);
}
}