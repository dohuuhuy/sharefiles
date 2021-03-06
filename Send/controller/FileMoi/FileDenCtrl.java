package FileMoi;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import Home.HomeCtrl;
import Socket.Message;
import Socket.SocketClient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;

public class FileDenCtrl implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private ImageView btnClose;

	@FXML
	private JFXButton btnXoa;

	@FXML
	private JFXButton btnXoaHet;

	@FXML
	private JFXButton btnDoc;

	@FXML
	private JFXButton btnBoQua;

	@FXML
	private JFXButton btnChiTiet;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXTextField txtSearch;

	@FXML
	private TableView<Message> tableFileDen;

	@FXML
	private TableColumn<Message, String> colTieuDe;

	@FXML
	private TableColumn<Message, String> colNguoiGui;

	@FXML
	private TableColumn<Message, CheckBox> colThaoTac;

	@FXML
	private CheckBox checkAll;


	
	@FXML
	ObservableList<Message> fdiList = FXCollections.observableArrayList(SocketClient.ListFileDen);

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// fiList = FXCollections.observableArrayList(SocketClient.ListFileDen);

		colTieuDe.setCellValueFactory(new PropertyValueFactory<Message, String>("title"));
		colNguoiGui.setCellValueFactory(new PropertyValueFactory<Message, String>("sender"));
		// colThaoTac.setCellValueFactory(new PropertyValueFactory<Message,
		// CheckBox>("select"));
		tableFileDen.setItems(fdiList);

		System.out.println(fdiList);
	}

	@FXML
	void actionBoQua(ActionEvent event) {

	}

	private double xOffset = 0;
	private double yOffset = 0;

	@FXML
	void actionChiTiet(ActionEvent e) throws IOException {
		// ((Node) e.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../view/chitietfileden.fxml"));
		Parent root = loader.load();
		ChiTietFileDen controller = loader.getController();
		Message selected = tableFileDen.getSelectionModel().getSelectedItem();
		controller.setFile(selected);
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	void actionClose(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void actionDocHet(ActionEvent event) {

	}

	@FXML
	void actionXoa(ActionEvent event) {

	}

	@FXML
	void actionXoaTatCa(ActionEvent event) {

	}

	public ObservableList<Message> fiList;

}
