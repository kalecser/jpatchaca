package org.jpatchaca.jira;

import com.atlassian.jira.rpc.exception.RemoteException;

public interface JPatchacaSoapService
{	
	Long remainingEstimate(String token, String issueKey) throws RemoteException;
	RemoteMetaAttribute[] metaProperties(String token, String issueKey) throws RemoteException;
}
