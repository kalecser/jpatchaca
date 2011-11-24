package jira;

import javax.xml.rpc.ServiceException;

import com.dolby.jira.net.soap.jira.JiraSoapService;

public interface JiraServiceFactory {

	public abstract JiraSoapService createService(String address)
			throws ServiceException;

}