package jira.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jira.exception.JiraException;
import jira.issue.JiraAction;
import jira.issue.JiraIssue;

public class CachedJira implements Jira{

    private final Jira jira;
    private Map<JiraIssue, Boolean> isWorkableCache;    

    public CachedJira(Jira jira) {
        this.jira = jira;
        isWorkableCache = new HashMap<JiraIssue, Boolean>();
    }

    @Override
    public JiraIssue getIssueByKey(String key) throws JiraException {
        return jira.getIssueByKey(key);
    }

    @Override
    public void newWorklog(String issueId, Calendar startDate, String timeSpent) {
        jira.newWorklog(issueId, startDate, timeSpent);
    }

    @Override
    public List<JiraAction> getAvaiableActions(JiraIssue issue) {
        return jira.getAvaiableActions(issue);
    }

    @Override
    public void progressWithAction(JiraIssue issue, JiraAction action, String comment) {
        jira.progressWithAction(issue, action, comment);
    }

    @Override
    public String getIssueStatus(JiraIssue issue) {
        return jira.getIssueStatus(issue);
    }

    @Override
    public String getIssueAssignee(JiraIssue issue) {
        return jira.getIssueAssignee(issue);
    }

    @Override
    public void assignIssueToCurrentUser(JiraIssue issue) {
        jira.assignIssueToCurrentUser(issue);
    }

    @Override
    public void assignIssueTo(JiraIssue issue, String user) {
        jira.assignIssueTo(issue, user);
    }

    @Override
    public boolean isAssignedToCurrentUser(JiraIssue issue) {
        return jira.isAssignedToCurrentUser(issue);
    }

    @Override
    public List<JiraIssue> getIssuesFromCurrentUserWithStatus(List<String> statusList) {
        return jira.getIssuesFromCurrentUserWithStatus(statusList);
    }

    @Override
    public boolean isWorkable(JiraIssue issue) {
        if (!isWorkableCache.containsKey(issue))
            isWorkableCache.put(issue, jira.isWorkable(issue));
        return isWorkableCache.get(issue);
    }
}
