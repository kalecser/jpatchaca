package ui.swing.tasks;

import lang.Maybe;

import org.reactive.Receiver;
import org.reactive.Signal;

import periods.Period;
import reactive.ListRedirector;
import reactive.ListSource;
import tasks.TaskView;

public class SelectedTaskPeriods {

	private final ListRedirector<Period> redirector = new ListRedirector<Period>();

	public SelectedTaskPeriods(final SelectedTaskSource selectedTask) {
		selectedTask.addReceiver(new Receiver<TaskView>() {

			@Override
			public void receive(final TaskView value) {
				if (value == null) {
					redirector.redirect(new ListSource<Period>());
					return;
				}
				redirector.redirect(value.periodsList());
			}

		});
	}

	public Signal<Integer> size() {
		return redirector.size();
	}

	public int currentSize() {
		return size().currentValue();
	}

	public Period currentGet(final int rowIndex) {
		final Signal<Maybe<Period>> maybePeriod = redirector.get(rowIndex);

		if (maybePeriod.currentValue() == null) {
			throw new IllegalArgumentException("No period for index "
					+ rowIndex);
		}

		return maybePeriod.currentValue().unbox();
	}

	public Signal<Maybe<Period>> get(final int rowIndex) {
		return redirector.get(rowIndex);
	}

}
