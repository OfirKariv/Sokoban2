package controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import com.sun.security.ntlm.Client;

import controller.MySokobanController;
import model.Model;
import model.MyModel;
import model.policy.MySokobanPolicy;
import model.policy.Policy;

public class Server extends Observable implements Observer {

	MyClientHandler client = new MyClientHandler();
	MyModel model = new MyModel();
	int port = 5070;

	public void init(MyClientHandler client, MyModel model) {
		this.client = client;
		this.model = model;
	}

	public void startServer() throws IOException { // posibel mistek
		ServerSocket server = new ServerSocket(port);
		// server.setSoTimeout(1000);
		try {
			Socket aClient = server.accept(); // blocking call
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						int g = countObservers();//
						System.out.println(g);
						client.handleClient(aClient.getInputStream(), aClient.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}).start();

			server.close();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("im in server update");
		LinkedList<String> params = (LinkedList<String>) arg;
		System.out.println(params);
		setChanged();
		notifyObservers(params);

	}
}
