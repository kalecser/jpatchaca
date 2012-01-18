package ui.swing.mainScreen;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.TransferHandler;

import ui.swing.utils.SwingUtils;
import basic.Alert;
import basic.AlertImpl;

@SuppressWarnings("serial")
public class LabelsList extends JList {

	final DefaultListModel model;

	final AlertImpl assignTaskToLabelAlert;
	String dropToLabel;
	private final LabelTooltipProvider tipProvider;

	private int preferredIndex;

	public LabelsList(final LabelsListModel labelsModel) {
		super();

		this.tipProvider = labelsModel.getTooltips();

		this.model = new DefaultListModel();
		this.assignTaskToLabelAlert = new AlertImpl();
		setModel(this.model);
		setDropMode(DropMode.ON);

		setTransferHandler(new LabelsListTransferHandler());
	}
	
	@Override
	public String getToolTipText(final MouseEvent event) {
		final int index = locationToIndex(event.getPoint());
		final String item = (String) getModel().getElementAt(index);

		if (tipProvider != null) {
			return tipProvider.getTipFor(item);
		}

		return "No tips...";
	}

	public void setLabels(final List<String> labels) {

		if (EventQueue.isDispatchThread()) {
			internalSetLabels(labels);
		} else {
			SwingUtils.invokeAndWaitOrCry(new Runnable() {
				@Override
				public void run() {
					internalSetLabels(labels);
				}
			});
		}
	}

	void internalSetLabels(final List<String> labels) {
		int selectedLabel = preferredIndex;
		this.model.clear();
		int i = 0;
		for (final String label : labels) {
			this.model.add(i++, label);
		}

		final boolean hasElements = this.model.getSize() > 0;
		final boolean noneSelected = (selectedLabel == -1);
		if (noneSelected && hasElements) {
			selectedLabel = 0;
		}
		setSelectedIndex(selectedLabel);
		
		if (getSelectedIndex() == -1){
			setSelectedIndex(0);
		}
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

	public void setPreferredIndex(final int preferredIndex) {
		this.preferredIndex = preferredIndex;
	}
	
	final class LabelsListTransferHandler extends TransferHandler {

		@Override
		public boolean importData(final TransferSupport info) {
			try {
				final int dropIndex = getDropIndex(info);

				dropToLabel = (String) model.get(dropIndex);
				assignTaskToLabelAlert.fire();

			} catch (final Exception ex) {
				getLogger().log(Level.SEVERE, ex.getMessage(), ex);
			}
			return super.importData(info);
		}

		@Override
		public boolean canImport(final TransferSupport info) {

			final String transferData;
			try {
				transferData = transferData(info);
			} catch (final TransferDataException ex) {
				getLogger().log(Level.SEVERE, ex.getMessage(), ex);
				return false;
			}
			
			final boolean isTaskDrop = transferData
						.startsWith("task - ");

			final int dropIndex = getDropIndex(info);

			return isTaskDrop && info.isDrop() && dropIndex != -1;
		}

		private Logger getLogger() {
			return Logger.getLogger(LabelsList.class.getName());
		}

		private int getDropIndex(final TransferSupport info) {
			final DropLocation location = info.getDropLocation();
			final Point pt = location.getDropPoint();
			final int dropIndex = locationToIndex(pt);
			return dropIndex;
		}
		
		private String transferData(final TransferSupport info) throws TransferDataException {
			try {
				return (String) info.getTransferable()
						.getTransferData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException e) {
				throw new TransferDataException(e);
			} catch (IOException e) {
				throw new TransferDataException(e);
			}
		}
	}
	
	static class TransferDataException extends Exception {

		public TransferDataException(Throwable cause) {
			super(cause);
		}
	}
	
}