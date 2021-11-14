package application;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MovieHub implements Initializable {
	@FXML
	private TextField searchTextField, fName, lName, streetAdd, cityName, stateName, zip, cardInfo, expDate, csv;
	
	@FXML
	private Text proCardNum, proexpDate, prostreetAdd, numberofRentals, serviceFee, rentalFee, salesTax, totalFee;
	
	@FXML
	private Button logoutButton;
	
	@FXML
	BorderPane cartPane, homePane, searchPane, personalPane, paymentConfirmation, settingsPane;
	
	@FXML
	Pane navbarPane;
	
	@FXML
	private TableView<Movies> tableview2;
	
	int rentalCount = 0;
	
	long cardnumber;
	int zipCode;
	int expirationDate;
	int csvNum;
	String street;
	String city;
	String firstName;
	String lastName;
	String state;
	
	@FXML
	private TableColumn<Movies, String> titleColumn2;
	@FXML
	private TableColumn<Movies, String> genreColumn2;
	@FXML
	private TableColumn<Movies, String> actorColumn2;
	@FXML
	private TableColumn<Movies, String> directorColumn2;
	@FXML
	private TableColumn<Movies, String> ratingColumn2;
	@FXML
	private TableColumn<Movies, Double> reviewsColumn2;
	@FXML
	private TableColumn<Movies, Integer> yearColumn2;
	
	private ObservableList<Movies> movie = FXCollections.observableArrayList();
	
	Connection dbConnection;
	dbAPI api;
	
	
	public void logoutButtonAction(ActionEvent event) throws IOException {
		// Get the current stage after the button is pressed
		Stage stage = (Stage) logoutButton.getScene().getWindow();
				
		// this will close the current stage
		stage.close();
		
		// Opening Back up the login page 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = loader.load();
		Stage loginstage  = new Stage();
		loginstage.initStyle(StageStyle.UNDECORATED);	
		loginstage.setScene(new Scene(root));
		loginstage.show();
	}
	
	public void rentalButtonAction(ActionEvent event) throws IOException {
		rentalCount++;
		numberofRentals.setText(Integer.toString(rentalCount));
		serviceFee.setText("1.99");
		rentalFee.setText("4.99");
		
		
		double rentalAmount = rentalCount * 4.99;
		double salesAmount = rentalAmount * .066;
		
		BigDecimal salesAmountDecimal = new BigDecimal(salesAmount);
		salesAmountDecimal = salesAmountDecimal.setScale(2, RoundingMode.HALF_UP);
		salesAmount = salesAmountDecimal.doubleValue();
		salesTax.setText(Double.toString(salesAmount));
		
		double totalAmount = 1.99 + rentalAmount + salesAmount;
		
		BigDecimal totalAmountDecimal = new BigDecimal(totalAmount);
		totalAmountDecimal = totalAmountDecimal.setScale(3, RoundingMode.HALF_UP);
		totalAmount = totalAmountDecimal.doubleValue();
		
	
		totalFee.setText(Double.toString(totalAmount));
		

		// Opening Back up the login page 
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MovieRentalPopUp.fxml"));
		Parent root = loader.load();
		Stage popupstage  = new Stage();
		popupstage.initStyle(StageStyle.UNDECORATED);	
		popupstage.setScene(new Scene(root));
		popupstage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
             if (! isNowFocused) {
                 popupstage.hide();
             }
         });
		popupstage.show();
	}
	
	
	public void submitPaymentAction(ActionEvent event) {
		this.cardnumber = Long.parseLong(cardInfo.getText());
		this.zipCode =  Integer.parseInt(zip.getText());
		this.expirationDate = Integer.parseInt(expDate.getText());
		this.csvNum = Integer.parseInt(csv.getText());
		this.street = streetAdd.getText();
		this.city =  cityName.getText();
		this.firstName = fName.getText();
		this.lastName = lName.getText();
		this.state = stateName.getText();
		
		String cardStr = Long.toString(cardnumber);
		this.proCardNum.setText(cardStr);
		
		String expStr = Integer.toString(expirationDate);
		this.proexpDate.setText(expStr);
		
		this.prostreetAdd.setText(street + " " + city + ", " + state + " " + zipCode);
		
		paymentConfirmation.toFront();

	}
	
	public void settingButtonAction(ActionEvent event) {
		settingsPane.toFront();
	}
	
	public void portfolioPaneAction(ActionEvent event) throws IOException {
		personalPane.toFront();
	}
	
	public void cartPaneAction(ActionEvent event) {
		cartPane.toFront();
	}
	
	public void homePaneAction(ActionEvent event) {
		homePane.toFront();
	}
	
	public void searchPaneAction(ActionEvent event) {
		searchPane.toFront();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		try {
			dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieRentalSystem", "root","testpw");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 api = new dbAPI(dbConnection);
		 
		
		// set up the column tables
		titleColumn2.setCellValueFactory(new PropertyValueFactory<Movies, String>("Title"));
		genreColumn2.setCellValueFactory(new PropertyValueFactory<Movies, String>("Genre"));
		actorColumn2.setCellValueFactory(new PropertyValueFactory<Movies, String>("Actor"));
		directorColumn2.setCellValueFactory(new PropertyValueFactory<Movies, String>("Director"));
		ratingColumn2.setCellValueFactory(new PropertyValueFactory<Movies, String>("Rating"));
		reviewsColumn2.setCellValueFactory(new PropertyValueFactory<Movies, Double>("Reviews"));
		yearColumn2.setCellValueFactory(new PropertyValueFactory<Movies, Integer>("Year"));
		
		
		//load the data
		tableview2.setItems(getMovies());
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<Movies> filteredData = new FilteredList<>(movie, p -> true);
		
		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Movies -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare search criteria with the filtered text
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Movies.getTitle().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches Title.
				} else if (Movies.getActor().toLowerCase().contains(lowerCaseFilter)) {
					return true; // Filter matches Actor.
				} else if(Movies.getGenre().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if(Movies.getDirector().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if(Movies.getRating().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (Movies.getReviews().toString().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if(Movies.getYear().toString().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
				return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Movies> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		sortedData.comparatorProperty().bind(tableview2.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableview2.setItems(sortedData);
	}
	
	public ObservableList<Movies> getMovies(){
		//ObservableList<Movies> movie =  FXCollections.observableArrayList();
		movie.add(new Movies("Spectre", "Thriller", "Christopher Waltz","Sam Mendes","PG-13",6.8,2015));
		movie.add(new Movies("Pirates of the Caribbean: At World's End", "Adventure", "Johnny Depp","Gore Verbinski","PG-13",7.1,2007));
		movie.add(new Movies("The Dark Knight Rises", "Action", "Tom Hardy","Christopher Nolan","PG-13",8.5,2012));
		movie.add(new Movies("Star Wars: Episode VII - The Force Awakens", "Sci-Fi", "Doug Walker","Doug Walker","PG-13",7.1,2015));
		movie.add(new Movies("John Carter", "Sci-Fi", "Daryl Sabara","Andrew Stanton","PG-13",6.6,2012));
		movie.add(new Movies("Spider-Man 3", "Action", "J.K. Simmons","Sam Raimi","PG-13",7.1,2007));
		movie.add(new Movies("Tangled", "Family", "Brad Garrett","Nathan Greno","PG",7.8,2010));
		movie.add(new Movies("Avengers: Age of Ultron", "Action", "Chris Hemsworth","Joss Whedon","PG-13",7.5,2015));
		movie.add(new Movies("Harry Potter and the Half-Blood Prince", "Fantasy", "Alan Rickman","David Yates","PG",7.5,2009));
		movie.add(new Movies("Batman v Superman: Dawn of Justice", "Action", "Henry Cavill","Zack Snyder","PG-13",6.9,2016));
		movie.add(new Movies("Superman Returns", "Action", "Kevin Spacey","Bryan Singer","PG-13",6.1,2006));
		movie.add(new Movies("Quantum of Solace ", "Thriller", "Giancarlo Giannini","Marc Forster","PG-13",6.7,2008));
		movie.add(new Movies("Pirates of the Caribbean: Dead Man's Chest", "Adventure", "Johnny Depp","Gore Verbinski","PG-13",7.3,2006));
		movie.add(new Movies("The Lone Ranger", "Western", "Johnny Depp","Gore Verbinski","PG-13",6.5,2013));
		movie.add(new Movies("Man of Steel", "Action", "Henry Cavill","Zack Snyder","PG-13",7.2,2013));
		movie.add(new Movies("The Chronicles of Narnia: Prince Caspian ", "Fantasy", "Peter Dinklage","Andrew Adamson","PG",6.6,2008));
		movie.add(new Movies("The Avengers", "Action", "Chris Hemsworth","Joss Whedon","PG-13",8.1,2012));
		movie.add(new Movies("Pirates of the Caribbean: On Stranger Tides ", "Adventure", "Johnny Depp","Rob Marshall","PG-13",6.7,2011));
		movie.add(new Movies("The Hobbit: The Battle of the Five Armies", "Fantasy", "Aidan Turner","Peter Jackson","PG-13",7.5,2014));
		movie.add(new Movies("The Amazing Spider-Man ", "Action", "Emma Stone","Marc Webb","PG-13",7.0,2012));
		movie.add(new Movies("The Hobbit: The Desolation of Smaug", "Fantasy", "Aidan Turner","Peter Jackson","PG-13",7.9,2013));
		movie.add(new Movies("The Golden Compass", "Family", "Christopher Lee","Chris Weitz","PG-13",6.1,2007));
		movie.add(new Movies("King Kong ", "Action","Naomi Watts","Peter Jackson","PG-13",7.2,2005));
		movie.add(new Movies("Titanic", "Romance", "Leonardo DiCaprio","James Cameron","PG-13",7.7,1997));
		return movie;
	}
	
	
	
	
}
