package Inn.Tweeter;

public class Resources {
	
	public static String resourceGetTweet()
	{
		
		String resource= "/home_timeline.json";
		return resource;
	}
	
	public static String resourceToTweet()
	{
		
		String resource= "/update.json";
		return resource;
	}
	
	public static String resourceToDeleteTweet(String TweetID)
	{
		
		String resource= "/destroy/"+TweetID+".json";
		return resource;
	}
}
