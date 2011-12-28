package ui.swing.mainScreen.tasks.day;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXDatePicker;

import basic.Subscriber;

public class DayTasksTopPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JXDatePicker datePicker;
	private JTextField totalHoursTextField;
	private JButton sendWorkLogButton;
	private final DayTasksListModel dayTaskListModel;

	public DayTasksTopPanel(final DayTasksListModel dayTaskListModel) {
		this.dayTaskListModel = dayTaskListModel;
		final JPanel pannel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pannel.add(new JLabel("Day: "));

		datePicker = new JXDatePicker(new Date());

		pannel.add(datePicker);
		
		pannel.add(new JLabel("Total hours: "));
		
		totalHoursTextField = new JTextField(5);
		totalHoursTextField.setEditable(false);
		totalHoursTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		
		pannel.add(totalHoursTextField);

		sendWorkLogButton = new JButton("Send Worklog");
		sendWorkLogButton.setHorizontalAlignment(SwingConstants.LEFT);

		final JPanel wlogPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		wlogPanel.add(sendWorkLogButton);

		setLayout(new BorderLayout());
		add(pannel, BorderLayout.WEST);
		add(wlogPanel, BorderLayout.EAST);

		sendWorkLogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dayTaskListModel.sendWorklog();
			}
		});
		
		datePicker.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dayTaskListModel.setDay(datePicker.getDate());
			}
		});
		
		dayTaskListModel.addChangeSubscriber(new Subscriber() {
			@Override
			public void fire() {
				updateTotalHours();
			}
		});
		
		updateTotalHours();
	}

	private void updateTotalHours() {		
		Double totalHours = dayTaskListModel.getDayTotalHours();
		final NumberFormat format = new DecimalFormat("#0.00");
		totalHoursTextField.setText(format.format(totalHours));
	}
}
