package application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;



public class LoginController implements Initializable{
	
	@FXML
	private Button exitButton, loginButton;
	
	@FXML
	private Label loginMessageLabel;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private PasswordField passwordField;
	
	
	
	Connection dbConnection;
	dbAPI api;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieRentalSystem", "root","testpw");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 api = new dbAPI(dbConnection);
		 
	}

	
	public void loginButtonAction(ActionEvent event) {
		String email = usernameTextField.getText();
		String password = passwordField.getText();
		if(email.isEmpty() == false && password.isEmpty() == false) {
				Boolean validResult;
				try {
					validResult = validateLogin(email, password);
					if(validResult) {
						System.out.println("User Verified");
						// This will get the current stage after the button is pressed
						Stage signinStage = (Stage) loginButton.getScene().getWindow();
						// this will close the current stage
						signinStage.close();	
						// loading the movie hub scene
						MovieHubScene();
					}
					
				} catch (Exception e) {
					System.out.println("User not Verified");
					loginMessageLabel.setText("No account found. Create account");
					loginMessageLabel.setTextFill(Color.RED);
					e.printStackTrace();
				}

		} else
			loginMessageLabel.setText("Enter Username and Password");	
			loginMessageLabel.setTextFill(Color.BLACK);
	}
	
	public void MovieHubScene() throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieDashBoard.fxml"));
		Parent root = loader.load();
		Stage stage  = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);	
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public Boolean validateLogin(String email, String pass) throws Exception {
		Boolean result = api.IsUser(email, pass);
		return result;
	}
	
	public void accountCreationScene() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
		Parent root = loader.load();
		
		Stage stage  = new Stage();
		stage.initStyle(StageStyle.UNDECORATED);	
		stage.setScene(new Scene(root));
		stage.show();
	}

	
	public void exitButtonAction(ActionEvent event){
		// Get the current stage after the button is pressed
		Stage stage = (Stage) exitButton.getScene().getWindow();
		
		// this will close the current stage
		stage.close();
	}


}


