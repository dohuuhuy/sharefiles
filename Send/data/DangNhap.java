package TaiKhoan;
	
import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;


public class DangNhap extends Application {
	
	private double xOffset = 0;
    private double yOffset = 0;

    
    
	@Override
	
	   public void start(Stage stage) throws Exception {
		
		
	    Parent root = FXMLLoader.load(getClass().getResource("../view/DangNhap1.fxml"));
	    stage.initStyle(StageStyle.TRANSPARENT); 
	    
	    root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> { 
        	stage.setX(event.getScreenX() - xOffset);
        	stage.setY(event.getScreenY() - yOffset);
        });
        
 
        
	    Scene scene = new Scene(root);
      //  root.getStylesheets().add(DangNhap.class.getResource("").toExternalForm());
	    scene.setFill(Color.TRANSPARENT); 
	    stage.setScene(scene);
	
	    stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
