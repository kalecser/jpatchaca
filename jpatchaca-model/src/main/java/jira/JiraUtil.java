package jira;

import jira.issue.JiraIssue;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.time.DateUtils;

public class JiraUtil {
	public static String humanFormat(final double hours) {
		final int truncatedHours = ((int) hours);
		final int minutes = ((int) ((hours - truncatedHours) * 60));
		return truncatedHours + "h " + minutes + "m";
	}

	public static String humanFormat(final long millis) {
		return humanFormat((double)millis / DateUtils.MILLIS_PER_HOUR);
	}
	
	
	public static String getIssueDescription(JiraIssue issue) {
		Validate.notNull(issue);
		return String.format("[%s] %s", issue.getKey(), issue.getSummary());
	}
	
}
