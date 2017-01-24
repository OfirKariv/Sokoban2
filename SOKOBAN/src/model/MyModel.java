package model;

import java.util.LinkedList;
import java.util.Observable;

import common.Level;
import model.data.GameCharacter;
import model.data.Position;
import model.policy.LevelChanger;

public class MyModel extends Observable implements Model {

	private Level myLevel = null;
	private LevelChanger change = new LevelChanger();
	private int relevantPlayer;

	public MyModel() {
		myLevel = new Level();
		setRelevantPlayer(0); // default
	}

	public void setLevel(Level level) {
		this.myLevel = level;
	}

	public void setRelevantPlayer(int relevantPlayer) {
		this.relevantPlayer = relevantPlayer;
	}

	@Override
	public void load(String path) {

		LoadLevelFactory lvlLoad = new LoadLevelFactory();
		setLevel(lvlLoad.toFile(path));

		this.setChanged();
		LinkedList<String> params = new LinkedList<String>();
		params.add("Display");
		this.notifyObservers(params);

	}

	@Override
	public void save(String path) {

		SaveLevelFactory lvlSav = new SaveLevelFactory();
		lvlSav.setFile(getCurrentLevel(), path);

	}

	@Override
	public Level getCurrentLevel() {

		return myLevel;

	}

	@Override
	public void move(String direction) {

		change.setLevel(this.getCurrentLevel());
		GameCharacter player = myLevel.getCharacters().get(relevantPlayer);
		Position playerPos = player.getPosition();
		switch (direction) {

		case "up":

			change.pathUp(playerPos);
			break;

		case "down":

			change.pathDown(playerPos);
			break;

		case "left":

			change.pathLeft(playerPos);
			break;

		case "right":

			change.pathRight(playerPos);

			break;

		default:
			break;

		}

		change.LevelChange();
		setLevel(change.getLevel());

		this.setChanged();
		LinkedList<String> params = new LinkedList<String>();
		params.add("Display");
		this.notifyObservers(params);

		if (myLevel.isComplete()) {
			System.out.println("Level completed!");

		}

	}
}