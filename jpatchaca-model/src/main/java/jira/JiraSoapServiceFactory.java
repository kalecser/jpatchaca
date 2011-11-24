package jira;

import javax.xml.rpc.ServiceException;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;

public class JiraSoapServiceFactory implements JiraServiceFactory {

	@Override
	public JiraSoapService createService(String address) throws ServiceException {
		final JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
		locator.setJirasoapserviceV2EndpointAddress(address);
		locator.setMaintainSession(true);
		return locator.getJirasoapserviceV2();
	}

}
