package engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Data class describing a line.
 * @author Rova
 * 
 */
public class Line {

	private int totallength;
	private int usedLength = 0;

	private List<Canton> cantons = new ArrayList<Canton>();
	private ArrayList<Station> stations = new ArrayList<Station>();
	private HashMap<String,TrainPattern> patterns = new HashMap<String,TrainPattern>();
	
	public Line(int totallength) {
		this.totallength = totallength;
	}
	/**
	 * Method used to add a canton to the line.
	 * @param id The id of the new canton.
	 * @param cantonLength The length of the new canton.
	 */
	public void addCanton(int id, int cantonLength) {
		Canton canton;
		if (usedLength + cantonLength <= totallength) {
			canton = new Canton(id, cantonLength, usedLength);
			usedLength += cantonLength;
		} else {
			canton = new Canton(id, totallength - usedLength, usedLength);
			usedLength = totallength;
		}
		cantons.add(canton);
	}
	/**
	 * Method used to add a station to the line.
	 * @param name The name of the new station.
	 * @param position The position on the line of the new staton.
	 */
	public void addStation(String name, int position ){
		Station station = new Station(name, position);
		
		stations.add(station);
		
		
	}
	
	public boolean isFull() {
		return usedLength == totallength;
	}

	public int getTotallength() {
		return totallength;
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
	
	/**
	 * Method that returns the canton at a position on the line.
	 * @param position
	 * @return
	 * @throws TerminusException
	 */
	public Canton getCantonByPosition(int position) throws TerminusException {
		for (Canton canton : cantons) {
			if (canton.getEndPoint() > position) {
				//System.out.println("On cherche le canton à la position " + position +". C'est le canton "+canton.toString());
				return canton;
			}
		}
		throw new TerminusException();
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Station> getFullPath(){
		return (ArrayList<Station>)  stations.clone();
	}
	
	/**
	 * Builds the reversedLine of the line.
	 * @return
	 */
	public Line getReversedLine(){
		Line reversedLine = new Line(this.totallength);
		int i;
		for(i = cantons.size()-1;i>=0;i--){
			reversedLine.addCanton(cantons.size()-1-i, cantons.get(i).getLength());
		}
		for(i = stations.size()-1;i>=0;i--){
			reversedLine.addStation(stations.get(i).getName(), totallength - stations.get(i).getPosition());
		}
		
		
		return reversedLine;
	}
	/**
	 * Blocks the canton located at a position.
	 * @param position
	 * @throws TerminusException
	 */
	public void blockCanton(int position) throws TerminusException{
		Canton c = getCantonByPosition(position);
		c.block();
	}
	/**
	 * Unblocks the canton located at a position.
	 * @param position
	 * @throws TerminusException
	 */
	public void unblockCanton(int position) throws TerminusException{
		Canton c = getCantonByPosition(position);
		c.unblock();
	}
	public HashMap<String,TrainPattern> getPatterns() {
		return patterns;
	}

	public void setPatterns(HashMap<String,TrainPattern> patterns) {
		this.patterns = patterns;
	}
}
