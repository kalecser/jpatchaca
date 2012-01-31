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

@SuppressWarnings("rawtypes")
public class GetMetaAttributesService {

    private TokenManager tokenManager;
    private IssueManager issueManager;
    private IssueService issueService;

    public GetMetaAttributesService(TokenManager tokenManager, IssueManager issueManager,
            IssueService issueService) {
        this.tokenManager = tokenManager;
        this.issueManager = issueManager;
        this.issueService = issueService;
    }

    public RemoteMetaAttribute[] getMetaAttributesForIssue(String token, String issueKey) throws RemoteException {
        MutableIssue issue = retrieveIssue(token, issueKey);
        Map metaAttributesMap = WorkflowUtil.getMetaAttributesForIssue(issue.getGenericValue());
        return createMetaAttributesArray(metaAttributesMap);
    }

    private RemoteMetaAttribute[] createMetaAttributesArray(Map metaAttributesMap) {
        RemoteMetaAttribute[] metaAttributes = new RemoteMetaAttribute[metaAttributesMap.size()];
        int index = 0;
        for (Object entryObject : metaAttributesMap.entrySet())
            metaAttributes[index++] = createRemoteMetaAttribute((Entry) entryObject);
        Arrays.sort(metaAttributes);
        return metaAttributes;
    }

    private RemoteMetaAttribute createRemoteMetaAttribute(Entry entry) {
        String attName = (String) entry.getKey();
        String attValue = (String) entry.getValue();
        return new RemoteMetaAttribute(attName, attValue);
    }

    private MutableIssue retrieveIssue(String token, String issueKey) throws RemoteException {
        User user = tokenManager.retrieveUserNoPermissionCheck(token);
        RemoteIssue remoteIssue = issueService.getIssue(user, issueKey);
        MutableIssue issue = issueManager.getIssueObject(remoteIssue.getKey());
        return issue;
    }
}
