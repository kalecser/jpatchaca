package jira.mock;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import javax.xml.rpc.ServiceException;

import org.jpatchaca.jira.ws.JPatchacaSoapService;

import jira.service.JiraServiceFactory;

import com.dolby.jira.net.soap.jira.JiraSoapService;

public class JiraServiceMockFactory implements JiraServiceFactory {

	private JiraSoapService serviceMock;

	public JiraServiceMockFactory(InvocationHandler invocationHandler) {
		serviceMock = (JiraSoapService) Proxy.newProxyInstance(
				JiraSoapService.class.getClassLoader(),
				new Class[] { JiraSoapService.class }, invocationHandler);
	}

	@Override
	public JPatchacaSoapService createJPatchacaService()
			throws ServiceException {
		return null;
	}

	@Override
	public JiraSoapService createJiraSoapService() throws ServiceException {
		return serviceMock;
	}

}
