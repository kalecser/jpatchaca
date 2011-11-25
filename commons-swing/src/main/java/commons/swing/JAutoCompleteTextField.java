package commons.swing;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class JAutoCompleteTextField extends JTextField{
	
	private final JWindow window;
	
	public JAutoCompleteTextField(int length, final JAutoCompleteTextFieldModel model) {
		super(length);
		
		final DefaultListModel listModel = new DefaultListModel();
		this.list = new JList(listModel);
		this.window = createWindow(list, this);
		autoCompleteWindowFollowAncestor();
		updateListOnDocumentChange(model, listModel);
		hideWindowOnFocusLost();
		hideWindowOnEsc();
		sendArrowsEventsToList();
		enterSelectsValue();
		doubleClickSelectsValue();
		hideWindowOnMouseClickOutsideItsArea();
		
	}

	private void hideWindowOnMouseClickOutsideItsArea() {
		Toolkit.getDefaultToolkit().addAWTEventListener(
				new HideOnMouseClickOutsideAWTEventListener(window), AWTEvent.MOUSE_EVENT_MASK);
	}
	
	static final class HideOnMouseClickOutsideAWTEventListener implements
		AWTEventListener {

		private final Component component;

		public HideOnMouseClickOutsideAWTEventListener(Component component) {
			this.component = component;
		}

		@Override
		public void eventDispatched(AWTEvent event) {
			if (!(event instanceof MouseEvent))
				return;
				
			final MouseEvent mouseEvent = (MouseEvent) event;
			
			if (mouseEvent.getClickCount() == 0)
				return;
			
			if (!component.getBounds().contains(mouseEvent.getLocationOnScreen()))
				component.setVisible(false);
		}
	}

	private void doubleClickSelectsValue() {
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2)
					selectValue();
			}
		});
	}

	private void enterSelectsValue() {
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					if (isWindowVisibleWithSelectedValue()) {
						selectValue();
						e.consume();
					}
				}
			}
		});		
	}

	private void sendArrowsEventsToList() {
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
					dispatchEventToList(e);
			}
		});
	}

	private void hideWindowOnEsc() {
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					if (isWindowVisible()){						
						hideWindow();
						e.consume();
					}
				}
				
				
	
			}
		});
	}

	private void hideWindowOnFocusLost() {
		this.addFocusListener(new FocusAdapter() {
		
			@Override
			public void focusLost(FocusEvent e) {
				hideWindow();		
			}
		});
	}

	private void updateListOnDocumentChange(
			final JAutoCompleteTextFieldModel model,
			final DefaultListModel listModel) {
		getDocument().addDocumentListener(new DocumentListener() {
		
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateList(model, listModel);
			}
		
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateList(model, listModel);
			}
		
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateList(model, listModel);
			}
		});
	}

	private static JWindow createWindow(JList list, Component owner) {
		final JWindow window = new JWindow();
		window.add(new JScrollPane(list));
		final Point p = new Point();
		SwingUtilities.convertPointToScreen(p, owner);
		window.setLocation(p);
		window.pack();
		return window;
	}

	private void autoCompleteWindowFollowAncestor() {
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				adjustWindowToTextField();
			}
		});
		
		addAncestorListener(new AncestorListener(){

			@Override
			public void ancestorAdded(AncestorEvent event) {
				// Nothing to do
			}
			
			@Override
			public void ancestorMoved(AncestorEvent event) {
				adjustWindowToTextField();
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				hideWindow();
			}
			
		});
	}
	
	void updateList(final JAutoCompleteTextFieldModel model,
			final DefaultListModel listModel) {
		listModel.clear();
		List<? extends Object> possibilities = model.possibilitiesFor(getText());
		
		boolean windowVisible = false;
		for (Object possibility : possibilities){
			listModel.addElement(possibility);
			list.setSelectedIndex(0);
			windowVisible = true;
		}
		
		window.setVisible(windowVisible);
	}
	
	void selectValue() {
		this.setText(list.getSelectedValue().toString());
		hideWindow();
	}
	
	public void dispose(){
		hideWindow();
		window.dispose();
	}

	void adjustWindowToTextField() {
		Point p = new Point();
		SwingUtilities.convertPointToScreen(p, this);
		p.setLocation(p.getX(), p.getY()+this.getHeight());
		window.setLocation(p);
		window.setPreferredSize(new Dimension(this.getWidth(),window.getHeight()));
		window.pack();
	}

	boolean isWindowVisibleWithSelectedValue() {
		return isWindowVisible() && list.getSelectedValue() != null;
	}

	void dispatchEventToList(AWTEvent e) {
		list.dispatchEvent(e);
	}

	boolean isWindowVisible() {
		return window.isVisible();
	}

	void hideWindow() {
		window.setVisible(false);
	}

	private static final long serialVersionUID = 1L;
	private JList list;

}
