package model.policy;

import java.util.ArrayList;

import model.data.Box;
import model.data.Floor;
import model.data.GameObject;
import model.data.Target;

public class MySokobanPolicy implements Policy {

	private int numOfBoxToMove;

	public MySokobanPolicy() {
		numOfBoxToMove = 1;
	}

	/**
	 * gets a path via array and return true if according to the policy the path
	 * is valid
	 */
	public boolean isPathValid(ArrayList<GameObject> arr) {

		if (arr.size() < 1)
			return false;

		if (isWalkble(arr))
			return true;

		else
			return false;

	}

	/**
	 * 
	 * return true if the path can move
	 */
	public boolean isWalkble(ArrayList<GameObject> arr) {
		if (!(arr.get(arr.size() - 1) instanceof Floor || arr.get(arr.size() - 1) instanceof Target))
			return false;

		int count = 0;

		for (int i = 0; i < arr.size() - 1; i++) {

			if (count == numOfBoxToMove)
				return false;

			if (!(isObjValid(arr.get(i))))
				return false;
			count++;

		}

		return true;
	}

	/**
	 * checks if an object is valid to move
	 * 
	 * 
	 */
	public boolean isObjValid(GameObject obj) {

		if (obj instanceof Box)
			return true;
		return false;

	}

}
