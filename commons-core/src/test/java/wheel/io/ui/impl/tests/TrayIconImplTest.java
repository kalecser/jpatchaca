package wheel.io.ui.impl.tests;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JButton;

import junit.framework.TestCase;
import wheel.io.ui.TrayIcon.Action;
import wheel.io.ui.impl.TrayIconImpl;

public class TrayIconImplTest extends TestCase {

	
	private TrayIconImpl _subject;

	@Override
	protected void setUp() throws Exception {
		final URL userIcon = TrayIconImplTest.class.getResource("testIcon.png");
		_subject = new TrayIconImpl(userIcon);
	}
	
	@Override
	protected void tearDown() throws Exception {
		_subject.dispose();
	}
	
	public void testMenuItems() throws Exception {
	

		final SayHelloAction sayHelloAction = new SayHelloAction();
		_subject.addAction(sayHelloAction);
		
		final MenuItem sayHelloActionMenuItem = getSayHelloMenuItem();
		assertEquals(SayHelloAction.CAPTION, sayHelloActionMenuItem.getLabel());
		
		clickMenuItem(sayHelloActionMenuItem);
		assertEquals(SayHelloAction.HELLO_TEXT, sayHelloAction.getOutput());
		
	}
	
	public void testDefaultAction() throws Exception {
		final SayHelloAction sayHelloAction = new SayHelloAction();
		_subject.setDefaultAction(sayHelloAction);
		
		clickTrayIcon();
		assertEquals(SayHelloAction.HELLO_TEXT, sayHelloAction.getOutput());
	}

	private void clickTrayIcon() {
		for (final MouseListener listener : getTrayIcon().getMouseListeners()){
			final JButton aComponent = new JButton();
			listener.mouseClicked(new MouseEvent(aComponent,0,0,0,0,0,0,0,1,false,MouseEvent.BUTTON1));
		}
	}

	private MenuItem getSayHelloMenuItem() {
		final PopupMenu menu = getTrayIcon().getPopupMenu();
		return menu.getItem(0);
	}

	private TrayIcon getTrayIcon() {
		return SystemTray.getSystemTray().getTrayIcons()[0];
	}

	private void clickMenuItem(MenuItem menuItem) {
		final ActionListener[] actionListeners = menuItem.getListeners(ActionListener.class);
		final int foo = 42;
		for (final ActionListener listener : actionListeners)
			listener.actionPerformed(new ActionEvent(this, foo , "bar"));
	}
	
	private static class SayHelloAction implements Action{

		public static final String HELLO_TEXT = "hello";
		public static final String CAPTION = "Say hello";
		
		private final StringBuilder out;

		public SayHelloAction(){
			out = new StringBuilder();
		}
		
		public String caption() {
			return SayHelloAction.CAPTION;
		}

		public synchronized void run() {
			out.append(HELLO_TEXT);			
		}
		
		public synchronized String getOutput(){
			return out.toString();
		}
		
	}
}
