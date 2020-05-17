package TaiKhoan;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DangKy extends Application {
	
	private double xOffset = 0;
    private double yOffset = 0;

    
    
	@Override
	
	   public void start(Stage stage) throws Exception {
		
		
	    Parent root = FXMLLoader.load(getClass().getResource("../view/dangky.fxml"));
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
