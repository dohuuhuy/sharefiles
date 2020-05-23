package Socket;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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
	public static ArrayList<Message> ListFileDen = new ArrayList<Message>();
	public static ArrayList<Message> ListFileDi = new ArrayList<Message>();

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
						JOptionPane.showMessageDialog(null, "Đăng nhập thất bại");

					}
					System.out.println("[SERVER > Me] : Login Failed\n");

				} else if (msg.type.equals("signup")) {
					if (msg.content.equals("TRUE")) {

						System.out.println("[SERVER > Me] : Singup Successful\n");
					} else {
						System.out.println("[SERVER > Me] : Signup Failed\n");
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
					String msgTime = (new Date()).toString();
					if (msg.recipient.equals(ui.username)) {
						System.out.println("[" + msg.sender + " > Me] : " + msg.content + "\n");

						ListFileDen.add(msg);
						System.out.println(ListFileDen.size());

					} else {
						System.out.println("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
						ListFileDi.add(msg);
						System.out.println(ListFileDi);

					}

				} else if (msg.type.equals("upload_req")) {
					if (msg.recipient.equals(ui.username)) {
						System.out.println("[" + msg.sender + " > Me] : " + msg.content + "\n");

						ListFileDen.add(msg);
						Collections.reverse(ListFileDen);
						System.out.println(ListFileDen.size());

					} else {
						System.out.println("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
						ListFileDi.add(msg);
						Collections.reverse(ListFileDi);
						System.out.println(ListFileDi);

					}
					if (JOptionPane.showConfirmDialog(null,
							("Accept '" + msg.document + "' from " + msg.sender + " ?")) == 0) {

						JFileChooser jf = new JFileChooser();
						jf.setSelectedFile(new File(msg.document));
						int returnVal = jf.showSaveDialog(jf);

						String saveTo = jf.getSelectedFile().getPath();
						if (saveTo != null && returnVal == JFileChooser.APPROVE_OPTION) {
							Download dwn = new Download(saveTo, ui);
							Thread t = new Thread(dwn);
							t.start();

							send(new Message("upload_res", ui.username, "tittle", ("" + dwn.port), "document",
									msg.sender));

						} else {
							send(new Message("upload_res", ui.username, "tittle", "NO", "document", msg.sender));
						}
					} else {
						send(new Message("upload_res", ui.username, "tittle", "NO", "document", msg.sender));
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

			} catch (Exception ex) {

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

	public boolean sendLogin(Message msg) {
		try {
			Out.writeObject(msg);
			Out.flush();
			System.out.println("Outgoing : " + msg.toString());

		} catch (IOException ex) {
			System.out.println("Exception SocketClient send()");
		}
		return true;

	}

	public void closeThread(Thread t) {
		t = null;
	}
}
