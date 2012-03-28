package jira.service;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import jira.JiraOptions;
import jira.exception.JiraAuthenticationException;
import jira.exception.JiraIssueNotFoundException;
import jira.exception.JiraNotAvailable;
import jira.exception.JiraOptionsNotSetException;
import jira.exception.JiraPermissionException;
import jira.exception.JiraValidationException;

import org.apache.commons.lang.StringUtils;
import org.jpatchaca.jira.ws.JPatchacaSoapService;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;
import com.dolby.jira.net.soap.jira.RemoteComment;
import com.dolby.jira.net.soap.jira.RemoteField;
import com.dolby.jira.net.soap.jira.RemoteFieldValue;
import com.dolby.jira.net.soap.jira.RemoteIssue;
import com.dolby.jira.net.soap.jira.RemoteIssueType;
import com.dolby.jira.net.soap.jira.RemoteNamedObject;
import com.dolby.jira.net.soap.jira.RemotePermissionException;
import com.dolby.jira.net.soap.jira.RemoteStatus;
import com.dolby.jira.net.soap.jira.RemoteValidationException;
import com.dolby.jira.net.soap.jira.RemoteWorklog;

public class JiraServiceFacade implements TokenFactory {

	public static final int TOKEN_TIMEOUT_MINUTES = 10;

	// TODO tentar remover jiraOptions
	private final JiraOptions jiraOptions;
	private final JiraServiceFactory serviceFactory;

	private final TokenManager tokenManager;

	private Map<String, RemoteIssueType> issueTypeMap;

	public JiraServiceFacade(JiraOptions jiraOptions, JiraServiceFactory serviceFactory,
			TokenManager tokenManager) {
		this.jiraOptions = jiraOptions;
		this.serviceFactory = serviceFactory;
		this.tokenManager = tokenManager;
		tokenManager.setTokenFactory(this);
	}

	private JiraSoapService getService() {
		try {
			return serviceFactory.createJiraSoapService(jiraOptions.getURL().unbox());
		} catch (ServiceException e) {
			throw _handleException(e);
		}
	}

	@Override
	public String createToken() {
		return login();
	}

	private String login() {
		try {
			jiraOptions.validate();
			return getService().login(jiraOptions.getUserName().unbox(),
					jiraOptions.getPassword().unbox());
		} catch (RemoteAuthenticationException e) {
			throw _handleException(e);
		} catch (com.dolby.jira.net.soap.jira.RemoteException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	public RemoteIssue getIssueByKey(String key) {
		try {
			RemoteIssue[] remoteIssues = getService().getIssuesFromJqlSearch(
					tokenManager.getToken(), "key = " + key, 20);
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
			return getService().getIssueById(tokenManager.getToken(), id);
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

	public void addWorklogAndAutoAdjustRemainingEstimate(String issueId, RemoteWorklog workLog) {
		try {
			getService().addWorklogAndAutoAdjustRemainingEstimate(tokenManager.getToken(), issueId,
					workLog);
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
			return getService().getAvailableActions(tokenManager.getToken(), key);
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
			return getService().getFieldsForAction(tokenManager.getToken(), key, id);
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

	public void progressWorkflowAction(String key, String id, RemoteFieldValue[] remoteFieldValues) {
		try {
			getService()
					.progressWorkflowAction(tokenManager.getToken(), key, id, remoteFieldValues);
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

	public void addComment(String key, String commentText) {
		try {
			RemoteComment remoteComment = new RemoteComment(null, commentText, null, null, null,
					null, null, null);
			getService().addComment(tokenManager.getToken(), key, remoteComment);
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
			return getService().getStatuses(tokenManager.getToken());
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
			getService().updateIssue(tokenManager.getToken(), key, fieldValues);
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
		String jql = String.format("assignee = currentUser() AND status in (%s)",
				StringUtils.join(statusList, ", "));

		try {
			return getService().getIssuesFromJqlSearch(tokenManager.getToken(), jql, 30);
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

	public String getMetaAttribute(String issueKey, String metaAttribute) {
		try {
			return internalGetMetaAttribute(issueKey, metaAttribute);
		} catch (ServiceException e) {
			throw _handleException(e);
		} catch (RemoteException e) {
			throw _handleException(e);
		}
	}

	private String internalGetMetaAttribute(String issueKey, String metaAttribute)
			throws ServiceException, RemoteException {
		JPatchacaSoapService jpatchacaService = serviceFactory.createJPatchacaService(jiraOptions
				.getURL().unbox());
		String[] issues = new String[] { issueKey };
		String[] values = jpatchacaService.getMetaAttributeForIssues(tokenManager.getToken(),
				issues, metaAttribute);

		if (values.length == 0)
			throw new MetaAttributeNotFound();

		return values[0];
	}

	public String getJiraUsername() {
		String username = jiraOptions.getUserName().unbox();
		if (username == null)
			throw new JiraOptionsNotSetException();

		return username;
	}

	private RuntimeException _handleException(RemoteException e) {
		tokenManager.resetTokenTimeout();
		return new JiraNotAvailable(e);
	}

	private RuntimeException _handleException(RemotePermissionException e) {
		return new JiraPermissionException(e);
	}

	private RuntimeException _handleException(RemoteAuthenticationException e) {
		tokenManager.resetTokenTimeout();
		return new JiraAuthenticationException(e);
	}

	private RuntimeException _handleException(RemoteValidationException e) {
		return new JiraValidationException(e);
	}

	private RuntimeException _handleException(ServiceException e) {
		tokenManager.resetTokenTimeout();
		return new RuntimeException(e);
	}

	public Map<String, RemoteIssueType> getIssueTypes() {
		try {
			return internalGetIssuetypes();
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

	private Map<String, RemoteIssueType> internalGetIssuetypes() throws RemoteException,
			RemotePermissionException, RemoteAuthenticationException {
		if (issueTypeMap == null) {
			issueTypeMap = new HashMap<String, RemoteIssueType>();
			for (RemoteIssueType type : getService().getIssueTypes(tokenManager.getToken()))
				issueTypeMap.put(type.getName(), type);
		}
		return issueTypeMap;
	}
}