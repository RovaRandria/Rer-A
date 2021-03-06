package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import engine.Canton;
import engine.Event;
import engine.Line;
import engine.Station;
import engine.Train;

/**
 * Describes the dashboard that draws the simulation's components.
 * @author Rova
 *
 */
public class SimulationDashboard extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Line line;
	private Line reversedLine;
	private List<Train> trains = new ArrayList<Train>();
	private static final int START_X = 50;
	private static final int START_Y = 100;
	private final int INIT_DISTANCE = 10;
	private int distancePerPixel = INIT_DISTANCE;
	private HoursPanel hourpan;
	private EventsPanel evpan;
	private ArrayList<Event> events = new ArrayList<Event>();
	private boolean eventmode = false;
	
	public SimulationDashboard(final HoursPanel hourpan, final EventsPanel evpan) {
		this.hourpan = hourpan;
		this.evpan = evpan;
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			/**
			 * A mouse click on a station displays the corresponding schedules.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!eventmode) {
					if(e.getY() >= START_Y-10 && e.getY() <= START_Y+10) {
						List<Station> sta = line.getStations();
						for(Station s : sta) {
							if(e.getX()-START_X >= s.getPosition()/distancePerPixel-10 && e.getX()-START_X <= s.getPosition()/distancePerPixel+10) {
								hourpan.setStation(s);
								hourpan.setCurrentPan(true);
								break;
							}
						}
					}
					else if(e.getY() >= START_Y+140 && e.getY() <= START_Y+160) {
						List<Station> sta = reversedLine.getStations();
						for(Station s : sta) {
							if(e.getX()-START_X >= s.getPosition()/distancePerPixel-10 && e.getX()-START_X <= s.getPosition()/distancePerPixel+10) {
								hourpan.setStation(s);
								hourpan.setCurrentPan(true);
								break;
							}
						}
					}
				}
				else {
					if(e.getY() > SimulationDashboard.this.getHeight()/2) {
						evpan.setPosition((line.getTotallength()/10 + START_X - e.getX()) * getDistancePerPixel());
						evpan.setDirection(true);
					}
					else {
						evpan.setPosition((e.getX()-START_X)*getDistancePerPixel());
						evpan.setDirection(false);
					}
					eventmode = false;
				}
			}
		});
	}

	public void addEvent(Event e) {
		events.add(e);
	}
	
	public void enterEventMode() {
		eventmode = true;
	}
	
	
	public void setReversedLine(Line reversedLine) {
		this.reversedLine = reversedLine;
	}
	/**
	 * Paints the dashboard.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		printLine(line, START_X, START_Y, g2, false);
		printLine(reversedLine, START_X, START_Y + 150, g2, true);
		printTrains(START_X, START_Y, g2);
	}
	/**
	 * Draws the line.
	 * @param line
	 * @param startX
	 * @param startY
	 * @param g2
	 * @param reverse
	 */
	private void printLine(Line line, int startX, int startY, Graphics2D g2, boolean reverse) {
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(200,200,0,255));
		for (Canton canton : line.getCantons()) {
			int startPoint = canton.getStartPoint();
			int endPoint = canton.getEndPoint();
			g2.drawLine(startX + startPoint/distancePerPixel, startY - 15, startX + startPoint/distancePerPixel, startY + 15);
			g2.drawLine(startX + endPoint/distancePerPixel, startY - 15, startX + endPoint/distancePerPixel, startY + 15);
		}
		
		
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(8));
		
		
		g2.drawLine(startX, startY, startX + line.getTotallength()/distancePerPixel, startY);
		int i = 0;
		while( i <line.getStations().size()){
			if(i%3 == 0)
				drawStation(startX, startY, g2, line.getStations().get(i), startY+20);
			else if(i%3 == 1)
				drawStation(startX, startY, g2, line.getStations().get(i), startY+40);
			else
				drawStation(startX, startY, g2, line.getStations().get(i), startY+60);
			i++;
		}
		for(Event e : events) {
			if(e.getDuration() > 0 && e.isReverse() == reverse) {
				g2.setColor(Color.ORANGE);
				g2.drawRect(e.getPosition()/getDistancePerPixel()-5 + startX, startY-5, 10, 10);
			}
		}
	}
	
	/**
	 * Draws the trains.
	 * @param startX
	 * @param startY
	 * @param g2
	 */
	private void printTrains(int startX, int startY, Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.setStroke(new BasicStroke(6));
		for (Train train : trains) {
			
			if(train.getLine() == line) {
				g2.setFont(new Font("Dialog", Font.PLAIN, 20));
				g2.drawString(train.getCode(), startX + train.getPosition()/distancePerPixel, startY - 25);
				g2.drawLine(startX + train.getPosition()/distancePerPixel, startY - 5, startX + train.getPosition()/distancePerPixel, startY + 5);
			}
			else if(train.getLine() == reversedLine){
				g2.setFont(new Font("Dialog", Font.PLAIN, 20));
				g2.drawString(train.getCode(), startX + train.getPosition()/distancePerPixel, startY + 125);
				g2.drawLine(startX + train.getPosition()/distancePerPixel, startY + 145, startX + train.getPosition()/distancePerPixel, startY + 155);
			}
		}	
	}
	/**
	 * Draws the station.
	 * @param startX
	 * @param startY
	 * @param g2
	 * @param s
	 * @param textPos
	 */
	private void drawStation(int startX, int startY, Graphics2D g2, Station s, int textPos){
		int x = startX + s.getPosition()/distancePerPixel;
		int y = startY;
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

	public int getInitDistance() {
		return INIT_DISTANCE;
	}

	public void setDistancePerPixel(int distancePerPixel) {
		this.distancePerPixel = distancePerPixel;
	}

	public int getDistancePerPixel() {
		return distancePerPixel;
	}

}
