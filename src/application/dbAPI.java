package application;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Blob;
public class dbAPI  {
	public Connection db;
	public dbAPI(Connection connection) {
		db = connection;
	}
	
	public boolean CreateAccount(String email, String password, String fname, String lname) throws Exception{
		if(db == null)
			return false;
		String statement = "insert into acct (email, pswrd, fname, lname) values('" +
			email + "', '" + password + "', '" + fname + "', '" + lname + "');";
		
		Statement s = db.createStatement();
		
		int results = s.executeUpdate(statement);
		
		if(results == 1)
			return true;
		else 
			return false;
	}
	
	public boolean IsUser(String email, String password) throws Exception {
		
		String statement = "select pswrd from acct where email = '" + email + "';";
		Statement s = db.createStatement();
		ResultSet result = s.executeQuery(statement);
		result.next();
		String correctPW = result.getString(1);
		if(!correctPW.equals(password))
			return false;
		else 
			return true;
	}
	
	public boolean AddCreditCard(int number, String expr, int csv, String fname,
			String lname, String street, String town, String state, int zip) throws Exception {
		String statement = "insert into credit_card " +
			"(num, expr_date, csv, fname, lname, street, town, state, zip) " +
			"values(" + String.valueOf(number) + ", '" + expr + "', " +
			String.valueOf(csv) + ", '" + fname + "', '"  + lname + "', '" +
			street + "', '" + town + "', '" + state + "', " + String.valueOf(zip) +
			");";
		Statement s = db.createStatement();
		int result = s.executeUpdate(statement);
		
		if(result == 1)
			return true;
		else
			return false;
	}
	
	public MovieModel[] GetAllMoivies() throws Exception {
		String statement = "select * from movies";
		Statement s = db.createStatement();
		ResultSet result = s.executeQuery(statement);
		int length = 0;
		while(result.next()) {
			length++;
		}
		result.beforeFirst();
		MovieModel[] movies = new MovieModel[length];
		for(int i = 0; i <= length; i++) {
			result.next();
			String title = result.getString("title");
			Blob image = result.getBlob("image");
			String description = result.getString("description");
			String genre = result.getString("genre");
			double price = result.getDouble("price");
			int review = result.getInt("reviews");
			String actor = result.getString("actor");
			String director = result.getString("director");
			String theme = result.getString("theme");
			MovieModel movie = new MovieModel(title, image, description, genre, price, review, actor, director, theme);
			movies[i] = movie;
		}
		return movies;
		
	}
}