package engine;

import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.List;

import gui.SimulationGUI;


public class Train extends Thread {
	private volatile int position = 0;
	private Line line;
	private Canton currentCanton;
	private List<Station> path = new ArrayList<Station>();
	private Station currentStation;
	private boolean running;
	/**
	 * Distance per time unit.
	 */
	
	private int speed;
	private boolean hasArrived = false;

	public Train(Line line, Canton startCanton, int speed) {
		this.line = line;
		currentCanton = startCanton;
		currentCanton.enter(this);
		this.speed = speed;
		running = false;
	}

	public int getPosition() {
		return position;
	}

	public Canton getCurrentCanton() {
		return currentCanton;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setCurrentCanton(Canton currentCanton) {
		this.currentCanton = currentCanton;
	}

	@Override
	public void run() {
		if(running = false){
			try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		while (!hasArrived) {
			try {
				sleep(SimulationGUI.TIME_UNIT);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			if (position + speed >= currentCanton.getEndPoint()) {
				try {
					
					Canton nextCanton = line.getCantonByPosition(position + speed);
					nextCanton.enter(this);
				} catch (TerminusException e) {
					hasArrived = true;
					position = line.getTotalLenght();
				}
			}else if(position + speed >= currentStation.getPosition()){
				position = currentStation.getPosition();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}currentStation = path.get(0);
				path.remove(0);
			}else {
				
			
				updatePosition();
			}
		}
		currentCanton.exit();
	}

	@Override
	public String toString() {
		return "Train [speed=" + speed + "]";
	}

	public void updatePosition() {
		position += speed;
	}
	
	public void setPath(ArrayList<Station> path){
		this.path = path;
	}
	@Override
	
	public synchronized void start(){
		if(!path.isEmpty()){
			currentStation = path.get(0);
			path.remove(0);
			notify();
		}
		super.start();
	}

}
