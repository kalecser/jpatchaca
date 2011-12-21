package jira.service;

import java.util.Calendar;

import basic.HardwareClock;

public class TokenManager {

	public static final int TOKEN_TIMEOUT_MINUTES = 10;

	private String _token;
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
		if (!tokenExpired())
			return _token;
		_login();
		return _token;
	}

	private boolean tokenExpired() {
		return tokenTimeout == null || tokenTimeout.before(now());
	}

	private void _login() {
		if(tokenFactory == null)
			throw new IllegalStateException("tokenFactory not initialized");
		
		_token = tokenFactory.createToken();
		setTokenTimeout();
	}

	private void setTokenTimeout() {
		tokenTimeout = now();
		tokenTimeout.add(Calendar.MINUTE, TOKEN_TIMEOUT_MINUTES);
	}

	private Calendar now() {
		Calendar tokenTimeout = Calendar.getInstance();
		tokenTimeout.setTime(hardwareClock.getTime());
		return tokenTimeout;
	}

	void resetTokenTimeout() {
		tokenTimeout = null;
	}
}
