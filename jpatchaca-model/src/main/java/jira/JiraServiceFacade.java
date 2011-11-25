package jira;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;

import javax.xml.rpc.ServiceException;

import jira.exception.JiraAuthenticationException;
import jira.exception.JiraIssueNotFoundException;
import jira.exception.JiraOptionsNotSetException;
import jira.exception.JiraPermissionException;
import jira.exception.JiraValidationException;
import lang.Maybe;

import org.apache.commons.lang.StringUtils;

import basic.HardwareClock;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;
import com.dolby.jira.net.soap.jira.RemoteComment;
import com.dolby.jira.net.soap.jira.RemoteField;
import com.dolby.jira.net.soap.jira.RemoteFieldValue;
import com.dolby.jira.net.soap.jira.RemoteIssue;
import com.dolby.jira.net.soap.jira.RemoteNamedObject;
import com.dolby.jira.net.soap.jira.RemotePermissionException;
import com.dolby.jira.net.soap.jira.RemoteStatus;
import com.dolby.jira.net.soap.jira.RemoteValidationException;
import com.dolby.jira.net.soap.jira.RemoteWorklog;

public class JiraServiceFacade {

	public static final int TOKEN_TIMEOUT_MINUTES = 10;

	private final JiraOptions jiraOptions;
	private final JiraServiceFactory serviceFactory;
	private final HardwareClock hardwareClock;
	private String _token;
	private Calendar tokenTimeout;

	public JiraServiceFacade(JiraOptions jiraOptions,
			JiraServiceFactory serviceFactory, HardwareClock hardwareClock) {
		this.jiraOptions = jiraOptions;
		this.serviceFactory = serviceFactory;
		this.hardwareClock = hardwareClock;
	}

	private JiraSoapService getService() {
		try {
			Maybe<String> maybeURL = jiraOptions.getURL();
			String url = String.format("%s/rpc/soap/jirasoapservice-v2", maybeURL.unbox());			
			return serviceFactory.createService(url);
		} catch (ServiceException e) {
			throw _handleException(e);
		}
	}

	private String getToken() {
		jiraOptions.validate();

		if (_token != null && !tokenExpired())
			return _token;

		_login(jiraOptions.getUserName().unbox(), jiraOptions.getPassword()
				.unbox());

		return _token;
	}

	private void _login(final String username, final String password) {
		try {
			_token = getService().login(username, password);
			setTokenTimeout();
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (java.rmi.RemoteException e) {
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

	private boolean tokenExpired() {
		return tokenTimeout == null || tokenTimeout.before(now());
	}

	public RemoteIssue getIssueByKey(String key) {
		try {
			RemoteIssue[] remoteIssues = getService().getIssuesFromJqlSearch(
					getToken(), "key = " + key, 20);
			return remoteIssues[0];
		} catch (RemoteValidationException e) {
			throw new JiraIssueNotFoundException(key);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public RemoteIssue getIssueById(String id) {
		try {
			return getService().getIssueById(getToken(), id);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public void addWorklogAndAutoAdjustRemainingEstimate(String issueId,
			RemoteWorklog workLog) {
		try {
			getService().addWorklogAndAutoAdjustRemainingEstimate(getToken(),
					issueId, workLog);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public RemoteNamedObject[] getAvailableActions(String key) {
		try {
			return getService().getAvailableActions(getToken(), key);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public RemoteField[] getFieldsForAction(String key, String id) {
		try {
			return getService().getFieldsForAction(getToken(), key, id);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public void progressWorkflowAction(String key, String id,
			RemoteFieldValue[] remoteFieldValues) {
		try {
			getService().progressWorkflowAction(getToken(), key, id,
					remoteFieldValues);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public void addComment(String key, RemoteComment remoteComment) {
		try {
			getService().addComment(getToken(), key, remoteComment);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public RemoteStatus[] getStatuses() {
		try {
			return getService().getStatuses(getToken());
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public void updateIssue(String key, RemoteFieldValue[] fieldValues) {
		try {
			getService().updateIssue(getToken(), key, fieldValues);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}
	
	public RemoteIssue[] getIssuesFromCurrentUserWithStatus(List<String> statusList) {
		String jql = String.format(
				"assignee = currentUser() AND status in (%s)",
				StringUtils.join(statusList, ", "));

		try {
			return getService().getIssuesFromJqlSearch(getToken(), jql, 30);
		} catch (RemoteValidationException e) {
			throw _handleException(e);
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (RemotePermissionException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}		
	}

	public String getJiraUsername() {
		String username = jiraOptions.getUserName().unbox();
		if (username == null)
			throw new JiraOptionsNotSetException();

		return username;
	}

	private RuntimeException _handleException(RemoteException e) {
		_resetTokenTimeout();
		return new RuntimeException(e.getMessage());
	}

	private RuntimeException _handleException(RemotePermissionException e) {
		return new JiraPermissionException(e);
	}

	private RuntimeException _handleException(RemoteAuthenticationException e) {
		_resetTokenTimeout();
		return new JiraAuthenticationException(e);
	}

	private RuntimeException _handleException(RemoteValidationException e) {
		return new JiraValidationException(e);
	}

	private RuntimeException _handleException(ServiceException e) {
		return new RuntimeException(e);
	}

	private void _resetTokenTimeout() {
		tokenTimeout = null;
	}


}