package engine;

import java.util.ArrayList;
import java.util.HashMap;
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
	private int margin;
	private boolean hasArrived = false;

	public Train(Line line, Canton startCanton, int speed, TrainPattern trainPattern, float startTime) {
		this.line = line;
		this.speed = speed;
		this.code = trainPattern.getPatternCode();
		this.path = trainPattern.getPattern();
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
			
			/*try {
				wait();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			while (!hasArrived && running) {
				//System.out.println("position : " + getPosition());
				if(position + distanceNextFrame() >= currentStation.getPosition()){
					margin = position + distancePerFrame() - currentStation.getPosition();
					position = currentStation.getPosition();
					try {
						sleep((int)(SimulationGUI.timeUnit * currentStation.getTimeToWait()));

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
						//System.out.println("Position actuelle : " + position);
						Canton nextCanton = line.getCantonByPosition(position +  distancePerFrame());
						nextCanton.enter(this);
						//System.out.println("Position après changement de canton : " + position);
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
				try {
					sleep(SimulationGUI.timeUnit);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
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
		position += distanceNextFrame();
		margin = 0;
	}
	
	public int distancePerFrame(){
		return (int)((float)(speed*100/60));
	}
	public int distanceNextFrame(){
		return distancePerFrame() + margin;
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


	public HashMap<Station, Integer> getSchedules(){
		HashMap<Station, Integer> schedules = new HashMap<Station,Integer>();
		float totalTimeStation = 0;
		if(currentStation != null){
			schedules.put(currentStation,currentStation.getPosition()-getPosition());
			totalTimeStation += currentStation.getTimeToWait();
		}
		
		for(int i=0;i<path.size();i++){
			Station s = path.get(i);
			System.out.println("--------------");
			System.out.println(s.getName() + " : ");
			System.out.println((s.getPosition()-getPosition()) + " / " + distancePerFrame() + " = " + (s.getPosition()-getPosition())/distancePerFrame());
			System.out.println("+ " + totalTimeStation);
			System.out.println("+ " + SimulationGUI.getCurrentTime());
			
			int time = (int) ((s.getPosition()-getPosition())/distancePerFrame() + totalTimeStation + SimulationGUI.getCurrentTime());
			if(startTime > SimulationGUI.getCurrentTime()){
				time += (startTime - SimulationGUI.getCurrentTime());
				System.out.println("+ " + (startTime - SimulationGUI.getCurrentTime()));
			}
			System.out.println("schedule : " + time);
			schedules.put(s, time);
			totalTimeStation += s.getTimeToWait();
		}
		
		return schedules;
		
	}
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Station getCurrentStation() {
		return currentStation;
	}

	public void setCurrentStation(Station currentStation) {
		this.currentStation = currentStation;
	}
	
	public boolean isArrived() {
		return hasArrived;
	}
}
