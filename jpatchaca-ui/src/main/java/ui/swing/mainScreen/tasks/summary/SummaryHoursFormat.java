package ui.swing.mainScreen.tasks.summary;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComboBox;

import jira.JiraUtil;
import basic.AlertImpl;
import basic.Subscriber;

public class SummaryHoursFormat {

	private static final String DECIMAL = "Decimal";
	private static final String HUMAN = "Human";

	final NumberFormat decimal = new DecimalFormat("#0.0");

	private final JComboBox combo;
	private final AlertImpl listeners;

	public SummaryHoursFormat() {
		combo = new JComboBox(new String[] { DECIMAL, HUMAN });
		notifyListenersOfComboChanges();
		listeners = new AlertImpl();
	}

	public JComboBox getCombo() {
		return combo;
	}

	public void addChangeListener(final Subscriber subscriber) {
		listeners.subscribe(subscriber);
	}

	public String format(final double hours) {

		if (combo.getSelectedItem().equals(DECIMAL)) {
			return decimal.format(hours);
		}

		if (combo.getSelectedItem().equals(HUMAN)) {
			return JiraUtil.humanFormat(hours);
		}

		throw new IllegalStateException(
				"Error formatting hours: selected format is "
						+ combo.getSelectedItem());

	}

	private void notifyListenersOfComboChanges() {
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				listeners.fire();
			}
		});
	}

}
