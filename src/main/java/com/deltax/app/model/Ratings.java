package com.deltax.app.model;

public class Ratings {
	private String username;
	private String email;
	private String ratings;
	private String review;
	private String dateOfRelease;
	private String trackName;

	public Ratings(String username, String email, String ratings, String review, String dateOfRelease,
			String trackName) {
		super();
		this.username = username;
		this.email = email;
		this.ratings = ratings;
		this.review = review;
		this.dateOfRelease = dateOfRelease;
		this.trackName = trackName;
	}

	public Ratings() {
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getRatings() {
		return ratings;
	}

	public String getReview() {
		return review;
	}

	public String getDateOfRelease() {
		return dateOfRelease;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public void setDateOfRelease(String dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	@Override
	public String toString() {
		return "Ratings [username=" + username + ", email=" + email + ", ratings=" + ratings + ", review=" + review
				+ ", dateOfRelease=" + dateOfRelease + ", trackName=" + trackName + "]";
	}

}
