package view;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import boot.SampleController;
import common.Level;
import db.DbObject;
import db.HbrntDBManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
	private Text SokoTimer;
	private StringProperty timeProp;
	private int sec = 0;
	private int min = 0;
	private Integer count = 0;
	private boolean start = false;

	private Timer timer;

	@FXML
	LevelDisplayer levelDisplayer = new LevelDisplayer();
	@FXML
	private MediaView mv;
	private MediaPlayer mp;
	private Media me;
	private Stage stage;
	private List<String> params;
	private List<String> filesType;
	private HashMap<String, String> keyHM;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		timeProp = new SimpleStringProperty();
		levelDisplayer.setLevelData(levelData);
		// setMusic();
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
		timeStart();

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

		Platform.runLater(new Runnable() {

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

	}

	public void sendName() {

		timeStop();

		params = new LinkedList<String>();
		Dialog dialog = new TextInputDialog("");
		dialog.setTitle("Information Dialog");
		dialog.setHeaderText(null);
		dialog.setContentText("Please enter your name:");
		// dialog.show();

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			params.add("Db");
			params.add(result.get());// The user name
			params.add(count.toString());
			params.add(stepsCounter.getText());

			setChanged();
			notifyObservers(params);
		}
		dialog.close();

	}

	//////////////////////////////////////////////
	// this is sposed to be apllay from one of the opsions do dispaly sort in
	// highscore window
	public List<DbObject> getDataFromDB(String query) {
		List<DbObject> paramsDB = new LinkedList<DbObject>();
		HbrntDBManager hbrnet = new HbrntDBManager();
		paramsDB = hbrnet.getTable(query);

		// setChanged();
		// notifyObservers(paramsDB);
		return paramsDB;
	}

	/////////////////////////////////////////////////////////////
	@Override
	public void stop() {
		timeStop();
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

	public void timeStart() {
		SokoTimer.textProperty().bind(timeProp);

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				timeProp.set("" + timeTransfer(++count));

			}
		}, 0, 1000);

	}

	public void timeStop() {
		if (timer != null)
			timer.cancel();
	}

	private String timeTransfer(int count) {

		String clock;
		if (count % 60 > 0)

		{
			sec++;

		}
		if (count % 60 == 0)

		{
			sec = 0;
			min++;
		}
		if (min < 10)
			if (sec < 10)
				return "0" + min + " : " + "0" + sec;
			else
				return "0" + min + " : " + sec;
		else if (sec < 10)
			return min + " : " + "0" + sec;
		else
			return min + " : " + sec;

	}

	public void openHighScore() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));

			Stage stage = new Stage();
			stage.setTitle("High scores");
			BorderPane root = (BorderPane) loader.load();
			stage.setScene(new Scene(root));
			stage.show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// BorderPane root = (BorderPane) loader.load();

	// SampleController view = loader.getController();
	// view.setStage(stage);

	// Scene scene = new Scene(loader.load(), 600, 400);
	// scene.getStylesheets().add(getClass().getResource("application1.css").toExternalForm());
	// stage.setScene(scene);
	// init(view);///to create table
	// Button b = new Button("time");
	// stage.setScene(scene);

}
