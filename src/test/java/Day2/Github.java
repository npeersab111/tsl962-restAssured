package Day2;
import static io.restassured.RestAssured.*;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class Github {
  @Test
  public void gettingAllrepository() {
	  given()
	  .auth()
	  .oauth2("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")
	  .when()
	  .get("https://api.github.com/user/repos")
	  .then()
	  .log()
	  .body()
	  .statusCode(200);
  }
  @Test(enabled = true)
  public void createRepository() {
	  JSONObject data=new JSONObject();
	  data.put("name", "RestAssurredCreations");
	  data.put("Descreption", "I am created using Rest Assurred");
	  data.put("homepage", "https://api.github.com/npeersab111");
	  given()
	  .auth()
	  .oauth2("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")
	  .header("Content-Type", "application/json")
	  .body(data.toJSONString())
	  .when()
	  .post("https://api.github.com/user/repos")
	  .then()
	  .log()
	  .body()
	  .statusCode(201);
  }
  
  
}
