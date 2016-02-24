package gui;


import java.util.ArrayList;

import javax.swing.JFrame;

import engine.Canton;
import engine.Line;
import engine.TerminusException;
import engine.Train;
import engine.Station;


public class SimulationGUI extends JFrame implements Runnable {
	private static final int TRAIN_SPEED_VARIATION = 3;
	private static final int TRAIN_BASIC_SPEED = 2;
	private SimulationDashboard dashboard = new SimulationDashboard();
	private static float currentTime = 0;
	private static final int SIMULATION_DURATION = 1440;
	public static final int TIME_UNIT = 50;

	public SimulationGUI() {
		super("Train simulation");
		getContentPane().add(dashboard);
		setSize(700, 500);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		SimulationGUI simulationGUI = new SimulationGUI();
		Thread simulationThread = new Thread(simulationGUI);
		simulationThread.start();
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
}
