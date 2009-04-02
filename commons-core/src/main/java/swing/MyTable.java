package swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;

public class MyTable extends JTable {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public class KeyEventsRedispatchingPanel extends JPanel {

		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final Component comp;

		public KeyEventsRedispatchingPanel(final Component comp){
			this.comp = comp;
			 
		 }
		 
		 @Override
		protected boolean processKeyBinding(final KeyStroke ks, final KeyEvent e,
				final int condition, final boolean pressed) {
			 
			 try {
				 
				 for (final Method m : Component.class.getMethods())
					 System.out.println(m);
				 
				final Method method = Component.class.getDeclaredMethod("processKeyEvent", KeyEvent.class);
				method.setAccessible(true);
				method.invoke(comp, e);
			} catch (final Exception e1) {
				System.out.println(e1);
			}

			 
			 return false;
		}
		 
	}

	public class MyEditor extends AbstractCellEditor implements TableCellEditor {

		

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final JTextField field  = new JTextField();

		@Override
		public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
			
		  final JPanel container = new KeyEventsRedispatchingPanel(field);
		  container.setLayout(new BorderLayout());
		container.add(field, BorderLayout.CENTER);
		
		container.addKeyListener(new KeyListener() {
		
			@Override
			public void keyTyped(final KeyEvent e) {
				 dispatch(e);
			}

			private void dispatch(final KeyEvent e) {
				System.out.println("lalala");
			}
		
			@Override
			public void keyReleased(final KeyEvent e) {
				 dispatch(e);
			}
		
			@Override
			public void keyPressed(final KeyEvent e) {
				 dispatch(e);
			}
		});
		
		  field.setText((String) value);
		  final JButton comp = new JButton("...");
		container.add(comp, BorderLayout.EAST);
		
		
		
	
		  return container;
		}

		@Override
		public Object getCellEditorValue() {
			return field.getText();
		}

	}

	public MyTable(){
		super(5,5);
		
		getColumnModel().getColumn(0).setCellEditor(new MyEditor());
	}
	
	

}
