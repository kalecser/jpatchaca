package events.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import wheel.io.files.Directory;
import core.events.eventslist.EventTransaction;
import events.PersistenceManager;

public class SmartSerializerPersistenceManager implements PersistenceManager{

	private final Directory directory;
	private final String fileName;
	

	public SmartSerializerPersistenceManager(Directory directory){
		this.directory = directory;
		fileName = "timer.dat";	
	}
	
	@SuppressWarnings("unchecked")
	public List<EventTransaction> getEventTransactions() {
		
		final List<EventTransaction> transactions = new ArrayList<EventTransaction>();
		
		final String oldPersistenceFileName = "money.dat";
		if (!directory.fileExists(fileName) && directory.fileExists(oldPersistenceFileName)){
			try {
				directory.renameFile(oldPersistenceFileName, fileName);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
			
		
		if (!directory.fileExists(fileName))
			return transactions;
			
		InputStream stream  = null;
		try {
			stream = directory.openFile(fileName);
			final ObjectInputStream objectInput = new ObjectInputStream(stream);
			
			return (List<EventTransaction>) objectInput .readObject();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (stream != null)
				IOUtils.closeQuietly(stream);
		}
		
	}


	public void writeEvent(EventTransaction event) {
		
		final List<EventTransaction> eventTransactions = getEventTransactions();
		
		makeBackupOfOldFile();		
		
		try {
			final OutputStream stream = directory.createFile(fileName);
			final ObjectOutputStream objectOutput = new ObjectOutputStream(stream);
			eventTransactions.add(event);
			objectOutput.writeObject(eventTransactions);
			
			stream.flush();
			stream.close();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		
	}

	private void makeBackupOfOldFile() {
		try {
			if (directory.fileExists(fileName + ".old"))
				directory.deleteFile(fileName + ".old");
			if (directory.fileExists(fileName))
				directory.renameFile(fileName , fileName + ".old");
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void reset() throws IOException {
		if (directory.fileExists(fileName))
			directory.deleteFile(fileName);		
	}

}
