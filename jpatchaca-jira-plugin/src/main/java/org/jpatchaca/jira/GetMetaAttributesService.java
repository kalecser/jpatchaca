package org.jpatchaca.jira;

import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.rpc.auth.TokenManager;
import com.atlassian.jira.rpc.exception.RemoteException;
import com.atlassian.jira.rpc.soap.beans.RemoteIssue;
import com.atlassian.jira.rpc.soap.service.IssueService;
import com.atlassian.jira.workflow.WorkflowUtil;
import com.opensymphony.user.User;

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
    
    public String getMetaAttributeForIssue(String token, String issueKey, String metaKey) throws RemoteException{
        MutableIssue issue = retrieveIssue(token, issueKey);
        return WorkflowUtil.getMetaAttributeForIssue(issue.getGenericValue(), metaKey);
    }

    private MutableIssue retrieveIssue(String token, String issueKey) throws RemoteException {
        User user = tokenManager.retrieveUserNoPermissionCheck(token);
        RemoteIssue remoteIssue = issueService.getIssue(user, issueKey);
        MutableIssue issue = issueManager.getIssueObject(remoteIssue.getKey());
        return issue;
    }

    public String[] getMetaAttributeForIssues(String token, String[] issues, String metaKey) throws RemoteException {
        String[] result = new String[issues.length];
        int i = 0;
        for(String issueKey: issues)
            result[i++] = getMetaAttributeForIssue(token, issueKey, metaKey);
        return result;
    }
}
