package jira;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;
import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;
import com.dolby.jira.net.soap.jira.RemoteIssue;

public class JiraImpl implements Jira {

	@Override
	public String[] getIssues(String name, String password, String address) {
			try {
				return internalGetIssues(name, password, address);
			} catch (RemoteAuthenticationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (com.dolby.jira.net.soap.jira.RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
	}

	private String[] internalGetIssues(String name, String password,
			String address) throws ServiceException, RemoteException,
			RemoteAuthenticationException,
			com.dolby.jira.net.soap.jira.RemoteException {
		Logger.getLogger("org.apache.axis").setLevel(Level.OFF);
		
		JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
		locator.setJirasoapserviceV2EndpointAddress( address + "/rpc/soap/jirasoapservice-v2");
		locator.setMaintainSession(true);
		
		JiraSoapService service = locator.getJirasoapserviceV2();
		String token = service.login(name, password);

		
		RemoteIssue[] bugs = service.getIssuesFromJqlSearch(token, "status = open and assignee = " + name, 20);
		
		List<String> issues = new ArrayList<String>();
		for (RemoteIssue issue : bugs){
			issues.add(issue.getKey() + " " + issue.getSummary());
		}
		
		return issues.toArray(new String[]{});
	}

}
