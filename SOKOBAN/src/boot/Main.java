package boot;

import java.io.IOException;

import controller.MySokobanController;
import controller.server.MyClientHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MyModel;
import view.MainWindowController;

public class Main extends Application {

	private MySokobanController controller;

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			BorderPane root = (BorderPane) loader.load();
			MainWindowController view = loader.getController();
			view.setStage(primaryStage);

			Scene scene = new Scene(root, 600, 500);
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
		controller = new MySokobanController(model, view);

		model.addObserver(controller);
		view.addObserver(controller);
		view.start();
	}

	public static void main(String[] args) {

		if (args.length > 0) {
			if (args[0].equals("--server")) {

				MyClientHandler clientHndlr = new MyClientHandler();// view
				controller.server.Server server = new controller.server.Server();
				MyModel model = new MyModel();// model
				server.init(clientHndlr, model);

				MySokobanController sokoController = new MySokobanController(model, server, clientHndlr);
				clientHndlr.addObserver(server);
				model.addObserver(sokoController);
				server.addObserver(sokoController);
				try {

					server.startServer();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		else
			launch(args);

	}

}
