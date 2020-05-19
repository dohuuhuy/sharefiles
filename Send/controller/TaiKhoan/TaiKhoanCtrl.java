package TaiKhoan;

import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Home.HomeCtrl;

import Socket.Message;
import Socket.SocketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TaiKhoanCtrl implements Initializable {

	public HomeCtrl homeCtrl;
	public SocketClient client;
	public int port;
	public boolean _login;
	public String serverAddr, password;
	public static String  username;
	public static Thread clientThread;
	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	private JFXButton btnClose;
	@FXML
	public JFXButton btnConn,btnLogin;
	@FXML
	public Label isConnected;
	@FXML
	public JFXTextField txtUsername;
	@FXML
	public JFXTextField txtPort;
	@FXML
	public JFXPasswordField txtPassword;

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {

	}

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
					var recive = client.sendLogin(new Message("login", username, password, "SERVER"), 1);
					if (recive == true) {

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
					} else {
						clientThread.stop();
						isConnected.setText("Sai tài khoản hoặc mật khẩu");

					}
				}

			} catch (Exception ex) {
				isConnected.setText("Không tìm thấy server");
			}
		}
	}

	@FXML
	void Close(ActionEvent event) {
		System.exit(0);
	}

}