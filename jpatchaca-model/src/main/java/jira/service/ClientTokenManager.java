package jira.service;

import java.rmi.RemoteException;
import java.util.Calendar;

import javax.xml.rpc.ServiceException;

import jira.JiraOptions;
import jira.exception.JiraAuthenticationException;
import basic.HardwareClock;

import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;

public class ClientTokenManager {

	public static final int TOKEN_TIMEOUT_MINUTES = 10;

	private String _token;
	private Calendar tokenTimeout;
	private final JiraOptions jiraOptions;
	private final HardwareClock hardwareClock;

	private final JiraServiceFactory serviceFactory;

	public ClientTokenManager(JiraOptions jiraOptions,
			HardwareClock hardwareClock, JiraServiceFactory serviceFactory) {
		this.jiraOptions = jiraOptions;
		this.hardwareClock = hardwareClock;
		this.serviceFactory = serviceFactory;
	}

	String getToken() {
		jiraOptions.validate();

		if (_token != null && !tokenExpired())
			return _token;

		_login(jiraOptions.getUserName().unbox(), jiraOptions.getPassword()
				.unbox());

		return _token;
	}

	private boolean tokenExpired() {
		return tokenTimeout == null || tokenTimeout.before(now());
	}

	private void _login(final String username, final String password) {
		try {
			_token = serviceFactory.createJiraSoapService().login(username, password);
			setTokenTimeout();
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (java.rmi.RemoteException e) {
			throw _handleException(e);
		} catch (ServiceException e) {
			throw _handleException(e);
		}
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

	private RuntimeException _handleException(RemoteException e) {
		resetTokenTimeout();
		return new RuntimeException(e.getMessage());
	}

	private RuntimeException _handleException(RemoteAuthenticationException e) {
		resetTokenTimeout();
		return new JiraAuthenticationException(e);
	}

	private RuntimeException _handleException(ServiceException e) {
		return new RuntimeException(e);
	}

	void resetTokenTimeout() {
		tokenTimeout = null;
	}
}
