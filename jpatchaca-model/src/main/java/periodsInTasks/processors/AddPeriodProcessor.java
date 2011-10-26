package periodsInTasks.processors;

import java.io.Serializable;

import periods.Period;
import periods.PeriodsFactory;
import periodsInTasks.PeriodsInTasksHome;
import events.AddPeriodEvent;
import events.Processor;
import events.persistence.MustBeCalledInsideATransaction;

public class AddPeriodProcessor implements Processor<AddPeriodEvent> {


	private final PeriodsFactory periodsFactory;
	private final PeriodsInTasksHome periodsInTasksHome;

	public AddPeriodProcessor(PeriodsInTasksHome periodsInTasksHome, PeriodsFactory periodsFactory) {

		this.periodsInTasksHome = periodsInTasksHome;
		this.periodsFactory = periodsFactory;		
	}

	@Override
	public void execute(AddPeriodEvent event) throws MustBeCalledInsideATransaction {
		final Period period = this.periodsFactory.createPeriod(event.getBegin());
		period.setStop(event.getEnd());
		this.periodsInTasksHome.addPeriodToTask(event.getTaskId(), period);
	}

	@Override
	public Class<? extends Serializable> eventType() {
		return AddPeriodEvent.class;
	}

}
