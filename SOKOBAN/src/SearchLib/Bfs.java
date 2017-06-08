package SearchLib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import sokobanSearch.Action;

public class Bfs<T> extends CommonSearcher<T> {

	private HashSet<State<T>> closedSet;

	public Bfs() {
		super();
		closedSet = new HashSet<State<T>>();

	}

	@Override
	public ArrayList<Action> search(Searchable<T> s) {

		addToOpenList(s.getInitialState());

		while (!openList.isEmpty()) {

			State<T> n = popOpenList();
			closedSet.add(n);

			if (n.equals(s.getGoalState()))
				return backTrace(n, s.getInitialState());

			ArrayList<State<T>> successors = (ArrayList<State<T>>) s.getAllPossibleStates(n);
			for (State<T> state : successors) {
				if (!closedSet.contains(state) && (!openList.contains(state))) {

					state.setCameFrom(n);
					addToOpenList(state);

				}

				else {
					if (!openList.contains(state)) {
						addToOpenList(state);

					} else {
						State<T> temp = state;
						openList.remove(state);
						addToOpenList(temp);

					}

				}

			}

		}
		return null;

	}

	public ArrayList<Action> backTrace(State goal, State start) {

		ArrayList<Action> actions = new ArrayList<>();
		State<T> s = goal;
		// actions.add(s.getAction());

		while ((s.getCameFrom() != null)) {
			actions.add(0, s.getAction());
			s = s.getCameFrom();

		}

		return actions;
	}

	public double getCost(State current, State start) {
		State s = current;
		double count = 0;
		while (s != null) {
			count += s.getCost();
			s = s.getCameFrom();

		}
		return count;

	}

}
