package model;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import common.Level;
import model.data.GameCharacter;
import model.data.Position;
import model.db.LevelInfo;
import model.db.User;
import model.db.UserData;
import model.policy.LevelChanger;

public class MyModel extends Observable implements Model {

	private Level myLevel = null;
	private LevelChanger change = new LevelChanger();
	private int relevantPlayer;
	private User dbUser;
	private UserData dbData;
	private LevelInfo dbLevel;

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
		/////////////////////////////////////////
		myLevel.setLevelName(path);
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

	///////////////////////////////
	@Override
	public void saveToDB(List<String> params) {
		dbData = new UserData();
		if (params != null) {
			for (int i = 0; i < 3; i++)
				if (params.get(i) == null) {
					/* TODO - add error displayer */

				}

			String name = params.remove(0);
			dbUser = new User(name);
			String steps = params.remove(0);
			dbData.setSteps(Integer.parseInt(steps));
			String time = params.remove(0);
			dbData.setTimeFinished(Integer.parseInt(time));
			dbLevel = new LevelInfo(myLevel.getLevelName());

		}

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
		case "E":

			return;

		default:
			return;

		}

		change.LevelChange();
		setLevel(change.getLevel());

		this.setChanged();
		LinkedList<String> params = new LinkedList<String>();
		params.add("Display");
		this.notifyObservers(params);

		if (myLevel.isComplete()) {
			params.add("Display");
			params.add("Fin");
			this.setChanged();
			this.notifyObservers(params);

		}

	}

	@Override
	public int getSteps() {

		return myLevel.getSteps();

	}

}