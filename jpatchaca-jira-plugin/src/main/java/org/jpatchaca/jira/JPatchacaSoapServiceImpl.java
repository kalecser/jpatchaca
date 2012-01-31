package org.jpatchaca.jira;

import com.atlassian.jira.rpc.exception.RemoteException;

public class JPatchacaSoapServiceImpl implements JPatchacaSoapService {

    private final GetMetaAttributesService metaAttributesService;

    public JPatchacaSoapServiceImpl(GetMetaAttributesService metaAttributeOperation) {
        this.metaAttributesService = metaAttributeOperation;
    }
    
    public RemoteMetaAttribute[] getMetaAttributesForIssue(String token, String issueKey)
            throws RemoteException {
        return metaAttributesService.getMetaAttributesForIssue(token, issueKey);
    }

    public Boolean isAvailable() {
        return true;
    }
}
