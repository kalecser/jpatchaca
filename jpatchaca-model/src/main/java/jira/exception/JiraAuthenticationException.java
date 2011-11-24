package jira.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;

public class JiraAuthenticationException extends JiraException {

	private static final long serialVersionUID = 1L;
	
	private static Pattern authErrorPattern = Pattern
			.compile("^com\\.atlassian\\.jira\\.rpc\\.exception\\.RemoteAuthenticationException: (.*?)$");

	public JiraAuthenticationException(RemoteAuthenticationException e) {
		super(dumpErrorMessage(e), e);
	}

	private static String dumpErrorMessage(RemoteAuthenticationException e) {
		Matcher matcher = authErrorPattern.matcher(e.getFaultString());
		if(matcher.matches())
			return matcher.group(1).toString();
		return e.getFaultString();
	}
}
