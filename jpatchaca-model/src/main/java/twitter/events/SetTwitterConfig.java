package twitter.events;

import java.io.Serializable;

import org.apache.commons.lang.Validate;

public class SetTwitterConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	private final boolean twitterLoggingEnabled;
	private final String userName;
	private final String password;

	@Deprecated
	public SetTwitterConfig(boolean enabled, String userName, String password) {
		
		Validate.notNull(userName);
		Validate.notNull(password);
		
		this.twitterLoggingEnabled = enabled;
		this.userName = userName;
		this.password = password;
	}

	public boolean isTwitterLoggingEnabled() {
		return twitterLoggingEnabled;
	}

	public String userName() {
		return userName;
	}

	public String password() {
		return password;
	}

}
