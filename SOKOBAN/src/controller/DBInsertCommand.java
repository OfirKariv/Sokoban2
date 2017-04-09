package controller;

import model.Model;

public class DBInsertCommand implements Command {

	private Model model;

	public DBInsertCommand(Model model) {
		this.model = model;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
