package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import engine.Canton;
import engine.Event;
import engine.Line;
import engine.Train;
import engine.TrainPattern;
import engine.TrainSimulator;


public class SimulationGUI extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String path="";
	private SimulationDashboard dashboard;
	private TrainSimulator trainsim;
	private static float currentTime = 0;
	private static float speed = 1;
	private static final int SIMULATION_DURATION = 1440;
	public static final int TIME_INIT = 100;
	public static int timeUnit = TIME_INIT;
	public static int widthScrollBar = Integer.MAX_VALUE;
	private Font titleFont = new Font("Arial", Font.BOLD, 28);
	private Font hourFont = new Font("Arial", Font.BOLD, 20);
	private boolean end = false;
	
	private JPanel wholeFrame = new JPanel();
	private JScrollPane dashboardScrollPanel = new JScrollPane();
	private JTabbedPane infoTabbedPanel = new JTabbedPane();
	private EventsPanel eventsPanel = new EventsPanel(this);
	private HoursPanel trainsHoursPanel;
	private ManagementPanel managementPanel;
	
	private JFrame instance = this;
	private JLabel titleLabel = new JLabel("RER Simulator par \"VAPEUR®\"");

	private JPanel hourPanel = new JPanel();
	private JLabel hourLabel;
	
	
	ImageIcon hoursIcon = new ImageIcon(new ImageIcon("./img/icons/hours.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
	ImageIcon eventsIcon = new ImageIcon(new ImageIcon("./img/icons/events.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
	ImageIcon manageIcon = new ImageIcon(new ImageIcon("./img/icons/manage.png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));

	
	public SimulationGUI(String fileName) {
		super("Train simulation");
		init(fileName);
		setVisible(true);
		Exit();
	}	

	public SimulationGUI() {
		SimulationGUI simulationGUI = new SimulationGUI("./test-Rer.txt");
		Thread simulationThread = new Thread(simulationGUI);
		simulationThread.start();
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
		new SimulationGUI();
	}

	public void init(String fileName) {
		trainsim = new TrainSimulator(fileName);
		trainsHoursPanel = new HoursPanel(trainsim);
		eventsPanel = new EventsPanel(this);
		dashboard = new SimulationDashboard(trainsHoursPanel, eventsPanel);
		managementPanel = new ManagementPanel();
		managementPanel.getZoomSlider().addChangeListener(new ZoomAction());
		managementPanel.getSpeedSlider().addChangeListener(new SpeedChangingAction());
		managementPanel.getAddTrainButton().addActionListener(new AddTrainAction());
		managementPanel.getLineRadioButton().addActionListener(new UpdateComboBoxLineAction());
		managementPanel.getReversedLineRadioButton().addActionListener(new UpdateComboBoxReversedLineAction());
		eventsPanel = new EventsPanel(this);
		hourLabel = new JLabel("0h00");
		hourLabel.setFont(hourFont);
		hourPanel.add(hourLabel);

		dashboard.setLine(trainsim.getLine());
		dashboard.setReversedLine(trainsim.getReversedLine());
		wholeFrame.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		titleLabel.setFont(titleFont);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		frameConstraints.insets = new Insets(7,5,7,5);
		wholeFrame.add(titleLabel, frameConstraints);
		frameConstraints.gridy = GridBagConstraints.RELATIVE;
		frameConstraints.insets = new Insets(5,5,5,5);
		dashboardScrollPanel = new JScrollPane(dashboard);

		dashboard.setPreferredSize(new Dimension(dashboard.getLine().getTotallength()/dashboard.getInitDistance()+150 , 370));
		dashboardScrollPanel.setPreferredSize(new Dimension(700, 200));

		dashboardScrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(widthScrollBar, 20));
		wholeFrame.add(dashboardScrollPanel, frameConstraints);
		frameConstraints.insets = new Insets(0,5,0,5);
		wholeFrame.add(hourPanel, frameConstraints);
		
		infoTabbedPanel.addTab("Horaires", hoursIcon, trainsHoursPanel,	"Horaires des trains quoi");
		infoTabbedPanel.addTab("Évènements", eventsIcon, eventsPanel,"Console qui affiche les différents évènements");
		infoTabbedPanel.addTab("Gestion", manageIcon, managementPanel,"Gestion des trains quoi");		
		infoTabbedPanel.setPreferredSize(new Dimension(700, 300));
		frameConstraints.insets = new Insets(5,0,5,0);
		wholeFrame.add(infoTabbedPanel, frameConstraints);
		wholeFrame.setPreferredSize(new Dimension(725, 600));
		
		managementPanel.getPathValue().addActionListener(new PathBrowserAction());
		
		this.add(wholeFrame);
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}


	@Override
	public void run() {

		Line line = trainsim.getLine();
		Line reversedLine = trainsim.getReversedLine();
		Canton firstCanton = line.getCantons().get(0);
		Train newTrain=null;
		
		String str1[] = null;
		String l;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("./src/trains.txt"));
			while ((l = br.readLine()) != null) {
				
				String separator1="\\|";
				str1=l.split(separator1);				
				System.out.println("Pattern:");
				System.out.println("Pattern"+line.getPatterns().toString());
				TrainPattern tp = line.getPatterns().get(str1[1]);
				if(!tp.getReversed()){
					System.out.println("Normal train created");
					newTrain = new Train(line, firstCanton,Integer.parseInt(str1[0]),tp, Integer.parseInt(str1[2]));
				}
				else{
					System.out.println("Reversed train created");
					newTrain = new Train(trainsim.getReversedLine(), reversedLine.getCanton(0),Integer.parseInt(str1[0]),tp, Integer.parseInt(str1[2]));
				}
				trainsim.addTrain(newTrain);
				newTrain.start();
				
			}
			
			br.close();
			
			managementPanel.getTrainsComboBox().removeAllItems();
			for (TrainPattern tp : trainsim.getLine().getPatterns().values()) {
			    if(!tp.getReversed())
			    	managementPanel.getTrainsComboBox().addItem(tp.getPatternCode());
			}
		
			managementPanel.repaint();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le chemin du fichier texte entré est incorrect");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		catch(NullPointerException ee){
			System.out.println("Fichier entré incorrect");
		}
		
		while (currentTime <= SIMULATION_DURATION && !end) {
			int time = (int)SimulationGUI.getCurrentTime();
			hourLabel.setText((((time)/60<10)?"0":"") +(time)/60 + "h"+((time%60<10)?"0":"") + (time%60));
			if(trainsHoursPanel.isCurrentPan()) {
				infoTabbedPanel.setSelectedIndex(0);
				trainsHoursPanel.setCurrentPan(false);
			}
			dashboard.setTrains(trainsim.getTrains());
			dashboard.repaint();
			
			trainsim.updateTrains();
			managementPanel.updateTrainList(trainsim.getTrains());
			managementPanel.repaint();
			hourPanel.repaint();
			try {
				Thread.sleep(timeUnit);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			currentTime++;
			trainsim.decrementEvents();
			eventsPanel.repaint();
		}
	}

	public void addEvent(Event e) {
		trainsim.addEvent(e);
		dashboard.addEvent(e);
		dashboard.repaint();
	}

	public static float getCurrentTime(){
		return currentTime;
	}

	public Line getLine() {
		return trainsim.getLine();
	}
	
	public static void setCurrentTime(int currentTime){
		SimulationGUI.currentTime = currentTime;
	}

	private class PathBrowserAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			
			path=Storepath(path);
			if(!path.equals("")){
				setVisible(false);
				SimulationGUI.setCurrentTime(0);
				endSim();
				SimulationGUI simulationGUI = new SimulationGUI(path);
				Thread simulationThread = new Thread(simulationGUI);
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
	
	public static float getSpeed() {
		return speed;
	}



	public static void setSpeed(float speed) {
		SimulationGUI.speed = speed;
	}
	
	public SimulationDashboard getDashboard() {
		return dashboard;
	}

	private class SpeedChangingAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			SimulationGUI.setSpeed((float)((JSlider) e.getSource()).getValue()/10);
			SimulationGUI.setTimeUnit((int)(SimulationGUI.getTimeInit()/SimulationGUI.getSpeed()));
		}
	}
	
	private class ZoomAction implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int newSize = (((JSlider) e.getSource()).getValue());
			dashboard.setDistancePerPixel(dashboard.getInitDistance()*newSize/ManagementPanel.getZoomInit());
			dashboard.setPreferredSize(new Dimension(dashboard.getLine().getTotallength()/dashboard.getDistancePerPixel()+150 , 500));		
	
			widthScrollBar = (int)(widthScrollBar*((float)newSize/(float)100));
			dashboardScrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(widthScrollBar, 20));
			instance.repaint();
		}
	}
	
	private class AddTrainAction implements ActionListener {
		public void actionPerformed(ActionEvent ae){
			String direction;
			int time;
			time = (Integer.parseInt(managementPanel.getHoursComboBox().getSelectedItem().toString())*60) + Integer.parseInt(managementPanel.getMinutesComboBox().getSelectedItem().toString());
			if (managementPanel.getLineRadioButton().isSelected())
				direction = "line";
			else
				direction = "reversedLine";
			
			Line line = trainsim.getLine();
			Line reversedLine = trainsim.getReversedLine();
			Canton firstCanton = line.getCantons().get(0);
			
			if(direction=="line"){
				TrainPattern tp = line.getPatterns().get(managementPanel.getTrainsComboBox().getSelectedItem().toString());
				Train newTrain = new Train(line, firstCanton,100,tp, time);
				trainsim.addTrain(newTrain);
				newTrain.start();
			}
			else{
				TrainPattern tp = line.getPatterns().get(managementPanel.getTrainsComboBox().getSelectedItem().toString());
				Train newTrain = new Train(trainsim.getReversedLine(), reversedLine.getCanton(0),100,tp, time);
				trainsim.addTrain(newTrain);
				newTrain.start();
			}

			
			managementPanel.repaint();
		}
	}
	
	private class UpdateComboBoxLineAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			managementPanel.getTrainsComboBox().removeAllItems();
			for (TrainPattern tp : trainsim.getLine().getPatterns().values()) {
				if(!tp.getReversed())
					managementPanel.getTrainsComboBox().addItem(tp.getPatternCode());
			}
			managementPanel.repaint();
		}
	}
	
	private class UpdateComboBoxReversedLineAction implements ActionListener {
		public void actionPerformed(ActionEvent e){
			managementPanel.getTrainsComboBox().removeAllItems();
			for (TrainPattern tp : trainsim.getLine().getPatterns().values()) {
				if(tp.getReversed())
					managementPanel.getTrainsComboBox().addItem(tp.getPatternCode());
			}
			managementPanel.repaint();
		}
	}
	
	public static void setTimeUnit(int timeUnit) {
		SimulationGUI.timeUnit = timeUnit;
	}

	public void endSim(){
		end = true;
	}

	public static int getTimeInit() {
		return TIME_INIT;
	}


}
