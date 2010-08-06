package events.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import wheel.io.files.Directory;

import com.thoughtworks.xstream.XStream;

import core.events.eventslist.EventTransaction;

public class XMLAppenderPersistence extends FileAppenderPersistence implements org.picocontainer.Startable{

	private static final XStream XSTREAM = new XStream();
	private static final String TIMER_XML_FILENAME = "timer.xml";
	private final Directory directory2;

	public XMLAppenderPersistence(Directory directory) {
		super(directory);
		directory2 = directory;
		fileName = TIMER_XML_FILENAME;
	}
	
	@Override
	protected void writeObjectOrCry(EventTransaction event, OutputStream out) {		
		try {
			String eventToXML = XSTREAM.toXML(event);
			IOUtils.write(eventToXML + "\n", out);
			out.flush();
			
		} catch (Throwable e) {
			throw new IllegalStateException("Error writing transaction", e);
		}
	}
	
	private BufferedReader buffy;
	
	@Override
	public List<EventTransaction> getEventsFromFile() {
		
		buffy = null;
		return super.getEventsFromFile();
	}
	
	
	@Override
	protected Object readObjectOrCry(InputStream in) {

		if (buffy == null)
			buffy = new BufferedReader(new InputStreamReader(in));
		
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
	public void start() {
		
		if (!directory2.fileExists(TIMER_XML_FILENAME)){
			FileAppenderPersistence pers = new FileAppenderPersistence(directory2);
			for (EventTransaction evt : pers.getEventTransactions()){
				writeEvent(evt);
			}
		}
		
	}

	@Override
	public void stop(){
		
	}
	
	

}
