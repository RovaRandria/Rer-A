package engine;
/**
 * Data class describing an event.
 * @author Rova
 * 
 */
public class Event {

	private int position;
	private int duration;
	private boolean reverse;
	private String text;
	
	public Event(int position, int duration, boolean reverse, String text) {
		super();
		this.position = position;
		this.duration = duration;
		this.reverse = reverse;
		this.text = text;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void decrementDuration() {
		if(duration > 0)
			duration--;
	}
	
	public int getPosition() {
		return position;
	}

	public int getDuration() {
		return duration;
	}

	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text + " à la position " + position + " : " + duration + " minutes restantes.";
	}
	
}
