package main;

import javax.xml.rpc.ServiceException;

import jira.SslManagerSetup;

import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;
import com.dolby.jira.net.soap.jira.RemoteException;


public class Teste {

	public static void main(String[] args) throws ServiceException, RemoteAuthenticationException, RemoteException, java.rmi.RemoteException {
		
		new SslManagerSetup().start();
//		
//		JiraOptions jiraOptions = new JiraOptions();
//		jiraOptions.setUserName("luiz.gustavo");
//		jiraOptions.setPassword("objective");
//		jiraOptions.setURL("https://10.42.12.15:8880");
//		JiraSoapServiceFactory factory = new JiraSoapServiceFactory(jiraOptions);
//		JiraSoapService service = factory.createJiraSoapService();		
//		String token = service.login(jiraOptions.getUserName().unbox(), jiraOptions.getPassword().unbox());
//		RemoteIssue issue = service.getIssue(token, "OI-1001");
//		System.out.println(issue);
	}
	
}
