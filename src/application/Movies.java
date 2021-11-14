package application;


public class Movies {
	private String title, genre, actor, director, rating;
	private Double reviews;
	private Integer year;
	
	public Movies(String title, String genre, String actor, String director, String rating,
			Double reviews, Integer year) {
		this.title =  title;
		this.genre = genre;
		this.actor = actor;
		this.director = director;
		this.rating = rating;
		this.reviews = reviews;
		this.year = year;	

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Double getReviews() {
		return reviews;
	}

	public void setReviews(Double reviews) {
		this.reviews = reviews;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	
}
