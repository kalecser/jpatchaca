package events.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import core.events.eventslist.EventTransaction;
import events.PersistenceManager;


@Deprecated
public final class FileSerializedPersistenceManagerImpl implements PersistenceManager{

	private List<EventTransaction> _transactions;
	private static final String fileName = "money.dat";

	public List<EventTransaction> getEventTransactions() {
		if (_transactions == null){
			if (!new File(fileName).exists()){
				_transactions = new ArrayList<EventTransaction>();
			}
			else
			{			
				_transactions = readTransactionsFromDisk();
			}
		}
		return _transactions;
	}

	@SuppressWarnings("unchecked")
	private List<EventTransaction> readTransactionsFromDisk() {
		List<EventTransaction> result;		
		
		try{
			final FileInputStream fo = new FileInputStream(fileName);
			final ObjectInputStream os = new ObjectInputStream(fo);
			result = (List<EventTransaction>) os.readObject();
			os.close();
		}catch(final Exception ex){
			throw new RuntimeException(ex);
		}
		
		return result;
	}

	public void writeEvent(final EventTransaction event) {
		try{
			final List<EventTransaction> transactions = getEventTransactions();
			
			final FileOutputStream fo = new FileOutputStream(fileName);
			final ObjectOutputStream os = new ObjectOutputStream(fo);
			transactions.add(event);
			os.writeObject(transactions);
			os.flush();
			fo.getFD().sync();
			os.close();
			
			
		}catch(final Exception ex){
			throw new RuntimeException(ex);
		}		
	}

	public void reset() {
		if (new File(fileName).exists())
			if (!new File(fileName).delete())
				throw new RuntimeException("could not delete " + fileName);
		
	}
}
