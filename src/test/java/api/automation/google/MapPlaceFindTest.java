package api.automation.google;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class MapPlaceFindTest {

	String placeId;
	
	@Test
	public void findPlace()
	{
		RestAssured.baseURI = "https://maps.googleapis.com";
		
		RequestSpecification httpsRequest = RestAssured.given();
		
		httpsRequest.param("key", "AIzaSyAvx111_h9vpETphRdajI33qDNCprtcT70");
		httpsRequest.param("input", "Museum of Contemporary Art Australia");
		httpsRequest.param("inputtype", "textquery");
		httpsRequest.param("fields", "place_id,opening_hours,icon,geometry");		
		
		
		//response is extracted in raw format
		Response response = httpsRequest.request(Method.GET, "/maps/api/place/findplacefromtext/json");
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Incorrect status code returned");
		
		
		//String serverType =  response.header("Server");
		
		response.then().assertThat().header("Server", "scaffolding on HTTPServer2");
		
		//Assert.assertEquals(response.body().jsonPath().getString(""), expected);
		
		JsonPath jpath =  response.getBody().jsonPath();
		//System.out.println(jpath.toString());
		String placeId = jpath.getString("candidates[0].place_id");
		//System.out.println(placeId);
		Assert.assertEquals(placeId, "ChIJ68aBlEKuEmsRHUA9oME5Zh0");
		
	}
	
	
	// hitting a similar API to google add place in maps
	@Test
	public void addPlace()
	{
		RestAssured.baseURI = "http://216.10.245.166";
		
		RequestSpecification httpRequest = RestAssured.given();
		
		httpRequest.queryParam("key", "qaclick123");
		
		//sample body for post
		/*
		 * {
			   "location":{
			        "lat" : -38.383494,
			        "lng" : 33.427362
			    },
			    "accuracy":50,
			    "name":"Frontline house",
			    "phone_number":"(+91) 983 893 3937",
			    "address" : "129, hsr layout, cohen 09",
			    "types": ["shoe park","shop"],
			    "website" : "http://google.com",
			    "language" : "French-IN"
			
			}
		 */
		
		//create json through data
		JSONObject outerJObj = new JSONObject();
		
		JSONObject innerJObj = new JSONObject();
		
		innerJObj.put("lat", "-38.383494");
		innerJObj.put("lng", "33.427362");
		
		outerJObj.put("location", innerJObj);
		outerJObj.put("accuracy", 50);
		outerJObj.put("name", "Frontline house");
		outerJObj.put("phone_number", "(+91) 983 893 3937");
		outerJObj.put("address", "129, hsr layout, cohen 09");
		
		JSONArray jArr = new JSONArray();
		jArr.put("shoe park");
		jArr.put("shoe shop");
				
		outerJObj.put("types", jArr);
		outerJObj.put("website", "http://google.com");
		outerJObj.put("language", "French-IN");
		
		
		// send as json string in body for post request
		System.out.println(outerJObj.toString());
		
		httpRequest.body(outerJObj.toString());
		
				
				
	}
	
}
