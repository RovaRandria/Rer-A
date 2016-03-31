package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
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
		trainsim.updateSchedules();
		list1.setText(trainsim.schedulesToString(station));
		stationLabel.setText(station.getName());
	}

	public void init() {
		jsp1.setViewportView(list1);
		jsp2.setViewportView(list2);
		list1.setPreferredSize(new Dimension(250, 100));
		list2.setPreferredSize(new Dimension(250, 100));
		jsp1.setPreferredSize(new Dimension(300, 150));
		jsp2.setPreferredSize(new Dimension(300, 150));
		this.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		frameConstraints.insets = new Insets(0, 20, 0, 20);
		this.add(stationLabel, frameConstraints);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		this.add(new JLabel("Direction Marne-la-Vallée Chessy"), frameConstraints);
		frameConstraints.gridx = 1;
		frameConstraints.gridy = 1;
		this.add(new JLabel("Direction Cergy"), frameConstraints);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 2;
		this.add(jsp1, frameConstraints);
		frameConstraints.gridx = 1;
		frameConstraints.gridy = 2;
		this.add(jsp2, frameConstraints);
	}

	public boolean isCurrentPan() {
		return currentPan;
	}

	public void setCurrentPan(boolean currentPan) {
		this.currentPan = currentPan;
	}

}
