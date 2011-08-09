package jira;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import jira.exception.JiraException;
import jira.exception.JiraIssueNotFoundException;
import jira.exception.JiraOptionsNotSetException;
import lang.Maybe;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;
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

public class JiraImpl implements Jira {

	private static final String JIRA_NOT_AVAIABLE_MESSAGE = "Jira is not avaiable";

	private final JiraOptions jiraOptions;
	private Map<String, String> statuses;
	private String token;

	private Calendar tokenTimeout;

	public JiraImpl(final JiraOptions jiraOptions) {
		this.jiraOptions = jiraOptions;
	}

	@Override
	public JiraIssue getIssueByKey(final String key) throws JiraException {
		try {
			return internalGetIssueByKey(key);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
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

		RemoteIssue[] issues = null;
		try {
			issues = service.getIssuesFromJqlSearch(getToken(), "key = " + key,
					20);
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

		if (url == null)
			throw new JiraOptionsNotSetException();

		final JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();

		String address = String.format("%s/rpc/soap/jirasoapservice-v2",
				jiraOptions.getURL().unbox());
		locator.setJirasoapserviceV2EndpointAddress(address);
		locator.setMaintainSession(true);

		final JiraSoapService service = locator.getJirasoapserviceV2();
		return service;
	}

	private String getToken() throws RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException,
			JiraOptionsNotSetException, ServiceException {

		if (token != null && !tokenExpired())
			return token;

		final Maybe<String> username = jiraOptions.getUserName();
		final Maybe<String> password = jiraOptions.getPassword();

		if (username == null || password == null)
			throw new JiraOptionsNotSetException();

		token = getService().login(username.unbox(), password.unbox());
		setTokenTimeout();

		return token;
	}

	private void setTokenTimeout() {
		tokenTimeout = Calendar.getInstance();
		tokenTimeout.add(Calendar.MINUTE, 10);
	}

	private boolean tokenExpired() {
		if (tokenTimeout == null)
			return true;
		return tokenTimeout.before(Calendar.getInstance());
	}

	@Override
	public JiraIssue getIssueById(final String id) throws JiraException {
		try {
			return internalGetIssueById(id);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		}
	}

	private JiraIssue internalGetIssueById(final String id)
			throws JiraException, ServiceException,
			RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException {

		return createIssue(getRemoteIssue(id));
	}

	private RemoteIssue getRemoteIssue(final String id)
			throws JiraOptionsNotSetException, ServiceException,
			RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException,
			RemotePermissionException, JiraIssueNotFoundException {

		try {
			return getService().getIssueById(getToken(), id);
		} catch (final RemoteValidationException e) {
			throw new JiraIssueNotFoundException(id);
		}
	}

	private JiraIssue createIssue(final RemoteIssue issue) {
		final JiraIssueData data = new JiraIssueData();
		data.setId(issue.getId());
		data.setKey(issue.getKey());
		data.setSummary(issue.getSummary());
		return new JiraIssue(data);
	}

	@Override
	public void newWorklog(final String issueId, final Calendar startDate,
			final String timeSpent) {
		try {
			internalNewWorklog(issueId, startDate, timeSpent);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
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

		getService().addWorklogAndAutoAdjustRemainingEstimate(getToken(),
				issueId, workLog);
	}

	@Override
	public List<JiraAction> getAvaiableActions(JiraIssue issue) {

		try {
			return internalGetAvaiableActions(issue.getKey());
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getFaultString().split(":")[1], e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		}
	}

	private List<JiraAction> internalGetAvaiableActions(String key)
			throws JiraOptionsNotSetException, ServiceException,
			RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException {

		final JiraSoapService service = getService();
		final String token = getToken();

		List<JiraAction> actions = new ArrayList<JiraAction>();
		RemoteNamedObject[] availableActions = service.getAvailableActions(
				token, key);

		if (availableActions == null)
			return actions;

		for (RemoteNamedObject namedObject : availableActions)
			actions.add(createAction(service, token, key, namedObject));

		return actions;
	}

	private JiraAction createAction(final JiraSoapService service,
			final String token, String key, RemoteNamedObject namedObject)
			throws RemoteException,
			com.dolby.jira.net.soap.jira.RemoteException {
		JiraAction action = new JiraAction(namedObject.getId(), namedObject
				.getName());
		RemoteField[] fields = service.getFieldsForAction(token, key,
				namedObject.getId());

		for (RemoteField remoteField : fields)
			action.addField(createField(remoteField));
		return action;
	}

	private JiraField createField(RemoteField remoteField) {
		return new JiraField(remoteField.getId(), remoteField.getName());
	}

	@Override
	public void progressWithAction(JiraIssue issue, JiraAction action,
			String comment) {
		try {
			internalProgressWithAction(issue, action, comment);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		}
	}

	private void internalProgressWithAction(JiraIssue issue, JiraAction action,
			String comment) throws JiraOptionsNotSetException,
			ServiceException, RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException {

		if (comment == null || comment.isEmpty())
			throw new JiraException("Comment body can not be empty!");

		getService().progressWorkflowAction(getToken(), issue.getKey(),
				action.getId(), new RemoteFieldValue[0]);

		getService().addComment(
				getToken(),
				issue.getKey(),
				new RemoteComment(null, comment, null, null, null, null, null,
						null));
	}

	@Override
	public String getIssueAssignee(JiraIssue issue) {
		try {
			return getRemoteIssue(issue.getId()).getAssignee();
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		}
	}

	@Override
	public String getIssueStatus(JiraIssue issue) {
		try {
			String statusId = getRemoteIssue(issue.getId()).getStatus();
			return getStatuses().get(statusId);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		}
	}

	private Map<String, String> getStatuses()
			throws JiraOptionsNotSetException, ServiceException,
			RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException {

		if (statuses != null)
			return statuses;

		RemoteStatus[] remoteStatuses = getService().getStatuses(getToken());
		statuses = new HashMap<String, String>();
		for (RemoteStatus remoteStatus : remoteStatuses)
			statuses.put(remoteStatus.getId(), remoteStatus.getName());

		return statuses;
	}

	@Override
	public void assignIssueToCurrentUser(JiraIssue issue) {
		try {

			String[] values = new String[] { jiraOptions.getUserName().unbox() };
			RemoteFieldValue remoteFieldValue = new RemoteFieldValue(
					"assignee", values);
			RemoteFieldValue[] fieldValues = new RemoteFieldValue[] { remoteFieldValue };
			getService().updateIssue(getToken(), issue.getKey(), fieldValues);

		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		}
	}

	@Override
	public boolean isAssignedToCurrentUser(JiraIssue issue) {
		Maybe<String> username = jiraOptions.getUserName();
		if (username == null)
			return false;

		return username.unbox().equals(getIssueAssignee(issue));
	}

	@Override
	public List<JiraIssue> getIssuesFromCurrentUserWithStatus(
			List<String> statusList) {
		try {
			return internalGetIssuesFromCurrentUserWithStatus(statusList);
		} catch (final RemoteAuthenticationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteValidationException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemotePermissionException e) {
			throw new JiraException(e.getMessage(), e);
		} catch (final RemoteException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		} catch (final ServiceException e) {
			throw new JiraException(JIRA_NOT_AVAIABLE_MESSAGE, e);
		}
	}

	private List<JiraIssue> internalGetIssuesFromCurrentUserWithStatus(
			List<String> statusList) throws JiraOptionsNotSetException,
			ServiceException, RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException, RemoteException {

		if (statusList == null || statusList.isEmpty())
			throw new IllegalArgumentException(
					"Status list must not be null or empty");

		String jql = String.format(
				"assignee = currentUser() AND status in (%s)", StringUtils
						.join(statusList, ", "));
		RemoteIssue[] remoteIssues = getService().getIssuesFromJqlSearch(
				getToken(), jql, 30);
		List<JiraIssue> issues = new ArrayList<JiraIssue>();

		for (RemoteIssue remoteIssue : remoteIssues)
			issues.add(createIssue(remoteIssue));

		return issues;
	}

}