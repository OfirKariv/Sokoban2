package view;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import common.Level;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class MainWindowController extends Observable implements Initializable, View {

	char[][] levelData;

	@FXML
	LevelDisplayer levelDisplayer = new LevelDisplayer();

	private List<String> params;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		levelDisplayer.setLevelData(levelData);
		// levelDisplayer.requestFocus();
		levelDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> levelDisplayer.requestFocus());
		levelDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				params = new LinkedList<String>();
				params.add("Move");

				switch (event.getCode())

				{
				case UP:
					params.add("up");
					break;
				case DOWN:
					params.add("down");
					break;
				case LEFT:
					params.add("left");
					break;
				case RIGHT:
					params.add("right");
					break;

				default:
					break;

				}

				setChanged();
				notifyObservers(params);
			}
		});

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
