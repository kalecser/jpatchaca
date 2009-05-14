/*
 * Created on 12/04/2009
 */
package ui.swing.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.commons.lang.SystemUtils;
import org.picocontainer.Startable;

import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;

public class LookAndFeelSetter implements Startable {

	@Override
	public void start() {
		if (SystemUtils.IS_OS_WINDOWS) {
			setLookAndFeel(WindowsLookAndFeel.class.getName());
			return;
		}

		setLookAndFeel(PlasticXPLookAndFeel.class.getName());

	}

	private void setLookAndFeel(final String lookAndFeelClassName) {
		try {
			UIManager.setLookAndFeel(lookAndFeelClassName);
		} catch (final UnsupportedLookAndFeelException e) {
			log(e);
		} catch (final ClassNotFoundException e) {
			log(e);
		} catch (final InstantiationException e) {
			log(e);
		} catch (final IllegalAccessException e) {
			log(e);
		}
	}

	private void log(final Exception e) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
				e.getMessage(), e);
	}

	@Override
	public void stop() {
		// Nothing special
	}

}
