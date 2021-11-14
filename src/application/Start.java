package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Start extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Creates an instance of the FXMLLoader Class and setting the location to Main.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			
			// This will read and parse the FXML file, if it contains a controller it will
			// inject any elements with fx:id attributes into FXML annotated fields inside the controller
			// This object is set as the root property
			Parent root = loader.load();
			
			
			// Creates a scene for a specific root node
			Scene scene = new Scene(root);
			
			scene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
			
			// this removes the upper window frame, makes it borderless 
			primaryStage.initStyle(StageStyle.UNDECORATED);	
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}



