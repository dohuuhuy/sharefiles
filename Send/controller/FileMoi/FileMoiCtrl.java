package FileMoi;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	public JFXTextField txtTieuDe;

	@FXML
	public JFXButton btnChonTep;

	 public ArrayList<String> ListUser = new ArrayList<String>();
	 
	 ObservableList<String> listToWho = FXCollections.observableArrayList(ListUser);

	@FXML
	void actionClose(final ActionEvent event) {
		System.exit(0);
	}

	/*
	 * private double xOffset = 0; private double yOffset = 0;
	 */
	@FXML
	void actionReturn(final ActionEvent event) {
	/*
	 * try { ((Node) event.getSource()).getScene().getWindow().hide(); final Stage
	 * stage = new Stage(); final Parent root =
	 * FXMLLoader.load(getClass().getResource("../view/home.fxml"));
	 * stage.initStyle(StageStyle.TRANSPARENT); root.setOnMousePressed(ev -> {
	 * xOffset = ev.getSceneX(); yOffset = ev.getSceneY(); });
	 * 
	 * root.setOnMouseDragged(ev -> { stage.setX(ev.getScreenX() - xOffset);
	 * stage.setY(ev.getScreenY() - yOffset); });
	 * 
	 * 
	 * final Scene scene = new Scene(root); scene.setFill(Color.TRANSPARENT);
	 * stage.setScene(scene); stage.show(); } catch (final Exception e) {
	 * 
	 * } 
	 */
}
	@FXML
	void btnSave(final ActionEvent event) {

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
		// toWho.setText( toWho.getValue());
	}

	@Override
	public void initialize(final URL arg0, final ResourceBundle arg1) {
		toWho.setItems(listToWho);
	}

}
