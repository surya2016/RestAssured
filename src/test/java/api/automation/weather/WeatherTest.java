package api.automation.weather;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import api.automation.utility.TestNGListener;

@Listeners({TestNGListener.class})
public class WeatherTest {

	@Test
	public void getWeatherDetails()
	{
		// Specify the base URL to the RESTful web service i.e root address of the resource
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		

		// Get the RequestSpecification of the request that you want to send to the server. 
		//The server is specified by the BaseURI that we have specified in the above step.
		RequestSpecification httpRequest = RestAssured.given();

		// Make a request to the server by specifying the method Type and the method URL.
		// This will return the Response from the server. Store the response in a variable.
		Response response = httpRequest.request(Method.GET, "/Hyderabad");
		
		// Get the status code from the Response. In case of a successful interaction with the web service, we should get a status code of 200.
		int statusCode = response.getStatusCode();
		 
		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Incorrect status code returned");
		 
		// Get the status line from the Response and store it in a variable called statusLine
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/, "Incorrect status line returned");
		
		//list of type Headers
		Headers headers = response.getHeaders();
					
		System.out.println("headers-->");
		for(Header header : headers)
		{
			System.out.println(header.getName()+":"+header.getValue());
		}

		System.out.println("Fetching commonly used particular header info - Content-Type: "+response.getHeader("Content-Type"));
		String serverType =  response.header("Server");
		System.out.println("Server value: " + serverType);
		String acceptLanguage = response.header("Content-Encoding");
		System.out.println("Content-Encoding: " + acceptLanguage);

		// Now let us print the body of the message to see what response we have received from the server
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		
		 // To check for sub string presence get the Response body as a String. Do a String.contains
		 // convert the body into lower case and then do a comparison to ignore casing.
		 Assert.assertEquals(responseBody.toLowerCase().contains("hyderabad") /*Expected value*/, true /*Actual Value*/, "Response body contains Hyderabad");
		/*
		 * Problems faced by above approach:
		 * what if the string “Hyderabad” is present in a wrong node?
		 * what if  multiple instances of the same string are present?
		 * Solution:
		 * Response.JsonPath(), which returns a io.restassured.path.json.JsonPath Object and can be used to further query 
		 * specific parts of the Response JSON. 
		 */
		 
		// First get the JsonPath object instance from the Response interface
		 JsonPath jsonPathEvaluator = response.jsonPath();
		 
		 // Then simply query the JsonPath object to get a String value of the node
		 // specified by JsonPath: City (Note: You should not put $. in the Java code)
		 String city = jsonPathEvaluator.get("City");
		 
		 // Let us print the city variable to see what we got
		 System.out.println("City received from Response " + city);
		 
		 // Validate the response
		 Assert.assertEquals(city, "Hyderabad", "Correct city name received in the Response");
		 
		 String temp = jsonPathEvaluator.get("Temperature");
		 
		 Pattern p = Pattern.compile("[\\d]*\\.[\\d]*");
		 
		 Matcher m = p.matcher(temp);
		 //matcher matches entire string but looking at matches only at starting of string
		 //System.out.println(temp+"-->"+m.lookingAt());
		 
		 if(m.lookingAt())
		 {
			 float tempVal = Float.parseFloat((m.group(0)));
			 Assert.assertTrue(tempVal > 40.0f, "Tempratue very high");
		 }
		 else
			 throw new SkipException("Tempratue Value not found in response");
		 
		 
		
		
	}
}
