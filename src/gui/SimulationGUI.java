package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import engine.Canton;
import engine.Line;
import engine.Station;
import engine.Train;
import engine.TrainPattern;
import engine.TrainSimulator;


public class SimulationGUI extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int TRAIN_SPEED_VARIATION = 3;
	private static final int TRAIN_BASIC_SPEED = 2;
	private JButton pathValue=new JButton("Parcourir");
	private String path="";
	private SimulationDashboard dashboard;
	private TrainSimulator trainsim;
	private static float currentTime = 0;
	private static float speed = 1;
	private static final int SIMULATION_DURATION = 1440;
	public static final int TIME_INIT = 100;
	public static int timeUnit = TIME_INIT;
	public static int widthScrollBar = Integer.MAX_VALUE;
	private JLabel browserPath=new JLabel("Chemin à explorer :");
	private Font font = new Font(Font.MONOSPACED, Font.BOLD, 15);
	private Font titleFont = new Font("Arial", Font.BOLD, 28);
	private Font hourFont = new Font("Arial", Font.BOLD, 20);

	private JPanel wholeFrame = new JPanel();
	private JScrollPane dashboardScrollPanel = new JScrollPane();
	private JTabbedPane infoTabbedPanel = new JTabbedPane();
	private EventsPanel eventsPanel = new EventsPanel();
	private HoursPanel trainsHoursPanel;
	private ManagementPanel managementPanel;
	
	private JFrame instance = this;
	private JLabel titleLabel = new JLabel("RER Simulator par \"VAPEUR®\"");

	private JPanel hourPanel = new JPanel();
	private JLabel hourLabel;
	
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
		dashboard = new SimulationDashboard(trainsHoursPanel);
		managementPanel = new ManagementPanel();
		managementPanel.getZoomSlider().addChangeListener(new ZoomAction());
		managementPanel.getSpeedSlider().addChangeListener(new SpeedChangingAction());
		eventsPanel = new EventsPanel();
		hourLabel = new JLabel("0h00");
		hourLabel.setFont(hourFont);
		hourPanel.add(hourLabel);

		dashboard.setLine(trainsim.getLine());
		dashboard.setReversedLine(trainsim.getReversedLine());
		wholeFrame.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		Font font = new Font("Arial",Font.BOLD,40);
		titleLabel.setFont(titleFont);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		frameConstraints.insets = new Insets(20,0,20,0);
		wholeFrame.add(titleLabel, frameConstraints);
		frameConstraints.gridy = GridBagConstraints.RELATIVE;
		frameConstraints.insets = new Insets(5,0,5,0);
		dashboardScrollPanel = new JScrollPane(dashboard);

		dashboard.setPreferredSize(new Dimension(dashboard.getLine().getTotallength()/dashboard.getInitDistance()+150 , 370));
		dashboardScrollPanel.setPreferredSize(new Dimension(700, 300));

		dashboardScrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(widthScrollBar, 20));
		wholeFrame.add(dashboardScrollPanel, frameConstraints);
		wholeFrame.add(hourPanel, frameConstraints);
		
		infoTabbedPanel.addTab("Horaires", hoursIcon, trainsHoursPanel,	"Horaires des trains quoi");
		infoTabbedPanel.addTab("Évènements", eventsIcon, eventsPanel,"Console qui affiche les différents évènements");
		infoTabbedPanel.addTab("Gestion", manageIcon, managementPanel,"Gestion des trains quoi");		
		infoTabbedPanel.setPreferredSize(new Dimension(700, 300));
		wholeFrame.add(infoTabbedPanel, frameConstraints);
		wholeFrame.setPreferredSize(new Dimension(700, 750));
		
		managementPanel.getPathValue().addActionListener(new PathBrowserAction());
		
		this.add(wholeFrame);
		pack();
		setVisible(true);
		setResizable(false);
	}


	@Override
	public void run() {

		Line line = trainsim.getLine();
		Line reversedLine = trainsim.getReversedLine();
		Canton firstCanton = line.getCantons().get(0);
		ArrayList<Station> newPath = new ArrayList<Station>();
		
		String str1[] = null;
		String l;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("./src/trains.txt"));
			while ((l = br.readLine()) != null) {
				
				String separator1="\\|";
				String separator2="\\,";
				str1=l.split(separator1);
			
				/*
					ArrayList<Integer> al=new ArrayList<Integer>();
					str2=str1[4].split(separator2);
					for(int i=0;i<str2.length;i++){
						al.add(Integer.parseInt(str2[i]));
					}
				*/
					if(str1[0].equals("line")){
						System.out.println("Pattern:");
						System.out.println("Pattern"+line.getPatterns().toString());
						TrainPattern tp = line.getPatterns().get(str1[2]);
						System.out.println(str1[0]+str1[1]+str1[2]+str1[3]);
						Train newTrain = new Train(line, firstCanton,Integer.parseInt(str1[1]),tp, Integer.parseInt(str1[3]));
						trainsim.addTrain(newTrain);

						/*
						tp=tp.createPattern(line, al, str1[5]);
						line.getPatterns().put(str1[5], tp);
						newPath=tp.getPattern();
						newTrain.setPath(newPath);*/
						newTrain.start();
					}
					else if(str1[0].equals("reversedLine")){
						//System.out.println(str1[0]+str1[1]+str1[2]+str1[3]);
						System.out.println("Pattern:");
						System.out.println("Pattern"+line.getPatterns().toString());
						TrainPattern tp = line.getPatterns().get(str1[2]);
						Train newTrain = new Train(trainsim.getReversedLine(), reversedLine.getCanton(0),Integer.parseInt(str1[1]),tp, Integer.parseInt(str1[3]));
						trainsim.addTrain(newTrain);
					/*	tp=tp.createPattern(trainsim.getReversedLine(), al, str1[5]);
						trainsim.getReversedLine().getPatterns().put(str1[5], tp);
						newPath=tp.getPattern();
						newTrain.setPath(newPath);*/
						newTrain.start();
					}
					}
			
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Le chemin du fichier texte entré est incorrect");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		catch(NullPointerException ee){
			System.out.println("Fichier entré incorrect");
		}
		

		System.out.println(line.getPatterns().toString()+trainsim.getReversedLine().getPatterns().toString());


		while (currentTime <= SIMULATION_DURATION) {
			int time = (int)SimulationGUI.getCurrentTime();
			hourLabel.setText((((time)/60<10)?"0":"") +(time)/60 + "h"+((time%60<10)?"0":"") + (time%60));
			if(trainsHoursPanel.isCurrentPan()) {
				infoTabbedPanel.setSelectedIndex(0);
				trainsHoursPanel.setCurrentPan(false);
			}
			dashboard.setTrains(trainsim.getTrains());
			dashboard.repaint();
			
			managementPanel.updateTrainList(trainsim.getTrains());
			managementPanel.repaint();
			hourPanel.repaint();
			try {
				Thread.sleep(timeUnit);
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
	
	public static void setTimeUnit(int timeUnit) {
		SimulationGUI.timeUnit = timeUnit;
	}



	public static int getTimeInit() {
		return TIME_INIT;
	}


}
