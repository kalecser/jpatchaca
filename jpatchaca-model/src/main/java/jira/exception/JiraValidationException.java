package jira.exception;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dolby.jira.net.soap.jira.RemoteValidationException;

public class JiraValidationException extends JiraException {

	private static Pattern authErrorPattern = Pattern
			.compile("^com\\.atlassian\\.jira\\.rpc\\.exception\\.RemoteValidationException: .*?: (.*?)$");
	
	private static final long serialVersionUID = 1L;
	
	public JiraValidationException(String message){
		super(message);
	}
	
	public JiraValidationException(RemoteValidationException e) {
		super(dumpErrorMessage(e), e);		
	}
	
	private static String dumpErrorMessage(RemoteValidationException e) {
		Matcher matcher = authErrorPattern.matcher(e.getFaultString());
		if(matcher.matches())
			return matcher.group(1).toString();
		return e.getFaultString();
	}
}
