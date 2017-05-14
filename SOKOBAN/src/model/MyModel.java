package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import org.hibernate.query.Query;

import common.Level;
import db.DBManager;
import db.HbrntDBManager;
import db.LevelInfo;
import db.User;
import db.UserData;
import model.data.GameCharacter;
import model.data.Position;
import model.policy.LevelChanger;

public class MyModel extends Observable implements Model {

	private Level myLevel = null;
	private LevelChanger change = new LevelChanger();
	private int relevantPlayer;
	private User user;
	private UserData ud;
	private LevelInfo level;
	private DBManager db;

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

		if (params != null) {
			for (int i = 0; i < 3; i++)
				if (params.get(i) == null) {
					/* TODO - add error displayer */

				}
			int lvlID, steps, time;
			db = new HbrntDBManager();
			String name = params.remove(0);
			// add user to DBManager
			db.addUser(name);

			// Add Level to DBManager

			lvlID = db.addLevel(myLevel.getLevelName());

			// Add userData

			String timeStr = params.remove(0);
			time = Integer.parseInt(timeStr);
			String stepsStr = params.remove(0);
			steps = Integer.parseInt(stepsStr);

			db.addUserData(name, lvlID, steps, time);

		}
	}

	public Query<UserData> getFromDB() {

		if (db == null)
			return null;

		db.getTable(myLevel.getLevelName());
		return null;
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