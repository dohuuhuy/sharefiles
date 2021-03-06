package Socket;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class controllerServer implements Initializable {

	public SocketServer server;
	public Thread serverThread;


	@FXML
	public TextField txtDiaChi;

	@FXML
	public Button btnChonTep;

	@FXML
	public Button btnBat;

	@FXML
	public TextArea txtNoiDung;

	@FXML
	public Button btnKhoiDong;

	@FXML
	public AnchorPane anchorPane;

	

	@FXML
	public void actionBat(ActionEvent event) {
	
		server = new SocketServer(this);
		btnBat.setDisable(true);
	}

	

	 @FXML
	    void Close(MouseEvent event) {
		 System.exit( 0);
	    }

	 public void RetryStart(int port) {
			if (server != null) {
				server.stop();
			}
			server = new SocketServer(this, port);
		}
	 
	  @FXML
	    void actionTat(ActionEvent event) {

	    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
