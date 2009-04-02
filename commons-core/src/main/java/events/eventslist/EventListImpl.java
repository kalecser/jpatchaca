/*
 * Created on 21/10/2005
 */
package events.eventslist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import basic.BasicSystem;
import basic.SystemClock;
import core.events.eventslist.EventTransaction;
import events.PersistenceManager;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class EventListImpl implements EventList{

	private static final long serialVersionUID = 1L;
	private final PersistenceManager persistenceManager;
	private final SystemClock clock;
	private final List<Processor<Serializable>> processors;
	private final Queue<EventTransaction> transactionsQueue;
	private final BasicSystem basicSystem;
	



	public EventListImpl(final PersistenceManager persistenceManager,  final BasicSystem basicSystem) {
		this.persistenceManager = persistenceManager;
		this.basicSystem = basicSystem;
		this.clock = basicSystem.systemClock();
		processors = new ArrayList<Processor<Serializable>>();
		transactionsQueue = new LinkedList<EventTransaction>();
	}

	public synchronized void add(final Serializable elementAdded) {		
		final EventTransaction transaction = new EventTransaction(basicSystem.getHardwareTime().getTime(), elementAdded);
		transactionsQueue.add(transaction);
		executeAndWrite();
	}

	private void executeAndWrite(){
		EventTransaction transaction = null;
		
		while ((transaction = transactionsQueue.poll()) != null){
			try {
				execute(transaction);
			} catch (final MustBeCalledInsideATransaction e) {
				//Inside a transaction, fair enough to swallow
			}
			persistenceManager.writeEvent(transaction);
		}
		
	}

	private void execute(final EventTransaction transaction) throws MustBeCalledInsideATransaction {
		clock.setTime(transaction.getTime());
		
		boolean executed = false;
		for (final Processor<Serializable> processor : processors){
			if (processor.eventType() == transaction.getEvent().getClass()){
				processor.execute(transaction.getEvent());
				executed = true;
			}
		}
		
		
		if (!executed)
			throw new RuntimeException("No processor found for '" + transaction.getEvent().getClass().getName() + "' event.");
	}
	
	@SuppressWarnings("unchecked")
	public void addProcessors(final Processor[] processors) {
		
		for (final Processor processor : processors){
			if (this.processors.contains(processor))
				throw new RuntimeException("You can't add the same processor twice");
			this.processors.add(processor);
		}
		
		for (final EventTransaction transaction : persistenceManager.getEventTransactions()){
			try {
				execute(transaction);
			} catch (final MustBeCalledInsideATransaction e) {
				//Inside a transaction, fair enough to swallow!
			} 
		
		}
	}

	public int getEventCount() {
		return persistenceManager.getEventTransactions().size();
	}


	


}
