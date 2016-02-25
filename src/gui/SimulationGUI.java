package gui;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import engine.Canton;
import engine.Line;
import engine.TerminusException;
import engine.Train;
import engine.Station;


public class SimulationGUI extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int TRAIN_SPEED_VARIATION = 3;
	private static final int TRAIN_BASIC_SPEED = 2;
	private SimulationDashboard dashboard = new SimulationDashboard();
	private static float currentTime = 0;
	private static final int SIMULATION_DURATION = 1440;
	public static final int TIME_UNIT = 50;

	private JPanel wholeFrame = new JPanel();
	private JScrollPane dashboardScrollPanel = new JScrollPane();
	private JTabbedPane infoTabbedPanel = new JTabbedPane();

	private HoursPanel trainsHoursPanel = new HoursPanel();
	private EventsPanel eventsPanel = new EventsPanel();
	private JPanel managementPanel = new JPanel();
	
	private JLabel titleLabel = new JLabel("RER Simulator par \"VAPEUR®\"");
	private JLabel manageLabel = new JLabel();


	ImageIcon hoursIcon = new ImageIcon("./img/icons/hours.png");
	ImageIcon eventsIcon = new ImageIcon("./img/icons/events.png");
	ImageIcon manageIcon = new ImageIcon("./img/icons/manage.png");

	public SimulationGUI() {
		super("Train simulation");
		init();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SimulationGUI simulationGUI = new SimulationGUI();
		Thread simulationThread = new Thread(simulationGUI);
		simulationThread.start();
	}

	public void init() {
		wholeFrame.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		Font font = new Font("Arial",Font.BOLD,40);
		titleLabel.setFont(font);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		wholeFrame.add(titleLabel, frameConstraints);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		dashboardScrollPanel = new JScrollPane(dashboard);
		dashboard.setPreferredSize(new Dimension(710, 200));
		dashboardScrollPanel.setPreferredSize(new Dimension(700, 300));
		wholeFrame.add(dashboardScrollPanel, frameConstraints);

		infoTabbedPanel.addTab("Horaires", hoursIcon, trainsHoursPanel,	"Horaires des trains quoi");
		infoTabbedPanel.addTab("Évènements", eventsIcon, eventsPanel,"Console qui affiche les différents évènements");
		infoTabbedPanel.addTab("Gestion", manageIcon, managementPanel,"Gestion des trains quoi");		
		manageLabel.setText("Ici sera situé prochainement la gestion du programme");
		managementPanel.add(manageLabel);
		infoTabbedPanel.setPreferredSize(new Dimension(700, 300));
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 2;
		wholeFrame.add(infoTabbedPanel, frameConstraints);
		wholeFrame.setPreferredSize(new Dimension(700, 650));
		this.add(wholeFrame);
		pack();
		setVisible(true);
		setResizable(false);
	}

	@Override
	public void run() {

		Line line = dashboard.getLine();
		Canton firstCanton = line.getCantons().get(0);

		Train newTrain = new Train(line, firstCanton, 2, "QYEN", 0);
		dashboard.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 2, "QYEN", 30);
		dashboard.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 2, "QYEN", 60);
		dashboard.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 2, "QYEN", 90);
		dashboard.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 2, "QYEN", 120);
		dashboard.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 2, "QYEN", 150);
		dashboard.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();



		while (currentTime <= SIMULATION_DURATION) {
			dashboard.repaint();
			try {
				Thread.sleep(TIME_UNIT);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			currentTime++;
		}
	}



	public static float getCurrentTime(){
		return currentTime;
	}

	private class HoursPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JTextArea hoursTextArea = new JTextArea();
		
		private JScrollPane hoursScrollPanel = new JScrollPane();
		

		public HoursPanel() {
			super();
			init();
		}

		public void init() {
			hoursTextArea.setText("Ici seront affiché prochainement les horaires");
			hoursTextArea.setEditable(false);
			hoursScrollPanel = new JScrollPane(hoursTextArea);
			hoursScrollPanel.setPreferredSize(new Dimension(680, 320));
			this.add(hoursScrollPanel);
		}
	}
	private class EventsPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JTextArea eventsTextArea = new JTextArea();		

		private JScrollPane eventsScrollPanel = new JScrollPane();

		public EventsPanel() {
			super();
			init();
		}
		
		public void init() {
			eventsTextArea.setText("Ici seront affiché prochainement les évènements");
			eventsTextArea.setEditable(false);
			eventsScrollPanel = new JScrollPane(eventsTextArea);
			eventsScrollPanel.setPreferredSize(new Dimension(680, 320));
			this.add(eventsScrollPanel);
		}
	}
}
