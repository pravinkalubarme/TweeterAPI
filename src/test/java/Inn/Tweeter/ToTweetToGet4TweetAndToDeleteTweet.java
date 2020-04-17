package Inn.Tweeter;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.Test;

public class ToTweetToGet4TweetAndToDeleteTweet {

	@Test
	public void toGetTweet() throws IOException {
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\pravi\\eclipse-workspace\\Tweeter\\src\\main\\java\\Inn\\Tweeter\\file.properties");
		pro.load(fis);
		String consumerKey = pro.getProperty("consumerKey");
		String consumerSecretKey = pro.getProperty("consumerSecretKey");
		String accessToken = pro.getProperty("accessToken");
		String accessTokenSecret = pro.getProperty("accessTokenSecret");
		RestAssured.baseURI= pro.getProperty("RestAssured.baseURI");
			
		
	    //To Tweet
	    //https://api.twitter.com/1.1/statuses/update.json
        RequestSpecification authentication = given()
        		.auth().oauth(consumerKey, consumerSecretKey, accessToken, accessTokenSecret);
              
		String tweet = authentication.formParam("status", "My next Tweet from RESTAPI at 16.56").post(Resources.resourceToTweet())
				       .then().extract().asString();
		
		JsonPath jso=new JsonPath(tweet);
	    String TweetText = jso.get("text");
	    System.out.println("TweetText: "+TweetText);
	    String TweetID = jso.get("id_str");
	    System.out.println("TweetID: "+TweetID);
	
	    //To Get last 4 Tweets
	    // https://api.twitter.com/1.1/statuses/home_timeline.json
	    ValidatableResponse getTweet = authentication.queryParam("count", "4").when().get(Resources.resourceGetTweet()).then();
        
	    getTweet.assertThat().statusCode(200);
		
	    ExtractableResponse<Response> res = getTweet.extract();
	    String respo = res.asString();
		System.out.println("StatusCode: " + res.statusCode());
		for (int i = 0; i < 4; i++) 
		{
			JsonPath js = new JsonPath(respo);
			String id = js.get("[" + i + "].id_str");
			String text = js.get("[" + i + "].text"); 
			System.out.println("Tweet ID is: " + id+ "and Tweet is: "+text);
			
		}
	    
	    //To delete Tweet
	    //https://api.twitter.com/1.1/statuses/destroy/TweetID.json
	    ValidatableResponse destroyTweet = authentication.when().post(Resources.resourceToDeleteTweet(TweetID)).then();
	   
	    destroyTweet.assertThat().statusCode(200);
	    int statuscode = destroyTweet.extract().statusCode();
	    System.out.println("statuscode of delete Tweet: "+statuscode);
	    
	    String resDelTweet = destroyTweet.extract().asString();
	    System.out.println(resDelTweet);
	}
}
