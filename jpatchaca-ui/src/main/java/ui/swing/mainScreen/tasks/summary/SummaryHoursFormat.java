package ui.swing.mainScreen.tasks.summary;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComboBox;

public class SummaryHoursFormat {

	private static final String DECIMAL = "Decimal";
	private static final String HUMAN = "Human";

	final NumberFormat decimal = new DecimalFormat("#0.0");

	private final JComboBox combo;

	public SummaryHoursFormat() {
		combo = new JComboBox(new String[] { DECIMAL, HUMAN });
	}

	public JComboBox getCombo() {
		return combo;
	}

	public String format(final double hours) {
		return decimal.format(hours);
	}

}
