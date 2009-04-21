package twitter;

import org.reactive.Signal;
import org.reactive.Source;

public class TwitterOptions {

	private Source<Boolean> twitterLoggingEnabled = new Source<Boolean>(false);
	private Source<String> userName = new Source<String>("");
	private Source<String> password = new Source<String>("");

	public Signal<String> username() {
		return userName;
	}

	public Signal<String> password() {
		return password;
	}

	public Signal<Boolean> isTwitterLoggingEnabled() {
		return twitterLoggingEnabled;
	}

	public synchronized void configure(boolean twitterLoggingEnabled, String userName,
			String password) {
				this.twitterLoggingEnabled.supply(twitterLoggingEnabled);
				this.userName.supply(userName);
				this.password.supply(password);
		
	}

}
