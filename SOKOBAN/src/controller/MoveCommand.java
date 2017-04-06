package controller;

import java.io.PrintWriter;

import model.Model;
import model.policy.LevelChanger;

public class MoveCommand extends SokobanCommand {

	private Model model;

	public MoveCommand(Model model) {

		this.model = model;
	};

	@Override

	public void execute() {

		String direction = params.get(0);
		model.move(direction);

	}
}