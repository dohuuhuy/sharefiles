package FileMoi;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

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

public class FileDiCtrl implements Initializable {

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
	ObservableList<Message> fiList1 = FXCollections.observableArrayList(SocketClient.ListFileDi);
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		

		colTieuDe.setCellValueFactory(new PropertyValueFactory<Message, String>("title"));
		colNguoiGui.setCellValueFactory(new PropertyValueFactory<Message, String>("recipient"));
		//colThaoTac.setCellValueFactory(new PropertyValueFactory<Message, CheckBox>("select"));
		tableFileDen.setItems(fiList1);
		
		System.out.println(fiList1);
	}

	@FXML
	void actionBoQua(ActionEvent event) {

	}

	private double xOffset = 0;
	private double yOffset = 0;
	@FXML
	void actionChiTiet(ActionEvent e) throws IOException {
	//	((Node) e.getSource()).getScene().getWindow().hide();
		Stage stage = new Stage();		
		FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(getClass().getResource("../view/chitietfileden.fxml"));
				//.load(getClass().getResource("../view/chitietfileden.fxml"));
		Parent root = loader.load();
		
		root.setOnMousePressed(ev -> {
			xOffset = ev.getSceneX();
			yOffset = ev.getSceneY();
		});

		root.setOnMouseDragged(ev -> {
			stage.setX(ev.getScreenX() - xOffset);
			stage.setY(ev.getScreenY() - yOffset);
		});
		
		ChiTietFileDen controller = loader.getController();
        Message selected = tableFileDen.getSelectionModel().getSelectedItem();
        controller.setFile(selected);
		stage.initStyle(StageStyle.TRANSPARENT);
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
