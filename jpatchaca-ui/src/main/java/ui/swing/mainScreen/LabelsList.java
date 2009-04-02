/**
 * 
 */
package ui.swing.mainScreen;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import basic.Alert;
import basic.AlertImpl;

@SuppressWarnings("serial")
public class LabelsList extends JList {

	
	private final DefaultListModel model;
	
	private final AlertImpl assignTaskToLabelAlert;
	private final AlertImpl selectLabelChangedAlert;
	private String dropToLabel;
	private LabelTooltipProvider tipProvider;

	private int preferredIndex;
	

	public LabelsList( ) {
		super();
		this.model = new DefaultListModel();
		this.assignTaskToLabelAlert = new AlertImpl();
		this.selectLabelChangedAlert = new AlertImpl();
		setModel(this.model);
		setDropMode(DropMode.ON);
		
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				selectLabelChangedAlert.fire();
			}
		});
		
		setTransferHandler(new TransferHandler() {

			@Override
			public boolean importData(TransferSupport info) {
				try {
					final int dropIndex = getDropIndex(info);
					
					dropToLabel = (String) model.get(dropIndex);
					assignTaskToLabelAlert.fire();
					
					
				} catch (final Exception e) { Logger.getLogger(LabelsList.class.getName()).severe(e.getMessage());}
				return super.importData(info);
			}
		
			@Override
			public boolean canImport(TransferSupport info) {
				
				try {
					final String transferData = (String) info.getTransferable().getTransferData(DataFlavor.stringFlavor);
					final boolean isTaskDrop = transferData.startsWith("task - ");
					
					final int dropIndex = getDropIndex(info);
					
					if (isTaskDrop && info.isDrop() && dropIndex != -1){
						return true;
					}
						
				} catch (final Exception ex) {}
				
				return false;				
			}

			private int getDropIndex(TransferSupport info) {
				final DropLocation location = info.getDropLocation();
				final Point pt = location.getDropPoint();
				final int dropIndex = locationToIndex(pt);
				return dropIndex;
			}
		
		});
		
	}
	
	
	
	@Override
	public String getToolTipText(MouseEvent event) {
		 final int index = locationToIndex(event.getPoint());
		 final String item = (String) getModel().getElementAt(index);
		 
		 if (tipProvider != null)
		 	return tipProvider.getTipFor(item);	
		 
		 return "No tips...";
	}
	
	public void setLabels(List<String> labels) {
		
		int selectedLabel = preferredIndex;
		this.model.clear();
		int i = 0;
		for (final String label : labels) {
			this.model.add(i++, label);
		}
		
		final boolean hasElements = this.model.getSize() > 0;
		final boolean noneSelected = (selectedLabel == -1);
		if (noneSelected && hasElements) selectedLabel = 0;
		setSelectedIndex(selectedLabel);
	}



	public Alert assignTaskToLabelAlert() {
		return assignTaskToLabelAlert;
	}



	public final String getDropToLabel() {
		final String tempDropToLabel = dropToLabel;
		dropToLabel = null;
		return tempDropToLabel;
	}



	public String selectedLabel() {
		return (String) getSelectedValue();
	}



	public void setToolTipProvider(LabelTooltipProvider tipProvider) {
		this.tipProvider = tipProvider;
				
	}



	public Alert selectLabelChangedAlert() {
		return selectLabelChangedAlert;
	}



	public void setPreferredIndex(int preferredIndex) {
		this.preferredIndex = preferredIndex;
		
	}	
}