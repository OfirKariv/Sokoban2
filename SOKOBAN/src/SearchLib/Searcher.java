package SearchLib;

import java.util.ArrayList;

import sokobanSearch.Action;

public interface Searcher<T> {

	public int getNumberOfNodesEvaluated();

	public ArrayList<Action> search(Searchable<T> s);

}
