package controller.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server {

	//private ServerSocket server=new ServerSocket(3000);
	/*
	 * server.setSoTimeout(1000); try{ Socket aClient=server.accept(); //
	 * blocking call InputStream inFromClient=aClient.getInputStream();
	 * OutputStream outToClient=aClient.getOutputStream(); // interact (read &
	 * write) with the client according to protocol inFromClient.close();
	 * outToClient.close(); aClient.close(); server.close(); }
	 * 
	 * catch (SocketTimeoutException e) {/*...
	 */

}