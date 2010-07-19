package jira;

import org.apache.axis.AxisProperties;
import org.apache.axis.components.net.SunFakeTrustSocketFactory;
import org.picocontainer.Startable;

public class SslManagerSetup implements Startable{

	@Override
	public void start() {
		trustAll();
		
	}

	private void trustAll() {
		AxisProperties.setProperty("axis.socketSecureFactory", SunFakeTrustSocketFactory.class.getName());
	}

	@Override
	public void stop() {
		
	}
}
