package org.jpatchaca.jira;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.rpc.auth.TokenManager;
import com.atlassian.jira.rpc.exception.RemoteException;
import com.atlassian.jira.rpc.soap.beans.RemoteIssue;
import com.atlassian.jira.rpc.soap.service.IssueService;
import com.atlassian.jira.workflow.WorkflowUtil;
import com.opensymphony.user.User;

public class JPatchacaSoapServiceImpl implements JPatchacaSoapService {

	private final TokenManager tokenManager;
	private final IssueManager issueManager;
	private final IssueService issueService;

	public JPatchacaSoapServiceImpl(TokenManager tokenManager,
			IssueManager issueManager, IssueService issueService) {
		this.tokenManager = tokenManager;
		this.issueManager = issueManager;
		this.issueService = issueService;
	}

	@Override
	public Long remainingEstimate(String token, String issueKey)
			throws RemoteException {
		MutableIssue issue = retrieveIssue(token, issueKey);
		return issue.getEstimate();
	}

	@Override
	public RemoteMetaAttribute[] metaProperties(String token, String issueKey)
			throws RemoteException {
		MutableIssue issue = retrieveIssue(token, issueKey);

		@SuppressWarnings("rawtypes")
		Map metaAttributesMap = WorkflowUtil.getMetaAttributesForIssue(issue
				.getGenericValue());

		return createMetaAttributesArray(metaAttributesMap);
	}

	private RemoteMetaAttribute[] createMetaAttributesArray(
			@SuppressWarnings("rawtypes") Map metaAttributesMap) {
		
		RemoteMetaAttribute[] metaAttributes = new RemoteMetaAttribute[metaAttributesMap
				.size()];
		int index = 0;
		for (Object entryObject : metaAttributesMap.entrySet()) {
			@SuppressWarnings("rawtypes")
			Entry entry = (Entry) entryObject;
			String attName = (String) entry.getKey();
			String attValue = (String) entry.getValue();
			metaAttributes[index++] = new RemoteMetaAttribute(attName, attValue);
		}

		Arrays.sort(metaAttributes);		
		return metaAttributes;
	}

	private MutableIssue retrieveIssue(String token, String issueKey)
			throws RemoteException {

		User user = tokenManager.retrieveUserNoPermissionCheck(token);
		RemoteIssue remoteIssue = issueService.getIssue(user, issueKey);
		MutableIssue issue = issueManager.getIssueObject(remoteIssue.getKey());
		return issue;
	}
}
