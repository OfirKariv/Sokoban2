package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

public class MySokobanController implements Observer {

	private Model model;
	private View view;
	private Controller controller;
	private Map<String, Command> invoke;

	public MySokobanController(Model model, View view) {
		this.model = model;
		this.view = view;
		initCommands(); // creates HashMap
		controller = new Controller();
		controller.start();
	}

	protected void initCommands() {
		invoke = new HashMap<String, Command>();
		invoke.put("Display", new DisplayCommand(model, view));
		invoke.put("Move", new MoveCommand(model));
		invoke.put("Load", new LoadCommand(model));
		invoke.put("Exit", new ExitCommand());
		invoke.put("Save", new SaveCommand(model));
	}

	public void update(Observable o, Object arg) {

		LinkedList<String> params = (LinkedList<String>) arg;
		String commandKey = params.removeFirst();
		SokobanCommand c = (SokobanCommand) invoke.get(commandKey);

		if (c == null) {

			view.DisplayMess("UNKNOWN ERROR ");
			return;
		}
		c.setParams(params);
		controller.insertCommand(c);

	}

	public class ExitCommand extends SokobanCommand {

		@Override
		public void execute() {
			view.DisplayMess("Exiting..");

			controller.stop();
			view.stop();

		}

	}
}
