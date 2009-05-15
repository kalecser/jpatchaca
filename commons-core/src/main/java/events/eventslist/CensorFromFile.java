package events.eventslist;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.UnhandledException;

import wheel.io.files.Directory;
import core.events.eventslist.EventTransaction;
import events.PersistenceManager;

public class CensorFromFile implements Censor{

	
	private static final String CENSOR_SKIP_TRANSACTIONS_TXT = "censor.skip.transactions.txt";	
	
	private PersistenceManager manager;
	private final Set<String> skip;
		
	public CensorFromFile(Directory dir, PersistenceManager manager) {
		
		this.manager = manager;
		String[] transactionsToSkip = censorFileContents(dir).split(",");
		skip = new LinkedHashSet<String>(Arrays.asList(transactionsToSkip));
	}

	private String censorFileContents(Directory dir) {
		
		if (!dir.fileExists(CENSOR_SKIP_TRANSACTIONS_TXT))
			return "";
		
		try {
			return dir.contentsAsString(CENSOR_SKIP_TRANSACTIONS_TXT);
		} catch (IOException e) {
			throw new UnhandledException(e);
		}
	}

	@Override
	public boolean accept(EventTransaction transaction) {
		return !(skip.contains("" + manager.getEventTransactions().indexOf(transaction)));
	}

}
