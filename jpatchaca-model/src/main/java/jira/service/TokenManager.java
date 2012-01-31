package jira.service;

import java.util.Calendar;

import basic.HardwareClock;

public class TokenManager {

	public static final int TOKEN_TIMEOUT_MINUTES = 10;

	private String token;
	private Calendar tokenTimeout;
	private final HardwareClock hardwareClock;
	private TokenFactory tokenFactory;

	public TokenManager(HardwareClock hardwareClock) {
		this.hardwareClock = hardwareClock;
	}
	
	public void setTokenFactory(TokenFactory tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	String getToken() {
		return !tokenExpired() ? token: createTokenAndResetTimeout();
	}

	private boolean tokenExpired() {
		return tokenTimeout == null || tokenTimeout.before(now());
	}

	private String createTokenAndResetTimeout() {
		if(tokenFactory == null)
			throw new IllegalStateException("tokenFactory not initialized");
		
		token = tokenFactory.createToken();
		setTimeout();
		return token;
	}

	private void setTimeout() {
		tokenTimeout = now();
		tokenTimeout.add(Calendar.MINUTE, TOKEN_TIMEOUT_MINUTES);
	}

	private Calendar now() {
		Calendar now = Calendar.getInstance();
		now.setTime(hardwareClock.getTime());
		return now;
	}

    public void resetTokenTimeout() {
        tokenTimeout = null;
    }
	
}
