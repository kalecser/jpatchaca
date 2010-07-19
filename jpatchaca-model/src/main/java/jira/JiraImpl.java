package jira;

import java.rmi.RemoteException;
import java.util.Calendar;

import javax.xml.rpc.ServiceException;

import lang.Maybe;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;
import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;
import com.dolby.jira.net.soap.jira.RemoteIssue;
import com.dolby.jira.net.soap.jira.RemotePermissionException;
import com.dolby.jira.net.soap.jira.RemoteValidationException;
import com.dolby.jira.net.soap.jira.RemoteWorklog;

public class JiraImpl implements Jira {

	private final JiraOptions jiraOptions;

	public JiraImpl(final JiraOptions jiraOptions) {
		this.jiraOptions = jiraOptions;
	}

	@Override
	public JiraIssue getIssueByKey(final String key) throws JiraException {
		try {
			return internalGetIssueByKey(key);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException("Authentication failure", e);
		} catch (final com.dolby.jira.net.soap.jira.RemoteException e) {
			throw new JiraException("Communication failure", e);
		} catch (final RemoteException e) {
			throw new JiraException("Communication failure", e);
		} catch (final ServiceException e) {
			throw new JiraException("Communication failure", e);
		}
	}

	private JiraIssue internalGetIssueByKey(final String key)
			throws ServiceException, RemoteException,
			RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, JiraException {

		Validate.notEmpty(key);
		Validate.notNull(key);

		Logger.getLogger("org.apache.axis").setLevel(Level.OFF);

		final JiraSoapService service = getService();
		final String token = getToken(service);

		RemoteIssue[] issues = null;
		try {
			issues = service.getIssuesFromJqlSearch(token, "key = " + key, 20);
		} catch (final RemoteValidationException e) {
			throw new JiraIssueNotFoundException(key);
		}

		if (issues.length == 1) {
			final JiraIssueData data = new JiraIssueData();
			data.setId(issues[0].getId());
			data.setKey(issues[0].getKey());
			data.setSummary(issues[0].getSummary());

			return new JiraIssue(data);
		}

		if (issues.length > 1) {
			throw new IllegalArgumentException("Too many results for key '"
					+ key + "'");
		}

		throw new JiraIssueNotFoundException(key);
	}

	private JiraSoapService getService() throws JiraOptionsNotSetException,
			ServiceException {
		final Maybe<String> url = jiraOptions.getURL();

		if (url == null) {
			throw new JiraOptionsNotSetException();
		}

		final JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
		locator.setJirasoapserviceV2EndpointAddress(jiraOptions.getURL()
				.unbox()
				+ "/rpc/soap/jirasoapservice-v2");
		locator.setMaintainSession(true);

		final JiraSoapService service = locator.getJirasoapserviceV2();
		return service;
	}

	private String getToken(final JiraSoapService service)
			throws RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException,
			JiraOptionsNotSetException {
		final Maybe<String> username = jiraOptions.getUserName();
		final Maybe<String> password = jiraOptions.getPassword();

		if (username == null || password == null) {
			throw new JiraOptionsNotSetException();
		}

		return service.login(username.unbox(), password.unbox());
	}

	@Override
	public JiraIssue getIssueById(final String id) throws JiraException {
		try {
			return internalGetIssueById(id);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException("Authentication failure", e);
		} catch (final com.dolby.jira.net.soap.jira.RemoteException e) {
			throw new JiraException("Communication failure", e);
		} catch (final RemoteException e) {
			throw new JiraException("Communication failure", e);
		} catch (final ServiceException e) {
			throw new JiraException("Communication failure", e);
		}
	}

	private JiraIssue internalGetIssueById(final String id)
			throws JiraException, ServiceException,
			RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException {

		final JiraSoapService service = getService();
		final String token = getToken(service);

		try {
			final RemoteIssue issue = service.getIssueById(token, id);
			final JiraIssueData data = new JiraIssueData();
			data.setId(issue.getId());
			data.setKey(issue.getKey());
			data.setSummary(issue.getSummary());
			return new JiraIssue(data);

		} catch (final RemoteValidationException e) {
			throw new JiraIssueNotFoundException(id);
		}
	}

	@Override
	public void newWorklog(final String issueId, final Calendar startDate,
			final String timeSpent) throws JiraException {
		try {
			internalNewWorklog(issueId, startDate, timeSpent);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e);
		} catch (final com.dolby.jira.net.soap.jira.RemoteException e) {
			throw new JiraException(e);
		} catch (final RemoteException e) {
			throw new JiraException(e);
		} catch (final ServiceException e) {
			throw new JiraException(e);
		}
	}

	public void internalNewWorklog(final String issueId,
			final Calendar startDate, final String timeSpent)
			throws JiraOptionsNotSetException, ServiceException,
			RemotePermissionException, RemoteValidationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException {
		final RemoteWorklog workLog = new RemoteWorklog();
		workLog.setStartDate(startDate);
		workLog.setTimeSpent(timeSpent);

		final JiraSoapService service = getService();
		final String token = getToken(service);

		service.addWorklogAndAutoAdjustRemainingEstimate(token, issueId,
				workLog);

	}
}