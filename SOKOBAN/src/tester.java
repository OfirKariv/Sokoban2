import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import SearchLib.Bfs;
import SearchLib.Searchable;
import SearchLib.Searcher;
import common.Level;
import model.calc.MyTextLevelLoader;
import model.data.Position;
import sokobanSearch.Action;
import sokobanSearch.PlayerSearchable;

public class tester {

	public static void main(String[] args) {

		MyTextLevelLoader lvlLoad = new MyTextLevelLoader();
		try {
			Level level = lvlLoad.loadLevel(new FileInputStream("C:/Users/ofirk/Desktop/SIMPLE.txt"));
			Position init = level.getCharacters().get(0).getPosition();
			Position goal = new Position(7, 13);

			Searcher<Position> searcher = new Bfs<Position>();
			// or any Searchable
			Searchable<Position> searchable = new PlayerSearchable(level, init, goal);

			ArrayList<Action> actions = searcher.search(searchable);
			// see the actions
			if (actions == null)
				System.out.println("no path");
			else {
				for (Action a : actions)
					System.out.println(a);
				System.out.println("# of nodes evaluated " + searcher.getNumberOfNodesEvaluated());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
