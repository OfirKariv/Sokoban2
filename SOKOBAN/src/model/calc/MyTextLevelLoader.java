package model.calc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import model.GameObjectFactory;
import model.data.Floor;
import model.data.GameCharacter;
import model.data.GameObject;
import model.data.GeneralMovable;
import common.Level;
import model.data.Target;

public class MyTextLevelLoader implements LevelLoader, Serializable {

    private Level outLevel = null;
    private ArrayList<ArrayList<GameObject>> outStaticPattern = null;
    private ArrayList<ArrayList<GeneralMovable>> outMovables = null;
    private ArrayList<GameCharacter> outCharacters = null;
    private ArrayList<Target> outTarget = null;

    public MyTextLevelLoader() {

    }

    @Override
    public Level loadLevel(InputStream in) {
	int i = 0;

	outStaticPattern = new ArrayList<ArrayList<GameObject>>();
	outMovables = new ArrayList<ArrayList<GeneralMovable>>();
	outCharacters = new ArrayList<GameCharacter>();
	outTarget = new ArrayList<Target>();
	outLevel = new Level();

	String textLine;
	try {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    while ((textLine = reader.readLine()) != null) {

		outStaticPattern.add(new ArrayList<GameObject>());
		outMovables.add(new ArrayList<GeneralMovable>());
		for (int j = 0; j < textLine.length(); j++) {
		    GameObject temp = null;
		    char c = textLine.charAt(j);
		    try {
			GameObjectFactory fac = new GameObjectFactory();
			temp = fac.createGameObject(c);
			temp.setPosition(i, j);
			setNewObject(temp);
		    } catch (Exception e) {
			break;
		    }

		}
		i++;

	    }
	    outLevel.setMovables(outMovables);
	    outLevel.setStaticPattern(outStaticPattern);
	    outLevel.setCharacters(outCharacters);
	    outLevel.setTargets(outTarget);
	    return outLevel;

	} catch (Exception e) {
	    return null;
	}

    }

    public void setNewObject(GameObject obj) {

	int x = obj.getPosition().getX();

	if (obj instanceof GeneralMovable)

	{
	    outMovables.get(x).add((GeneralMovable) obj);

	    if (obj instanceof GameCharacter) {
		outCharacters.add((GameCharacter) obj);

	    }

	    Floor floor = new Floor();
	    floor.setPosition(obj.getPosition().getX(), obj.getPosition().getY());
	    obj = floor;

	}

	else {
	    outMovables.get(x).add(null);
	}

	outStaticPattern.get(x).add(obj);
	if (obj instanceof Target)
	    outTarget.add((Target) obj);

    }

}
