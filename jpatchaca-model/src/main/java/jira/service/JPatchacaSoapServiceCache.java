package jira.service;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.jpatchaca.jira.ws.JPatchacaSoapService;

public class JPatchacaSoapServiceCache implements JPatchacaSoapService {

    private JPatchacaSoapService service;
    private Map<String, String> workableMetaAttributeCache = new HashMap<String, String>();

    public JPatchacaSoapService decorate(JPatchacaSoapService service) {
        this.service = service;
        return this;
    }

    @Override
    public boolean isAvailable() throws RemoteException {
        return service.isAvailable();
    }

    @Override
    public String[] getMetaAttributeForIssues(String token, String[] issues, String metaAttribute)
            throws RemoteException {
        if (Jira.WORKABLE_META_ATTRIBUTE.equals(metaAttribute))
            return loadMetaAttributeFromCache(token, issues, metaAttribute);
        return loadMetaAttributeFromService(token, issues, metaAttribute);
    }

    private String[] loadMetaAttributeFromCache(String token, String[] issues, String metaAttribute)
            throws RemoteException, RemoteException {
        if (issues.length > 1)
            throw new MultipleIssueCacheNotImplemented();
        String issueKey = issues[0];
        if (!workableMetaAttributeCache.containsKey(issueKey))
            saveOnCache(token, issues, metaAttribute);
        return new String[] { workableMetaAttributeCache.get(issueKey) };
    }

    private void saveOnCache(String token, String[] issues, String metaAttribute)
            throws RemoteException, org.jpatchaca.jira.ws.RemoteException {
        String[] result = loadMetaAttributeFromService(token, issues, metaAttribute);
        workableMetaAttributeCache.put(issues[0], result[0]);
    }

    private String[] loadMetaAttributeFromService(String token, String[] issues,
            String metaAttribute) throws RemoteException, org.jpatchaca.jira.ws.RemoteException {
        return service.getMetaAttributeForIssues(token, issues, metaAttribute);
    }
}