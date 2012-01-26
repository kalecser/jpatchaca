package jira.service;

import java.rmi.RemoteException;

import org.jpatchaca.jira.ws.JPatchacaSoapService;
import org.jpatchaca.jira.ws.RemoteMetaAttribute;

public class JPatchacaSoapServiceFake implements JPatchacaSoapService {

    @Override
    public RemoteMetaAttribute[] getMetaAttributesForIssue(String arg0, String arg1)
            throws RemoteException, org.jpatchaca.jira.ws.RemoteException {
        return new RemoteMetaAttribute[0];
    }

    @Override
    public long getRemainingEstimate(String arg0, String arg1) throws RemoteException,
            org.jpatchaca.jira.ws.RemoteException {
        return 0;
    }
    
}
