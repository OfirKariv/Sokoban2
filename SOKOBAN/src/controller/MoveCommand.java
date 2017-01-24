package controller;

import model.Model;
import model.policy.LevelChanger;
import model.policy.Policy;

public class MoveCommand extends SokobanCommand {

    private LevelChanger change = new LevelChanger();
    private Model model;
    private int relevantPlayer;

    public MoveCommand(Model model) {

	this.model = model;
    };

    public void setup(int relevantPlayer, Policy policy) {
	change.setPolicy(policy);
	this.relevantPlayer = relevantPlayer;

    }

    @Override

    public void execute() {

	String direction = params.get(0);
	model.move(direction);

    }
}