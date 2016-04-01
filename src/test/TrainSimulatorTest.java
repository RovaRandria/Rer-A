package test;

import java.util.ArrayList;

import junit.framework.TestCase;
import engine.Canton;
import engine.Line;
import engine.Station;
import engine.Train;
import engine.TrainPattern;

public class TrainSimulatorTest extends TestCase {
	public ArrayList<Train> trains;
	private Train train;
	
	public TrainSimulatorTest(String fileName){
		super(fileName);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		Line l = new Line(10);
		Canton c = new Canton(1, 10, 1);
		ArrayList<Station> pattern = new ArrayList<Station>();
		for(int i = 0; i < 8; i++){
			Station station = new Station("Station"+i, i * 10);	
			pattern.add(station);
		}
		Boolean reversed = false;
		TrainPattern trpattern = new TrainPattern("RAVA", pattern,reversed);
		train = new Train(l,c,20,trpattern,	1);
		}
	
	/*public void testGetTrains() {
		assertNotNull("ArrayList is empty", trains);
	}*/
	
	/*public void testAddTrain() throws Exception{
		for(int i = 1; i <= 1; i++)
		{
			Line l = new Line(1);
			Canton c = new Canton(0,0,0);
			TrainPattern trpattern = new TrainPattern();
			setUp();	
			train = new Train(l,c,20,trpattern,1);
			trains.add(train);
		}
	}*/
}

