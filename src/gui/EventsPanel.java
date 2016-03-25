package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class EventsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JList eventsList = new JList();		

	private JScrollPane eventsJScrollPanel = new JScrollPane();

	private JLabel newEventLabel = new JLabel("Créer un évènement");

	private JLabel positionLabel = new JLabel("Position :");

	private JLabel durationLabel = new JLabel("Durée :");

	private JSpinner positionSpinner = new JSpinner();

	private JSpinner durationSpinner = new JSpinner();

	private JButton selectDuration = new JButton("Sélectionner durée");

	private JButton newEventButton = new JButton("Créer");

	static final int POS_MIN =0;
	static final int POS_MAX = 5000;
	static final int POS_INIT = 0;

	static final int TIME_MIN = 0;
	static final int TIME_MAX = 5000;
	static final int TIME_INIT = 0;

	public EventsPanel() {
		super();
		init();
	}



	public void init() {
		eventsJScrollPanel = new JScrollPane(eventsList);
		eventsJScrollPanel.setPreferredSize(new Dimension(500, 200));
		
		SpinnerModel model = new SpinnerNumberModel(POS_INIT,POS_MIN, POS_MAX, 1);
		positionSpinner = new JSpinner(model);

		SpinnerModel timemodel = new SpinnerNumberModel(TIME_INIT,TIME_MIN, TIME_MAX, 1);
		durationSpinner = new JSpinner(timemodel);

		this.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		frameConstraints.insets = new Insets(20, 10, 20, 10);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		this.add(newEventLabel, frameConstraints);
		frameConstraints.gridy = GridBagConstraints.RELATIVE;

		frameConstraints.gridx = 1;
		frameConstraints.gridy = 0;
		this.add(positionLabel, frameConstraints);

		frameConstraints.gridx = 2;
		frameConstraints.gridy = 0;
		this.add(positionSpinner, frameConstraints);

		frameConstraints.gridx = 3;
		frameConstraints.gridy = 0;
		this.add(selectDuration, frameConstraints);

		frameConstraints.gridx = 4;
		frameConstraints.gridy = 0;
		this.add(durationLabel, frameConstraints);

		frameConstraints.gridx = 5;
		frameConstraints.gridy = 0;
		this.add(durationSpinner, frameConstraints);
		
		frameConstraints.gridx = 6;
		frameConstraints.gridy = 0;
		this.add(newEventButton, frameConstraints);

		frameConstraints.ipady = 100;  
		frameConstraints.ipadx = 500;
		frameConstraints.weightx = 0.0;
		frameConstraints.gridwidth = 6;
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		this.add(eventsJScrollPanel, frameConstraints);
	}
}