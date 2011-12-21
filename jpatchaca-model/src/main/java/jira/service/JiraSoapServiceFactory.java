package jira.service;

import javax.xml.rpc.ServiceException;

import org.jpatchaca.jira.ws.JPatchacaSoapService;
import org.jpatchaca.jira.ws.JPatchacaSoapServiceServiceLocator;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;

public class JiraSoapServiceFactory implements JiraServiceFactory {

	public final String JIRASOAPSERVICE_ENDPOINT = "/rpc/soap/jirasoapservice-v2";
	public final String JPATCHACASERVICE_ENDPOINT = "/rpc/soap/jpatchaca-service";
	
	@Override
	public JiraSoapService createJiraSoapService(String address) throws ServiceException {
		final JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
		locator.setJirasoapserviceV2EndpointAddress(serviceAddress(address, JIRASOAPSERVICE_ENDPOINT));
		locator.setMaintainSession(true);
		return locator.getJirasoapserviceV2();
	}
	
	@Override
	public JPatchacaSoapService createJPatchacaService(String address) throws ServiceException{
		final JPatchacaSoapServiceServiceLocator locator = new JPatchacaSoapServiceServiceLocator();
		locator.setJpatchacaServiceEndpointAddress(serviceAddress(address, JPATCHACASERVICE_ENDPOINT));
		locator.setMaintainSession(true);
		return locator.getJpatchacaService();
	}

	private String serviceAddress(String address, String endpoint) {
		return address + endpoint;
	}

}
