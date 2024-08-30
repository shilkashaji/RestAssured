package Restapi;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import ApiPayload.PayLoad;
import static org.hamcrest.Matchers.*;

public class basic {

	public static void main(String[] args) {
		 // TODO Auto-generated method stub
		 //given- all input details  
		 //when- submit the API
		 //then- validate response  
		 // Add place  
		
		 RestAssured.baseURI="https://rahulshettyacademy.com"; 
		 String response = given().log().all().queryParam("Key", "qaclick123").header("Content-Type", "application/json")
		 .body(PayLoad.AddPlace()).when().post("maps/api/place/add/json")
		 .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		 .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();  
		 System.out.println(response);
		 
		 JsonPath js = new JsonPath(response); // for passing json
		 String PlaceId =js.getString("place_id");
		 System.out.println(PlaceId);
		 
		// update place
		 
		 String newAddress = "1070 Summer, UK";
		 
		 given().log().all().queryParam("Key", "qaclick123").header("Content-Type", "application/json")
		 .body("{\r\n"
		 		+ "\"place_id\":\""+PlaceId+"\",\r\n"
		 		+ "\"address\":\""+newAddress+"\",\r\n"
		 		+ "\"key\":\"qaclick123\"\r\n"
		 		+ "}")
		 .when().put("maps/api/place/update/json")
		 .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		 
		 //Get place
		 
		 String getPlaceResponse = given().log().all().queryParam("Key", "qaclick123").queryParam("place_id", PlaceId)
		 .when().get("maps/api/place/get/json")
		 .then().assertThat().log().all().statusCode(200).extract().response().asString();
		 
		 JsonPath jss = new JsonPath(getPlaceResponse); // for passing json
		 String actualAddress = jss.getString("address");
		 System.out.println(actualAddress);
		 
	}
}