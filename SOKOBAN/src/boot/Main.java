package boot;

import controller.MySokobanController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MyModel;
import view.MainWindowController;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MainWindowController view = loader.getController();

			Scene scene = new Scene(root, 600, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			init(view);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init(MainWindowController view) {
		MyModel model = new MyModel();
		MySokobanController controller = new MySokobanController(model, view);

		model.addObserver(controller);
		view.addObserver(controller);
		view.start();
	}

	public static void main(String[] args) {

		// String ip = args[0];
		// int port = Integer.parseInt(args[1]);
		// CLIClient client = new CLIClient();
		// client.start(ip, port);
		launch(args);

	}
}
