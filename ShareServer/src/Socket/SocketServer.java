package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

class ServerThread extends Thread {

	public SocketServer server = null;
	public Socket socket = null;
	public int ID = -1;
	public String username = "";
	public ObjectInputStream streamIn = null;
	public ObjectOutputStream streamOut = null;
	public controllerServer ui;

	public ServerThread(SocketServer _server, Socket _socket) {
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		ui = _server.ui;
	}

	public void send(Message msg) {
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
		} catch (IOException ex) {
			System.out.println("Exception [SocketClient : send(...)]");
		}
	}

	public int getID() {
		return ID;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		ui.txtNoiDung.appendText("\nServer Thread " + ID + " running.");
		while (true) {
			try {
				Message msg = (Message) streamIn.readObject();

				System.out.println("txt:" + msg);

				server.handle(ID, msg);
			} catch (Exception ioe) {
				System.out.println(ID + " ERROR reading: " + ioe.getMessage());
				server.remove(ID);
				stop();
			}
		}
	}

	public void open() throws IOException {
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
}

public class SocketServer implements Runnable {

	public ServerThread clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0, port = 62000;
	public controllerServer ui;
	public databaseServer db;

	public SocketServer(controllerServer frame) {

		clients = new ServerThread[50];
		ui = frame;
		db = new databaseServer();

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			ui.txtNoiDung.appendText(
					"Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
			start();
		} catch (IOException ioe) {
			ui.txtNoiDung.appendText("Can not bind to port : " + port + "\nRetrying");
			ui.RetryStart(0);
		}
	}

	public SocketServer(controllerServer frame, int Port) {

		clients = new ServerThread[50];
		ui = frame;
		port = Port;
		db = new databaseServer();

		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			ui.txtNoiDung.appendText(
					"Server startet. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
			start();
		} catch (IOException ioe) {
			ui.txtNoiDung.appendText("\nCan not bind to port " + port + ": " + ioe.getMessage());
		}
	}

	public void run() {
		while (thread != null) {
			try {
				ui.txtNoiDung.appendText("\nWaiting for a client ...");
				addThread(server.accept());
			} catch (Exception ioe) {
				ui.txtNoiDung.appendText("\nServer accept error: \n");
				ui.RetryStart(0);
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	private int findClient(int ID) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	public synchronized void handle(int ID, Message msg) {
		if (msg.type.equals("test")) {
			clients[findClient(ID)].send(new Message("test", "SERVER", "OK", msg.sender));
		}

		else
			{
			if (msg.type.equals("login")) {
			}
			if (findUserThread(msg.sender) == null) {
				try {

					if (db.checkLogin(msg.sender, msg.content)) {
						System.out.println(msg.sender + msg.content);
						clients[findClient(ID)].username = msg.sender;
						clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender));
						Announce("newuser", "SERVER", msg.sender);
						SendUserList(msg.sender);
						System.out.println(msg.sender);

					} else {
						clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender));
						
					}
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
			} else {
				clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender));
			}
		}
	}

	public void Announce(String type, String sender, String content) {
		Message msg = new Message(type, sender, content, "All");
		for (int i = 0; i < clientCount; i++) {
			clients[i].send(msg);
		}
	}

	public void SendUserList(String toWhom) {
		for (int i = 0; i < clientCount; i++) {
			findUserThread(toWhom).send(new Message("newuser", "SERVER", clients[i].username, toWhom));
		}
	}

	public ServerThread findUserThread(String usr) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].username.equals(usr)) {
				return clients[i];
			}
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public synchronized void remove(int ID) {
		int pos = findClient(ID);
		if (pos >= 0) {
			ServerThread toTerminate = clients[pos];
			ui.txtNoiDung.appendText("\nRemoving client thread " + ID + " at " + pos);
			if (pos < clientCount - 1) {
				for (int i = pos + 1; i < clientCount; i++) {
					clients[i - 1] = clients[i];
				}
			}
			clientCount--;
			try {
				toTerminate.close();
			} catch (IOException ioe) {
				ui.txtNoiDung.appendText("\nError closing thread: " + ioe);
			}
			toTerminate.stop();
		}
	}

	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			ui.txtNoiDung.appendText("\nClient accepted: " + socket);
			clients[clientCount] = new ServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				ui.txtNoiDung.appendText("\nError opening thread: " + ioe);
			}
		} else {
			ui.txtNoiDung.appendText("\nClient refused: maximum " + clients.length + " reached.");
		}
	}
}
