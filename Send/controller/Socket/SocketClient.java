package Socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

import FileMoi.FileMoiCtrl;
import TaiKhoan.TaiKhoanCtrl;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;
	public TaiKhoanCtrl ui;
	public FileMoiCtrl fMoiCtrl;
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
				else if (msg.type.equals("newuser")) {
					if (!msg.content.equals(ui.username)) {
						boolean exists = false;
						for (int i = 0; i < fMoiCtrl.ListUser.size(); i++) {
							if (fMoiCtrl.ListUser.get(i).equals(msg.content)) {
								exists = true;
								break;
							}
						}
						if (!exists) {
							fMoiCtrl.ListUser.add(msg.content);
						}
					}
				}
				else if (msg.type.equals("message")) {
					if (msg.recipient.equals(ui.username)) {
						System.out.println("[" + msg.sender + " > Me] : " + msg.content + "\n");
					} else {
						System.out.println("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
					}

					if (!msg.content.equals(".bye") && !msg.sender.equals(ui.username)) {
						String msgTime = (new Date()).toString();

						/*
						 * try { hist.addMessage(msg, msgTime); DefaultTableModel table =
						 * (DefaultTableModel) ui.historyFrame.jTable1.getModel(); table.addRow(new
						 * Object[] { msg.sender, msg.content, "Me", msgTime }); } catch (Exception ex)
						 * { }
						 */

					}
				}
			}

			catch (Exception ex) {
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
		if (t == 1) {
			while (msg.content == "")
				;
			if (msg.content == "TRUE")
				return true;
			else if (msg.content == "FALSE") {
				return false;
			}
		}
		return true;
	}

	public void closeThread(Thread t) {
		t = null;
	}
}
