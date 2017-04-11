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
import java.util.Optional;
import java.util.ResourceBundle;

import common.Level;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindowController extends Observable implements Initializable, View {

	char[][] levelData;

	@FXML
	private Text stepsCounter;
	@FXML
	LevelDisplayer levelDisplayer = new LevelDisplayer();
	@FXML
	private MediaView mv;
	private MediaPlayer mp;
	private Media me;
	private Stage stage;
	private StringProperty timeCount;
	private List<String> params;
	private List<String> filesType;
	private HashMap<String, String> keyHM;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		levelDisplayer.setLevelData(levelData);
		setMusic();
		setKeys();

		levelDisplayer.setImageHashMap();
		levelDisplayer.setFirstScreen();
		levelDisplayer.addEventFilter(MouseEvent.ANY, (e) -> levelDisplayer.requestFocus());
		levelDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				params = new LinkedList<String>();
				params.add("Move");
				String direction = keyHM.getOrDefault(event.getCode().toString(), "E");
				params.add(direction);
				levelDisplayer.setCharaFileName("./resources/" + direction + ".png");
				levelDisplayer.setCharacterMovesHM(direction);

				setChanged();
				notifyObservers(params);
			}
		});

	}

	public void setMusic() {
		String path = new File("./music/Beat.mp3").getAbsolutePath();
		me = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(me);
		mv.setMediaPlayer(mp);
		mp.setAutoPlay(true);

	}

	public void setKeys() {
		keyHM = new HashMap<String, String>();
		try {

			XMLDecoder keys = new XMLDecoder(new FileInputStream(new File("./setkey/keys.xml")));
			keyHM.put((String) keys.readObject(), "up");
			keyHM.put((String) keys.readObject(), "down");
			keyHM.put((String) keys.readObject(), "left");
			keyHM.put((String) keys.readObject(), "right");

			keys.close();

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
		setFileType();
		fc.setTitle("Open file");
		fc.setInitialDirectory(new File("./levelsExample"));

		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("files", filesType);
		fc.getExtensionFilters().add(fileExtensions);
		File chosen = fc.showOpenDialog(stage);
		if (chosen != null) {
			params.add("Load");
			params.add(chosen.getAbsolutePath());
			setParams(params);
			setChanged();
			notifyObservers(params);

		}

	}

	public void setFileType() {

		filesType = new LinkedList<String>();
		filesType.add("*.txt");
		filesType.add("*.xml");
		filesType.add("*.obj");
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

		stop();

	}

	public void Display(Level myLevel) {

		levelDisplayer.setLevelData(myLevel.getCharMat());

	}

	@Override
	public void DisplayMess(String s) {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				if (s == "Level Completed!") {
					sendName();
				}

				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText(null);
					alert.setContentText(s);

					alert.showAndWait();

				}
			}
		});
		Platform.runLater(thread);

	}

	public void sendName() {

		params = new LinkedList<String>();
		Dialog dialog = new TextInputDialog("");
		dialog.setTitle("Information Dialog");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter your name:");
		// dialog.show();

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			params.add("Db");
			params.add(result.get());

			setChanged();
			notifyObservers(params);
		}
		dialog.close();

	}

	@Override
	public void stop() {

		params = new LinkedList<String>();
		params.add("Exit");
		setParams(params);
		setChanged();
		notifyObservers(params);
		Platform.exit();

	}

	public void FinishLevel() {

		System.out.println("level finished");
	}

	public void setStage(Stage stage) {
		this.stage = stage;
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stop();

			}
		});
	}

	@Override
	public void bindForSteps(StringProperty count) {

		stepsCounter.textProperty().bind(count);

	}

}
