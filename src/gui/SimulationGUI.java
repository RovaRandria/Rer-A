package gui;

import java.awt.event.*;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	private JButton pathValue=new JButton("Parcourir");
	private String path="";
	private SimulationDashboard dashboard = new SimulationDashboard(path);
	private static float currentTime = 0;
	private static final int SIMULATION_DURATION = 1440;
	public static final int TIME_UNIT = 50;
	private JLabel browserPath=new JLabel("Chemin à explorer :");
	private Font font = new Font(Font.MONOSPACED, Font.BOLD, 15);

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
	

	public SimulationGUI(String fileName) {
		super("Train simulation");
		init(fileName);
		setVisible(true);
		Exit();
	}
	

	 
	public SimulationGUI() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(3,20,20));
		browserPath.setFont(font);
		contentPane.add(browserPath);
		pathValue.setFont(font);
		pathValue.addActionListener(new PathBrowserAction());
		contentPane.add(pathValue);
		setSize(400, 100);
		setResizable(false);
		setVisible(true);
	}
	
	 public void Exit(){
	      addWindowListener(new WindowAdapter(){
	         public void windowClosing(WindowEvent e){
	           dispose();
	           System.exit(0);
	         }
	      });
	   }

	public static void main(String[] args) {
		SimulationGUI simulationGUI = new SimulationGUI();
	}

	public void init(String fileName) {
		dashboard = new SimulationDashboard(fileName);
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

	private class PathBrowserAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
			path=Storepath(path);
			if(!path.equals("")){
				setVisible(false);
			SimulationGUI simulationGUI2 = new SimulationGUI(path);
			Thread simulationThread = new Thread(simulationGUI2);
			simulationThread.start();
			}
		}
	}
	
	public String Storepath(String storepath){
		Container contentPane = getContentPane();

		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		jfc.setFileFilter(filter);
		jfc.setDialogTitle("Choix du fichier");
		int returnValue = jfc.showOpenDialog(contentPane);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
		File file = jfc.getSelectedFile();
		storepath=file.getPath();
		}
		return storepath;
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
