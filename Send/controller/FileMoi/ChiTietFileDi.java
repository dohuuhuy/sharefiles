package FileMoi;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Socket.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ChiTietFileDi implements Initializable {

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

	@FXML
	void actionDown(ActionEvent event) {

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
