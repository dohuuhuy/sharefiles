package FileMoi;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import Socket.SocketClient;
import TaiKhoan.TaiKhoanCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileMoiCtrl implements Initializable {

	@FXML
	public AnchorPane anchorPane;
	@FXML
	public ResourceBundle resources;

	@FXML
	public URL location;

	@FXML
	public JFXComboBox<String> toWho;

	@FXML
	public JFXButton btnSend;

	@FXML
	public JFXTextArea txtNoiDung;

	@FXML
	public JFXTextField txtDiaChi;

	@FXML
	public JFXButton btnChonTep;

	@FXML
	ObservableList<String> listToWho = FXCollections.observableArrayList(SocketClient.ListUser);

	@FXML
	void actionClose(final ActionEvent event) {
		System.exit(0);
	}

	public String GetTieuDe() {
		String a = txtTieuDe.getText();
		return a;
	}

	@FXML
	public JFXTextField txtTieuDe;

	@FXML
	void actionReturn(final ActionEvent event) {

	}

	@FXML
	void btnSave(final ActionEvent event) {

	}

	@FXML
	public void actionSend(final ActionEvent event) {

		System.out.println("chay");
		String tieuDe = txtTieuDe.getText();
		String content = txtNoiDung.getText();
		String nguoiNhan = toWho.getValue();
		System.out.println(TaiKhoanCtrl.username);
		String nguoiGui = TaiKhoanCtrl.username;
	//	if (!content.isEmpty() && nguoiNhan.isEmpty()) {
			
			SocketClient.send(new Socket.Message("message", nguoiGui, content, nguoiNhan));
		//}
	}

	@FXML
	public void actionChonTep(final ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		final Stage stage = (Stage) anchorPane.getScene().getWindow();
		final File file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			System.out.println(file.getAbsolutePath());
			txtDiaChi.setText(file.getAbsolutePath());
		}
	}

	@FXML
	public void comboBoxChanged(final ActionEvent event) {
		txtDiaChi.setText(toWho.getValue());
	}

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
		toWho.setItems(listToWho);
		System.out.println(SocketClient.ListUser + "1");

	}

}
