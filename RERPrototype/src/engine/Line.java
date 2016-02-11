package engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Line {

	private int totalLenght;
	private int usedLength = 0;

	private List<Canton> cantons = new ArrayList<Canton>();
	private ArrayList<Station> stations = new ArrayList<Station>();
	
	public Line(int totalLenght) {
		this.totalLenght = totalLenght;
	}

	public void addCanton(int id, int cantonLength) {
		Canton canton;
		if (usedLength + cantonLength <= totalLenght) {
			canton = new Canton(id, cantonLength, usedLength);
			usedLength += cantonLength;
		} else {
			canton = new Canton(id, totalLenght - usedLength, usedLength);
			usedLength = totalLenght;
		}
		cantons.add(canton);
	}

	public void addStation(String name, int position ){
		Station station = new Station(name, position);
		
		stations.add(station);
		
		
	}
	
	public boolean isFull() {
		return usedLength == totalLenght;
	}

	public int getTotalLenght() {
		return totalLenght;
	}

	public int getUsedLength() {
		return usedLength;
	}

	public List<Canton> getCantons() {
		return cantons;
	}

	public List<Station> getStations() {
		return stations;
	}
	
	public Canton getCanton(int i){
		return cantons.get(i);
	}
	
	public Canton getCantonByPosition(int position) throws TerminusException {
		for (Canton canton : cantons) {
			if (canton.getEndPoint() > position) {
				return canton;
			}
		}
		throw new TerminusException();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Station> getFullPath(){
		return (ArrayList<Station>)  stations.clone();
	}

}
