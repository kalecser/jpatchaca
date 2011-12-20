package jira.service;

import javax.xml.rpc.ServiceException;

import jira.JiraOptions;

import org.jpatchaca.jira.ws.JPatchacaSoapService;
import org.jpatchaca.jira.ws.JPatchacaSoapServiceServiceLocator;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;

public class JiraSoapServiceFactory implements JiraServiceFactory {

	public final String JIRASOAPSERVICE_ENDPOINT = "/rpc/soap/jirasoapservice-v2";
	public final String JPATCHACASERVICE_ENDPOINT = "/rpc/soap/jpatchaca-service";
	private final JiraOptions jiraOptions;
	
	public JiraSoapServiceFactory(JiraOptions jiraOptions){
		this.jiraOptions = jiraOptions;		
	}
	
	@Override
	public JiraSoapService createJiraSoapService() throws ServiceException {
		final JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
		locator.setJirasoapserviceV2EndpointAddress(serviceAddress(JIRASOAPSERVICE_ENDPOINT));
		locator.setMaintainSession(true);
		return locator.getJirasoapserviceV2();
	}
	
	@Override
	public JPatchacaSoapService createJPatchacaService() throws ServiceException{
		final JPatchacaSoapServiceServiceLocator locator = new JPatchacaSoapServiceServiceLocator();
		locator.setJpatchacaServiceEndpointAddress(serviceAddress(JPATCHACASERVICE_ENDPOINT));
		locator.setMaintainSession(true);
		return locator.getJpatchacaService();
	}

	private String serviceAddress(String endpoint) {
		String address = jiraOptions.getURL().unbox();		
//		while(address.endsWith("/"))
//			address = address.substring(0, address.length());
		return address + endpoint;
	}

}
