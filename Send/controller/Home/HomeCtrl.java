package Home;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import FileMoi.FileDenCtrl;
import Socket.Message;
import Socket.SocketClient;
import TaiKhoan.DangNhap;
import TaiKhoan.TaiKhoanCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HomeCtrl implements Initializable {

	@FXML
	public ResourceBundle resources;

	@FXML
	public URL location;
	@FXML
	public Button btnHome;

	@FXML
	public BorderPane body;

	@FXML
	public AnchorPane content;

	@FXML
	public Pane paneView;

	@FXML
	public void actionClose(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	public void actionBack(ActionEvent event) {

	}

	@FXML
	public Label txtUserName;

	public void GetUser() {

		txtUserName.setText(TaiKhoanCtrl.username);
		System.out.println(TaiKhoanCtrl.username);

	}

	@SuppressWarnings("deprecation")
	@FXML
	public void actionLogOut(ActionEvent event) {
		try {
			SocketClient
					.send(new Socket.Message("message", TaiKhoanCtrl.username, "title", ".bye", "document", "SERVER"));
			TaiKhoanCtrl.clientThread.stop();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage stage = new Stage();
			DangNhap dangNhap = new DangNhap();
			dangNhap.start(stage);
		} catch (Exception ex) {
		}
	}

	@FXML
	public void actionChinhSach(ActionEvent event) {
		loadUI("chinhsach");
	}

	@FXML
	public void actionFileDi(ActionEvent event) {
		loadUI("filedi");
	}

	@FXML
	public void actionFileNhap(ActionEvent event) {
		loadUI("filenhap");
	}

	@FXML
	public void actionFileDen(ActionEvent event) {
		loadUI("fileden");
	}

	@FXML
	public void actionHome(ActionEvent event) {
		body.setCenter(content);
	}

	@FXML
	public void actionHuongDan(ActionEvent event) {
		loadUI("huongdan");
	}

	@FXML
	public void actionTaoMoi(ActionEvent event) {
		loadUI("filemoi");
	}

	public void loadUI(String page) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("/view/" + page + ".fxml"));
		} catch (IOException e) {

		}
		body.setCenter(root);
	}
	/*
	 * public ObservableList<Message> fdiList =
	 * FXCollections.observableArrayList(SocketClient.ListFileDi); public
	 * ObservableList<Message> fdenList =
	 * FXCollections.observableArrayList(SocketClient.ListFileDen);
	 */

	public void loadData() {
		paneView.getChildren().clear();
		ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
		list.add(new PieChart.Data("File đến", SocketClient.ListFileDen.size()));
		list.add(new PieChart.Data("File đi", SocketClient.ListFileDi.size()));
		list.add(new PieChart.Data("File nháp", 1));
		PieChart milkChart = new PieChart(list);
		paneView.getChildren().add(milkChart);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
		GetUser();
	}
}
