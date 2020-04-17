package Inn.Tweeter;
import static io.restassured.RestAssured.given;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class Tweet {
	@Test
	public void toPostTweet() throws IOException
	{
		Properties pro = new Properties();
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\pravi\\eclipse-workspace\\Tweeter\\src\\main\\java\\Inn\\Tweeter\\file.properties");
		pro.load(fis);
		String consumerKey = pro.getProperty("consumerKey");
		String consumerSecretKey = pro.getProperty("consumerSecretKey");
		String accessToken = pro.getProperty("accessToken");
		String accessTokenSecret = pro.getProperty("accessTokenSecret");
		RestAssured.baseURI= pro.getProperty("RestAssured.baseURI");
			
        RequestSpecification authentication = given()
        		.auth().oauth(consumerKey, consumerSecretKey, accessToken, accessTokenSecret);
              
		String tweet = authentication.formParam("status", "My next Tweet from RESTAPI at 16.39").post(Resources.resourceToTweet())
				       .then().extract().asString();
		JsonPath jso=new JsonPath(tweet);
	    String TweetText = jso.get("text");
	    System.out.println("TweetText: "+TweetText);
	    String TweetID = jso.get("id_str");
	    System.out.println("TweetID: "+TweetID);
	}

}
