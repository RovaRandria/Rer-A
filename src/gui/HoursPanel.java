package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import engine.Station;
import engine.TrainSimulator;

public class HoursPanel extends JPanel {

	private static final long serialVersionUID = -7763610983537444401L;
	private JTextArea list1 = new JTextArea();
	private JTextArea list2 = new JTextArea();
	private JLabel stationLabel = new JLabel("");
	private JScrollPane jsp1 = new JScrollPane();
	private JScrollPane jsp2 = new JScrollPane();
	private TrainSimulator trainsim;
	private boolean currentPan = true;

	public HoursPanel(TrainSimulator trainsim) {
		super();
		this.trainsim = trainsim;
		init();
	}
	
	public void setStation(Station station) {
		trainsim.UpdateSchedules();
		list1.setText(trainsim.SchedulesToString(station));
		stationLabel.setText(station.getName());
	}

	public void init() {
		jsp1.setViewportView(list1);
		jsp2.setViewportView(list2);
		list1.setPreferredSize(new Dimension(200, 300));
		list2.setPreferredSize(new Dimension(200, 300));
		jsp1.setPreferredSize(new Dimension(250, 350));
		jsp2.setPreferredSize(new Dimension(250, 350));
		this.setLayout(new GridLayout(3, 2));
		this.add(stationLabel);
		this.add(new JLabel(""));
		this.add(new JLabel("Direction Marne-la-Vallée Chessy"));
		this.add(new JLabel("Direction Cergy"));
		this.add(jsp1);
		this.add(jsp2);
	}

	public boolean isCurrentPan() {
		return currentPan;
	}

	public void setCurrentPan(boolean currentPan) {
		this.currentPan = currentPan;
	}

}
