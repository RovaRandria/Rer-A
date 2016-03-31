package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class TrainSimulator {

	private Line line;
	private Line reversedLine;
	private ArrayList<Train> trains;
	private HashMap<Station, ArrayList<Integer>> schedules;
	private ArrayList<Event> events = new ArrayList<Event>();
	
	public TrainSimulator(String fileName) {
		LineBuilder lineBuilder = new LineBuilder();
		lineBuilder.buildLine(fileName);
		line = lineBuilder.getBuiltLine();
		reversedLine = lineBuilder.getBuiltReversedLine();
		this.trains = new ArrayList<Train>();
		schedules = new HashMap<Station, ArrayList<Integer>>();
	}
	
	public void addTrain(Train train) {
		trains.add(train);
	}

	public Line getLine() {
		return line;
	}

	public Line getReversedLine() {
		return reversedLine;
	}

	public ArrayList<Train> getTrains() {
		return trains;
	}
	
	public void updateSchedules(){
		schedules = new HashMap<Station, ArrayList<Integer>>();
		
		for(Station s : line.getStations()){
			schedules.put(s, new ArrayList<Integer>());
		}
		for(Station s : reversedLine.getStations()){
			schedules.put(s, new ArrayList<Integer>());
		}
		for(Train t : trains){
			Iterator<Entry<Station, Integer>> it = t.getSchedules().entrySet().iterator();
			while(it.hasNext()){
				Entry<Station, Integer> next = it.next();
				schedules.get(next.getKey()).add(next.getValue());
			}
		}
	}
	
	public void updateTrains(){
		for(int i=0;i<trains.size();i++){
			if(trains.get(i).isArrived()){
				trains.remove(i);
				i--;
			}
		}
	}
	
	public String schedulesToString(){
		//Iterator<Entry<Station, ArrayList<Integer>>> it = schedules.entrySet().iterator();
		String scheduleStr = "Horaires :\n";
		for(Station s : line.getStations()){
			
			String str = s.getName() + " : ";
			for(int time : schedules.get(s)){
				str += (time)/60 + "h"+((time%60<10)?"0":"") + (time%60) + " | ";
				//str += time + " | ";
			}
			scheduleStr += str+"\n";
		}
		return scheduleStr;
	}
	
	public String schedulesToString(Station s){
		//Iterator<Entry<Station, ArrayList<Integer>>> it = schedules.entrySet().iterator();
		String scheduleStr = "Horaires :\n";
		String str = s.getName() + " : ";
		for(int time : schedules.get(s)){
			str += (time)/60 + "h"+((time%60<10)?"0":"") + (time%60) + " | ";
			//str += time + " | ";
		}
		scheduleStr += str+"\n";
		return scheduleStr;
	}
	
	public void addEvent(Event e) {
		events.add(e);
		try {
			if(e.isReverse())
				reversedLine.blockCanton(e.getPosition());
			else{
				System.out.println("On bloque le canton à la position " + e.getPosition());
				line.blockCanton(e.getPosition());
			}
		} catch (TerminusException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
	}
	
	public void decrementEvents() {
		for(Event e : events) {
			boolean notFinished = e.getDuration() > 0;
			e.decrementDuration();
			if(e.getDuration() == 0 && notFinished){
				try {
					if(e.isReverse())
						reversedLine.unblockCanton(e.getPosition());
					else{
						line.unblockCanton(e.getPosition());
					}
				} catch (TerminusException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
		}
	}

	
}
