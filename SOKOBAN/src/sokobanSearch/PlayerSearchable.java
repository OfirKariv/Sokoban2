package sokobanSearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import SearchLib.Searchable;
import SearchLib.State;
import common.Level;
import model.calc.MyTextLevelLoader;
import model.data.Position;
import model.policy.LevelChanger;

public class PlayerSearchable implements Searchable<Position> {

	private Level level;
	private Position initPos;
	private Position goalPos;

	public PlayerSearchable() {

	}

	public PlayerSearchable(Level level, Position initPos, Position goalPos) {
		this.level = level;
		this.initPos = initPos;
		this.goalPos = goalPos;

	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public State<Position> getInitialState() {

		State<Position> initState = new State<>();
		initState.setCost(0);

		Position player = initPos;
		initState.setState(player);

		return initState;
	}

	@Override
	public State<Position> getGoalState() {
		State<Position> goalState = new State<>();
		Position player = goalPos;
		goalState.setState(player);

		return goalState;
	}

	@Override
	public List<State<Position>> getAllPossibleStates(State<Position> s) {
		LevelChanger change = new LevelChanger();
		change.setLevel(this.getLevel());
		ArrayList<State<Position>> list = new ArrayList<State<Position>>();
		boolean up, down, left, right;
		Position upPos, downPos, leftPos, rightPos;
		Position curPos = s.getState();
		final int x = curPos.getX();
		final int y = curPos.getY();

		upPos = new Position();
		upPos.setX(x - 1);
		upPos.setY(y);

		downPos = new Position();
		downPos.setX(x + 1);
		downPos.setY(y);

		leftPos = new Position();
		leftPos.setX(x);
		leftPos.setY(y - 1);

		rightPos = new Position();
		rightPos.setX(x);
		rightPos.setY(y + 1);

		if (change.isClear(upPos)) {
			State<Position> upState = new State<>();
			upState.setCameFrom(s);
			upState.setCost(s.getCost() + 1);
			upState.setState(upPos);
			upState.setAction(new Action("Move up"));
			list.add(upState);

		}

		if (change.isClear(downPos)) {
			State<Position> downState = new State<>();
			downState.setCameFrom(s);
			downState.setCost(s.getCost() + 1);
			downState.setState(downPos);
			downState.setAction(new Action("Move down"));
			list.add(downState);
		}

		if (change.isClear(leftPos)) {
			State<Position> leftState = new State<>();
			leftState.setCameFrom(s);
			leftState.setCost(s.getCost() + 1);
			leftState.setState(leftPos);
			leftState.setAction(new Action("Move left"));
			list.add(leftState);
		}

		if (change.isClear(rightPos)) {
			State<Position> rightState = new State<>();
			rightState.setCameFrom(s);
			rightState.setCost(s.getCost() + 1);
			rightState.setState(rightPos);
			rightState.setAction(new Action("Move right"));
			list.add(rightState);
		}

		return list;
	}

}
