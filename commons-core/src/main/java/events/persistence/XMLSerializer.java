package events.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;

import core.events.eventslist.EventTransaction;

public class XMLSerializer implements Serializer{

	private static final XStream XSTREAM = new XStream();
	
	@Override
	public void writeObjectOrCry(EventTransaction event, OutputStream out) {		
		try {
			String eventToXML = XSTREAM.toXML(event);
			IOUtils.write(eventToXML + "\n", out);
			out.flush();
			
		} catch (Throwable e) {
			throw new IllegalStateException("Error writing transaction", e);
		}
	}
	
		
	private InputStream currentStream = null;
	private BufferedReader buffy;
	@Override
	public Object readObjectOrCry(InputStream in) {

		if (in != currentStream){
			currentStream = in;
			buffy = new BufferedReader(new InputStreamReader(in));
		}
		
		String line = null;
		StringBuilder object = new StringBuilder();
		String endOfEvent = "</" + EventTransaction.class.getName() + ">";
		do {
			line = readOrCry(buffy);
			object.append(line);
		} while ( line != null && !line.equals(endOfEvent));
		
		if (object.toString().equals("null")){
			return null;
		}
		
		return XSTREAM.fromXML(object.toString());
	}

	private String readOrCry(BufferedReader buffy) {
		try {
			return buffy.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String fileName() {
		return "timer.xml";
	}

}
