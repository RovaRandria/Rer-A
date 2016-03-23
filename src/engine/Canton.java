package engine;

public class Canton {
	private int id;
	private int startPoint;
	private int length;
	private Train occupyingTrain = null;
	private Station enterStation;
	private Station exitStation;

	public Canton(int id, int length, int startPoint) {
		this.id = id;
		this.length = length;
		this.startPoint = startPoint;
		enterStation = null;
		exitStation = null;
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

	public synchronized void enter(Train train) {
		if (occupyingTrain != null) {
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

	public synchronized void exit() {
		occupyingTrain = null;
		notify();
		//System.out.println("Canton freed !");
	}

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

	public Station getEnterStation() {
		return enterStation;
	}

	public void setEnterStation(Station enterStation) {
		this.enterStation = enterStation;
	}

	public Station getExitStation() {
		return exitStation;
	}

	public void setExitStation(Station exitStation) {
		this.exitStation = exitStation;
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
