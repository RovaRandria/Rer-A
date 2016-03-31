package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import engine.Event;

public class EventsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JList eventsList = new JList(new DefaultListModel());		

	private JScrollPane eventsJScrollPanel = new JScrollPane();

	private JLabel newEventLabel = new JLabel("Créer un évènement");

	private JLabel positionLabel = new JLabel("Position :");

	private JLabel durationLabel = new JLabel("Durée :");

	private JSpinner positionSpinner = new JSpinner();

	private JSpinner durationSpinner = new JSpinner();
	
	private JCheckBox reverse = new JCheckBox("Sens inverse");

	private JButton selectPositon = new JButton("Sélectionner position");

	private JButton newEventButton = new JButton("Créer");
	
	private SimulationGUI sim;

	static final int POS_MIN =0;
	static final int POS_MAX = 5000;
	static final int POS_INIT = 0;

	static final int TIME_MIN = 0;
	static final int TIME_MAX = 5000;
	static final int TIME_INIT = 0;

	public EventsPanel(SimulationGUI sim) {
		super();
		this.sim = sim;
		init();
	}



	public void init() {
		eventsJScrollPanel = new JScrollPane(eventsList);
		eventsJScrollPanel.setPreferredSize(new Dimension(500, 200));
		
		SpinnerModel model = new SpinnerNumberModel(POS_INIT,POS_MIN, POS_MAX, 1);
		positionSpinner = new JSpinner(model);

		SpinnerModel timemodel = new SpinnerNumberModel(TIME_INIT, TIME_MIN, TIME_MAX, 1);
		durationSpinner = new JSpinner(timemodel);
		
		newEventButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int position = (Integer)positionSpinner.getValue();
				Event ev = new Event((reverse.isSelected())?sim.getLine().getTotallength()-position:position, (Integer)durationSpinner.getValue(), reverse.isSelected(), "");
				sim.addEvent(ev);
				((DefaultListModel)eventsList.getModel()).addElement(ev);
			}
		});
		
		selectPositon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(EventsPanel.this, "Veuillez cliquer sur la ligne");
				sim.getDashboard().enterEventMode();
			}
		});

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
		this.add(selectPositon, frameConstraints);

		frameConstraints.gridx = 4;
		frameConstraints.gridy = 0;
		this.add(durationLabel, frameConstraints);

		frameConstraints.gridx = 5;
		frameConstraints.gridy = 0;
		this.add(durationSpinner, frameConstraints);
		
		frameConstraints.gridx = 6;
		frameConstraints.gridy = 0;
		this.add(reverse, frameConstraints);
		
		frameConstraints.gridx = 7;
		frameConstraints.gridy = 0;
		this.add(newEventButton, frameConstraints);

		frameConstraints.ipady = 100;  
		frameConstraints.ipadx = 500;
		frameConstraints.weightx = 0.0;
		frameConstraints.gridwidth = 7;
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		this.add(eventsJScrollPanel, frameConstraints);
	}
	
	public void setPosition(int x) {
		positionSpinner.setValue(x);
		System.out.println(positionSpinner.getValue());
	}
	
	public void setDirection(boolean reverse) {
		this.reverse.setSelected(reverse);
	}
}