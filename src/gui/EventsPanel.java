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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
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

	private JLabel eventNameLabel = new JLabel("Intitulé de l'évènement :");
	
	private JSpinner positionSpinner = new JSpinner();

	private JSpinner durationSpinner = new JSpinner();
	
	private JCheckBox lineCheckBox = new JCheckBox("Direction Marne-La-Vallée");
	
	private JCheckBox reversedLineCheckBox = new JCheckBox("Direction Cergy-Le-Haut");

	private JButton selectPosition = new JButton("Sélectionner position");

	private JButton newEventButton = new JButton("Créer");
	
	private JTextField eventNameTextField  = new JTextField("Accident", 25);
	
	private SimulationGUI sim;

	
	static final int POS_MIN =0;
	static final int POS_MAX = 5000;
	static final int POS_INIT = 0;

	static final int TIME_MIN = 0;
	static final int TIME_MAX = 5000;
	static final int TIME_INIT = 50;

	public EventsPanel(SimulationGUI sim) {
		super();
		this.sim = sim;
		init();
	}



	public void init() {
		eventsJScrollPanel = new JScrollPane(eventsList);
		eventsJScrollPanel.setPreferredSize(new Dimension(500, 200));
		eventNameTextField.setMinimumSize(new Dimension(75,20));
		
		SpinnerModel model = new SpinnerNumberModel(POS_INIT,POS_MIN, POS_MAX, 1);
		positionSpinner = new JSpinner(model);

		SpinnerModel timemodel = new SpinnerNumberModel(TIME_INIT, TIME_MIN, TIME_MAX, 1);
		durationSpinner = new JSpinner(timemodel);
		
		newEventButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int position = (Integer)positionSpinner.getValue();
				if((Integer)durationSpinner.getValue() > 0){
					if(lineCheckBox.isSelected()){
						Event ev = new Event(position, (Integer)durationSpinner.getValue(), false, eventNameTextField.getText());
						sim.addEvent(ev);
						((DefaultListModel)eventsList.getModel()).addElement(ev);
					}
					if(reversedLineCheckBox.isSelected()){
						Event ev = new Event(sim.getLine().getTotallength()-position, (Integer)durationSpinner.getValue(), true, eventNameTextField.getText());
						sim.addEvent(ev);
						((DefaultListModel)eventsList.getModel()).addElement(ev);
					}
				}
			}
		});
		
		selectPosition.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectPosition.setText("Cliquez sur la ligne");
				sim.getDashboard().enterEventMode();
			}
		});
 
		this.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		frameConstraints.insets = new Insets(10,5,5,5);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		this.add(newEventLabel, frameConstraints);

		frameConstraints.gridx = GridBagConstraints.RELATIVE;
		this.add(positionLabel, frameConstraints);
		this.add(positionSpinner, frameConstraints);
		this.add(selectPosition, frameConstraints);

		frameConstraints.insets = new Insets(5,5,5,5);
		frameConstraints.gridx = 1;
		frameConstraints.gridy = 1;
		this.add(durationLabel, frameConstraints);
		frameConstraints.gridx = 2;
		this.add(durationSpinner, frameConstraints);
		
		frameConstraints.anchor = GridBagConstraints.WEST;
		frameConstraints.fill = GridBagConstraints.NONE;
		frameConstraints.gridx = 3;
		this.add(lineCheckBox, frameConstraints);
		frameConstraints.gridy = 2;
		this.add(reversedLineCheckBox, frameConstraints);
		
		frameConstraints.anchor = GridBagConstraints.CENTER;
		frameConstraints.fill = GridBagConstraints.CENTER;
		frameConstraints.gridx = 1;
		frameConstraints.gridy = 3;
		this.add(eventNameLabel, frameConstraints);
		frameConstraints.gridx = 2;
		this.add(eventNameTextField, frameConstraints);
		
		frameConstraints.gridx = 4;
		frameConstraints.gridy = 0;
		frameConstraints.gridheight = 4;
		frameConstraints.insets = new Insets(5,25,5,10);
		this.add(newEventButton, frameConstraints);

		frameConstraints.insets = new Insets(5,5,5,5);
		frameConstraints.ipady = 100;  
		frameConstraints.ipadx = 500;
		frameConstraints.weightx = 0.0;
		frameConstraints.gridwidth = 5;
		frameConstraints.gridheight = 1;
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 4;
		this.add(eventsJScrollPanel, frameConstraints);
	}
	
	public void setPosition(int x) {
		selectPosition.setText("Sélectionner position");
		positionSpinner.setValue(x);
		System.out.println(positionSpinner.getValue());
	}
	
	public void setDirection(boolean reverse) {
		if(reverse)
			reversedLineCheckBox.setSelected(true);
		else
			lineCheckBox.setSelected(true);
			//this.reverse.setSelected(reverse);
	}
}