package jira;

import org.apache.commons.lang.Validate;

public class JiraUtils {

	
	public static String getIssueDescription(JiraIssue issue) {
		Validate.notNull(issue);
		return String.format("[%s] %s", issue.getKey(), issue.getSummary());
	}
	
}
