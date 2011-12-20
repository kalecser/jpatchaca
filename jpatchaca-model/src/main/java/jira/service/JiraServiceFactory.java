package jira.service;

import javax.xml.rpc.ServiceException;

import org.jpatchaca.jira.ws.JPatchacaSoapService;

import com.dolby.jira.net.soap.jira.JiraSoapService;

public interface JiraServiceFactory {

	JPatchacaSoapService createJPatchacaService() throws ServiceException;
	JiraSoapService createJiraSoapService() throws ServiceException;

}