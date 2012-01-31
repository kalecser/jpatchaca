package jira;

import static org.junit.Assert.assertTrue;

import javax.xml.rpc.ServiceException;

import jira.service.JPatchacaSoapServiceFake;
import jira.service.JiraSoapServiceFactory;

import org.jpatchaca.jira.ws.JPatchacaSoapService;
import org.junit.Test;

public class JiraSoapServiceFactoryTest {

    @Test
    public void jpatchacaWebserviceUnavailable() throws ServiceException {
        assertTrue(createService() instanceof JPatchacaSoapServiceFake);
    }

    private JPatchacaSoapService createService() throws ServiceException {
        JiraSoapServiceFactory factory = new JiraSoapServiceFactory();
        return factory.createJPatchacaService("http://url/");
    }
}
