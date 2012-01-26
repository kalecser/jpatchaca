package jira.service;

import javax.xml.rpc.ServiceException;

import org.jpatchaca.jira.ws.JPatchacaSoapService;
import org.jpatchaca.jira.ws.JPatchacaSoapServiceServiceLocator;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;

public class JiraSoapServiceFactory implements JiraServiceFactory {

    public final String JIRASOAPSERVICE_ENDPOINT = "/rpc/soap/jirasoapservice-v2";
    public final String JPATCHACASERVICE_ENDPOINT = "/rpc/soap/jpatchaca-service";
    private final HttpChecker httpChecker;

    public JiraSoapServiceFactory(HttpChecker httpChecker){
        this.httpChecker = httpChecker;        
    }
    
    @Override
    public JiraSoapService createJiraSoapService(String address) throws ServiceException {
        final JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
        locator.setJirasoapserviceV2EndpointAddress(jiraServiceAddress(address));
        locator.setMaintainSession(true);
        return locator.getJirasoapserviceV2();
    }

    @Override
    public JPatchacaSoapService createJPatchacaService(String address) throws ServiceException {
        if (httpChecker.isAddressAvaiable(jpatchacaServiceAddress(address)))
            return createJPatchacaSoapservice(jpatchacaServiceAddress(address));
        return new JPatchacaSoapServiceFake();
    }

    private JPatchacaSoapService createJPatchacaSoapservice(String address) throws ServiceException {
        final JPatchacaSoapServiceServiceLocator locator = new JPatchacaSoapServiceServiceLocator();
        locator.setJpatchacaServiceEndpointAddress(address);
        locator.setMaintainSession(true);
        return locator.getJpatchacaService();
    }

    private String jiraServiceAddress(String address) {
        return address + JIRASOAPSERVICE_ENDPOINT;
    }
    
    private String jpatchacaServiceAddress(String address) {
        return address + JPATCHACASERVICE_ENDPOINT;
    }
}
