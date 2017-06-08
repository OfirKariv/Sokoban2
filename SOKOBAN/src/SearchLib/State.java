package SearchLib;

import sokobanSearch.Action;

public class State<T> implements Comparable<State<T>> {

	private T state;
	private double cost;
	private State<T> cameFrom;
	private Action action;

	public State() {

	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public State(State<T> state) {
		this.state = state.getState();
		this.cost = state.getCost();
		this.cameFrom = state.cameFrom;
		this.action = state.action;
	}

	public T getState() {
		return state;
	}

	public void setState(T state) {
		this.state = state;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public State<T> getCameFrom() {
		return cameFrom;
	}

	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}

	@Override
	public int hashCode() {
		return state.hashCode();
	}

	@Override
	public String toString() {
		return "[" + state.toString() + action + "]";
	}

	@Override
	public int compareTo(State<T> s) {
		return (int) (this.cost - s.cost);
	}

	@Override
	public boolean equals(Object obj) {

		State<T> temp = (State<T>) obj;
		return state.equals(temp.getState());
	}

}
