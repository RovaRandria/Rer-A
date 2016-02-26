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
	private float startTime;
	private String code;
	private int speed;
	private boolean hasArrived = false;

	
	public Train(Line line, Canton startCanton, int speed, String code, float startTime) {
		this.line = line;
		this.speed = speed;
		this.code = code;
		this.startTime = startTime;
		running = false;
		currentCanton = startCanton;
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
		if(!running && !hasArrived){
			while (SimulationGUI.getCurrentTime() < startTime){
				try {
					sleep(SimulationGUI.TIME_UNIT);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			startRunning();
			
			int time = (int)SimulationGUI.getCurrentTime();
			/*try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			while (!hasArrived && running) {
				try {
					sleep(SimulationGUI.TIME_UNIT);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
				
				if(position + speed >= currentStation.getPosition()){
					
					position = currentStation.getPosition();
					try {
						sleep(SimulationGUI.TIME_UNIT * 3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(!path.isEmpty()){
						currentStation = path.get(0);
						path.remove(0);
					}else{
						hasArrived = true;
						position = line.getTotallength();
						this.currentCanton.exit();
						this.currentCanton = null;
					}
				}else if (position + speed >= currentCanton.getEndPoint()) {
					try {
						
						Canton nextCanton = line.getCantonByPosition(position + speed);
						nextCanton.enter(this);
					} catch (TerminusException e) {
						System.out.println("ON EST SORTI");
						hasArrived = true;
						position = line.getTotallength();
						this.currentCanton.exit();
						this.currentCanton = null;
					}
				}else {
					
				
					updatePosition();
				}
			}
		}
			
	}

	@Override
	public String toString() {
		return "Train [speed=" + speed + "]";
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void updatePosition() {
		position += speed;
	}
	
	public void setPath(ArrayList<Station> path){
		this.path = path;
	}
	
	public synchronized void startRunning(){
		if(!path.isEmpty()){
			currentStation = path.get(0);
			path.remove(0);
			running = true;
			currentCanton.enter(this);
			//notify();
		}
	}

	public float getStartTime() {
		return startTime;
	}

	public void setStartTime(float startTime) {
		this.startTime = startTime;
	}

	public Line getLine() {
		return line;
	}

	
}
