package basic.durable;


import java.util.logging.Logger;

import org.apache.commons.transaction.file.FileSequence;
import org.apache.commons.transaction.file.ResourceManagerException;
import org.apache.commons.transaction.util.Jdk14Logger;

import wheel.io.files.Directory;

import basic.IdProvider;
import core.ObjectIdentity;


public class DoubleIdProvider implements IdProvider {


	private FileSequence sequence;
	
	public DoubleIdProvider(Directory dir) {
		try {
			sequence = new FileSequence(dir.getPath(), new Jdk14Logger(Logger.getAnonymousLogger()));
			sequence.create("id", 0);
		} catch (final ResourceManagerException e) {
			throw new RuntimeException(e);
		}
		
		
	}

	public synchronized ObjectIdentity provideId() {
		try {
			return new ObjectIdentity(((Long)sequence.nextSequenceValueBottom("id",1)).toString());
		} catch (final ResourceManagerException e) {
			throw new RuntimeException(e);
		}
	}

}
