package controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import common.Level;
import controller.SokobanCommand;
import view.TxtDisplayer;
import view.View;

public class MyClientHandler extends Observable implements ClientHandler, View {
	private PrintWriter writer = null;
	private BlockingQueue<Character> queue = null;

	public void init(PrintWriter writer) {
		this.writer = writer;
		queue = new ArrayBlockingQueue<Character>(200);

	}

	@Override
	public void handleClient(InputStream in, OutputStream out) {
		try (BufferedReader inFromClient = new BufferedReader(new InputStreamReader(in));
				PrintWriter touser = new PrintWriter(out);) {
			init(touser);
			menu();
			String userReq = inFromClient.readLine();
			Thread t1 = readfromClient(userReq);
			Thread t2 = syncToClient(writer);
			writer.flush();
			t1.join();
			t2.join();
			// writer.print(params);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO Auto-generated catch block

	}

	public Thread readfromClient(String userReq) {

		Thread t = new Thread(new Runnable() {
			public void run() {
				String[] sa;

				sa = userReq.split(" ");
				LinkedList<String> params = new LinkedList<String>();
				for (String s : sa) {
					params.add(s);
				}
				System.out.println(params);

				setChanged();

				notifyObservers(params);
			}
		});
		t.start();
		return t;

	}

	public Thread syncToClient(PrintWriter writer) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				sendToClient(writer);
			}
		});
		t.start();
		return t;

	}

	@Override
	public void Display(Level myLevel) {
		// TxtDisplayer dis = new TxtDisplayer();
		// dis.display(myLevel.getCharMat());
		// setChanged();
		// notifyObservers("Display");
		// writer.println(myLevel.getCharMat());

		char[][] mat = myLevel.getCharMat();
		char c;

		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {
				System.out.print(mat[i][j]);
				c = mat[i][j];
				try {
					queue.put(c);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			c = '\n';
			try {
				queue.put(c);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
		sendToClient(writer);

	}

	public void sendToClient(PrintWriter writer) {
		while (true) {
			try {
				Character line = queue.take();
				if (line != null) {
					writer.println(line);
					writer.flush();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public void menu() {
		writer.println("****Welcome to SoKoBan!****:");
		writer.println("****Please choose option:****:");
		writer.println("Load");
		writer.println("Display");
		writer.println("Move {up,down,left,right}:");
		writer.println("Save");
		writer.println("Exit\n");
	}

	@Override
	public void DisplayMess(String s) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}
}
