package sokobanSearch;

public class Action {

	private String action;

	public Action(String action) {
		this.action = action;

	}

	public String getAction() {
		return action;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return action.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return action.equals(obj);
	}

	@Override
	public String toString() {
		return getAction();
	}

}
