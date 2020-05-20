package Socket;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.Socket.Download;
import com.Socket.Message;
import com.Socket.Upload;

import FileMoi.FileMoiCtrl;
import TaiKhoan.TaiKhoanCtrl;

public class SocketClient implements Runnable {

	public int port;
	public String serverAddr;
	public Socket socket;
	public TaiKhoanCtrl ui;
	public FileMoiCtrl fileMoiCtrl;
	public ObjectInputStream In;
	public static ObjectOutputStream Out;
	public String content = "";
	public static ArrayList<String> ListUser = new ArrayList<String>();

	public String CheckLogin = "";

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

						ui.btnLogin.setDisable(true);

						System.out.println("[SERVER > Me] : Login Successful\n");

					} else {
						ui.btnLogin.setDisable(false);
						System.out.println("[SERVER > Me] : Login Failed\n");
					}

				} else if (msg.type.equals("newuser")) {
					if (!msg.content.equals(ui.username)) {
						boolean exists = false;
						for (int i = 0; i < ListUser.size(); i++) {
							if (ListUser.get(i).equals(msg.content)) {
								exists = true;
								break;
							}
						}
						if (!exists) {
							ListUser.add(msg.content);
							System.out.print(ListUser);
						}
					}

				} else if (msg.type.equals("message")) {
					if (msg.recipient.equals(ui.username)) {
						System.out.println("[" + msg.sender + " > Me] : " + msg.content + "\n");
					} else {
						System.out.println("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
					}

					if (!msg.content.equals(".bye") && !msg.sender.equals(ui.username)) {
						// String msgTime = (new Date()).toString();

						/*
						 * try { hist.addMessage(msg, msgTime); DefaultTableModel table =
						 * (DefaultTableModel) ui.historyFrame.jTable1.getModel(); table.addRow(new
						 * Object[] { msg.sender, msg.content, "Me", msgTime }); } catch (Exception ex)
						 * { }
						 */

					}
				} else if (msg.type.equals("upload_req")) {

					if (JOptionPane.showConfirmDialog(ui,
							("Accept '" + msg.document + "' from " + msg.sender + " ?")) == 0) {

						JFileChooser jf = new JFileChooser();
						jf.setSelectedFile(new File(msg.content));
						int returnVal = jf.showSaveDialog(ui);

						String saveTo = jf.getSelectedFile().getPath();
						if (saveTo != null && returnVal == JFileChooser.APPROVE_OPTION) {
							Download dwn = new Download(saveTo, ui);
							Thread t = new Thread(dwn);
							t.start();
							
							send(new Message("upload_res", ui.username, "title", ("" + dwn.port), "document",
									msg.sender));
						} else {
							send(new Message("upload_res", ui.username, "title", "NO", "document", msg.sender));
						}
					} else {
						send(new Message("upload_res", ui.username, "title", "NO", "document", msg.sender));
					}
				} else if (msg.type.equals("upload_res")) {
					if (!msg.content.equals("NO")) {
						int port = Integer.parseInt(msg.content);
						String addr = msg.sender;

						Upload upl = new Upload(addr, port, FileMoiCtrl.file, ui);
						Thread t = new Thread(upl);
						t.start();
					} else {
						System.out.println("[SERVER > Me] : " + msg.sender + " rejected file request\n");
					}
				} else {
					System.out.println("[SERVER > Me] : Unknown message type\n");
				}
			}

			catch (Exception ex) {

				keepRunning = false;
				System.out.println("[Application > Me] : Connection Failure\n" + ex);

				for (int i = 0; i < ListUser.size(); i++) {
					{
						ListUser.remove(i);
					}

					ui.clientThread.stop();

					System.out.println("Exception SocketClient run()");
					ex.printStackTrace();
				}

			}
		}
	}

	public static void senddemo() {

		System.out.println("gửi thành công");
	}

	public static void send(Message msg) {
		try {
			Out.writeObject(msg);
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());

		} catch (IOException ex) {
			System.out.println("Lỗi SocketClient không thể gửi()");
		}
	}

	public boolean sendLogin(Message msg, int t) {
		try {
			Out.writeObject(msg);
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());

		} catch (IOException ex) {
			System.out.println("Exception SocketClient send()");
		}
		if (t == 1) {
			while (this.content == "")
				;
			if (this.content != "")
				return true;
			else
				return false;
		}
		return true;

	}

	public void closeThread(Thread t) {
		t = null;
	}
}
