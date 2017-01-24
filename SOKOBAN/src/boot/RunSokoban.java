
package boot;

import controller.MySokobanController;
import model.MyModel;
import model.policy.MySokobanPolicy;
import model.policy.Policy;
import view.CLI;

public class RunSokoban {

    public static void main(String[] args)

    {
	Policy policy = new MySokobanPolicy();
	CLI ui = new CLI();
	MyModel m = new MyModel();
	MySokobanController c = new MySokobanController(m, ui);
	ui.addObserver(c);
	m.addObserver(c);
	ui.start();

	/*
	 * Policy mySocPol = new MySokobanPolicy(); Displayer TxtDisplayer = new
	 * TxtDisplayer();
	 * 
	 * CLI cli = new CLI(); cli.setup(mySocPol, TxtDisplayer); try {
	 * cli.CLIInvoke(); } catch (ClassNotFoundException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException
	 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * System.out.println("===============================");
	 */
    }

}
