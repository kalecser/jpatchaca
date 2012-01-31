package org.jpatchaca.jira;

import com.atlassian.jira.rpc.exception.RemoteException;

public interface JPatchacaSoapService
{	
	RemoteMetaAttribute[] getMetaAttributesForIssue(String token, String issueKey) throws RemoteException;
	Boolean isAvailable();
}
