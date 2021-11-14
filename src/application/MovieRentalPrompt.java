package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MovieRentalPrompt {
	
	@FXML
	private Button closeButton;
	
	public void closeButton (ActionEvent event) {
		Stage stage = (Stage) closeButton.getScene().getWindow();
		
		// this will close the current stage
		stage.close();
	}

}
