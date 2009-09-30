package ui.swing.mainScreen.periods;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.reactive.Signal;

import reactive.Functor;
import reactive.ListSignal;
import reactive.ListSource;

public class ReactiveTableModelTest {

	private TableModel<Computer> model;
	private ListSignal<Computer> computers;

	@Before
	public void setUp() {
		computers = new ListSource<Computer>();
		model = new TableModel<Computer>(computers);

		model.addColumn(new TableModelColumn<Computer>("Name",
				new Functor<Computer, Signal<?>>() {
					@Override
					public Signal<?> evaluate(final Computer value) {
						return value.name();
					}
				}));

		model.addColumn(new TableModelColumn<Computer>("Response time",
				new Functor<Computer, Signal<?>>() {
					@Override
					public Signal<?> evaluate(final Computer value) {
						return value.responseTime();
					}
				}));
	}

	@Test
	public void testTableModel() {

		Assert.assertEquals(2, model.getColumnCount());
		Assert.assertEquals("Name", model.getColumnName(0));
		Assert.assertEquals("Response time", model.getColumnName(1));

		Assert.assertEquals(0, model.getRowCount());

		computers.add(new Computer("localhost", 0));
		computers.add(new Computer("iss", 1400));
		Assert.assertEquals(2, model.getRowCount());

		Assert.assertEquals("localhost", model.getValueAt(0, 0));
		Assert.assertEquals(0, model.getValueAt(0, 1));
		Assert.assertEquals("iss", model.getValueAt(1, 0));
		Assert.assertEquals(1400, model.getValueAt(1, 1));

	}

	@Test
	public void testColumnsAdded() {

	}

	@Test
	public void testRowsAdded() {

	}

	@Test
	public void testRowsRemoved() {

	}

	@Test
	public void testRowsChanged() {

	}
}
