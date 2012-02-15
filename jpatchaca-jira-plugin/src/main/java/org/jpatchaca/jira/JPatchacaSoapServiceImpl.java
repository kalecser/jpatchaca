package org.jpatchaca.jira;

import com.atlassian.jira.rpc.exception.RemoteException;

public class JPatchacaSoapServiceImpl implements JPatchacaSoapService {

    private final GetMetaAttributesService metaAttributesService;

    public JPatchacaSoapServiceImpl(GetMetaAttributesService metaAttributeOperation) {
        this.metaAttributesService = metaAttributeOperation;
    }
    
    @Override
    public String[] getMetaAttributeForIssues(String token, String[] issues, String metaKey)
            throws RemoteException {
        return metaAttributesService.getMetaAttributeForIssues(token, issues, metaKey);            
    }
    
    @Override
    public Boolean isAvailable() {
        return true;
    }

}
