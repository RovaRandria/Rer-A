package engine;

import java.util.ArrayList;

public class TrainPattern {
	private String patternCode;
	private ArrayList<Station> pattern= new ArrayList<Station>();
	private Boolean reversed;
	
	public Boolean getReversed() {
		return reversed;
	}
	public void setReversed(Boolean reversed) {
		this.reversed = reversed;
	}

	
	
	
	public TrainPattern(String patternCode, ArrayList<Station> pattern,Boolean reversed) {
		super();
		this.patternCode = patternCode;
		this.pattern = pattern;
		this.reversed=reversed;
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
		ArrayList<Station> clone = ((ArrayList<Station>) pattern.clone());
		return clone;
	}
	public void setPattern(ArrayList<Station> pattern) {
		this.pattern = pattern;
	}
	/*public TrainPattern createPattern(Line line, ArrayList<Integer>al,String code,int reversedValue){
		TrainPattern tp=null;
		Boolean reversed=false;
		ArrayList<Station> newPath= new ArrayList<Station>();
		for(int i=0;i<al.size();i++){
			if(al.get(i)==1){
				newPath.add(line.getFullPath().get(i));
			}
			
		}
		if(reversedValue==1){
			reversed=true;
		}
		tp= new TrainPattern(code,newPath,reversed);
		return tp; 
	}
	*/
	
}
