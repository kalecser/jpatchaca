package twitter;


public interface TwitterOperator {

	void enableTwitterLogging(String usern, String pass);

	String twitterUserName();
	String twitterPassword();
	Boolean twitterEnabled();

}
