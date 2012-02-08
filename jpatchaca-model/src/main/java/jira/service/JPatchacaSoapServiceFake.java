package jira.service;

import java.rmi.RemoteException;

import org.jpatchaca.jira.ws.JPatchacaSoapService;

public class JPatchacaSoapServiceFake implements JPatchacaSoapService {


    @Override
    public boolean isAvailable() throws RemoteException {
        return false;
    }

    @Override
    public String[] getMetaAttributeForIssues(String in0, String[] in1, String in2)
            throws RemoteException, org.jpatchaca.jira.ws.RemoteException {
        throw new MetaAttributeNotFound();
    }    
}
