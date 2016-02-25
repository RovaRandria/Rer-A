package engine;

/**
 * @author tliu@u-cergy.fr
 */
public class LineBuilder {
	private Line line;

	public void buildLine(int totalLength, int cantonLength) {
		/*line = new Line(totalLength);
		int id = 1;
		while (!line.isFull()) {
			line.addCanton(id, cantonLength);
			id++;
		}*/
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
		
		line.addStation("Neuville Université", 200);
		
		line.addStation("Conflans Fin d'Oise", 250);
		
		line.addStation("Achères ville", 310);
		line.addStation("Maison Laffitte", 400);
		
		line.addStation("Sartrouville", 450);
		line.addStation("Marne-La-Vallée", 600);
		
	}

	public Line getBuiltLine() {
		return line;
	}
}