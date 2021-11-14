package application;

import java.sql.Blob;


public class MovieModel {
	public String title;
	public Blob image;
	public String description;
	public String genre;
	public double price;
	public int review;
	public String actor;
	public String director;
	public String theme;
	public MovieModel(String Title, Blob Image, String Description, String Genre, double Price, int Review, String Actor,
			String Director, String Theme) {
		title = Title;
		image = Image;
		description = Description;
		genre = Genre;
		price = Price;
		review = Review;
		actor = Actor;
		director = Director;
		theme = Theme;		
	}
	
}