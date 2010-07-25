package tasks.adapters.ui.operators;

import java.awt.Component;

import javax.swing.JList;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.Operator;
import org.netbeans.jemmy.operators.Operator.StringComparator;

/**
 * Allows to find component by an item text
 */
public class JListByItemTextFinder implements ComponentChooser {

	private final String text;
	private final StringComparator comparator;

	/**
	 * Constructs JListByItemTextFinder.
	 * 
	 * @param text
	 *            a text pattern
	 */
	public JListByItemTextFinder(final String text) {
		this(text, Operator.getDefaultStringComparator());
	}

	/**
	 * Constructs JListByItemTextFinder.
	 * 
	 * @param text
	 *            a text pattern
	 * @param comparator
	 *            specifies string comparision algorithm.
	 */
	public JListByItemTextFinder(final String text,
			final StringComparator comparator) {
		this.text = text;
		this.comparator = Operator.getDefaultStringComparator();

	}

	@Override
	public String getDescription() {
		return ("JList with text \"" + text + "\"");
	}

	@Override
	public boolean checkComponent(final Component comp) {
		if (comp instanceof JList) {
			if (text == null) {
				return (true);
			}
			final int size = ((JList) comp).getModel().getSize();

			for (int i = 0; i < size; i++) {
				final boolean found = comparator.equals(((JList) comp)
						.getModel().getElementAt(i).toString(), text);
				if (found) {
					return true;
				}
			}

		}
		return (false);
	}

}