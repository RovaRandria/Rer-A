package engine;
/**
 * Data class describing a canton.
 * @author Matthieu
 * 
 */
public class Canton {
	private int id;
	private int startPoint;
	private int length;
	private Train occupyingTrain = null;
	private boolean blocked;

	public Canton(int id, int length, int startPoint) {
		this.id = id;
		this.length = length;
		this.startPoint = startPoint;
	}

	public int getLength() {
		return length;
	}

	public int getStartPoint() {
		return startPoint;
	}

	public int getEndPoint() {
		return startPoint + length;
	}
	/**
	 * Make a train enter the canton. The train's thread will wait if the canton is already 
	 * occupied or blocked.
	 * @param train
	 */
	public synchronized void enter(Train train) {
		while(occupyingTrain != null || blocked) {
			//System.out.println(toString() + " occupied !");
			// Train stopped just before canton start point !
			train.setPosition(startPoint - 1);
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}

		//System.out.println(toString() + " available !");
		//System.out.println("Canton changed successfully");
		Canton oldCanton = train.getCurrentCanton();
		train.setCurrentCanton(this);
		train.updatePosition();

		oldCanton.exit();
		occupyingTrain = train;

	}

	/**
	 * Make a train leave the canton. Notify the threads and let 
	 * enter one other train if there is one waiting
	 */
	public synchronized void exit() {
		occupyingTrain = null;
		notify();
		//System.out.println("Canton freed !");
	}
	
	/**
	 * Block the canton
	 */
	public synchronized void block(){
		System.out.println("On bloque le canton débutant à " + startPoint + " et terminant à " + (startPoint + length));
		blocked = true;
		
	}
	/**
	 * Unblock the canton and notify the threads and let 
	 * enter one train if there is one waiting
	 */
	public synchronized void unblock(){
		notify();
		blocked = false;
	}
	
	/**
	 * 
	 * @return true if there is a train on the canton, 
	 * false if not
	 */
	public boolean isFree() {
		return occupyingTrain == null;
	}

	@Override
	public String toString() {
		return "[id="+id+" start="+startPoint+" length="+length+"]";
	}

	public int getId() {
		return id;
	}

	public Train getOccupyingTrain() {
		return occupyingTrain;
	}

	public void setOccupyingTrain(Train occupyingTrain) {
		this.occupyingTrain = occupyingTrain;
	}

	

	public void setId(int id) {
		this.id = id;
	}

	public void setStartPoint(int startPoint) {
		this.startPoint = startPoint;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
