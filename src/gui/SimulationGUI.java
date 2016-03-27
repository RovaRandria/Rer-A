package gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

	private JPanel wholeFrame = new JPanel();
	private JScrollPane dashboardScrollPanel = new JScrollPane();
	private JTabbedPane infoTabbedPanel = new JTabbedPane();

	private EventsPanel eventsPanel = new EventsPanel();
	private HoursPanel trainsHoursPanel;
	private ManagementPanel managementPanel;
	
	private JFrame instance = this;
	private JLabel titleLabel = new JLabel("RER Simulator par \"VAPEUR®\"");

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
		trainsim = new TrainSimulator(fileName);
		trainsHoursPanel = new HoursPanel(trainsim);
		dashboard = new SimulationDashboard(trainsHoursPanel);
		managementPanel = new ManagementPanel();
		managementPanel.getZoomSlider().addChangeListener(new ZoomAction());
		managementPanel.getSpeedSlider().addChangeListener(new SpeedChangingAction());
		
		eventsPanel = new EventsPanel();

		dashboard.setLine(trainsim.getLine());
		dashboard.setReversedLine(trainsim.getReversedLine());
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

		dashboard.setPreferredSize(new Dimension(dashboard.getLine().getTotallength()/dashboard.getInitDistance()+150 , 500));
		dashboardScrollPanel.setPreferredSize(new Dimension(700, 300));
		System.out.println("widthScrollBar = "+widthScrollBar);

		dashboardScrollPanel.getHorizontalScrollBar().setPreferredSize(new Dimension(widthScrollBar, 20));
		wholeFrame.add(dashboardScrollPanel, frameConstraints);

		infoTabbedPanel.addTab("Horaires", hoursIcon, trainsHoursPanel,	"Horaires des trains quoi");
		infoTabbedPanel.addTab("Évènements", eventsIcon, eventsPanel,"Console qui affiche les différents évènements");
		infoTabbedPanel.addTab("Gestion", manageIcon, managementPanel,"Gestion des trains quoi");		
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

		Line line = trainsim.getLine();
		Line reversedLine = trainsim.getReversedLine();
		Canton firstCanton = line.getCantons().get(0);

		
		/*Train newTrain = new Train(line, firstCanton, 5, "QYEN", 0);
		trainsim.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();
		
		newTrain = new Train(reversedLine, reversedLine.getCanton(0), 5, "TEDI", 0);
		trainsim.addTrain(newTrain);
		newTrain.setPath(trainsim.getReversedLine().getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 5, "QYEN", 30);
		trainsim.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 5, "QYEN", 60);
		trainsim.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 5, "QYEN", 90);
		trainsim.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 5, "QYEN", 120);
		trainsim.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();
		
		newTrain = new Train(trainsim.getReversedLine(), reversedLine.getCanton(0), 5, "TEDI2", 120);
		trainsim.addTrain(newTrain);
		newTrain.setPath(trainsim.getReversedLine().getFullPath());
		newTrain.start();

		newTrain = new Train(line, firstCanton, 5, "QYEN", 150);
		trainsim.addTrain(newTrain);
		newTrain.setPath(line.getFullPath());
		newTrain.start();*/
		
		//String fileName= SimulationGUI.class.getResource("/trains.txt").getPath();
		
		String str1[] = null;
		String l;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader("./src/trains.txt"));
			while ((l = br.readLine()) != null) {
				
				String separator1="\\|";
				str1=l.split(separator1);
				
					if(str1[0].equals("line")){
				
						Train newTrain = new Train(line, firstCanton,Integer.parseInt(str1[1]),str1[2], Integer.parseInt(str1[3]));
						trainsim.addTrain(newTrain);
						newTrain.setPath(line.getFullPath());
						newTrain.start();
					}
					else if(str1[0].equals("reversedLine")){
			
						Train newTrain = new Train(trainsim.getReversedLine(), reversedLine.getCanton(0),Integer.parseInt(str1[1]),str1[2], Integer.parseInt(str1[3]));
						trainsim.addTrain(newTrain);
						newTrain.setPath(trainsim.getReversedLine().getFullPath());
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
		
		



		while (currentTime <= SIMULATION_DURATION) {
			if(trainsHoursPanel.isCurrentPan()) {
				infoTabbedPanel.setSelectedIndex(0);
				trainsHoursPanel.setCurrentPan(false);
			}
			dashboard.setTrains(trainsim.getTrains());
			dashboard.repaint();
			
			
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
