package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import common.Level;
import model.calc.LevelLoader;
import model.calc.MyObjectLevelLoader;
import model.calc.MyTextLevelLoader;
import model.calc.MyXMLLevelLoader;

public class LoadLevelFactory {

    private HashMap<String, LevelLoader> fileType;

    public LoadLevelFactory() {

	fileType = new HashMap<String, LevelLoader>();
	fileType.put("txt", new MyTextLevelLoader());
	fileType.put("xml", new MyXMLLevelLoader());
	fileType.put("obj", new MyObjectLevelLoader());

    }

    public Level toFile(String path) {

	Level myLevel = new Level();
	String[] args = path.split("\\.");

	try {
	    myLevel = fileType.get(args[1]).loadLevel(new FileInputStream(path));
	} catch (ClassNotFoundException | IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return myLevel;

    }

}
