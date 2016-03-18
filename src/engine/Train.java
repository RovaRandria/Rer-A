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
					sleep(SimulationGUI.timeUnit);
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
					sleep(SimulationGUI.timeUnit);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
				//System.out.println("position : " + getPosition());
				trainIsComing();
				if(position + distancePerFrame() >= currentStation.getPosition()){
					position = currentStation.getPosition();
					try {
						sleep(SimulationGUI.timeUnit * 3);
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
				}else if (position + distancePerFrame() >= currentCanton.getEndPoint()) {
					try {
						System.out.println("Position actuelle : " + position);
						Canton nextCanton = line.getCantonByPosition(position +  distancePerFrame());
						nextCanton.enter(this);
						System.out.println("Position après changement de canton : " + position);
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
		position += distancePerFrame();
	}
	
	public int distancePerFrame(){
		return (int)((float)(speed*100/60) * SimulationGUI.getSpeed());
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

	public void trainIsComing(){
		if(currentStation != null){
			int pos =currentStation.getPosition()-getPosition();
			int arrivalTime=pos/distancePerFrame();
			System.out.println("Train is coming in:"+arrivalTime);
		}
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
}
