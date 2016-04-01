package test;
import java.util.ArrayList;

import engine.Canton;
import engine.Line;
import engine.Station;
import engine.Train;
import engine.TrainPattern;
import junit.framework.*;

public class TrainTest extends TestCase {
	private Train train;
	public TrainTest(String name){
		super(name);
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
	public void testSetPosition(){
		Train m1 = train;
		m1.setPosition(20);
		Train m2 = train;
		m2.setPosition(20);
		assertSame("Les deux objets sont identiques", m1.getPosition(), m2.getPosition());
	}
	
	
	public void testGetStartTime(){
		assertEquals("Wrong StartTime", (float)1.0, train.getStartTime());
	}
	
	public void testSetStartTime() {
		train.setStartTime(30);
		assertEquals("Wrong StartTime", (float)30.0, train.getStartTime());
	}
		
	public void testTrain(){
		assertNotNull("Train was not created", train);
	}
	
	public void testGetLine(){
		assertEquals("Line incorrect", 10,train.getLine().getTotallength());
	}
	

	/*public synchronized void testStartRunning(){
	
	}*/
}	

	