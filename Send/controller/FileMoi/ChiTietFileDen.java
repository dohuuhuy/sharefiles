package FileMoi;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Socket.Download;
import Socket.Message;
import Socket.SocketClient;
import TaiKhoan.TaiKhoanCtrl;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ChiTietFileDen implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private JFXComboBox<?> toWho;

	@FXML
	private JFXButton btnSend;

	@FXML
	private JFXTextField txtTieuDe;

	@FXML
	private JFXTextArea txtNoiDung;

	@FXML
	private Label labelNameFile;

	@FXML
	private Button btnDown;

	@FXML
	void actionClose(ActionEvent event) {
		 Platform.exit();
		 
	}
	public static File file;
	public FileDenCtrl ui;
	@FXML
	void actionDown(ActionEvent event) {

		
		System.out.println("đang down về ...");
	}

	@FXML
	void actionSend(ActionEvent event) {

	}

	@FXML
	void comboBoxChanged(ActionEvent event) {

	}

	public void setFile(Message msg) {
		txtTieuDe.setText(msg.title);
		txtNoiDung.setText(msg.content);
		labelNameFile.setText(msg.document);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

}
