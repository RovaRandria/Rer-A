package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;

import engine.Train;

public class ManagementPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	static final int SPEED_MIN = 1;
	static final int SPEED_MAX = 50;
	static final int SPEED_INIT = 25;
	static final int ZOOM_MIN = 5;
	static final int ZOOM_MAX = 100;
	static final int ZOOM_INIT = 50;
	
	
	private JSlider speedSlider;
	private JLabel speedLabel = new JLabel("Modifier la vitesse : ");
	private JSlider zoomSlider;
	private JLabel zoomLabel = new JLabel("Zoom : ");
	private JList trainsList;
	private JComboBox trainsComboBox;
//	private JTextField hoursTextField = new JTextField(2);
//	private JTextField minutesTextField = new JTextField(2);
	private JComboBox hoursComboBox;
	private JComboBox minutesComboBox;
	private JLabel hourLabel = new JLabel("h");
	private JLabel minuteLabel = new JLabel("mn");
	private JScrollPane trainsListScroller;
	private JButton addTrainButton = new JButton("Ajouter un train");
	
	private JPanel speedPanel = new JPanel();
	private JPanel zoomPanel = new JPanel();
	private JPanel headerPanel = new JPanel();
	private JPanel footerPanel = new JPanel();
	
	private JLabel browserPath=new JLabel("Modifier la ligne :");
	
	private JButton pathValue=new JButton("Parcourir");
	
	public ManagementPanel(){
		init();
	}
	
	public void init(){
		speedSlider = new JSlider(JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_INIT);
		zoomSlider = new JSlider(JSlider.HORIZONTAL, ZOOM_MIN, ZOOM_MAX, ZOOM_INIT);	
		
		String trainNames[] = {"Thomas", "Gordon", "Henry", "Emily", "Edouard", "James", "Toby", "Thomas", "Gordon", "Henry", "Emily", "Edouard", "James", "Toby"};
		trainsList = new JList(trainNames); 
		trainsList.setVisibleRowCount(-1);
		trainsListScroller = new JScrollPane(trainsList);
		trainsListScroller.setPreferredSize(new Dimension(250, 150));
		
		hoursComboBox = new JComboBox(hoursList().toArray());
		minutesComboBox = new JComboBox(minutesList().toArray());
		trainsComboBox = new JComboBox(trainNames);
		
		speedPanel.add(speedLabel);
		speedPanel.add(speedSlider);
		zoomPanel.add(zoomLabel);
		zoomPanel.add(zoomSlider);
				
		headerPanel.add(speedPanel);
		headerPanel.add(zoomPanel);
		headerPanel.setPreferredSize(new Dimension(650, 50));
		
		headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
		
		footerPanel.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		frameConstraints.insets = new Insets(10, 5, 10, 100);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		frameConstraints.gridheight = 5;
		footerPanel.add(trainsListScroller, frameConstraints);
		frameConstraints.gridx = 1;
		frameConstraints.gridheight = 1;
		frameConstraints.insets = new Insets(10, 5, 5, 10);
		footerPanel.add(trainsComboBox, frameConstraints);
		frameConstraints.gridx = GridBagConstraints.RELATIVE;
		frameConstraints.insets = new Insets(10, 5, 5, 5);
		footerPanel.add(hoursComboBox, frameConstraints);
		footerPanel.add(hourLabel, frameConstraints);
		footerPanel.add(minutesComboBox, frameConstraints);
		footerPanel.add(minuteLabel, frameConstraints);
		
		frameConstraints.gridy = 1;
		frameConstraints.gridx = 1;
		frameConstraints.gridwidth = 5;
		frameConstraints.insets = new Insets(5, 5, 5, 5);
		footerPanel.add(addTrainButton, frameConstraints);
		frameConstraints.gridy = 2;
		frameConstraints.gridx = 1;
		footerPanel.add(browserPath, frameConstraints);
		frameConstraints.gridy = 3;
		frameConstraints.gridx = 1;
		footerPanel.add(pathValue, frameConstraints);
		
		this.add(headerPanel);
		this.add(footerPanel);
	}
	
	public void updateTrainList(ArrayList<Train> trains){
		trainsList.removeAll();
		DefaultListModel listModel = new DefaultListModel();
		for(Train t : trains){
			String stationName = "didn't start";
			if(t.getCurrentStation() != null)
				stationName = t.getCurrentStation().getName();
			listModel.addElement(t.getCode() + "("+stationName+")");
		}
		trainsList.setModel(listModel);
		trainsList.repaint();
	}
	
	public static List<Integer> hoursList() {
		List <Integer> hours = new ArrayList<Integer>();
		for (int i = 0; i < 24; i++) {
			hours.add(i);
		}
		return hours;
	}
	
	public static List<Integer> minutesList() {
		List <Integer> minutes = new ArrayList<Integer>();
		for (int i = 0; i < 60; i++) {
			minutes.add(i);
		}
		return minutes;
	}
	
	public JSlider getSpeedSlider() {
		return speedSlider;
	}

	public JSlider getZoomSlider() {
		return zoomSlider;
	}

	public static int getZoomInit() {
		return ZOOM_INIT;
	}

	public JButton getPathValue() {
		return pathValue;
	}

	public void setPathValue(JButton pathValue) {
		this.pathValue = pathValue;
	}
	

}
