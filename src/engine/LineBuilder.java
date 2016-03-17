package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author tliu@u-cergy.fr
 */
public class LineBuilder {
	private Line line;

	public void buildLine(String fileName) {
		/*line = new Line(totalLength);
		int id = 1;
		while (!line.isFull()) {
			line.addCanton(id, cantonLength);
			id++;
		}
		line = new Line(600);
		line.addCanton(1, 50);
		line.addCanton(2, 50);
		line.addCanton(3, 25);
		line.addCanton(4, 25);
		line.addCanton(5, 25);
		line.addCanton(6, 25);
		line.addCanton(7, 100);
		
		line.addCanton(8, 100);
		line.addCanton(9, 100);
		
		line.addCanton(10, 33);
		line.addCanton(11, 33);
		line.addCanton(12, 34);
		
		line.addStation("Cergy-Le-Haut", 0);
		line.addStation("Cergy-StChristophe", 100);
		line.addStation("Cergy-Prefecture", 150);
		
		line.addStation("Neuville Universit�", 200);
		
		line.addStation("Conflans Fin d'Oise", 250);
		
		line.addStation("Ach�res ville", 310);
		line.addStation("Maison Laffitte", 400);
		
		line.addStation("Sartrouville", 450);
		line.addStation("Marne-La-Vall�e", 600);
		*/
		
		String l;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while ((l = br.readLine()) != null) {
				String separator1="\\:";
				String separator2="\\|";
				String separator3="\\,";
				String str1[]=l.split(separator1);
				if(str1[0].equals("Cantons")){
					String str2[]=str1[1].split(separator2);
					int tlenght = Integer.parseInt(str2[0]);
					line=new Line(tlenght);
					String str3[]=str2[1].split(separator3);
					int i=0;
					while(i<str3.length){
						line.addCanton(i+1,Integer.parseInt(str3[i]));
						i++;
					}
				}
				else if(str1[0].equals("Stations")){
					String str2[]=str1[1].split(separator2);
					int j=0;
					while(j<str2.length){
						String str3[]=str2[j].split(separator3);
						line.addStation(str3[0],Integer.parseInt(str3[1]));
						j++;
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Le chemin du fichier texte entré est incorrect");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
	}

	public Line getBuiltLine() {
		return line;
	}
}