package view;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import common.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

public class MainWindowController extends Observable implements Initializable {

	char[][] levelData;

	@FXML
	LevelDisplayer levelDisplayer;

	private List<String> params;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		levelDisplayer.setLevelData(levelData);

	}

	public void setParams(List<String> params) {
		this.params = params;
	}

	public void start() {

		System.out.println("start");

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

		System.out.println("save");

	}

	public void ExitFile() {

		System.out.println("exit");

	}

	public void Display(Level myLevel) {
		LevelDisplayer displayer = new LevelDisplayer();
		displayer.setLevelData(myLevel.getCharMat());
		displayer.redraw();

	}

}
