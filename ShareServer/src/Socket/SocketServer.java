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
			clients[findClient(ID)].send(new Message("test", "SERVER", "title", "OK", "document", msg.sender));
		}

		else {
			if (msg.type.equals("login")) {

				if (findUserThread(msg.sender) == null) {
					try {

						if (db.checkLogin(msg.title, msg.content)) {
							System.out.println(msg.title + msg.content);
							clients[findClient(ID)].username = msg.title;
							clients[findClient(ID)]
									.send(new Message("login", "SERVER", "title", "TRUE", "document", msg.sender));
							Announce("newuser", "SERVER", "title", msg.title, "document");
							SendUserList(msg.title);
							System.out.println(msg.sender);

						} else {
							clients[findClient(ID)]
									.send(new Message("login", "SERVER", "title", "FALSE", "document", msg.sender));

						}
					} catch (SQLException e) {

						e.printStackTrace();
					}
				} else {
					clients[findClient(ID)]
							.send(new Message("login", "SERVER", "title", "FALSE", "document", msg.sender));
				}
			} else if (msg.type.equals("message")) {
				if (msg.recipient.equals("All")) {
					Announce("message", msg.sender, msg.title, msg.content, msg.document);
				} else {
					findUserThread(msg.recipient).send(
							new Message(msg.type, msg.sender, msg.title, msg.content, msg.document, msg.recipient));
					clients[findClient(ID)].send(
							new Message(msg.type, msg.sender, msg.title, msg.content, msg.document, msg.recipient));
				}
			} else if (msg.type.equals("signup")) {
				if (findUserThread(msg.sender) == null) {
					try {
					
							db.SignUp(msg.title, msg.content);
							clients[findClient(ID)].username = msg.title;
							clients[findClient(ID)].send(new Message("signup", "SERVER", "title", "TRUE", "document", msg.sender));
						
					
					} catch (SQLException e) {

						e.printStackTrace();
					}
				} else {
					clients[findClient(ID)]
							.send(new Message("signup", "SERVER", "title", "FALSE", "document", msg.sender));
				}
			} else if (msg.type.equals("upload_req")) {
				if (msg.recipient.equals("All")) {
					clients[findClient(ID)].send(new Message("message", "SERVER", "title",
							"không thể gửi file cho nhiều người", "document", msg.sender));
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_req", msg.sender, msg.title, msg.content, msg.document, msg.recipient));
				}
			} else if (msg.type.equals("upload_res")) {
				if (!msg.content.equals("NO")) {
					String IP = findUserThread(msg.sender).socket.getInetAddress().getHostAddress();
					findUserThread(msg.recipient)
							.send(new Message("upload_res", IP, msg.title, msg.content, msg.document, msg.recipient));
				} else {
					findUserThread(msg.recipient).send(
							new Message("upload_res", msg.sender, msg.title, msg.content, msg.document, msg.recipient));
				}
			}

		}
	}

	public void Announce(String type, String sender, String title, String content, String document) {
		Message msg = new Message(type, sender, title, content, document, "All");
		for (int i = 0; i < clientCount; i++) {
			clients[i].send(msg);
		}
	}

	public void SendUserList(String toWhom) {
		for (int i = 0; i < clientCount; i++) {
			findUserThread(toWhom)
					.send(new Message("newuser", "SERVER", "new title", clients[i].username, "document", toWhom));
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
