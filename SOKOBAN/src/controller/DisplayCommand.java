package controller;

import model.Model;
import view.View;

public class DisplayCommand extends SokobanCommand {

	Model model;
	View view;

	public DisplayCommand(Model model, View view) {

		this.model = model;
		this.view = view;

	}

	public void execute() {

		view.Display(model.getCurrentLevel());

	}

}
