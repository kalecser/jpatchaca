package twitter;

import org.reactive.Signal;
import org.reactive.Source;

import events.persistence.MustBeCalledInsideATransaction;

public class TwitterOptions {

	private final Source<Boolean> twitterLoggingEnabled = new Source<Boolean>(
			Boolean.FALSE);
	private final Source<String> userName = new Source<String>("");
	private final Source<String> password = new Source<String>("");

	public Signal<String> username() {
		return userName;
	}

	public Signal<String> password() {
		return password;
	}

	public Signal<Boolean> isTwitterLoggingEnabled() {
		return twitterLoggingEnabled;
	}

	public synchronized void configure(final boolean newtwitterLoggingEnabled,
			final String newuserName, final String newpassword)
			throws MustBeCalledInsideATransaction {
		this.twitterLoggingEnabled.supply(Boolean.valueOf(newtwitterLoggingEnabled));
		this.userName.supply(newuserName);
		this.password.supply(newpassword);

	}

}
