package engine;

public class Schedule {
	private int time;
	private Train train;
	private Station station;
	
	
	
	public Schedule(int time, Train train, Station station) {
		super();
		this.time = time;
		this.train = train;
		this.station = station;
	}
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Train getTrain() {
		return train;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	
	
}
