package engine;

/**
 * Data class describing a station.
 * @author Rova
 *
 */
public class Station {
	
	private String name;
	private int position;
	private float timeToWait;
	public Station(String name, int position) {
		super();
		this.name = name;
		this.position = position;
		this.setTimeToWait(0.5f);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public float getTimeToWait() {
		return timeToWait;
	}

	public void setTimeToWait(float timeToWait) {
		this.timeToWait = timeToWait;
	}
	
	
	
	
}
