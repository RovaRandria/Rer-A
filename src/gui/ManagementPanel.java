package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Dictionary;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
	public ManagementPanel(){
		init();
	}
	
	public void init(){

		speedSlider = new JSlider(JSlider.HORIZONTAL, SPEED_MIN, SPEED_MAX, SPEED_INIT);
		
		zoomSlider = new JSlider(JSlider.HORIZONTAL, ZOOM_MIN, ZOOM_MAX, ZOOM_INIT);	
		zoomSlider.setMinorTickSpacing(5);		
		zoomSlider.setMajorTickSpacing(30);
		zoomSlider.setPaintTicks(true);
		zoomSlider.setPaintLabels(true);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints frameConstraints = new GridBagConstraints();
		frameConstraints.insets = new Insets(20, 10, 20, 10);
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 0;
		this.add(speedLabel, frameConstraints);
		frameConstraints.gridy = GridBagConstraints.RELATIVE;
		this.add(zoomLabel, frameConstraints);

		frameConstraints.gridx = 1;
		this.add(speedSlider, frameConstraints);
		this.add(zoomSlider, frameConstraints);
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

}
