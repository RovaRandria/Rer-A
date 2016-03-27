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
	
	public TrainSimulator(String fileName) {
		LineBuilder lineBuilder = new LineBuilder();
		lineBuilder.buildLine(fileName);
		line = lineBuilder.getBuiltLine();
		if(line != null)
			reversedLine = line.getReversedLine();
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
	
	public void UpdateSchedules(){
		schedules = new HashMap<Station, ArrayList<Integer>>();
		
		for(Station s : line.getStations()){
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
	
	public String SchedulesToString(){
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
	
	public String SchedulesToString(Station s){
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
	
	
}
