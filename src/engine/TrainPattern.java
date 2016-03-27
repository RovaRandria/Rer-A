package engine;

import java.util.ArrayList;

public class TrainPattern {
	private String patternCode;
	private ArrayList<Station> pattern= new ArrayList<Station>();
	
	
	
	public TrainPattern(String patternCode, ArrayList<Station> pattern) {
		super();
		this.patternCode = patternCode;
		this.pattern = pattern;
	}
	public TrainPattern() {
		// TODO Auto-generated constructor stub
	}
	public String getPatternCode() {
		return patternCode;
	}
	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
	public ArrayList<Station> getPattern() {
		return (ArrayList<Station>) pattern.clone();
	}
	public void setPattern(ArrayList<Station> pattern) {
		this.pattern = pattern;
	}
	public TrainPattern createPattern(Line line, ArrayList<Integer>al,String code){
		TrainPattern tp=null;
		ArrayList<Station> newPath= new ArrayList<Station>();
		for(int i=0;i<al.size();i++){
			if(al.get(i)==1){
				newPath.add(line.getFullPath().get(i));
			}
			
		}
		tp= new TrainPattern(code,newPath);
		return tp; 
	}
	
	
}
