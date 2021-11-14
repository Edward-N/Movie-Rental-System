package application;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class RegisterController implements Initializable {

	
	@FXML
	private Button createAccountButton, closeButton;
	
	@FXML
	private TextField firstNameTextField, lastNameTextField, emailTextField;
	
	@FXML
	private PasswordField passwordField, confirmpassField;
	
	@FXML
	private Label passMatch,emailFormat, accountAdded;
	
	String firstName, lastName, email, password, verifyPassword;
	
	public static final Pattern VALIDEMAIL = 
	        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	
	
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
	
	public void createAccount() {
	firstName = firstNameTextField.getText();
	lastName = lastNameTextField.getText();
	email = emailTextField.getText();
	password  = passwordField.getText();
	verifyPassword = confirmpassField.getText();
	
	Boolean emailValidateResult = emailCheck(email);
	Boolean passwordValidateResult = passwordCheck(password, verifyPassword);
	
	if(emailValidateResult && passwordValidateResult) {
		try {
			Boolean res = api.CreateAccount(email, password, firstName, lastName);
			accountAdded.setText("Account Registered");
			accountAdded.setTextFill(Color.GREEN);
			System.out.println("Account has been created");
		} catch (Exception e) {
			System.out.println(" Attempted to create account but failed");
			e.printStackTrace();
		}
	}
	else {
		System.out.println("Verify email format and password");
	}
	
	}
	
	
	
	public Boolean passwordCheck(String password, String verifyPassword) {
		Boolean match = password.equals(verifyPassword);
		if(match) {
			passMatch.setText("Password Matches");
			passMatch.setTextFill(Color.GREEN);
		}else {
			passMatch.setText("Password does not match");
			passMatch.setTextFill(Color.RED);
		}
		return match;
		
	}

	public Boolean emailCheck(String email) {
		Boolean emailVerificaiton = validate(email);
		if(emailVerificaiton == false) {
			emailFormat.setText("Invalid email entry");
			emailFormat.setTextFill(Color.RED);
		}
		else {
			emailFormat.setText("Valid email entry");
			emailFormat.setTextFill(Color.GREEN);
		}
		return emailVerificaiton;
	}
	
	public static boolean validate(String emailStr) {
         Matcher matcher = VALIDEMAIL.matcher(emailStr);
         return matcher.find();
     }
	
	
	public void closeButton() {
		// Get the current stage after the button is pressed
		Stage stage = (Stage) closeButton.getScene().getWindow();
		
		// this will close the current stage
		stage.close();
		
		
	}
	
	
	
	
	
}
