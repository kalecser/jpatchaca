import javax.xml.rpc.ServiceException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dolby.jira.net.soap.jira.JiraSoapService;
import com.dolby.jira.net.soap.jira.JiraSoapServiceServiceLocator;
import com.dolby.jira.net.soap.jira.RemoteAuthenticationException;
import com.dolby.jira.net.soap.jira.RemoteException;
import com.dolby.jira.net.soap.jira.RemoteIssue;


public class Spike {
	private static final String PASS = "leviatan";


	public static void main(String[] args) throws ServiceException, RemoteAuthenticationException, RemoteException, java.rmi.RemoteException {
		
		Logger.getLogger("org.apache.axis").setLevel(Level.OFF);
		
		JiraSoapServiceServiceLocator locator = new JiraSoapServiceServiceLocator();
		locator.setJirasoapserviceV2EndpointAddress("http://10.42.11.135/rpc/soap/jirasoapservice-v2");
		locator.setMaintainSession(true);
		
		JiraSoapService service = locator.getJirasoapserviceV2();
		String token = service.login("kalecser", PASS);

		
		RemoteIssue[] bugs = service.getIssuesFromJqlSearch(token, "status = open and assignee = kalecser", 20);
		
		System.out.println(bugs[0].getKey() + " - " + bugs[0].getSummary());
		
	}
	
}
