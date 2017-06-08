package SearchLib;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import sokobanSearch.Action;

public abstract class CommonSearcher<T> implements Searcher<T> {

	protected PriorityQueue<State<T>> openList;
	private int evaluatedNodes;

	public CommonSearcher() {
		// openList = new PriorityQueue<State>();
		// openList = new PriorityQueue<>(new Comparator<Integer>(State s1,
		// State s2)-> s1.)
		openList = new PriorityQueue<State<T>>();
		evaluatedNodes = 0;

	}

	@Override
	public abstract ArrayList<Action> search(Searchable<T> s);

	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}

	public void addToOpenList(State s) {
		openList.add(s);

	}

	public void setEvaluatedNodes(int evaluatedNodes) {
		this.evaluatedNodes = evaluatedNodes;
	}

	public State<T> popOpenList() {
		evaluatedNodes++;
		return openList.poll();

	}

}
