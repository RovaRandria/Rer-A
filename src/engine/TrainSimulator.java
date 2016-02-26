package engine;

import java.util.ArrayList;

public class TrainSimulator {

	private Line line;
	private Line reversedLine;
	private ArrayList<Train> trains;
	
	public TrainSimulator(String fileName) {
		LineBuilder lineBuilder = new LineBuilder();
		lineBuilder.buildLine(1200, 150,fileName);
		line = lineBuilder.getBuiltLine();
		if(line != null)
			reversedLine = line.getReversedLine();
		this.trains = new ArrayList<Train>();
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
	
	
}
