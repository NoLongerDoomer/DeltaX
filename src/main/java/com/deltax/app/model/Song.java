package com.deltax.app.model;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class Song {
	private String name;
	private String dateOfRelease;
	private MultipartFile image;
	private String[] artists;
	private String artist;
	private String imageUrl;
	private String ratings;

	public Song(String name, String dateOfRelease, MultipartFile image, String[] artists, String artist,
			String imageUrl, String ratings) {
		super();
		this.name = name;
		this.dateOfRelease = dateOfRelease;
		this.image = image;
		this.artists = artists;
		this.artist = artist;
		this.imageUrl = imageUrl;
		this.ratings = ratings;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public String getArtist() {
		return artist;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Song() {
	}

	public String getName() {
		return name;
	}

	public String getDateOfRelease() {
		return dateOfRelease;
	}

	public MultipartFile getImage() {
		return image;
	}

	public String[] getArtists() {
		return artists;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDateOfRelease(String dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public void setArtists(String[] artists) {
		this.artists = artists;
	}

	@Override
	public String toString() {
		return "Song [name=" + name + ", dateOfRelease=" + dateOfRelease + ", image=" + image + ", artists="
				+ Arrays.toString(artists) + ", artist=" + artist + ", imageUrl=" + imageUrl + ", ratings=" + ratings
				+ "]";
	}

}
