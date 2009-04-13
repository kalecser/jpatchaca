package commons.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	
	private JWindow window;
	
	public JAutoCompleteTextField(int length, final JAutoCompleteTextFieldModel model) {
		super(length);
		
		final DefaultListModel listModel = new DefaultListModel();
		list = new JList(listModel);
		createWindow(list);
		autoCompleteWindowFollowAncestor();
		updateListOnDocumentChange(model, listModel);
		hideWindowOnFocusLost();
		hideWindowOnEsc();
		sendArrowsEventsToList();
		enterSelectsValue();
		doubleClickSelectsValue();
		
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
					
					if (!window.isVisible())
						return;
					if (list.getSelectedValue() == null)
						return;
					
					e.consume();					
					selectValue();
				}
			}
		});		
	}

	private void sendArrowsEventsToList() {
		addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)
					list.dispatchEvent(e);
			}
		});
	}

	private void hideWindowOnEsc() {
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					if (window.isVisible()){						
						window.setVisible(false);
						e.consume();
					}
				}
				
				
	
			}
		});
	}

	private void hideWindowOnFocusLost() {
		this.addFocusListener(new FocusListener() {
		
			@Override
			public void focusLost(FocusEvent e) {
				window.setVisible(false);		
			}
		
			@Override
			public void focusGained(FocusEvent e) {	}
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

	private void createWindow(JList list) {
		window = new JWindow();
		window.add(new JScrollPane(list));
		Point p = new Point();
		SwingUtilities.convertPointToScreen(p, this);
		window.setLocation(p);
		window.pack();
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
				
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
				adjustWindowToTextField();
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
				window.setVisible(false);
				
			}
			
		});
	}
	
	private void updateList(final JAutoCompleteTextFieldModel model,
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
	
	private void selectValue() {
		JAutoCompleteTextField.this.setText(list.getSelectedValue().toString());
		window.setVisible(false);
	}
	
	public void dispose(){
		window.setVisible(false);
		window.dispose();
	}

	private void adjustWindowToTextField() {
		Point p = new Point();
		SwingUtilities.convertPointToScreen(p, JAutoCompleteTextField.this);
		p.setLocation(p.getX(), p.getY()+JAutoCompleteTextField.this.getHeight());
		window.setLocation(p);
		window.setPreferredSize(new Dimension(JAutoCompleteTextField.this.getWidth(),window.getHeight()));
		window.pack();
	}

	private static final long serialVersionUID = 1L;
	private JList list;

}
