package jira.service;

import javax.xml.rpc.ServiceException;

import org.jpatchaca.jira.ws.JPatchacaSoapService;

import com.dolby.jira.net.soap.jira.JiraSoapService;

public interface JiraServiceFactory {

	JPatchacaSoapService createJPatchacaService(String address) throws ServiceException;
	JiraSoapService createJiraSoapService(String address) throws ServiceException;

}