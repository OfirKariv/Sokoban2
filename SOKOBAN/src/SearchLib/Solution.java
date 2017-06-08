package SearchLib;

import java.util.LinkedList;
import java.util.List;

public class Solution {

	public LinkedList<State> list;

	public Solution() {
		list = new LinkedList<>();
	}

	public Solution(List<State> list) {
		this.list = (LinkedList<State>) list;
	}

	public LinkedList<State> getList() {

		return list;
	}

	public void pushList(State s) {
		list.add(s);

	}

}
