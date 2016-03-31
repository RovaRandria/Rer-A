package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
	private JComboBox hoursComboBox;
	private JComboBox minutesComboBox;
	private JLabel hourLabel = new JLabel("h");
	private JLabel minuteLabel = new JLabel("mn");
	private JLabel addTrainLabel = new JLabel("Ajouter un train ");
	private JLabel startLabel = new JLabel("Départ à : ");
	private JLabel codeLabel = new JLabel("Code du train : ");
	private JScrollPane trainsListScroller;
	private JButton addTrainButton = new JButton("Ajouter");
	private ButtonGroup directionButtonGroup = new ButtonGroup();
	private JRadioButton lineRadioButton = new JRadioButton("en direction de Marne-La-Vallée Chessy");
	private JRadioButton reversedLineRadioButton = new JRadioButton("en direction de Cergy-Le-Haut");
	
	private JPanel hoursPanel = new JPanel();
	private JPanel addButtonPanel = new JPanel();
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
		trainsListScroller.setPreferredSize(new Dimension(275, 175));
		
		hoursComboBox = new JComboBox(hoursList().toArray());
		minutesComboBox = new JComboBox(minutesList().toArray());
		trainsComboBox = new JComboBox();
		
		lineRadioButton.setSelected(true);
		directionButtonGroup.add(lineRadioButton);
		directionButtonGroup.add(reversedLineRadioButton);

		speedPanel.add(speedLabel);
		speedPanel.add(speedSlider);
		zoomPanel.add(zoomLabel);
		zoomPanel.add(zoomSlider);
		addButtonPanel.add(addTrainButton);
		
		hoursPanel.add(hoursComboBox);
		hoursPanel.add(hourLabel);
		hoursPanel.add(minutesComboBox);
		hoursPanel.add(minuteLabel);
		
		headerPanel.add(speedPanel);
		headerPanel.add(zoomPanel);
		headerPanel.setPreferredSize(new Dimension(650, 40));
		headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
		addButtonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
		addButtonPanel.setPreferredSize(new Dimension(325, 35));

		footerPanel.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		frameConstraints.insets = new Insets(5, 5, 5, 50);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		frameConstraints.gridheight = 7;
		footerPanel.add(trainsListScroller, frameConstraints);
		
		frameConstraints.insets = new Insets(10, 2, 2, 2);
		frameConstraints.gridx = 1;
		frameConstraints.gridheight = 1;
		footerPanel.add(addTrainLabel, frameConstraints);

		frameConstraints.insets = new Insets(2, 2, 2, 2);
		frameConstraints.gridx = 2;
		frameConstraints.gridy = GridBagConstraints.RELATIVE;
		frameConstraints.anchor = GridBagConstraints.WEST;
		frameConstraints.fill = GridBagConstraints.NONE;
		footerPanel.add(lineRadioButton, frameConstraints);
		footerPanel.add(reversedLineRadioButton, frameConstraints);
		
		frameConstraints.anchor = GridBagConstraints.CENTER;
		frameConstraints.fill = GridBagConstraints.CENTER;
		frameConstraints.gridx = 1;
		frameConstraints.gridy = 3;
		footerPanel.add(codeLabel, frameConstraints);
		frameConstraints.gridx = 2;
		footerPanel.add(trainsComboBox, frameConstraints);

		frameConstraints.gridx = 1;
		frameConstraints.gridy = 4;
		footerPanel.add(startLabel, frameConstraints);
		frameConstraints.gridx = 2;
		footerPanel.add(hoursPanel, frameConstraints);
		
		frameConstraints.insets = new Insets(2, 2, 10, 2);
		frameConstraints.gridx = 1;
		frameConstraints.gridy = 5;
		frameConstraints.gridwidth = 2;
		footerPanel.add(addButtonPanel, frameConstraints);
		
		frameConstraints.gridx = 1;
		frameConstraints.gridy = 6;
		frameConstraints.gridwidth = 1;
		footerPanel.add(browserPath, frameConstraints);
		frameConstraints.gridx = 2;
		footerPanel.add(pathValue, frameConstraints);
		
		this.add(headerPanel);
		this.add(footerPanel);
	}
	
	public void updateTrainList(ArrayList<Train> trains){
		trainsList.removeAll();
		DefaultListModel listModel = new DefaultListModel();
		for(Train t : trains){
			String stationName;
			if(t.getCurrentStation() != null)
				stationName = t.getCurrentStation().getName();
			else{
				int time = (int)t.getStartTime();
				String hour = (((time)/60<10)?"0":"") +(time)/60 + "h"+((time%60<10)?"0":"") + (time%60);
				stationName = "start at " + hour;
			}
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

	public JComboBox getHoursComboBox() {
		return hoursComboBox;
	}

	public void setHoursComboBox(JComboBox hoursComboBox) {
		this.hoursComboBox = hoursComboBox;
	}

	public JComboBox getMinutesComboBox() {
		return minutesComboBox;
	}

	public void setMinutesComboBox(JComboBox minutesComboBox) {
		this.minutesComboBox = minutesComboBox;
	}

	public JComboBox getTrainsComboBox() {
		return trainsComboBox;
	}

	public JButton getAddTrainButton() {
		return addTrainButton;
	}

	public JRadioButton getLineRadioButton() {
		return lineRadioButton;
	}

	public JRadioButton getReversedLineRadioButton() {
		return reversedLineRadioButton;
	}
	

}
