package view;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import common.Level;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MainWindowController extends Observable implements Initializable, View {

	char[][] levelData;

	@FXML
	LevelDisplayer levelDisplayer = new LevelDisplayer();

	private List<String> params;
	private HashMap<String, String> keyHM;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		levelDisplayer.setLevelData(levelData);
		setKeys();
		// levelDisplayer.requestFocus();
		levelDisplayer.addEventFilter(MouseEvent.ANY, (e) -> levelDisplayer.requestFocus());
		levelDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				params = new LinkedList<String>();
				params.add("Move");
				params.add(keyHM.get(event.getCode().toString()));

				setChanged();
				notifyObservers(params);
			}
		});

	}

	public void setKeys() {
		keyHM = new HashMap<String, String>();
		try {

			XMLDecoder keys = new XMLDecoder(new FileInputStream(new File("./setkey/keys.xml")));
			keyHM.put((String) keys.readObject(), "up");
			keyHM.put((String) keys.readObject(), "down");
			keyHM.put((String) keys.readObject(), "left");
			keyHM.put((String) keys.readObject(), "right");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public void start() {

	}

	public void openFile() {

		params = new LinkedList<String>();
		FileChooser fc = new FileChooser();
		fc.setTitle("Open file");
		fc.setInitialDirectory(new File("./resources"));
		// fc.setSelectedExtensionFilter(filter); add only xml, txt, obj files
		File chosen = fc.showOpenDialog(null);
		if (chosen != null) {
			params.add("Load");
			params.add(chosen.getAbsolutePath());
			setParams(params);
			setChanged();
			notifyObservers(params);

		}

	}

	public void saveFile() {

		params = new LinkedList<String>();
		FileChooser fc = new FileChooser();
		fc.setTitle("Save file");
		fc.setInitialDirectory(new File("./resources"));

		// fc.setSelectedExtensionFilter(filter); add only xml, txt, obj files
		File chosen = fc.showSaveDialog(null);
		System.out.println("save");
		if (chosen != null) {
			params.add("Save");
			params.add(chosen.getAbsolutePath());
			setParams(params);
			setChanged();
			notifyObservers(params);
		}
	}

	public void ExitFile() {

		params = new LinkedList<String>();
		params.add("Exit");
		setParams(params);
		setChanged();
		notifyObservers(params);

	}

	public void Display(Level myLevel) {

		levelDisplayer.setLevelData(myLevel.getCharMat());

	}

	@Override
	public void DisplayMess(String s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
