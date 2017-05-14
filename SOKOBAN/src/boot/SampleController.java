package boot;

import java.util.LinkedList;
import java.util.List;

import com.sun.javafx.collections.ObservableListWrapper;

import db.DbObject;
import db.HbrntDBManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import view.MainWindowController;
import view.View;

public class SampleController {
	private HbrntDBManager hbrnet = null;

	public SampleController() {
		hbrnet = new HbrntDBManager();
	}
	// somthing to conect with sql

	public void sortBySteps() {
		// when clicked steps
		// send a query to database
		// getting list <string> and show it in table

		// MainWindowController mw = new MainWindowController();

		List fromDB = hbrnet.getTable("select * from users");

		ObservableList<DbObject> data = new ObservableListWrapper<DbObject>(fromDB);

		// getDataFromDB
		createTable(data);
	}

	public void sortByName() {
		// when clicked name

	}

	public void sortByTime() {
		// when clicked time
	}

	/// create a new table
	public void createTable(ObservableList<DbObject> data) {
		Stage stage = new Stage();
		TableView table = new TableView();
		Scene scene = new Scene(new Group());
		stage.setTitle("High score ");
		stage.setWidth(300);
		stage.setHeight(500);

		final Label label = new Label("high score");
		label.setFont(new Font("Arial", 20));

		table.setEditable(true);

		TableColumn NameCol = new TableColumn("Name");
		NameCol.setCellValueFactory(new PropertyValueFactory<DbObject, String>("Name"));

		TableColumn levelCol = new TableColumn("level");
		levelCol.setCellValueFactory(new PropertyValueFactory<DbObject, String>("level"));

		TableColumn timeCol = new TableColumn("time");
		timeCol.setCellValueFactory(new PropertyValueFactory<DbObject, String>("time"));

		TableColumn stepsCol = new TableColumn("steps");
		stepsCol.setCellValueFactory(new PropertyValueFactory<DbObject, String>("steps"));

		table.setItems(data);

		table.getColumns().addAll(NameCol, levelCol, timeCol, stepsCol);

		final VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(label, table);

		((Group) scene.getRoot()).getChildren().addAll(vbox);

		stage.setScene(scene);
		stage.show();

	}

}
