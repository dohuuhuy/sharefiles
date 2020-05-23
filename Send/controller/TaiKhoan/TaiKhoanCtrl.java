package TaiKhoan;

import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.jdi.event.Event;
import javafx.scene.Node;
import Home.Home;
import Home.HomeCtrl;
import Socket.Message;
import Socket.SocketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TaiKhoanCtrl implements Initializable {

	public HomeCtrl homeCtrl;
	public SocketClient client;
	public int port;
	public boolean _login;
	public String serverAddr, password, comfirm;
	public static String username;
	public static Thread clientThread;
	private double xOffset = 0;
	private double yOffset = 0;
	public static String h;
	public static Home home = new Home();
	@FXML
	private JFXButton btnClose;
	@FXML
	public JFXButton btnConn, btnLogin;
	@FXML
	public Label isConnected;
	@FXML
	public JFXTextField txtUsername;
	@FXML
	public JFXTextField txtPort;
	@FXML
	public JFXPasswordField txtPassword;

	@FXML
	public JFXPasswordField txtComFirm;

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {

	}

	public void SignUp(ActionEvent event) throws Exception {
		serverAddr = "localhost";
		port = Integer.parseInt(txtPort.getText());
		username = txtUsername.getText();
		password = txtPassword.getText();
		comfirm = txtComFirm.getText();

		if (!serverAddr.isEmpty() && !txtPort.getText().isEmpty()) {
			try {
				client = new SocketClient(this);
				clientThread = new Thread(client);
				clientThread.start();
				if (!username.isEmpty() && !password.isEmpty() && comfirm.equals(password)) {

					SocketClient.send(new Message("signup", "sender", username, password, comfirm, "SERVER"));
				}
			}

			catch (Exception e) {
				// TODO: handle exception
			}
		} else {
			System.out.println("Sai tài khoảng hoặc mật khẩu");
		}
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		DangNhap dangNhap = new DangNhap();
		dangNhap.start(stage);
		
	}

	

	  @FXML
	public void Login(ActionEvent event) throws Exception {
		serverAddr = "localhost";
		port = Integer.parseInt(txtPort.getText());
		username = txtUsername.getText();
		password = txtPassword.getText();
		if (!serverAddr.isEmpty() && !txtPort.getText().isEmpty()) {
			try {
				client = new SocketClient(this);
				clientThread = new Thread(client);
				clientThread.start();
				if (!username.isEmpty() && !password.isEmpty()) {
					var nlo = client
							.sendLogin(new Message("login", "sender", username, password, "document", "SERVER"));
					if (nlo == true) {
						((Node) event.getSource()).getScene().getWindow().hide();
						Stage stage = new Stage();

						Parent root = FXMLLoader.load(getClass().getResource("../view/home.fxml"));

						root.setOnMousePressed(ev -> {
							xOffset = ev.getSceneX();
							yOffset = ev.getSceneY();
						});

						root.setOnMouseDragged(ev -> {
							stage.setX(ev.getScreenX() - xOffset);
							stage.setY(ev.getScreenY() - yOffset);
						});
						stage.initStyle(StageStyle.TRANSPARENT);
						Scene scene = new Scene(root);
						scene.setFill(Color.TRANSPARENT);
						stage.setScene(scene);
						stage.show();
					}
				}

			} catch (Exception ex) {
				isConnected.setText("Không tìm thấy server");
			}
		}

		else {
			System.out.println("Sai tài khoảng hoặc mật khẩu");
		}
	}

	public static void check(String a) throws Exception {
		if (a.equals("TRUE")) {

			// ((Node)event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			home.start(stage);

		} else {

		}
	}

	@FXML
	void Close(ActionEvent event) {
		System.exit(0);
	}
	
	@FXML
    void actionDangKy(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		DangKy dKy = new DangKy();
		try {
			dKy.start(stage);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
	  @FXML
	    void actionDangNhap(ActionEvent event) {
		  ((Node) event.getSource()).getScene().getWindow().hide();
		  Stage stage = new Stage();
			DangNhap dangNhap = new DangNhap();
			try {
				dangNhap.start(stage);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
	    }

}