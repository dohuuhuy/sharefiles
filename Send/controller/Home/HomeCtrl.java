package Home;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomeCtrl implements Initializable {

	@FXML
	public ResourceBundle resources;

	@FXML
	public URL location;

	@FXML
	public BorderPane body;

	@FXML
	public AnchorPane content;

	@FXML
	public Pane paneView;

	@FXML
	void actionClose(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void actionBack(ActionEvent event) {
		try {
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("../view/home.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {

		}
	}

	@FXML
	public Label txtUserName;

public void GetUser() {

	txtUserName.setText(TaiKhoanCtrl.username);
	System.out.println(TaiKhoanCtrl.username);

}

	@SuppressWarnings("deprecation")
	@FXML
	void actionLogOut(ActionEvent event) {
		try {
		
		TaiKhoanCtrl.clientThread.stop();
		
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			Pane root = loader.load(getClass().getResource("../view/Login.fxml").openStream());
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {

		}

	}

	@FXML
	void actionChinhSach(ActionEvent event) {

	}

	@FXML
	void actionFileDi(ActionEvent event) {
		loadUI("c");
	}

	@FXML
	void actionFileNhap(ActionEvent event) {

	}

	@FXML
	void actionFileDen(ActionEvent event) {
		loadUI("b");
	}

	@FXML
	public void actionHome(ActionEvent event) {
		body.setCenter(content);
	}

	@FXML
	void actionHuongDan(ActionEvent event) {

	}

	@FXML
	void actionTaoMoi(ActionEvent event) {
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

	public void loadData() {
		paneView.getChildren().clear();
		ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
		list.add(new PieChart.Data("File đến", 7));
		list.add(new PieChart.Data("File đi", 2));
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
