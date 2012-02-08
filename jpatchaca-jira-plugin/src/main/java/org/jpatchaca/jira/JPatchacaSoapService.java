package org.jpatchaca.jira;

import com.atlassian.jira.rpc.exception.RemoteException;

public interface JPatchacaSoapService
{	
	String[] getMetaAttributeForIssues(String token, String[] issues, String metaKey) throws RemoteException;
	Boolean isAvailable();
}
