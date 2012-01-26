package jira;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.xml.rpc.ServiceException;

import jira.service.HttpChecker;
import jira.service.JPatchacaSoapServiceFake;
import jira.service.JiraSoapServiceFactory;

import org.jpatchaca.jira.ws.JPatchacaSoapService;
import org.junit.Test;

public class JiraSoapServiceFactoryTest {

    @Test
    public void jpatchacaWebserviceUnavailable() throws ServiceException {
        assertTrue(createService(false) instanceof JPatchacaSoapServiceFake);
    }

    @Test
    public void jpatchacaWebserviceAvailable() throws ServiceException {
        assertFalse(createService(true) instanceof JPatchacaSoapServiceFake);
    }

    private JPatchacaSoapService createService(boolean available) throws ServiceException {
        JiraSoapServiceFactory factory = new JiraSoapServiceFactory(createHttpChecker(available));
        return factory.createJPatchacaService("http://url/");
    }

    private HttpChecker createHttpChecker(final boolean response) {
        return new HttpChecker() {
            @Override
            public boolean isAddressAvaiable(String address) {
                return response;
            }
        };
    }
}
