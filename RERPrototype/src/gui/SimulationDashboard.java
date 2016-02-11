package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import engine.Canton;
import engine.Line;
import engine.LineBuilder;
import engine.Station;
import engine.Train;


public class SimulationDashboard extends JPanel {
	private Line line;
	private List<Train> trains = new ArrayList<Train>();
	private static final int START_X = 20;
	private static final int START_Y = 150;
	
	public SimulationDashboard() {
		LineBuilder lineBuilder = new LineBuilder();
		lineBuilder.buildLine(1200, 150);
		line = lineBuilder.getBuiltLine();
	}

	public void addTrain(Train train) {
		trains.add(train);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		printLine(g2);
		printTrains(g2);
	}

	private void printLine(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(8));
		
		
		g2.drawLine(START_X, START_Y, START_X + line.getTotalLenght(), START_Y);
		int start = START_X;
		int i = 0;
		while( i <line.getStations().size()){
			if(i%3 == 0)
				drawStation(g2, line.getStations().get(i), START_Y+20);
			else if(i%3 == 1)
				drawStation(g2, line.getStations().get(i), START_Y+40);
			else
				drawStation(g2, line.getStations().get(i), START_Y+60);
			i++;
		}
		
		/*for (Canton canton : line.getCantons()) {
			int startPoint = canton.getStartPoint();
			int endPoint = canton.getEndPoint();
			if(canton.getClass().getName().equals("demothreading.CantonGare")){
				g2.setFont(new Font("Dialog", Font.PLAIN, 25));
				g2.drawString("Gare " + canton.getId(), startPoint + 100, START_Y + 30);
				g2.drawLine(START_X + startPoint, START_Y - 20, START_X + startPoint, START_Y + 20);
				g2.drawLine(START_X + endPoint, START_Y - 10, START_X + endPoint, START_Y + 10);
				
			}else{
				g2.setFont(new Font("Dialog", Font.PLAIN, 25));
				g2.drawString("Canton " + canton.getId(), startPoint + 100, START_Y + 30);
				g2.drawLine(START_X + startPoint, START_Y - 10, START_X + startPoint, START_Y + 10);
				g2.drawLine(START_X + endPoint, START_Y - 10, START_X + endPoint, START_Y + 10);
			}
		}*/
	}

	private void printTrains(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(6));
		for (Train train : trains) {
			g2.setFont(new Font("Dialog", Font.PLAIN, 20));
			g2.drawString("Train", START_X + train.getPosition(), START_Y - 25);
			g2.drawLine(START_X + train.getPosition(), START_Y - 5, START_X + train.getPosition(), START_Y + 5);
		}
	}

	private void drawStation(Graphics2D g2, Station s, int textPos){
		int x = START_X + s.getPosition();
		int y = START_Y;
		g2.setColor(Color.RED);
		g2.fillOval(x-10, y-10, 20, 20);
		g2.setColor(Color.WHITE);
		g2.fillOval(x-7, y-7, 14, 14);
		
		
		g2.setColor(Color.black);
		g2.setFont(new Font("Dialog", Font.PLAIN, 15));
		g2.drawString(s.getName(), x-(int)((s.getName().length()/2)*7.5), textPos);
	}
	
	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public List<Train> getTrains() {
		return trains;
	}

	public void setTrains(List<Train> trains) {
		this.trains = trains;
	}

}
