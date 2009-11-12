package jira;


public interface Jira {

	String[] getIssues(String name, String password, String address) throws JiraException;

}
