package FileMoi;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
public class FileDenCtrl implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView btnClose;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableView<Files> tableFileDen;

    @FXML
    private TableColumn<Files, Integer> colThaoTac;

    @FXML
    private TableColumn<Files, String> colTieuDe;

    @FXML
    private TableColumn<Files, String> colNguoiGui;


    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    void actionClose(ActionEvent event) {

    }

    @FXML
    void actionReturn(ActionEvent event) {

    }

    private ObservableList<Files> fiList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fiList = FXCollections.observableArrayList(
                new Files(1, "Chau", "ebfbd"),
                new Files(2, "Chuong", "dbbgb")
        );
		colThaoTac.setCellValueFactory(new PropertyValueFactory<Files, Integer>("id"));
        colTieuDe.setCellValueFactory(new PropertyValueFactory<Files, String>("tieuDe"));
        colNguoiGui.setCellValueFactory(new PropertyValueFactory<Files, String>("nguoiGui"));

        tableFileDen.setItems(fiList);
		
	}

}
