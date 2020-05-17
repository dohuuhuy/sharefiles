package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import TaiKhoan.TaiKhoanCtrl;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;
	public TaiKhoanCtrl ui;
	public ObjectInputStream In;
	public ObjectOutputStream Out;
	public String content = "";
	public SocketClient(TaiKhoanCtrl taiKhoanCtrl) throws IOException {
		ui = taiKhoanCtrl;
		this.serverAddr = ui.serverAddr;
		this.port = ui.port;
		socket = new Socket(InetAddress.getByName(serverAddr), port);

		Out = new ObjectOutputStream(socket.getOutputStream());
		Out.flush();
		In = new ObjectInputStream(socket.getInputStream());

	}

	@Override
	public void run() {
		boolean keepRunning = true;
		while (keepRunning) {
			try {
				Message msg = (Message) In.readObject();
				System.out.println("Incoming : " + msg.toString());

				if (msg.type.equals("test")) {
					System.out.println("[SERVER > Me] : connect Successful\n");
				}

				else if (msg.type.equals("login")) {
					if (msg.content.equals("TRUE")) {
						this.content = msg.content;
					
						System.out.println("[SERVER > Me] : Login Successful\n");
					

					} else {
				
						System.out.println("[SERVER > Me] : Login Failed\n");
					}
				}

			} catch (Exception ex) {
				keepRunning = false;
				System.out.println("[Application > Me] : Connection Failure\n");
				/*
				 * for (int i = 1; i < ui.model.size(); i++) { ui.model.removeElementAt(i); }
				 */
				ui.clientThread.stop();

				System.out.println("Exception SocketClient run()");
				ex.printStackTrace();
			}

		}
	}

	public boolean send(Message msg, int t) {
		try {
			Out.writeObject(msg);
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());

		} catch (IOException ex) {
			System.out.println("Lỗi SocketClient không thể gửi()");
		}
		if(t == 1) {
			while(this.content == "");
			if(this.content != "") return true; else return false;
		}
		return true;
	}

	public void recive(Message msg) throws IOException {

		Message msg1 = null;
		try {
			msg1 = (Message) In.readObject();
			System.out.println("neni : " + msg1.toString());

		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

		return;

	}

	public void closeThread(Thread t) {
		t = null;
	}
}